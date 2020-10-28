import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class filemanager {
	public static void main(String[] args) {
		filemanager m=new filemanager();
		m.fm_frame.ft.SetCurrentPath("~");
	}
	public filemanager(){
        fm_frame=new fm_visual(this);
	}
	public String[] getDirectories(String name){
		File file = new File(name);
		return file.list((current, name1) -> new File(current, name1).isDirectory());
	}
	public String[] getRootDirectories(){
		FileSystem fileSystem = FileSystems.getDefault();
		Iterable<Path> dirs = fileSystem.getRootDirectories();
		List<String> somelist = new ArrayList<String>();
        for (Path name : dirs) {
        	somelist.add(name.toString());
        }
        String[] retArray = new String[ somelist.size() ];
        somelist.toArray( retArray );
        return retArray;
	}
	public String ShowInputMessage(String title,String body){
		JFrame someframe=new JFrame(title);
		return JOptionPane.showInputDialog(someframe, body);
	}
	public void ShowMessage(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	fm_visual fm_frame;
    
}