import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class OWPMWindow extends JDialog implements OWConstants, OWFunc {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JButton reconnectButton;
	private JButton disconnectButton;
	private JButton addAlertToAPButton;
	private JToggleButton pinButton;
	private JLabel totalPortsLabel;
	private JLabel availalePortsLabel;
	private JLabel totalPortsNumLabel;
	private JLabel availablePortsNumLabel;
	
	private String tServerName;
	private String tServerIP;
	private String tServerPort;
	private String dnName;
	
	private Timer timer;
	private TimerTask task;
	private OWPortMonitor monitor;
	
	private OWAlertTemplate loadedTemplateForAP;
	private OWAlertTemplate loadedTemplateForTP;
	private JButton addAlertToTPButton;
	public OWPMWindow(String name, String ip, String port, String dn) {
		tServerIP = ip;
		tServerPort = port;
		dnName = dn;
		tServerName = name;
		
		buildDialog();
		
		startMonitoring();	
	}
	
	protected class OWPortMonitorEventHandler implements ActionListener, OWConstants {
		
		public void actionPerformed(ActionEvent portMonitorActionEvent) {
			if (portMonitorActionEvent.getSource() == reconnectButton) {
				startMonitoring();
				if (monitor != null && monitor.getMonitorState().equals(OPENED_PROTOCOL_STATE)){
					disconnectButton.setEnabled(true);
					reconnectButton.setEnabled(false);
				}
			} else if (portMonitorActionEvent.getSource() == disconnectButton){
				stopMonitoring();
				if (monitor == null || monitor.getMonitorState().equals(CLOSED_PROTOCOL_STATE)){
					disconnectButton.setEnabled(false);
					reconnectButton.setEnabled(true);
				}
			} else if (portMonitorActionEvent.getSource() == pinButton){
				switchAlwaysOnTop();
			}
		}
	}

	public void startMonitoring () {
		monitor = new OWPortMonitor (tServerIP, tServerPort, dnName);
		
		if (monitor.getMonitorState().equals(OPENED_PROTOCOL_STATE)) {
			setPortMonitorTitle (tServerName);
			
			timer = new Timer();
			task = new TimerTask (){
				@Override
				public void run() {
					monitor.update();
					totalPortsNumLabel.setText(monitor.getTotalPorts());
					availablePortsNumLabel.setText(monitor.getAvailablePorts());
					if (loadedTemplateForAP != null) {
						if ((new OWAlert(loadedTemplateForAP, availablePortsNumLabel.getText())).isAlerted()) {
							availablePortsNumLabel.setForeground(Color.red);
						} else availablePortsNumLabel.setForeground(Color.green);
					}
					if (loadedTemplateForTP != null) {
						if ((new OWAlert(loadedTemplateForTP, totalPortsNumLabel.getText())).isAlerted()) {
							totalPortsNumLabel.setForeground(Color.red);
						} else totalPortsNumLabel.setForeground(Color.green);
					}
				}
			};			
			timer.scheduleAtFixedRate(task, 0, PORT_MONITOR_FREQUENCY);
		}
	}
	
	public void stopMonitoring(){
		if (monitor != null) monitor.shutDownMonitor();
		monitor = null;
		if (task != null) task.cancel();
		task = null;
		timer = null;		
		cleanData();
	}
	
	public String getPortMonitorState() {
		if (monitor != null) return monitor.getMonitorState();
		else return CLOSED_PROTOCOL_STATE;
	}
	
	private void cleanData() {
		totalPortsNumLabel.setText(PORT_MONITOR_NOT_CONNECTED);
		availablePortsNumLabel.setText(PORT_MONITOR_NOT_CONNECTED);
		setPortMonitorTitle (tServerName + " - " + PORT_MONITOR_NOT_CONNECTED);
	}
	
	private void setPortMonitorTitle (String str) {
		this.setTitle(str);
	}

	
	private void switchAlwaysOnTop () {
		if (this.isAlwaysOnTop()) this.setAlwaysOnTop(false); 
		else this.setAlwaysOnTop(true);
	}
	

	public String getIPandPortOfPM () {
		return tServerIP + ":" + tServerPort;
	}
	
	protected class AddAlertToAPButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OWOpenAlertsWindow openAlertWin = new OWOpenAlertsWindow();
			openAlertWin.setVisible(true);
			
			loadedTemplateForAP = openAlertWin.getChoosenTemplate();
			openAlertWin.getChoosenTemplateNAme();
		}
	}
	
	protected class AddAlertToTPButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OWOpenAlertsWindow openAlertWin = new OWOpenAlertsWindow();
			openAlertWin.setVisible(true);
			
			loadedTemplateForTP = openAlertWin.getChoosenTemplate();
			openAlertWin.getChoosenTemplateNAme();
		}
	}

	private final void buildDialog(){
		OWPortMonitorEventHandler actionHandler = new OWPortMonitorEventHandler();
		
		setTitle(tServerName);
		setType(Type.POPUP);
		setResizable(false);
		setBounds(100, 100, 319, 140);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		reconnectButton = new JButton(PORT_MONITOR_RECONNECT_BUTTON_NAME);
		reconnectButton.setEnabled(false);
		reconnectButton.setFont(MAIN_WINDOW_FONT);
		reconnectButton.setBackground(Color.LIGHT_GRAY);
		reconnectButton.addActionListener(actionHandler);
		
		disconnectButton = new JButton(PORT_MONITOR_DISCONNECT_BUTTON_NAME);
		disconnectButton.setFont(MAIN_WINDOW_FONT);
		disconnectButton.setBackground(Color.LIGHT_GRAY);
		disconnectButton.addActionListener(actionHandler);
		
		pinButton = new JToggleButton();
		pinButton.setIcon(PORT_MONITOR_PIN_ICON_1);
		pinButton.setSize(32, 32);
		pinButton.setSelectedIcon(PORT_MONITOR_PIN_ICON_2);
		pinButton.setOpaque(false);
		pinButton.setContentAreaFilled(false);
		pinButton.setBorderPainted(false);
		pinButton.setFocusPainted(false);
		pinButton.addActionListener(actionHandler);
		
		addAlertToAPButton = new JButton();
		addAlertToAPButton.setIcon(PORT_MONITOR_ALERT_ICON);
		addAlertToAPButton.setOpaque(false);
		addAlertToAPButton.setContentAreaFilled(false);
		addAlertToAPButton.setBorderPainted(false);
		addAlertToAPButton.setFocusPainted(false);
		addAlertToAPButton.addActionListener(new AddAlertToAPButtonActionListener());
		
		addAlertToTPButton = new JButton();
		addAlertToTPButton.setIcon(PORT_MONITOR_ALERT_ICON);
		addAlertToTPButton.setOpaque(false);
		addAlertToTPButton.setFocusPainted(false);
		addAlertToTPButton.setContentAreaFilled(false);
		addAlertToTPButton.setBorderPainted(false);
		addAlertToTPButton.addActionListener(new AddAlertToTPButtonActionListener());
		
		totalPortsLabel = new JLabel(PORT_MONITOR_TOTAL_PORTS_LABEL_NAME);
		totalPortsLabel.setFont(MAIN_WINDOW_FONT_16);
		
		availalePortsLabel = new JLabel(PORT_MONITOR_AVAILABLE_PORTS_LABEL_NAME);
		availalePortsLabel.setFont(MAIN_WINDOW_FONT_16);
		
		totalPortsNumLabel = new JLabel(PORT_MONITOR_NOT_CONNECTED);
		totalPortsNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPortsNumLabel.setFont(MAIN_WINDOW_FONT_16);
		
		availablePortsNumLabel = new JLabel(PORT_MONITOR_NOT_CONNECTED);
		availablePortsNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		availablePortsNumLabel.setFont(MAIN_WINDOW_FONT_16);		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(reconnectButton, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(disconnectButton))
						.addComponent(totalPortsLabel)
						.addComponent(availalePortsLabel))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(availablePortsNumLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addComponent(totalPortsNumLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(addAlertToTPButton, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addComponent(addAlertToAPButton, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
						.addComponent(pinButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(addAlertToTPButton, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(totalPortsLabel)
							.addComponent(totalPortsNumLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(availalePortsLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addComponent(availablePortsNumLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addComponent(addAlertToAPButton, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(reconnectButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(disconnectButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(pinButton, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
					.addGap(1))
		);
		contentPanel.setLayout(gl_contentPanel);
		
		this.addWindowListener(new PMAdapter());
		
		setVisible(true);
	}
	
	private class PMAdapter extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			stopMonitoring();
			//OutboundWatchdog.deletePMfromPMlist((OWPMWindow)e.getSource());
			OutboundWatchdog.rerenderObjectTree();
		}
	}
}
