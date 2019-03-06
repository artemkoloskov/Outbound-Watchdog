import java.awt.Font;

import javax.swing.ImageIcon;

public interface OWConstants {
	
	public static final String 		CLIENT_NAME = 													"Watchdog";
	
	public static final Font 		MAIN_WINDOW_FONT = 												new Font("Segoe UI Light", Font.PLAIN, 11);
	public static final Font 		MAIN_WINDOW_FONT_16 = 											new Font("Segoe UI Light", Font.PLAIN, 16);
	public static final Font 		MAIN_WINDOW_FONT_18 = 											new Font("Segoe UI Light", Font.PLAIN, 18);
	public static final Font 		MAIN_WINDOW_FONT_BOLD = 										new Font("Segoe UI Light", Font.BOLD, 11);
	public static final Font 		MAIN_WINDOW_FONT_BOLD_20 = 										new Font("Segoe UI Light", Font.BOLD, 20);
	public static final int 		MAIN_WINDOW_WIDTH = 											333;
	public static final int 		MAIN_WINDOW_HEIGHT = 											382;
	public static final int 		MAIN_WINDOW_OFFSET = 											100;
	public static final String 		MAIN_WINDOW_TITLE = 											"Outbound watchdog v. 0.015";
	
	public static final String 		MAIN_WINDOW_STATUS_PANEL_NAME = 								"Status";
	public static final String 		MAIN_WINDOW_CONNECTION_PANEL_NAME = 							"Connection options";
	public static final String 		MAIN_WINDOW_CAMPAIGN_INFO_PANEL_NAME = 							"Campaign Info";
	public static final String 		MAIN_WINDOW_HOST_LABEL_NAME = 									"Host:";
	public static final String 		MAIN_WINDOW_PORT_LABEL_NAME = 									"Port:";
	public static final String 		MAIN_WINDOW_CONNECT_BUTTON_NAME = 								"Connect";
	public static final String 		MAIN_WINDOW_CLOSE_CONNECTION_BUTTON_NAME = 						"Close all connections";
	public static final String 		MAIN_WINDOW_GET_STATE_BUTTON_NAME = 							"Get state";
	public static final String 		MAIN_WINDOW_OBJECT_VIEW_PANEL_NAME = 							"Object view";
	public static final String 		MAIN_WINDOW_OBJECT_TREE_TOP_NODE_NAME = 						"Objects";
	public static final String		MAIN_WINDOW_PORT_MONITOR_PANEL_NAME = 							"Port monitor";
	public static final String		MAIN_WINDOW_TOTAL_PORT_LABEL_NAME = 							"Total ports:";
	public static final String		MAIN_WINDOW_AVAILABLE_PORTS_LABEL = 							"Available ports:";
	public static final String		MAIN_WINDOW_PORT_LABELS_CLEAN = 								"not monitored";
	public static final String		MAIN_WINDOW_FILE_MENU_NAME = 									"File";
	public static final String		MAIN_WINDOW_EXIT_MENU_ITEM_NAME = 								"Exit";
	//public static final String	MAIN_WINDOW_ = 			"";
	
	public static final String		PORT_MONITOR_PORT_LABELS_CLEAN = 								"Not monitored";
	public static final String		PORT_MONITOR_TOTAL_PORTS_LABEL_NAME = 							"Total ports:";
	public static final String		PORT_MONITOR_AVAILABLE_PORTS_LABEL_NAME =						"Available ports:";
	public static final String		PORT_MONITOR_NOT_CONNECTED = 									"not connected";
	public static final String		PORT_MONITOR_DISCONNECT_BUTTON_NAME = 							"Disconnect";
	public static final String		PORT_MONITOR_RECONNECT_BUTTON_NAME = 							"Reconnect";
	public static final String		PORT_MONITOR_PANEL_NAME = 										"Port monitor";
	public static final String		PORT_MONITOR_ERROR = 											"ERROR";
	public static final int			PORT_MONITOR_FREQUENCY = 										1000;
	public static final ImageIcon 	PORT_MONITOR_PIN_ICON_1 = 										new ImageIcon("pin-1.png");
	public static final ImageIcon 	PORT_MONITOR_PIN_ICON_2 = 										new ImageIcon("pin-2.png");
	public static final ImageIcon 	PORT_MONITOR_ALERT_ICON = 										new ImageIcon("alert.png");
	public static final ImageIcon 	PORT_MONITOR_UP_ARROW_ICON = 									new ImageIcon("up-arrow.png");
	public static final ImageIcon 	PORT_MONITOR_DOWN_ARROW_ICON = 									new ImageIcon("down-arrow.png");
	//public static final String	PORT_MONITOR_ = 			"";
	
	public static final String		ALERT_SENTINEL = 												"-1";
	public static final String		ALERT_TEMP_WIN_LOW_LIMIT_LABEL_NAME = 							"x";
	public static final String		ALERT_TEMP_WIN_HIGH_LIMIT_LABEL_NAME = 							"y";
	public static final String		ALERT_TEMP_WIN_DEFAULT_VALUE_TEXT_FIELD = 						"0";
	public static final String		ALERT_TEMP_WIN_VALUE_MAIN_LABEL_NAME = 							": chose the type of template and enter limits";
	public static final String		ALERT_TEMP_WIN_TITLE = 											"New alert template";
	public static final String		ALERT_FILE_NAME = 												"alert_collection.txt";
	public static final String		ALERT_FILE_PATH = 												"";
	public static final String		ALERT_FILE_NAME_PATTERN = 										"name: ";
	public static final String		ALERT_FILE_LOWER_LIMIT_PATTERN = 								" lower_limit: ";
	public static final String		ALERT_FILE_UPPER_LIMIT_PATTERN = 								" upper_limit: ";
	public static final String		ALERT_FILE_SINGLE_LIMIT_PATTERN = 								" single_limit: ";
	public static final String		ALERT_FILE_COMPARISON_PATTERN = 								" comparison: ";
	public static final String		ALERT_LOADING_WIN_TITLE = 										"Load alert template";
	public static final String		ALERT_LOADING_WIN_EDIT_BUTTON_NAME = 							"Open template";
	public static final String		ALERT_LOADING_WIN_CANCEL_BUTTON_NAME = 							"Cancel";
	public static final String		ALERT_LOADING_WIN_DESCRIPTION = 								"Choose alert template from the list to edit";
	public static final String		ALERT_LOADING_WIN_ALERT_TEMP_PANEL_NAME = 						"Alert template";
	public static final String		ALERT_LOADING_WIN_TESTED_VALUE = 								"[tested value]";
	public static final String		ALERT_NAME_DIALOG_TITLE = 										"Save template as...";
	public static final String		ALERT_NAME_DIALOG_SAVE_BUTTON_NAME =							"Save template";
	public static final String		ALERT_NAME_DIALOG_LABEL = 										"Enter a name for template";
	public static final String		ALERT_NAME_DIALOG_ERROR = 										"No name entered, please enter name for template";
	public static final String		ALERT_NEW_TEMPLATE_EXIST_ERROR_ = 								"Template with this name already exist";
	public static final String		ALERT_NEW_TEMPLATE_LOAD_BUTTON_NAME = 							"Load template";
	public static final String		ALERT_NEW_TEMPLATE_SAVE_BUTTON_NAME = 							"Save template";
	public static final String		ALERT_NEW_TEMPLATE_SAVE_AS_BUTTON_NAME = 						"Save template as...";

	
	//public static final String		ALERT_NAME_DIALOG_
	
	public static final String 		CONNECTION_CLOSED_STATUS = 										"Connection closed";
	public static final String 		FAILED_TO_CLOSE_CONNECTION_STATUS = 							"Failed to close connection";
	public static final String 		CONNECTION_SUCCESFUL_STATUS = 									"Connection established";
	public static final String 		CONNECTION_FAILED_STATUS = 										"Connection failed";
	public static final String 		CONNECTION_ALREADY_OPENED_STATUS = 								"Connection already opened!";
	public static final String 		CONNECTION_ALREADY_CLOSED_STATUS = 								"Connection already closed!";
	public static final String 		CONNECTED_TO_CONF_SERVER_STATUS = 								"Connected to configuration server";
	public static final String 		NO_ACTIVE_CONNECTION_STATUS = 									"There is no active connection!";
	public static final String		ERROR_WHILE_BUILDING_OBJECT_VIEW_STATUS =						"Error ocured while building object view";
	public static final String		SERVER_IS_NOT_SELECTED_STATUS = 								"Error connecting to server: no server selected in object view";
	
	public static final String 		OPENED_PROTOCOL_STATE = 										"Opened";
	public static final String 		CLOSED_PROTOCOL_STATE = 										"Closed";
	public static final String 		WRONG_URI_ENTERED = 											"Please check entered data, connection failed";
	public static final String 		HOST_PATTERN = 													" - [Host: ";
	public static final String 		PORT_PATTERN = 													"; port: ";
	public static final String 		DOMEN_PATTERN = 												".homecredit.ru";
	public static final String		FAILED_GETTING_SWITCH_ID = 										"No switch DBID";
	public static final String		AND_STRING = 													"AND";
	public static final String		OR_STRING = 													"OR";

	public static final String		ERROR_CONNECTING = 												"Error connecting to a server: ";
	public static final String		ERROR_CLOSING_CONNECTION = 										"Error closing connection to a server: ";
	
	public static final String 		AUTH_WINDOW_USER_NAME_LABEL_NAME = 								"Login:";
	public static final String 		AUTH_WINDOW_USER_PASSWORD_LABEL_NAME = 							"Password:";
	public static final String 		AUTH_WINDOW_USER_NAME_DEFAULT = 								"AKoloskov";
	public static final String 		AUTH_WINDOW_USER_PASS_DEFAULT = 								"1234";
	//public static final String 		AUTH_WINDOW_USER_NAME_DEFAULT = 								"";
	//public static final String 		AUTH_WINDOW_USER_PASS_DEFAULT = 								"";
	public static final String 		AUTH_WINDOW_APP_NAME_LABEL_NAME = 								"Application:";
	public static final String 		AUTH_WINDOW_DEFAULT_APP_NAME = 									"default";
	public static final String 		AUTH_WINDOW_PORT_LABEL_NAME = 									"Port:";
	public static final String 		AUTH_WINDOW_DEFAULT_PORT = 										"5000";
	public static final String 		AUTH_WINDOW_HOST_LABEL_NAME = 									"Host:";
	public static final String 		AUTH_WINDOW_DEFAULT_HOST = 										"localhost";
	//public static final String 		AUTH_WINDOW_DEFAULT_HOST = 										"";
	public static final String 		AUTH_WINDOW_OK_BUTTON_NAME = 									"OK";
	public static final String 		AUTH_WINDOW_CANCEL_BUTTON_NAME = 								"Cancel";
	public static final String 		AUTH_WINDOW_ALERT_WRONG_INFO_ENTERED = 							"Please check entered data, connection to config server failed";
	public static final String 		AUTH_WINDOW_ERROR = 											"ERROR";
	//public static final String 		AUTH_WINDOW_ = 												"";	
	
	enum AlertWinComboBoxVal {
		ALERT_TEMP_WIN_VALUE_EQUALS_X																("value equals to (=x):"),
		ALERT_TEMP_WIN_VALUE_LESS_X																	("value is less then (<x):"),
		ALERT_TEMP_WIN_VALUE_GREATER_X																("value is greater then (>x):"),
		ALERT_TEMP_WIN_VALUE_LESS_OR_EQUALS_X														("value is less then or equals to (<=x):"),
		ALERT_TEMP_WIN_VALUE_GREATER_OR_EQUALS_X													("value is greater then or equals to (>=x):"),
		ALERT_TEMP_WIN_VALUE_OUTSIDE																("value is outside the limits (<x OR >y):"),
		ALERT_TEMP_WIN_VALUE_OUTSIDE_OR_LOW															("value is outside the limits or equals to lower limit (<=x OR >y):"),
		ALERT_TEMP_WIN_VALUE_OUTSIDE_OR_HIGH														("value is outside the limits or equals to upper limit (<x OR >=y):"),
		ALERT_TEMP_WIN_VALUE_OUTSIDE_OR_EQUALS														("value is outside or equals to the limits (<=x OR >=y):"),
		ALERT_TEMP_WIN_VALUE_INSIDE																	("value is inside the limits (>x AND <y):"),
		ALERT_TEMP_WIN_VALUE_INSIDE_OR_LOW															("value is inside the limits or equals to lower limit (>=x AND <y):"),
		ALERT_TEMP_WIN_VALUE_INSIDE_OR_HIGH															("value is inside the limits or equals to upper limit (>x AND <=y):"),
		ALERT_TEMP_WIN_VALUE_INSIDE_OR_EQUALS														("value is inside or equals to the limits (>=x AND <=y):");
		
	    private final String display;
	    
	    private AlertWinComboBoxVal (String s) {
	        display = s;
	    }
	    @Override
	    public String toString() {
	        return display;
	    }
	}
	
	enum ComparisonTypes {
		VALUE_EQUALS_X																				("="),
		VALUE_LESS_X																				("<"),
		VALUE_GREATER_X																				(">"),
		VALUE_LESS_OR_EQUALS_X																		("<="),
		VALUE_GREATER_OR_EQUALS_X																	(">="),
		VALUE_OUTSIDE																				("<,>"),
		VALUE_OUTSIDE_OR_LOW																		("<=,>"),
		VALUE_OUTSIDE_OR_HIGH																		("<,>="),
		VALUE_OUTSIDE_OR_EQUALS																		("<=,>="),
		VALUE_INSIDE																				(">,<"),
		VALUE_INSIDE_OR_LOW																			(">=,<"),
		VALUE_INSIDE_OR_HIGH																		(">,<="),
		VALUE_INSIDE_OR_EQUALS																		(">=,<=");
		
	    private final String display;
	    
	    private ComparisonTypes (String s) {
	        display = s;
	    }
	    @Override
	    public String toString() {
	        return display;
	    }
	}
}
