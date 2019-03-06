import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


public class OWAuthWindow extends JFrame implements OWConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JLabel userNameLabel;
	public JTextField userNameTextField;
	public JLabel userPasswordLabel;
	public JPasswordField userPassPasswordField;
	public JSeparator separator;
	public JLabel appNameLabel;
	public JTextField appNameTextField;
	public JLabel hostNameLabel;
	public JTextField hostNameTextField;
	public JLabel portLabel;
	public JTextField portTextField;
	public JSeparator separator_1;
	public JButton okButton;
	public JButton cancelButton;

	/**
	 * Create the frame.
	 */
	public OWAuthWindow() {
		setFont(MAIN_WINDOW_FONT);
		setTitle(MAIN_WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 270, 273);
		setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		userNameLabel = new JLabel(AUTH_WINDOW_USER_NAME_LABEL_NAME);
		userNameLabel.setFont(MAIN_WINDOW_FONT);
		
		userNameTextField = new JTextField(AUTH_WINDOW_USER_NAME_DEFAULT);
		userNameTextField.setFont(MAIN_WINDOW_FONT);
		userNameTextField.setColumns(10);
		
		userPasswordLabel = new JLabel(AUTH_WINDOW_USER_PASSWORD_LABEL_NAME);
		userPasswordLabel.setFont(MAIN_WINDOW_FONT);
		
		userPassPasswordField = new JPasswordField(AUTH_WINDOW_USER_PASS_DEFAULT);
		userPassPasswordField.setFont(MAIN_WINDOW_FONT);
		
		appNameLabel = new JLabel(AUTH_WINDOW_APP_NAME_LABEL_NAME);
		appNameLabel.setFont(MAIN_WINDOW_FONT);
		
		appNameTextField = new JTextField(AUTH_WINDOW_DEFAULT_APP_NAME);
		appNameTextField.setFont(MAIN_WINDOW_FONT);
		appNameTextField.setColumns(10);
		
		hostNameLabel = new JLabel(AUTH_WINDOW_HOST_LABEL_NAME);
		hostNameLabel.setFont(MAIN_WINDOW_FONT);
		
		hostNameTextField = new JTextField(AUTH_WINDOW_DEFAULT_HOST);
		hostNameTextField.setFont(MAIN_WINDOW_FONT);
		hostNameTextField.setColumns(10);
		
		portLabel = new JLabel(AUTH_WINDOW_PORT_LABEL_NAME);
		portLabel.setFont(MAIN_WINDOW_FONT);
		
		portTextField = new JTextField(AUTH_WINDOW_DEFAULT_PORT);
		portTextField.setFont(MAIN_WINDOW_FONT);
		portTextField.setColumns(10);
		
		okButton = new JButton(AUTH_WINDOW_OK_BUTTON_NAME);
		okButton.setBackground(Color.LIGHT_GRAY);
		okButton.setFont(MAIN_WINDOW_FONT);
		
		cancelButton = new JButton(AUTH_WINDOW_CANCEL_BUTTON_NAME);
		cancelButton.setBackground(Color.LIGHT_GRAY);
		cancelButton.setFont(MAIN_WINDOW_FONT);
		
		separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(userNameLabel)
								.addComponent(userPasswordLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(userPassPasswordField)
								.addComponent(userNameTextField, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(hostNameLabel)
								.addComponent(appNameLabel)
								.addComponent(portLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(portTextField, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
								.addComponent(appNameTextField, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
								.addComponent(hostNameTextField, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))))
					.addGap(283))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(okButton, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
							.addGap(55)
							.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
					.addGap(283))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userNameLabel)
						.addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userPasswordLabel)
						.addComponent(userPassPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(appNameLabel)
						.addComponent(appNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(hostNameLabel)
						.addComponent(hostNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(portLabel)
						.addComponent(portTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(okButton)
						.addComponent(cancelButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		contentPane.getRootPane().setDefaultButton(okButton);
		okButton.requestFocus();
	}
}
