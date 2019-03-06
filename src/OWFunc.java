import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.genesyslab.platform.commons.protocol.Protocol;


public interface OWFunc extends OWConstants {
	
	public static boolean isProtocolOpened (Protocol protocol) {
		if (protocol != null&&protocol.getState().toString().equals(OPENED_PROTOCOL_STATE)) return true;
		else if (protocol == null||protocol.getState().toString().equals(CLOSED_PROTOCOL_STATE)) return false;
		else return false;
	}
	
	public static URI makeUri (String ip, String port) {
		try {			
			return new URI ("tcp://" + ip + ":" + port);
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
			System.out.print("Error making URI" + "\r\n");
			JOptionPane.showMessageDialog(new JFrame(), WRONG_URI_ENTERED, AUTH_WINDOW_ERROR, JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public static String extractHostIP (String str){
		Pattern pattern = Pattern.compile(HOST_PATTERN.substring(4) + "(.*?);");
	    Matcher matcher = pattern.matcher(str);
	    String str1 = "";
	    while (matcher.find()) {
	    	str1 = matcher.group(1);
	    }
		return str1;
	}
	
	public static String extractPort (String str) {
		Pattern pattern = Pattern.compile(PORT_PATTERN.substring(2)+ "(.*?)]");
	    Matcher matcher = pattern.matcher(str);
	    String str1 = "";
	    while (matcher.find()) {
	    	str1 = matcher.group(1);
	    }
		return str1;
	}	
	
	public static String extractSipPort(String str) {
		return str.substring(str.lastIndexOf(":") + 1);
	}
	
	public static String extractServerName (String str){
		return str.substring(0, str.indexOf(" "));
	}
	
	public static String extractAlertName (String str){
		String result = str.substring(str.indexOf(ALERT_FILE_NAME_PATTERN) + ALERT_FILE_NAME_PATTERN.length(), str.indexOf(ALERT_FILE_LOWER_LIMIT_PATTERN));
		while (result.endsWith("\t")) {
			result = result.substring(0, result.length()-1);
		}
		return result;
	}

	public static String extractLowerLimit (String str){
		String result = str.substring(str.indexOf(ALERT_FILE_LOWER_LIMIT_PATTERN) + ALERT_FILE_LOWER_LIMIT_PATTERN.length(), str.indexOf(ALERT_FILE_UPPER_LIMIT_PATTERN));
		while (result.endsWith("\t")) {
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	public static String extractUpperLimit (String str){
		String result = str.substring(str.indexOf(ALERT_FILE_UPPER_LIMIT_PATTERN) + ALERT_FILE_UPPER_LIMIT_PATTERN.length(), str.indexOf(ALERT_FILE_SINGLE_LIMIT_PATTERN));
		while (result.endsWith("\t")) {
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	public static String extractSingleLimit (String str){
		String result = str.substring(str.indexOf(ALERT_FILE_SINGLE_LIMIT_PATTERN) + ALERT_FILE_SINGLE_LIMIT_PATTERN.length(), str.indexOf(ALERT_FILE_COMPARISON_PATTERN));
		while (result.endsWith("\t")) {
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	public static ComparisonTypes extractComparison (String str){
		String compareString = str.substring(str.indexOf(ALERT_FILE_COMPARISON_PATTERN) + ALERT_FILE_COMPARISON_PATTERN.length());
		while (compareString.endsWith("\t")) {
			compareString = compareString.substring(0, compareString.length()-1);
		}		
			if (compareString.equals(ComparisonTypes.VALUE_LESS_X.toString())) return ComparisonTypes.VALUE_LESS_X;
			else if (compareString.equals(ComparisonTypes.VALUE_LESS_OR_EQUALS_X.toString())) return ComparisonTypes.VALUE_LESS_OR_EQUALS_X;
			else if (compareString.equals(ComparisonTypes.VALUE_GREATER_X.toString())) return ComparisonTypes.VALUE_GREATER_X;
			else if (compareString.equals(ComparisonTypes.VALUE_GREATER_OR_EQUALS_X.toString())) return ComparisonTypes.VALUE_GREATER_OR_EQUALS_X;
			else if (compareString.equals(ComparisonTypes.VALUE_EQUALS_X.toString())) return ComparisonTypes.VALUE_EQUALS_X;
			else if (compareString.equals(ComparisonTypes.VALUE_OUTSIDE.toString())) return ComparisonTypes.VALUE_OUTSIDE;
			else if (compareString.equals(ComparisonTypes.VALUE_OUTSIDE_OR_LOW.toString())) return ComparisonTypes.VALUE_OUTSIDE_OR_LOW;
			else if (compareString.equals(ComparisonTypes.VALUE_OUTSIDE_OR_HIGH.toString())) return ComparisonTypes.VALUE_OUTSIDE_OR_HIGH;
			else if (compareString.equals(ComparisonTypes.VALUE_OUTSIDE_OR_EQUALS.toString())) return ComparisonTypes.VALUE_OUTSIDE_OR_EQUALS;
			else if (compareString.equals(ComparisonTypes.VALUE_INSIDE.toString())) return ComparisonTypes.VALUE_INSIDE;
			else if (compareString.equals(ComparisonTypes.VALUE_INSIDE_OR_LOW.toString())) return ComparisonTypes.VALUE_INSIDE_OR_LOW;
			else if (compareString.equals(ComparisonTypes.VALUE_INSIDE_OR_HIGH.toString())) return ComparisonTypes.VALUE_INSIDE_OR_HIGH;
			else if (compareString.equals(ComparisonTypes.VALUE_INSIDE_OR_EQUALS.toString())) return ComparisonTypes.VALUE_INSIDE_OR_EQUALS;		
			else return null;
		
	}
	
}
