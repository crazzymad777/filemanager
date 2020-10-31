import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.UIManager;

public class Filemanager {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception ignored){}

		new Filemanager().mainForm.filesTree.SetCurrentPath("/");
	}
	public Filemanager(){
		mainForm = new MainForm(this);
		unix = new File("/").getPath().equals("/");
	}
	public String[] getRootsOrInRoot(){
		File[] roots = File.listRoots();
		List<String> list = new ArrayList<>();
        for (File file : roots) {
        	list.add(file.getPath());
        }
        String[] retArray = new String[ list.size() ];
		list.toArray( retArray );

        if (retArray.length == 1)
		{
			if (retArray[0].equals(File.separator))
			{
				File root = new File(retArray[0]);
				retArray = root.list();
			}
		}

        return retArray;
	}
	public String ShowInputMessage(String title,String body){
		JFrame frame=new JFrame(title);
		return JOptionPane.showInputDialog(frame, body);
	}
	public void ShowMessage(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	MainForm mainForm;
	boolean unix;
}