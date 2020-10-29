import java.awt.Component;
import java.awt.Image;
import java.util.Timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

public class TreeRenderer extends JLabel implements TreeCellRenderer {

	public TreeRenderer(Filemanager filemanager) {
		this.filemanager = filemanager;

		ImageIcon openIcon = new ImageIcon(getClass().getResource("/images/ofolder.png"));
		iconOpenedFolder = new ImageIcon(openIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));

		ImageIcon closedIcon = new ImageIcon(getClass().getResource("/images/cfolder.png"));
		iconClosedFolder = new ImageIcon(closedIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));

		ImageIcon refreshIcon = new ImageIcon(getClass().getResource("/images/refresh.png"));
		iconRefreshFolder = new ImageIcon(refreshIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
	}
	@Override
	public Component getTreeCellRendererComponent(JTree arg0, Object val,boolean selected,boolean expanded, boolean leaf,int row, boolean hasFocus) {
		String text="Root Directory";
		if(!val.toString().equals("")) text=val.toString();
		setIcon(iconClosedFolder);
		if(leaf){
			setIcon(iconFile);
		}else{
			text=text.replace("\\", "").replace("/", "");
			if(openedRow ==row) {
				setIcon(iconOpenedFolder);
			}
			if(loadingRow ==row){
				setIcon(iconRefreshFolder);
			}
		}
		if(selected){
			text="<u>"+text+"</u>";
		}
		text="<html>"+text+"</html>";
		setText(text);
		return this;
	}
	Icon iconFile = UIManager.getIcon("Tree.leafIcon");
	ImageIcon iconRefreshFolder;
	ImageIcon iconOpenedFolder;
	ImageIcon iconClosedFolder;
	Timer timer = new Timer();
	Filemanager filemanager;
	Integer loadingRow =-1;
	int openedRow = -1;
}
