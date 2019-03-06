import java.awt.Component;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class OWTreeRenderer extends DefaultTreeCellRenderer implements OWConstants 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HashMap<String,OWPMWindow> portMonitors;
    
	public OWTreeRenderer(HashMap<String,OWPMWindow> openPortMonitors) 
	{
		portMonitors = openPortMonitors;
	}

	public Component getTreeCellRendererComponent(JTree tree,Object value, boolean sel,boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		JLabel node = (JLabel) super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
		
		node.setFont(portMonitors.containsKey(OWFunc.extractHostIP(node.getText()) + ":" + OWFunc.extractPort(node.getText()))? MAIN_WINDOW_FONT_BOLD:MAIN_WINDOW_FONT);
		
		return node;
	}
}
