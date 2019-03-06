import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.commons.protocol.MessageHandler;
import com.genesyslab.platform.commons.threading.CompletionHandler;
import com.genesyslab.platform.voice.protocol.TServerProtocol;
import com.genesyslab.platform.voice.protocol.tserver.AddressType;
import com.genesyslab.platform.voice.protocol.tserver.ControlMode;
import com.genesyslab.platform.voice.protocol.tserver.RegisterMode;
import com.genesyslab.platform.voice.protocol.tserver.events.EventError;
import com.genesyslab.platform.voice.protocol.tserver.events.EventRegistered;
import com.genesyslab.platform.voice.protocol.tserver.events.EventResourceInfo;
import com.genesyslab.platform.voice.protocol.tserver.requests.dn.RequestRegisterAddress;


public class OWPortMonitor implements OWConstants, OWFunc {
	private String totalPorts = "";
	private String availablePorts = "";
	private String dnName;
	private TServerProtocol tServerProtocol;
	
	public OWPortMonitor (String ip, String port, String dn) {		
		dnName = dn;
		tServerProtocol = OWTServerFunc.setUpTServerProtocol(OWFunc.makeUri(ip, port));
		tServerProtocol.setMessageHandler(new PortMonitorMessageHandler());
		OWTServerFunc.openTServerProtocol(tServerProtocol);
	}
	
	public void turnOnMonitor () {
		OWTServerFunc.openTServerProtocol(tServerProtocol);
	}
	
	public void shutDownMonitor () {
		OWTServerFunc.closeTServerProtocol(tServerProtocol);
	}
	
	public void update() {
		requestRegisterDN (tServerProtocol, dnName);
	}
	
	private void requestRegisterDN (TServerProtocol protocol, String dnName){
		if (OWFunc.isProtocolOpened(protocol)||protocol != null) {
			
			RequestRegisterAddress requestRegisterAddress = RequestRegisterAddress.create(dnName, RegisterMode.ModeShare, ControlMode.RegisterDefault, AddressType.DN);
			
			try {
				protocol.requestAsync(requestRegisterAddress, null, new PortMonitorCompletionHandler());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
		} else {
			//TODO
		}
	}
	
	protected class PortMonitorCompletionHandler  implements  CompletionHandler<Message, Object> {

		@Override
		public void completed(Message message, Object arg1) {
			PortMonitorMessageHandler messageHandler = new PortMonitorMessageHandler();
			messageHandler.onMessage(message);			
		}

		@Override
		public void failed(Throwable arg0, Object arg1) {
			//TODO
		}

	}
	
	protected class PortMonitorMessageHandler implements MessageHandler {
		
		@Override
		public void onMessage(Message message) {
			switch (message.messageId()) {
				case EventRegistered.ID:
					setTotalPorts(((EventRegistered)message).getExtensions().getPair("total-ports").getValue().toString());
					setAvailablePorts(((EventRegistered)message).getExtensions().getPair("available-ports").getValue().toString());
					break;
				case EventResourceInfo.ID:
					setTotalPorts(((EventRegistered)message).getExtensions().getPair("total-ports").getValue().toString());
					setAvailablePorts(((EventRegistered)message).getExtensions().getPair("available-ports").getValue().toString());
					break;
				case EventError.ID:
					//TODO
					break;
				//default:
					//updateInfo(message.toString());
			}
		}
	}
	
	private void setTotalPorts (String str) {
		totalPorts = str;
	}
	
	public String getTotalPorts () {
		return totalPorts;
	}
	
	private void setAvailablePorts (String str) {
		availablePorts = str;
	}
	
	public String getAvailablePorts () {
		return availablePorts;
	}
	
	public String getMonitorState () {
		return tServerProtocol.getState().toString();
	}
}
