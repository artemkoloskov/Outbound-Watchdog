import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.voice.protocol.TServerProtocol;


public interface OWTServerFunc extends OWConstants, OWFunc {
	public static TServerProtocol setUpTServerProtocol (URI serverUri) {
		TServerProtocol protocol;
		try {
			protocol = new TServerProtocol(new Endpoint(serverUri));
			protocol.setClientName(CLIENT_NAME);
		} catch (Exception e){
			e.printStackTrace();
			System.out.print("Error setting up tserver protocol" + "\r\n");
			protocol = null;
		}
		return protocol;
	}
	
	public static boolean openTServerProtocol (TServerProtocol protocol){
		if (!OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.open();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error openning tserver protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CONNECTING + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
	
	public static boolean closeTServerProtocol (TServerProtocol protocol){
		if (OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.close();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error closing tserver protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CLOSING_CONNECTION + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
}
