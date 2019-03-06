import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.outbound.protocol.OutboundServerProtocol;


public interface OWOutboundFunc extends OWConstants, OWFunc{
	
	public static OutboundServerProtocol setOutboundUpProtocol(URI serverUri) {
		OutboundServerProtocol protocol;
		
		try {
			protocol = new OutboundServerProtocol(new Endpoint (serverUri));
			protocol.setClientName(CLIENT_NAME);
		} catch (Exception e){
			e.printStackTrace();
			protocol = null;
		}
		
		return protocol;
		
	}
	
	public static boolean openOutboundConnection(OutboundServerProtocol protocol) {
		if (!OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.open();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error openning outbound protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CONNECTING + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
	
	public static boolean closeOutboundConnection(OutboundServerProtocol protocol) {
		if (OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.close();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error closing outbound protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CLOSING_CONNECTION + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}	
}
