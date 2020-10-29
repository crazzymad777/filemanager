import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.UIManager;

public class filemanager {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception ignored){}

		filemanager m=new filemanager();
		m.fm_frame.ft.SetCurrentPath("~");
	}
	public filemanager(){
        fm_frame=new fm_visual_ex(this);
	}
	public String[] getRootDirectories(){
		FileSystem fileSystem = FileSystems.getDefault();
		Iterable<Path> dirs = fileSystem.getRootDirectories();
		List<String> list = new ArrayList<>();
        for (Path name : dirs) {
        	list.add(name.toString());
        }
        String[] retArray = new String[ list.size() ];
        list.toArray( retArray );
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
	
	fm_visual_ex fm_frame;
    
}