import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.configuration.protocol.types.CfgAppType;
import com.genesyslab.platform.management.protocol.MessageServerProtocol;


public interface OWMessageFunc extends OWConstants, OWFunc {
	
	public static MessageServerProtocol setUpMessageProtocol(URI serverUri) {
		MessageServerProtocol protocol;
		
		try {
			protocol = new MessageServerProtocol(new Endpoint (serverUri));
			protocol.setClientType(CfgAppType.CFGGenericServer.ordinal());
			protocol.setClientName(CLIENT_NAME);
			protocol.setClientHost("vld-ctr-050.homecredit.ru");
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), WRONG_URI_ENTERED, AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
			protocol = null;
		}
		
		return protocol;		
	}
	
	public static boolean openMessageConnection(MessageServerProtocol protocol) {
		if (!OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.open();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error openning message protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CONNECTING + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
	
	public static boolean closeMessageConnection(MessageServerProtocol protocol) {
		if (OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.close();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error closing message protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CLOSING_CONNECTION + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
}
