import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class OWNewAlertTemplateWindow extends JDialog implements OWConstants{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel xLabel;
	private JLabel yLabel;
	private JTextField xTextField;	
	private JTextField yTextField;
	
	private JLabel alertTemplateLabel;
	private JButton loadTempButton;
	private JButton saveTemplateButton;
	private JButton saveTemplateAsButton;
	private JButton cancelButton;
	private JComboBox<AlertWinComboBoxVal> comboBox;
	private OWAlertTemplate loadedTemplate;
	private String loadedTemplateName;
	private String nameToSave;
	private HashMap <String,OWAlertTemplate> listOfTemplates = new HashMap<String,OWAlertTemplate>();
	
	

	public OWNewAlertTemplateWindow() {
		initAlertTemplateWindow ();
	}	
	
	protected class ComboBoxEventHandler implements ActionListener, OWConstants {
		public void actionPerformed(ActionEvent portMonitorActionEvent) {
			switch ((AlertWinComboBoxVal)comboBox.getSelectedItem()) {
			case ALERT_TEMP_WIN_VALUE_EQUALS_X: 
				showSecondLimit (false);
				break;
			case ALERT_TEMP_WIN_VALUE_GREATER_OR_EQUALS_X:
				showSecondLimit (false);
				break;
			case ALERT_TEMP_WIN_VALUE_GREATER_X:
				showSecondLimit (false);
				break;
			case ALERT_TEMP_WIN_VALUE_LESS_OR_EQUALS_X:
				showSecondLimit (false);
				break;
			case ALERT_TEMP_WIN_VALUE_LESS_X:
				showSecondLimit (false);
				break;
			default:
				showSecondLimit (true);
				break;
			}
		}
	}
	
	protected class LoadButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OWOpenAlertsWindow openAlertWin = new OWOpenAlertsWindow();
			openAlertWin.setVisible(true);
			
			loadedTemplate = openAlertWin.getChoosenTemplate();
			loadedTemplateName = openAlertWin.getChoosenTemplateNAme();
			
			if (loadedTemplate != null) {
				setWindowTitle (loadedTemplateName);
				alertTemplateLabel.setText(loadedTemplateName + ALERT_TEMP_WIN_VALUE_MAIN_LABEL_NAME);
				
				enableSaveTempButton (true);
				
				if (loadedTemplate.getSingleLimit() == -1) {
					showSecondLimit (true);
					xTextField.setText(loadedTemplate.getLowerLimit() + "");
					yTextField.setText(loadedTemplate.getUpperLimit() + "");
					comboBox.setSelectedIndex(loadedTemplate.getComparison().ordinal());					
				} else if (loadedTemplate.getLowerLimit() == -1 && loadedTemplate.getUpperLimit() == -1) {
					showSecondLimit(false);
					xTextField.setText(loadedTemplate.getSingleLimit() + "");
					comboBox.setSelectedIndex(loadedTemplate.getComparison().ordinal());
				}
			}
		}
	}
	
	protected class saveAsButtonActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			OWAlertTemplate temp = buildTemplate();
			
			Path path = FileSystems.getDefault().getPath(ALERT_FILE_PATH, ALERT_FILE_NAME);
			
			nameToSave = (new OWNameToSaveAlertTemplate()).getEnteredName();
			
			if (nameToSave != null) {			
				try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
				    String line = null;
				    while ((line = reader.readLine()) != null) {
				    	listOfTemplates.put(OWFunc.extractAlertName(line), new OWAlertTemplate(OWFunc.extractLowerLimit(line), OWFunc.extractUpperLimit(line), OWFunc.extractSingleLimit(line), OWFunc.extractComparison(line)));
				    }
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				if (listOfTemplates.containsKey(nameToSave)) {
					JOptionPane.showMessageDialog(new JFrame(), ALERT_NEW_TEMPLATE_EXIST_ERROR_, AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
				} else {
					try (PrintWriter writer = new PrintWriter(new FileWriter(path.toString(), true))) {
					    String str =  ALERT_FILE_NAME_PATTERN + nameToSave + ALERT_FILE_LOWER_LIMIT_PATTERN + temp.getLowerLimit() + ALERT_FILE_UPPER_LIMIT_PATTERN + temp.getUpperLimit() + ALERT_FILE_SINGLE_LIMIT_PATTERN + temp.getSingleLimit() + ALERT_FILE_COMPARISON_PATTERN + temp.getComparison().toString() + "\n";
					    writer.append(str, 0, str.length());
					} catch (Exception ex) {
					    ex.printStackTrace();
					}
					closeDialog();
				}
			}			
		}
	}
	
	protected class saveButtonActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			OWAlertTemplate temp = buildTemplate();
			
			Path path = FileSystems.getDefault().getPath(ALERT_FILE_PATH, ALERT_FILE_NAME);

			try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){

		        String line;
		        String input = "";

		        while ((line = reader.readLine()) != null) input += line + "\n";

		        String replaceThis = ALERT_FILE_NAME_PATTERN + loadedTemplateName + ALERT_FILE_LOWER_LIMIT_PATTERN + loadedTemplate.getLowerLimit() + ALERT_FILE_UPPER_LIMIT_PATTERN + loadedTemplate.getUpperLimit() + ALERT_FILE_SINGLE_LIMIT_PATTERN + loadedTemplate.getSingleLimit() + ALERT_FILE_COMPARISON_PATTERN + loadedTemplate.getComparison().toString();
		        String replaceWith = ALERT_FILE_NAME_PATTERN + loadedTemplateName + ALERT_FILE_LOWER_LIMIT_PATTERN + temp.getLowerLimit() + ALERT_FILE_UPPER_LIMIT_PATTERN + temp.getUpperLimit() + ALERT_FILE_SINGLE_LIMIT_PATTERN + temp.getSingleLimit() + ALERT_FILE_COMPARISON_PATTERN + temp.getComparison().toString();
		        
		        input = input.replace(replaceThis, replaceWith); 

		        FileOutputStream writer = new FileOutputStream(path.toString());
		        writer.write(input.getBytes());
		        writer.close();

		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
			
			closeDialog();
		}
	}
	
	private OWAlertTemplate buildTemplate () {
		ComparisonTypes comparison = null;
		String lower = "";
		String upper = "";
		String single = "";
		
		switch ((AlertWinComboBoxVal)comboBox.getSelectedItem()) {
		case ALERT_TEMP_WIN_VALUE_EQUALS_X: 
			lower = ALERT_SENTINEL;
			upper = ALERT_SENTINEL;
			single = xTextField.getText();
			comparison = ComparisonTypes.VALUE_EQUALS_X;
			break;
		case ALERT_TEMP_WIN_VALUE_LESS_X:
			lower = ALERT_SENTINEL;
			upper = ALERT_SENTINEL;
			single = xTextField.getText();
			comparison = ComparisonTypes.VALUE_LESS_X;
			break;
		case ALERT_TEMP_WIN_VALUE_LESS_OR_EQUALS_X:
			lower = ALERT_SENTINEL;
			upper = ALERT_SENTINEL;
			single = xTextField.getText();
			comparison = ComparisonTypes.VALUE_LESS_OR_EQUALS_X;
			break;
		case ALERT_TEMP_WIN_VALUE_GREATER_X:
			lower = ALERT_SENTINEL;
			upper = ALERT_SENTINEL;
			single = xTextField.getText();
			comparison = ComparisonTypes.VALUE_GREATER_X;
			break;
		case ALERT_TEMP_WIN_VALUE_GREATER_OR_EQUALS_X:
			lower = ALERT_SENTINEL;
			upper = ALERT_SENTINEL;
			single = xTextField.getText();
			comparison = ComparisonTypes.VALUE_GREATER_OR_EQUALS_X;
			break;
		case ALERT_TEMP_WIN_VALUE_OUTSIDE:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_OUTSIDE;
			break;
		case ALERT_TEMP_WIN_VALUE_OUTSIDE_OR_LOW:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_OUTSIDE_OR_LOW;
			break;
		case ALERT_TEMP_WIN_VALUE_OUTSIDE_OR_HIGH:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_OUTSIDE_OR_HIGH;
			break;
		case ALERT_TEMP_WIN_VALUE_OUTSIDE_OR_EQUALS:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_OUTSIDE_OR_EQUALS;
			break;		
		case ALERT_TEMP_WIN_VALUE_INSIDE:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_INSIDE;
			break;
		case ALERT_TEMP_WIN_VALUE_INSIDE_OR_LOW:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_INSIDE_OR_LOW;
			break;
		case ALERT_TEMP_WIN_VALUE_INSIDE_OR_HIGH:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_INSIDE_OR_HIGH;
			break;
		case ALERT_TEMP_WIN_VALUE_INSIDE_OR_EQUALS:
			lower = xTextField.getText();
			upper = yTextField.getText();
			single = ALERT_SENTINEL;
			comparison = ComparisonTypes.VALUE_INSIDE_OR_EQUALS;
			break;			
		}
		return new OWAlertTemplate (lower, upper, single, comparison);
	}
	
	protected class cancelButtonActionListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			closeDialog();
		}
	}
	
	private void closeDialog () {
		this.dispose();
	}
	
	private void enableSaveTempButton (boolean bool) {
		saveTemplateButton.setEnabled(bool);
	}
	
	private void showSecondLimit (boolean bool) {
		yTextField.setVisible(bool);
		yLabel.setVisible(bool);
	}
	
	private void setWindowTitle (String str) {
		this.setTitle(str);
	}
	
	private void initAlertTemplateWindow () {
		setResizable(false);
		setBounds(100, 100, 468, 131);
		setTitle(ALERT_TEMP_WIN_TITLE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		alertTemplateLabel = new JLabel(ALERT_TEMP_WIN_TITLE + ALERT_TEMP_WIN_VALUE_MAIN_LABEL_NAME);
		alertTemplateLabel.setFont(MAIN_WINDOW_FONT);
		alertTemplateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alertTemplateLabel.setVerticalAlignment(SwingConstants.TOP);
		
		xLabel = new JLabel(ALERT_TEMP_WIN_LOW_LIMIT_LABEL_NAME);
		xLabel.setFont(MAIN_WINDOW_FONT);
		
		xTextField = new JTextField();
		xTextField.setText(ALERT_TEMP_WIN_DEFAULT_VALUE_TEXT_FIELD);
		xTextField.setFont(MAIN_WINDOW_FONT);
		xTextField.setColumns(10);
		
		yLabel = new JLabel(ALERT_TEMP_WIN_HIGH_LIMIT_LABEL_NAME);
		yLabel.setFont(MAIN_WINDOW_FONT);
		
		yTextField = new JTextField();
		yLabel.setLabelFor(yTextField);
		yTextField.setText(ALERT_TEMP_WIN_DEFAULT_VALUE_TEXT_FIELD);
		yTextField.setFont(MAIN_WINDOW_FONT);
		yTextField.setColumns(10);
		
		showSecondLimit (false);
		
		comboBox = new JComboBox<AlertWinComboBoxVal>();
		comboBox.setMaximumRowCount(15);
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setFont(MAIN_WINDOW_FONT);
		comboBox.setModel(new DefaultComboBoxModel<AlertWinComboBoxVal>(AlertWinComboBoxVal.values()));
		
		loadTempButton = new JButton(ALERT_NEW_TEMPLATE_LOAD_BUTTON_NAME);
		loadTempButton.setBackground(Color.LIGHT_GRAY);
		loadTempButton.setFont(MAIN_WINDOW_FONT);
		loadTempButton.addActionListener(new LoadButtonActionListener());
		
		cancelButton = new JButton(ALERT_LOADING_WIN_CANCEL_BUTTON_NAME);
		cancelButton.setBackground(Color.LIGHT_GRAY);
		cancelButton.setFont(MAIN_WINDOW_FONT);
		cancelButton.addActionListener(new cancelButtonActionListener());
				
		saveTemplateButton = new JButton(ALERT_NEW_TEMPLATE_SAVE_BUTTON_NAME);
		saveTemplateButton.setEnabled(false);
		saveTemplateButton.setFont(MAIN_WINDOW_FONT);
		saveTemplateButton.setBackground(Color.LIGHT_GRAY);
		saveTemplateButton.addActionListener(new saveButtonActionListener());
		
		saveTemplateAsButton = new JButton(ALERT_NEW_TEMPLATE_SAVE_AS_BUTTON_NAME);
		saveTemplateAsButton.setFont(MAIN_WINDOW_FONT);
		saveTemplateAsButton.setBackground(Color.LIGHT_GRAY);
		saveTemplateAsButton.addActionListener(new saveAsButtonActionListener());
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(alertTemplateLabel)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(xLabel, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(xTextField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(yLabel, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(yTextField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(loadTempButton)
							.addGap(18)
							.addComponent(saveTemplateButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(saveTemplateAsButton)
							.addGap(18)
							.addComponent(cancelButton)))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(alertTemplateLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(xLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(xTextField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(yLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(yTextField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(loadTempButton)
						.addComponent(saveTemplateButton)
						.addComponent(saveTemplateAsButton)
						.addComponent(cancelButton))
					.addGap(19))
		);
		contentPanel.setLayout(gl_contentPanel);
		comboBox.addActionListener(new ComboBoxEventHandler());
	}
}
