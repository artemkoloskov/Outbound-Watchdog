import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.genesyslab.platform.applicationblocks.com.IConfService;
import com.genesyslab.platform.applicationblocks.com.objects.CfgApplication;
import com.genesyslab.platform.applicationblocks.com.objects.CfgCampaign;
import com.genesyslab.platform.applicationblocks.com.objects.CfgDN;
import com.genesyslab.platform.applicationblocks.com.queries.CfgApplicationQuery;
import com.genesyslab.platform.applicationblocks.com.queries.CfgCampaignQuery;
import com.genesyslab.platform.applicationblocks.com.queries.CfgDNQuery;
import com.genesyslab.platform.commons.collections.KeyValueCollection;
import com.genesyslab.platform.commons.collections.KeyValuePair;
import com.genesyslab.platform.commons.protocol.ProtocolException;
import com.genesyslab.platform.configuration.protocol.ConfServerProtocol;
import com.genesyslab.platform.configuration.protocol.types.CfgAppType;
import com.genesyslab.platform.configuration.protocol.types.CfgDNType;
import com.genesyslab.platform.configuration.protocol.types.CfgObjectState;
import com.genesyslab.platform.outbound.protocol.OutboundServerProtocol;
import com.genesyslab.platform.outbound.protocol.outboundserver.events.EventCampaignStatus;
import com.genesyslab.platform.outbound.protocol.outboundserver.requests.RequestGetCampaignStatus;


public class OutboundWatchdog implements OWConstants, OWFunc{
	
	private static OWMainWindow mainWindow;
	private static OWAuthWindow authWindow;
	private static URI hostUri;
	private static ConfServerProtocol confProtocol = null;
	private static IConfService confService;
	private static CfgApplicationQuery cfgAppQuery;
	private static Collection<CfgApplication> cfgAppCollection;
	private static Collection<CfgDN> dnCollection;
	private static String selectedServerHostIP;
	private static String selectedServerPort;
	private static HashMap <String,OWPMWindow> openPortMonitors = new HashMap<String,OWPMWindow>();

	/*======================================
	 * MAIN
	 =======================================*/
	
	public static void main(String[] args)
	{ 
		authWindow = new OWAuthWindow();
		
		OWAuthWindowEventHandler authWindowEventHandler = new OWAuthWindowEventHandler();
		
		authWindow.okButton.addActionListener(authWindowEventHandler);
		authWindow.cancelButton.addActionListener(authWindowEventHandler);
		
		authWindow.setVisible(true);
	}
	
	/*======================================
	 * AUTH WINDOW HANDLER
	 =======================================*/
	
	protected static class OWAuthWindowEventHandler implements ActionListener, OWConstants {

		public void actionPerformed(ActionEvent authWinEvent) 
		{
			mainWindowRoutine();
			if (authWinEvent.getSource() == authWindow.okButton) {
				hostUri = OWFunc.makeUri(
						authWindow.hostNameTextField.getText(), 
						authWindow.portTextField.getText());
				
				confProtocol = OWConfFunc.setConfUpProtocol(
						hostUri, 
						authWindow.appNameTextField.getText(), 
						authWindow.userNameTextField.getText(), 
						authWindow.userPassPasswordField.getPassword());
				
				if (OWFunc.isProtocolOpened(confProtocol)) {
					mainWindowRoutine();
					authWindow.dispose();
				} else if (OWConfFunc.openConfConnection(confProtocol)) {
					mainWindowRoutine();
					authWindow.dispose();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), 
							AUTH_WINDOW_ALERT_WRONG_INFO_ENTERED, 
							AUTH_WINDOW_ERROR, 
							JOptionPane.ERROR_MESSAGE);
					return;
				}								
			} else if (authWinEvent.getSource() == authWindow.cancelButton) System.exit(0);
		}
	}
	
	/*======================================
	 * MAIN WINDOW HANDLING AND SETUP
	 =======================================*/
	
	public static void mainWindowRoutine () 
	{
		mainWindow = new OWMainWindow();
		mainWindow.updateStatus(CONNECTED_TO_CONF_SERVER_STATUS + HOST_PATTERN + confProtocol.getEndpoint().getHost() + PORT_PATTERN + confProtocol.getEndpoint().getPort());
		
		OWMainWindowEventHandler mainWindowEventHandler = new OWMainWindowEventHandler();
		mainWindow.connectButton.addActionListener(mainWindowEventHandler);
		mainWindow.closeConnectionButton.addActionListener(mainWindowEventHandler);
		mainWindow.getStateButton.addActionListener(mainWindowEventHandler);
		mainWindow.objectTree.addTreeSelectionListener(mainWindowEventHandler);
		mainWindow.exitMenuItem.addActionListener(mainWindowEventHandler);
		
		OWConfFunc.closeConfConnection(confProtocol);
		confService = OWConfFunc.setUpConfServise(confProtocol);
		OWConfFunc.openConfConnection(confProtocol);
		
		fillObjectTree();
		
		mainWindow.setVisible(true);
	}
	
	protected static class OWMainWindowEventHandler implements ActionListener, OWConstants, TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)	mainWindow.objectTree.getLastSelectedPathComponent();
		    if (node == null) return;
		    if (node.isLeaf()) {
				selectedServerHostIP = OWFunc.extractHostIP(node.toString());
				selectedServerPort = OWFunc.extractPort(node.toString());
				
				mainWindow.updateStatus(HOST_PATTERN.substring(3) + selectedServerHostIP + PORT_PATTERN + selectedServerPort + "]");
			}
		}
		
		public void actionPerformed(ActionEvent mainWindowActionEvent) {
			if (mainWindowActionEvent.getSource() == mainWindow.connectButton) {
				
			    if (mainWindow.objectTree.getLastSelectedPathComponent() == null) {
			    	mainWindow.updateStatus(SERVER_IS_NOT_SELECTED_STATUS);
			    	return;			
			    }
					
				String serverName = OWFunc.extractServerName(mainWindow.objectTree.getLastSelectedPathComponent().toString());
				
				if (!openPortMonitors.containsKey(selectedServerHostIP + ":" + selectedServerPort)) {
					if (checkForTrunkGroup(getSwitchDBID(selectedServerHostIP, selectedServerPort))) {
						//OWPMWindow window = new OWPMWindow(serverName, selectedServerHostIP, selectedServerPort, getResourceDN ());
						openPortMonitors.put(selectedServerHostIP + ":" + selectedServerPort, new OWPMWindow(serverName, selectedServerHostIP, selectedServerPort, getResourceDN ()));
					} else mainWindow.changeInfo ("no trunk Group");
				} else mainWindow.updateStatus(CONNECTION_ALREADY_OPENED_STATUS);
				
				
				rerenderObjectTree();
			} else if (mainWindowActionEvent.getSource() == mainWindow.closeConnectionButton) {
				int numOfPMs = openPortMonitors.size();
				
				for (int i = 0; i < numOfPMs; i++) {
					Iterator <String> itr = openPortMonitors.keySet().iterator();
					String key = itr.next();
					openPortMonitors.get(key).stopMonitoring();
					openPortMonitors.get(key).dispose();
					openPortMonitors.remove(key);
				}
				
				mainWindow.objectTree.setCellRenderer(new DefaultTreeCellRenderer());
			}
			else if (mainWindowActionEvent.getSource() == mainWindow.getStateButton) {
				//TODO
				
				loadMCPCollection();
				/*CfgCampaignQuery query = new CfgCampaignQuery();
				query.setState(CfgObjectState.CFGEnabled);
				query.setName("OBN_IP_HCFB_Primiritel_Campaign");
				Collection <CfgCampaign> col = null;
				
				try {
					col = confService.retrieveMultipleObjects (CfgCampaign.class, query);
					Iterator<CfgCampaign> itr = col.iterator();
					
					for (int i = 0; i < col.size(); i++) {
						mainWindow.updateInfo(itr.next().toString());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				OutboundServerProtocol ass = null;
				ass = OWOutboundFunc.setOutboundUpProtocol(OWFunc.makeUri("obncc-out-01", "5420"));
				OWOutboundFunc.openOutboundConnection(ass);
				mainWindow.updateInfo(ass.getState().toString());
				
				Iterator<CfgCampaign> itr2 = col.iterator();
				RequestGetCampaignStatus req = RequestGetCampaignStatus.create();
				CfgCampaign assss = itr2.next();
				
				req.setCampaignId(assss.getDBID());
				req.setGroupId(assss.getCampaignGroups().iterator().next().getGroupDBID());
				EventCampaignStatus res = null;
				
				try {
					res = (EventCampaignStatus) ass.request(req);
				} catch (ProtocolException | IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainWindow.updateInfo(res.toString());
				
				OWOutboundFunc.closeOutboundConnection(ass);*/
			} else if (mainWindowActionEvent.getSource() == mainWindow.exitMenuItem) {
				System.exit(0);
			}
		}
	}
	
	/*======================================
	 * FUNCTIONS
	 =======================================*/
	public static void deletePMfromPMlist (OWPMWindow pm) {
		openPortMonitors.remove(pm.getIPandPortOfPM());
	}
	
	public static void rerenderObjectTree () {
		mainWindow.objectTree.setCellRenderer(new OWTreeRenderer(openPortMonitors));
	}
		
	private static String getSwitchDBID(String host, String port){
		cfgAppQuery = OWConfFunc.setUpAppQuery (CfgAppType.CFGTServer);
		
		try {
			cfgAppCollection = confService.retrieveMultipleObjects (CfgApplication.class, cfgAppQuery);
			Iterator<CfgApplication> itr = cfgAppCollection.iterator();
			
			for (int i = 0; i < cfgAppCollection.size(); i++) {
				CfgApplication app = itr.next();
				
				if (app.getServerInfo().getHost().getIPaddress().equals(host) && app.getServerInfo().getPort().equals(port)) {
					return ((KeyValuePair)((KeyValueCollection)((KeyValuePair)((KeyValueCollection)((KeyValuePair) app.getFlexibleProperties().iterator().next()).getValue()).iterator().next()).getValue()).iterator().next()).getStringKey();
				}
			}
			
			return FAILED_GETTING_SWITCH_ID;
		} catch (Exception e1) {
			e1.printStackTrace();
			return FAILED_GETTING_SWITCH_ID;
		}
	}
		
	private static boolean checkForTrunkGroup (String switchID){
		CfgDNQuery dnQuery = new CfgDNQuery();
		dnQuery.setDnType(CfgDNType.CFGTrunkGroup);
		dnQuery.setSwitchDbid(Integer.parseInt(switchID));
		dnQuery.setState(CfgObjectState.CFGEnabled);
		
		dnCollection = null;
		
		try {
			dnCollection = confService.retrieveMultipleObjects (CfgDN.class, dnQuery);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		
		if (dnCollection == null) return false;
		else return true;
	}
		
	private static String getResourceDN (){
		cfgAppQuery = OWConfFunc.setUpAppQuery(CfgAppType.CFGGVPResourceMgr);
		
		String result = "";
		
		try {
			cfgAppCollection = confService.retrieveMultipleObjects(CfgApplication.class, cfgAppQuery);
			Iterator<CfgApplication> itrApp = cfgAppCollection.iterator();
			Iterator<CfgDN> itrDN = dnCollection.iterator();
			
			for (int i = 0; i < dnCollection.size(); i++){
				CfgDN dn = itrDN.next();
				KeyValueCollection kvcollection = (KeyValueCollection)((KeyValuePair)dn.getUserProperties().iterator().next()).getValue();
				for (int j = 0; j < cfgAppCollection.size(); j++){
					CfgApplication app = itrApp.next();
					
					String sipPort = OWFunc.extractSipPort(((KeyValueCollection) app.getOptions().getPair("proxy").getValue()).getPair("sip.transport.0").getValue().toString());
					KeyValuePair pairHostNameLow = new KeyValuePair("contact", "sip:" + app.getServerInfo().getHost().getName().toLowerCase() + ":" + sipPort);
					KeyValuePair pairHostNameUpper = new KeyValuePair("contact", "sip:" + app.getServerInfo().getHost().getName().toUpperCase() + ":" + sipPort);
					KeyValuePair pairHostIP = new KeyValuePair("contact", "sip:" + app.getServerInfo().getHost().getIPaddress() + ":" + sipPort);
					
					if (kvcollection.contains(pairHostNameLow)||kvcollection.contains(pairHostNameUpper)||kvcollection.contains(pairHostIP)){
						result = dn.getNumber().toString();
						return result;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			result = "No DN pointing RM";
			return result;
		}
		return result;
	}
		
	private static void fillObjectTree (){
		cfgAppQuery = OWConfFunc.setUpAppQuery (CfgAppType.CFGTServer);
		
		try {
			cfgAppCollection = confService.retrieveMultipleObjects (CfgApplication.class, cfgAppQuery);
			Iterator<CfgApplication> itr = cfgAppCollection.iterator();
			
			for (int i = 0; i < cfgAppCollection.size(); i++) {
				CfgApplication app = itr.next();
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(app.getName() + HOST_PATTERN + app.getServerInfo().getHost().getIPaddress() + PORT_PATTERN + app.getServerInfo().getPort() + "]");
				mainWindow.objectTreeTop.add(new DefaultMutableTreeNode(category));
				mainWindow.objectTreeModel.reload(mainWindow.objectTreeTop);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			mainWindow.updateStatus(ERROR_WHILE_BUILDING_OBJECT_VIEW_STATUS);
		}
	}
	
	private static void loadMCPCollection (){
		cfgAppQuery = OWConfFunc.setUpAppQuery (CfgAppType.CFGGVPMCP);
		
		try {
			cfgAppCollection = confService.retrieveMultipleObjects (CfgApplication.class, cfgAppQuery);
			mainWindow.updateInfo(cfgAppCollection.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}	