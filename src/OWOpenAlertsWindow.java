import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;


public class OWOpenAlertsWindow extends JDialog implements OWConstants {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton editButton;
	private JButton cancelButton;
	private JComboBox<String> alertNameListComboBox;
	private JLabel alertTempLoadLabel;
	private JPanel alertTemplatePanel;
	private JLabel valueLabel;
	private JLabel valueLabel2;
	private JLabel firstComparisonLabel;
	private JLabel secondComparisonLabel;
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel orAndLabel;
	private HashMap <String,OWAlertTemplate> listOfTemplates = new HashMap<String,OWAlertTemplate>();
	private OWAlertTemplate choosenTemplate;
	private String choosenTemplateName;
	

	public OWOpenAlertsWindow() {
		initWindow();
		
		loadAlertTemplatesList();
		
		fillAlertTemplatePanel();
	}
	
	private void loadAlertTemplatesList() {
		Path path = FileSystems.getDefault().getPath(ALERT_FILE_PATH, ALERT_FILE_NAME);
	     
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	if (line.length() > 2) listOfTemplates.put(OWFunc.extractAlertName(line), new OWAlertTemplate(OWFunc.extractLowerLimit(line), OWFunc.extractUpperLimit(line), OWFunc.extractSingleLimit(line), OWFunc.extractComparison(line)));
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}		
		Set<String> keys = listOfTemplates.keySet();
        Iterator<String> it = keys.iterator();
        while(it.hasNext()) {
            String key = it.next();
            alertNameListComboBox.addItem(key);
        }
        alertNameListComboBox.setSelectedIndex(0);
	}
	
	private void fillAlertTemplatePanel() {
		choosenTemplateName = (String) alertNameListComboBox.getSelectedItem();
		choosenTemplate = listOfTemplates.get(choosenTemplateName);
		
		if (choosenTemplate.getSingleLimit() == -1) {
			showSecondLimits(true);
			
			firstComparisonLabel.setText(choosenTemplate.getComparison().toString().substring(0, choosenTemplate.getComparison().toString().indexOf(",")));
			xLabel.setText(choosenTemplate.getLowerLimit() + "");
			
			if (choosenTemplate.getComparison() == ComparisonTypes.VALUE_INSIDE || choosenTemplate.getComparison() == ComparisonTypes.VALUE_INSIDE_OR_LOW || choosenTemplate.getComparison() == ComparisonTypes.VALUE_INSIDE_OR_HIGH || choosenTemplate.getComparison() == ComparisonTypes.VALUE_INSIDE_OR_EQUALS) {
				orAndLabel.setText(AND_STRING);
			} else orAndLabel.setText(OR_STRING);
			
			secondComparisonLabel.setText(choosenTemplate.getComparison().toString().substring(choosenTemplate.getComparison().toString().indexOf(",") + 1));
			yLabel.setText(choosenTemplate.getUpperLimit() + "");
		} else if (choosenTemplate.getLowerLimit() == -1 && choosenTemplate.getUpperLimit() == -1) {
			showSecondLimits(false);
			
			firstComparisonLabel.setText(choosenTemplate.getComparison().toString());
			xLabel.setText(choosenTemplate.getSingleLimit() + "");
		}
	}
	
	private void closeDialog () {
		this.dispose();
	}
	
	protected class alertNameListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			fillAlertTemplatePanel();
		}
	}
	
	protected class closeButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			choosenTemplateName = "";
			choosenTemplate = null;
			closeDialog();
		}
	}
	
	protected class editButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			closeDialog();
		}
	}	
	
	public OWAlertTemplate getChoosenTemplate () {
		return choosenTemplate;
	}
	
	public String getChoosenTemplateNAme () {
		return choosenTemplateName;
	}
	
	private void showSecondLimits (boolean bool) {
		orAndLabel.setVisible(bool);
		valueLabel2.setVisible(bool);
		secondComparisonLabel.setVisible(bool);
		yLabel.setVisible(bool);
	}
	
	private class PMAdapter extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			choosenTemplateName = "";
			choosenTemplate = null;
			closeDialog();
		}
	}
	
	private void initWindow () {
		setResizable(false);
		setTitle(ALERT_LOADING_WIN_TITLE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 230, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			editButton = new JButton(ALERT_LOADING_WIN_EDIT_BUTTON_NAME);
			editButton.setBackground(Color.LIGHT_GRAY);
			editButton.setFont(MAIN_WINDOW_FONT);
			getRootPane().setDefaultButton(editButton);
			editButton.addActionListener(new editButtonActionListener());
		}
		{
			cancelButton = new JButton(ALERT_LOADING_WIN_CANCEL_BUTTON_NAME);
			cancelButton.setBackground(Color.LIGHT_GRAY);
			cancelButton.setFont(MAIN_WINDOW_FONT);
			cancelButton.addActionListener(new closeButtonActionListener());
		}
		
		alertNameListComboBox = new JComboBox<String>();
		alertNameListComboBox.setMaximumRowCount(20);
		alertNameListComboBox.setBackground(Color.WHITE);
		alertNameListComboBox.setFont(MAIN_WINDOW_FONT);
		alertNameListComboBox.addActionListener(new alertNameListActionListener());
		
		alertTempLoadLabel = new JLabel(ALERT_LOADING_WIN_DESCRIPTION);
		alertTempLoadLabel.setHorizontalAlignment(SwingConstants.CENTER);
		alertTempLoadLabel.setLabelFor(contentPanel);
		alertTempLoadLabel.setFont(MAIN_WINDOW_FONT);
		
		alertTemplatePanel = new JPanel();
		alertTemplatePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), ALERT_LOADING_WIN_ALERT_TEMP_PANEL_NAME, TitledBorder.LEADING, TitledBorder.TOP, MAIN_WINDOW_FONT, null));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(alertTempLoadLabel, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(editButton)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(cancelButton))
						.addComponent(alertTemplatePanel, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
						.addComponent(alertNameListComboBox, 0, 208, Short.MAX_VALUE))
					.addGap(210))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(alertTempLoadLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(alertNameListComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(alertTemplatePanel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(editButton)
						.addComponent(cancelButton))
					.addContainerGap())
		);
		
		valueLabel = new JLabel(ALERT_LOADING_WIN_TESTED_VALUE);
		valueLabel.setFont(MAIN_WINDOW_FONT);
		
		firstComparisonLabel = new JLabel("...");
		firstComparisonLabel.setFont(MAIN_WINDOW_FONT);
		
		xLabel = new JLabel(ALERT_TEMP_WIN_LOW_LIMIT_LABEL_NAME);
		xLabel.setFont(MAIN_WINDOW_FONT);
		
		orAndLabel = new JLabel(OR_STRING);
		orAndLabel.setFont(MAIN_WINDOW_FONT);
		
		valueLabel2 = new JLabel(ALERT_LOADING_WIN_TESTED_VALUE);
		valueLabel2.setFont(MAIN_WINDOW_FONT);
		
		secondComparisonLabel = new JLabel("...");
		secondComparisonLabel.setFont(MAIN_WINDOW_FONT);
		
		yLabel = new JLabel(ALERT_TEMP_WIN_HIGH_LIMIT_LABEL_NAME);
		yLabel.setFont(MAIN_WINDOW_FONT);
		
		GroupLayout gl_alertTemplatePanel = new GroupLayout(alertTemplatePanel);
		gl_alertTemplatePanel.setHorizontalGroup(
			gl_alertTemplatePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_alertTemplatePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_alertTemplatePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_alertTemplatePanel.createSequentialGroup()
							.addComponent(valueLabel)
							.addGap(6)
							.addComponent(firstComparisonLabel, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(xLabel, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
						.addComponent(orAndLabel)
						.addGroup(gl_alertTemplatePanel.createSequentialGroup()
							.addComponent(valueLabel2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(secondComparisonLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(yLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		gl_alertTemplatePanel.setVerticalGroup(
			gl_alertTemplatePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_alertTemplatePanel.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_alertTemplatePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(valueLabel)
						.addComponent(firstComparisonLabel)
						.addComponent(xLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(orAndLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_alertTemplatePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(valueLabel2)
						.addComponent(secondComparisonLabel)
						.addComponent(yLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		alertTemplatePanel.setLayout(gl_alertTemplatePanel);
		contentPanel.setLayout(gl_contentPanel);
		
		this.addWindowListener(new PMAdapter());
	}
}
