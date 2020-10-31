import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Map;

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
        unix = new File("/").getPath().equals("/");
		mainForm = new MainForm(this);
	}
	public HashMap<File, String> getRootsOrInRoot(){
		HashMap<File, String> map = new HashMap<>();

        if (unix)
        {
            File[] filesInRoot = new File("/").listFiles();

            for (File file : filesInRoot) {
                if (file.isDirectory())
                {
                    map.put(file, file.getName());
                }
            }
        }
        else
        {
            File[] roots = File.listRoots();

            for (File file : roots) {
                map.put(file, file.getPath());
            }
        }
        return map;
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