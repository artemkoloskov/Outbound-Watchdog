import java.net.URI;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.genesyslab.platform.applicationblocks.com.ConfServiceFactory;
import com.genesyslab.platform.applicationblocks.com.IConfService;
import com.genesyslab.platform.applicationblocks.com.queries.CfgApplicationQuery;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.configuration.protocol.ConfServerProtocol;
import com.genesyslab.platform.configuration.protocol.types.CfgAppType;
import com.genesyslab.platform.configuration.protocol.types.CfgObjectState;


public interface OWConfFunc extends OWConstants, OWFunc{
	
	public static ConfServerProtocol setConfUpProtocol(URI serverUri, String app, String user, char[] password) {
		ConfServerProtocol protocol;
		
		String proccessedPass = "";
		for (int i = 0; i < password.length; i++){
			proccessedPass += password[i];
		}
		Arrays.fill(password, '0');
		
		try {
			protocol = new ConfServerProtocol(new Endpoint(serverUri));
			protocol.setClientApplicationType(CfgAppType.CFGSCE.asInteger());
			protocol.setClientName(app);
			protocol.setUserName(user);
			protocol.setUserPassword(proccessedPass);
		} catch (Exception e){
			e.printStackTrace();
			System.out.print("Error setting up config protocol" + "\r\n");
			protocol = null;
		}
		
		return protocol;
	}
	
	public static IConfService setUpConfServise(ConfServerProtocol protocol) {
		return ConfServiceFactory.createConfService(protocol);
	}
	
	public static boolean openConfConnection(ConfServerProtocol protocol) {
		if (!OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.open();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error openning config protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CONNECTING + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
	
	public static boolean closeConfConnection(ConfServerProtocol protocol) {
		if (OWFunc.isProtocolOpened(protocol)){
			try {
				protocol.close();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error closing config protocol" + "\r\n");
				JOptionPane.showMessageDialog(new JFrame(), ERROR_CLOSING_CONNECTION + protocol.getEndpoint().getHost() + ":" + protocol.getEndpoint().getPort(), AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
	
	public static CfgApplicationQuery setUpAppQuery (CfgAppType appType){
		CfgApplicationQuery query;
		query = new CfgApplicationQuery ();
		query.setState (CfgObjectState.CFGEnabled);
		query.setAppType(appType);
		
		return query;
	}
}
