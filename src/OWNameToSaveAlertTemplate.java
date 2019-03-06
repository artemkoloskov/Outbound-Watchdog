import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;


public class OWNameToSaveAlertTemplate extends JDialog implements OWConstants {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton saveAsButton;
	private JButton cancelButton;
	private JTextField templateNameTextField;
	private String enteredName = null;
	
	public OWNameToSaveAlertTemplate() {
		initDialog ();
	}
	
	public String getEnteredName () {
		return enteredName;
	}
	
	private void closeDialog () {
		this.dispose();
	}
	
	protected class cancelButtonActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			closeDialog();
		}
	}
	
	protected class saveTempButtonActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (templateNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(new JFrame(), ALERT_NAME_DIALOG_ERROR, AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
			} else {
				enteredName = templateNameTextField.getText();
				closeDialog();
			}
		}
	}
	
	private void initDialog () {
		setModal(true);
		setTitle (ALERT_NAME_DIALOG_TITLE);
		setResizable(false);
		setBounds(100, 100, 397, 130);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			saveAsButton = new JButton(ALERT_NAME_DIALOG_SAVE_BUTTON_NAME);
			saveAsButton.setBackground(Color.LIGHT_GRAY);
			saveAsButton.setFont(MAIN_WINDOW_FONT);
			saveAsButton.addActionListener(new saveTempButtonActionListener());
		}
		cancelButton = new JButton(AUTH_WINDOW_CANCEL_BUTTON_NAME);
		cancelButton.setBackground(Color.LIGHT_GRAY);
		cancelButton.setFont(MAIN_WINDOW_FONT);
		cancelButton.addActionListener(new cancelButtonActionListener());
		
		JLabel templateNameLabel = new JLabel(ALERT_NAME_DIALOG_LABEL);
		templateNameLabel.setFont(MAIN_WINDOW_FONT);
		
		templateNameTextField = new JTextField();
		templateNameTextField.setFont(MAIN_WINDOW_FONT);
		templateNameTextField.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(templateNameLabel))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
									.addComponent(saveAsButton)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cancelButton))
								.addComponent(templateNameTextField, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(templateNameLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(templateNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveAsButton)
						.addComponent(cancelButton))
					.addContainerGap(170, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		
		this.setVisible(true);
	}
}
