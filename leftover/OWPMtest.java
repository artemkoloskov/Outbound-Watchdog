
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.genesyslab.platform.commons.protocol.Endpoint;
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


public class OWPMtest extends JFrame implements OWConstants, OWFunc {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel totalPortsLabel;
	private JLabel availablePortsLabel;
	private JLabel totalPortsNumLabel;
	private JLabel availablePortsNumLabel;
	private JButton disconnectButton;
	private JButton reconnectButton;
	
	private TServerProtocol tServerProtocol = null;
	private String tServerName;
	private String tServerIp;
	private String tServerPort;
	private URI tServerUri;
	private String resourceDN;
	private Timer timer;
	private TimerTask task;
	private JToggleButton pinButton;

	/**
	 * Create the frame.
	 */
	public OWPMtest(String name, String ip, String port, String dn) {
		tServerName = name;
		tServerIp = ip;
		tServerPort = port;
		tServerUri = OWFunc.makeUri (tServerIp, tServerPort);
		resourceDN = dn;
		
		setType(Type.POPUP);
		setTitle(tServerName);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 125);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		OWPortMonitorEventHandler actionHandler = new OWPortMonitorEventHandler();
		
		disconnectButton = new JButton(PORT_MONITOR_DISCONNECT_BUTTON_NAME);
		disconnectButton.setBackground(Color.LIGHT_GRAY);
		disconnectButton.setFont(MAIN_WINDOW_FONT);
		disconnectButton.addActionListener(actionHandler);
		
		totalPortsNumLabel = new JLabel(PORT_MONITOR_NOT_CONNECTED);
		totalPortsNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPortsNumLabel.setFont(MAIN_WINDOW_FONT_18);
		
		availablePortsNumLabel = new JLabel(PORT_MONITOR_NOT_CONNECTED);
		availablePortsNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		availablePortsNumLabel.setFont(MAIN_WINDOW_FONT_18);
		
		reconnectButton = new JButton(PORT_MONITOR_RECONNECT_BUTTON_NAME);
		reconnectButton.setEnabled(false);
		reconnectButton.setFont(MAIN_WINDOW_FONT);
		reconnectButton.setBackground(Color.LIGHT_GRAY);
		reconnectButton.addActionListener(actionHandler);
		
		totalPortsLabel = new JLabel(PORT_MONITOR_TOTAL_PORTS_LABEL_NAME);
		totalPortsLabel.setFont(MAIN_WINDOW_FONT_18);
		
		availablePortsLabel = new JLabel(PORT_MONITOR_AVAILABLE_PORTS_LABEL_NAME);
		availablePortsLabel.setFont(MAIN_WINDOW_FONT_18);
		
		pinButton = new JToggleButton();
		pinButton.setIcon(PORT_MONITOR_PIN_ICON_1);
		pinButton.setSize(32, 32);
		pinButton.setSelectedIcon(PORT_MONITOR_PIN_ICON_2);
		pinButton.setOpaque(false);
		pinButton.setContentAreaFilled(false);
		pinButton.setBorderPainted(false);
		pinButton.setFocusPainted(false);
		pinButton.addActionListener(actionHandler);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(availablePortsLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(availablePortsNumLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(totalPortsLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
							.addComponent(totalPortsNumLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(reconnectButton, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(disconnectButton)
							.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
							.addComponent(pinButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(totalPortsLabel)
						.addComponent(totalPortsNumLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(availablePortsLabel)
						.addComponent(availablePortsNumLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(reconnectButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(disconnectButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pinButton, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		startMonitoring();
		contentPane.setLayout(gl_contentPane);
		
		validate();
		
	}
	
	protected class OWPortMonitorEventHandler implements ActionListener, OWConstants {
		
		public void actionPerformed(ActionEvent portMonitorActionEvent) {
			if (portMonitorActionEvent.getSource() == reconnectButton) {
				startMonitoring();
					disconnectButton.setEnabled(true);
					reconnectButton.setEnabled(false);
				
			} else if (portMonitorActionEvent.getSource() == disconnectButton){
				stopMonitoring();
					disconnectButton.setEnabled(false);
					reconnectButton.setEnabled(true);
				
			} else if (portMonitorActionEvent.getSource() == pinButton){
				setPMAlwaysOnTop();
			}
		}
	}
	
	private void startMonitoring () {
		try {
			setUpTServerProtocol(tServerUri);				
			openTServerConnection();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), PORT_MONITOR_ERROR_CONNECTING, PORT_MONITOR_ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (OWFunc.isProtocolOpened(tServerProtocol)){
			timer = new Timer();
			task = new TimerTask (){
				@Override
				public void run() {
					requestRegisterDN(resourceDN);
				}
			};
			timer.scheduleAtFixedRate(task, 0, PORT_MONITOR_FREQUENCY);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), PORT_MONITOR_ERROR_CONNECTING, PORT_MONITOR_ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setPortMonitorTitle (tServerName);
	}
	
	public void stopMonitoring(){
		timer.cancel();
		task = null;
		timer = null;
		
		try{
			closeTServerConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		totalPortsNumLabel.setText(PORT_MONITOR_NOT_CONNECTED);
		availablePortsNumLabel.setText(PORT_MONITOR_NOT_CONNECTED);
		setPortMonitorTitle (tServerName + " - " + PORT_MONITOR_NOT_CONNECTED);
		
	}
	
	public void setUpTServerProtocol (URI serverUri) {
		try {
			tServerProtocol = new TServerProtocol(new Endpoint(serverUri));
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), WRONG_URI_ENTERED, AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		tServerProtocol.setClientName(CLIENT_NAME);
		tServerProtocol.setMessageHandler(new PortMonitorMessageHandler());
	}
	
	public boolean openTServerConnection() {
		try {
			if(!OWFunc.isProtocolOpened(tServerProtocol)){
				tServerProtocol.open();
				return true;
			} else {
				return false;
			}
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean closeTServerConnection() {
		try {
			if(OWFunc.isProtocolOpened(tServerProtocol)){
				tServerProtocol.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void requestRegisterDN (String dnName){
		if (OWFunc.isProtocolOpened(tServerProtocol)||!(tServerProtocol == null)) {
			
			RequestRegisterAddress requestRegisterAddress = RequestRegisterAddress.create(dnName, RegisterMode.ModeShare, ControlMode.RegisterDefault, AddressType.DN);
			
			try {
				tServerProtocol.requestAsync(requestRegisterAddress, null, new PortMonitorCompletionHandler());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		
		} else {
			//TODO
		}
	}
	
	public Timer getTimer () {
		return timer;
	}
	
	protected class PortMonitorCompletionHandler  implements  CompletionHandler<Message, Object> {

		@Override
		public void completed(Message message, Object arg1) {
			PortMonitorMessageHandler messageHandler = new PortMonitorMessageHandler();
			messageHandler.onMessage((Message) message);			
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
					totalPortsNumLabel.setText(((EventRegistered)message).getExtensions().getPair("total-ports").getValue().toString());
					availablePortsNumLabel.setText(((EventRegistered)message).getExtensions().getPair("available-ports").getValue().toString());
					break;
				case EventResourceInfo.ID:
					totalPortsNumLabel.setText(((EventRegistered)message).getExtensions().getPair("total-ports").getValue().toString());
					availablePortsNumLabel.setText(((EventRegistered)message).getExtensions().getPair("available-ports").getValue().toString());
					break;
				case EventError.ID:
					//TODO
					break;
				//default:
					//updateInfo(message.toString());
			}
		}
	}
	
	public void setPortMonitorTitle (String str) {
		this.setTitle(str);
	}

	
	public void setPMAlwaysOnTop () {
		if (this.isAlwaysOnTop()) this.setAlwaysOnTop(false); 
		else this.setAlwaysOnTop(true);
	}
}
