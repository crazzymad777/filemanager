import java.awt.Component;
import java.awt.Image;
import java.util.Timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

public class fm_renderer extends JLabel implements TreeCellRenderer {

	public fm_renderer(major m) {
		mc=m;
		
		ImageIcon openIcon = new ImageIcon(getClass().getResource("/images/ofolder.png"));
		iopen_folder=new ImageIcon(openIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
		
		ImageIcon closedIcon = new ImageIcon(getClass().getResource("/images/cfolder.png"));
		iclosed_folder=new ImageIcon(closedIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
		
		ImageIcon refreshIcon = new ImageIcon(getClass().getResource("/images/refresh.png"));
		refresh_folder=new ImageIcon(refreshIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
	}
	@Override
	public Component getTreeCellRendererComponent(JTree arg0, Object val,boolean selected,boolean expanded, boolean leaf,int row, boolean hasFocus) {
		String text="Root Directory";
		if(val.toString()!="") text=val.toString();
		setIcon(iclosed_folder);
		if(leaf){
			setIcon(ifile);
		}else{
			text=text.replace("\\", "");
			if(open_row==row) {
				setIcon(iopen_folder);
			}
			if(loading_row==row){
				setIcon(refresh_folder);
			}
		}
		if(selected){
			text="<u>"+text+"</u>";
		}
		text="<html>"+text+"</html>";
		setText(text);
		return this;
	}
	int open_row=-1;
	ImageIcon iopen_folder; 
	ImageIcon iclosed_folder; 
	ImageIcon refresh_folder; 
	Icon ifile=UIManager.getIcon("Tree.leafIcon"); 
	major mc;
	Integer loading_row=-1;
	Timer timer=new Timer();
}
