import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class OWMainWindow extends JFrame implements OWConstants{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JButton connectButton;
	public JButton closeConnectionButton;
	public JPanel campaignInfoPanel;
	private JLabel statusLabel;
	public JButton getStateButton;
	private JTextArea campaignTextArea;
	public JPanel objectViewPanel;
	public JTree objectTree;
	public DefaultMutableTreeNode objectTreeTop;
	public DefaultTreeModel objectTreeModel;
	public JScrollPane scrollPane_1;
	public JSeparator separator;
	public JMenuBar menuBar;
	public JMenu fileMenu;
	public JMenuItem exitMenuItem;
	private JMenu alertMenu;
	private JMenuItem newPortAlertMenuItem;
	
	public OWMainWindow() {
		setFont(MAIN_WINDOW_FONT);
		setTitle(MAIN_WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 784, 640);
		setLocationRelativeTo(null); 
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		menuBar.setBackground(UIManager.getColor("Panel.background"));
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu(MAIN_WINDOW_FILE_MENU_NAME);
		fileMenu.setBackground(UIManager.getColor("Panel.background"));
		fileMenu.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		menuBar.add(fileMenu);
		
		exitMenuItem = new JMenuItem(MAIN_WINDOW_EXIT_MENU_ITEM_NAME);
		exitMenuItem.setBackground(UIManager.getColor("PasswordField.background"));
		exitMenuItem.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		
		fileMenu.add(exitMenuItem);
		
		alertMenu = new JMenu("Alerts");
		alertMenu.setBackground(UIManager.getColor("Panel.background"));
		alertMenu.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		menuBar.add(alertMenu);
		
		newPortAlertMenuItem = new JMenuItem("New alert...");
		alertMenu.add(newPortAlertMenuItem);
		newPortAlertMenuItem.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		newPortAlertMenuItem.setBackground(UIManager.getColor("Panel.background"));
		newPortAlertMenuItem.addActionListener(new NewAlertMenuItemActionListener());
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		campaignInfoPanel = new JPanel();
		campaignInfoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), MAIN_WINDOW_CAMPAIGN_INFO_PANEL_NAME, TitledBorder.LEADING, TitledBorder.TOP, MAIN_WINDOW_FONT, null));
		
		objectViewPanel = new JPanel();
		objectViewPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), MAIN_WINDOW_OBJECT_VIEW_PANEL_NAME, TitledBorder.LEADING, TitledBorder.TOP, MAIN_WINDOW_FONT, null));
		
		statusLabel = new JLabel("...");
		statusLabel.setFont(MAIN_WINDOW_FONT);
		
		separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(objectViewPanel, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(campaignInfoPanel, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
					.addGap(6))
				.addComponent(statusLabel, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(objectViewPanel, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
						.addComponent(campaignInfoPanel, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statusLabel))
		);
		
		objectTreeTop = new DefaultMutableTreeNode(MAIN_WINDOW_OBJECT_TREE_TOP_NODE_NAME);
		objectTree = new JTree(objectTreeTop);
		objectTree.setRootVisible(false);
		objectTree.setFont(MAIN_WINDOW_FONT);		
		objectTreeModel = (DefaultTreeModel) objectTree.getModel();
		objectTreeTop = (DefaultMutableTreeNode) objectTreeModel.getRoot();
		
		objectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		
		scrollPane_1 = new JScrollPane(objectTree);
		
		connectButton = new JButton(MAIN_WINDOW_CONNECT_BUTTON_NAME);
		connectButton.setBackground(Color.LIGHT_GRAY);
		connectButton.setFont(MAIN_WINDOW_FONT);
		
		closeConnectionButton = new JButton(MAIN_WINDOW_CLOSE_CONNECTION_BUTTON_NAME);
		closeConnectionButton.setBackground(Color.LIGHT_GRAY);
		closeConnectionButton.setFont(MAIN_WINDOW_FONT);

		GroupLayout gl_objectViewPanel = new GroupLayout(objectViewPanel);
		gl_objectViewPanel.setHorizontalGroup(
			gl_objectViewPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_objectViewPanel.createSequentialGroup()
					.addComponent(connectButton)
					.addPreferredGap(ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
					.addComponent(closeConnectionButton))
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
		);
		gl_objectViewPanel.setVerticalGroup(
			gl_objectViewPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_objectViewPanel.createSequentialGroup()
					.addGroup(gl_objectViewPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(connectButton)
						.addComponent(closeConnectionButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
		);
		objectViewPanel.setLayout(gl_objectViewPanel);
		
		getStateButton = new JButton(MAIN_WINDOW_GET_STATE_BUTTON_NAME);
		getStateButton.setBackground(Color.LIGHT_GRAY);
		getStateButton.setFont(MAIN_WINDOW_FONT);
		
		campaignTextArea = new JTextArea();
		campaignTextArea.setFont(MAIN_WINDOW_FONT);
		campaignTextArea.setLineWrap(true);
		campaignTextArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(campaignTextArea);
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		GroupLayout gl_campaignInfoPanel = new GroupLayout(campaignInfoPanel);
		gl_campaignInfoPanel.setHorizontalGroup(
			gl_campaignInfoPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
				.addGroup(gl_campaignInfoPanel.createSequentialGroup()
					.addComponent(getStateButton)
					.addGap(327))
		);
		gl_campaignInfoPanel.setVerticalGroup(
			gl_campaignInfoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_campaignInfoPanel.createSequentialGroup()
					.addComponent(getStateButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
		);
		campaignInfoPanel.setLayout(gl_campaignInfoPanel);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void updateStatus (String statusMessage){
		statusLabel.setText(statusMessage);
	}
	
	public void updateInfo (String newInfo){
		campaignTextArea.setText(campaignTextArea.getText() + "\r\n" + newInfo + "\r\n");
	}
	
	public void changeInfo (String newInfo){
		campaignTextArea.setText(newInfo + "\r\n");
	}
	
	protected class NewAlertMenuItemActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			(new OWNewAlertTemplateWindow()).setVisible(true);
		}
	}
}
