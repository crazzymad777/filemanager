import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class major{
	public static void main(String[] args) {
		major m=new major();
		m.fm_frame.ft.SetCurrentPath("~");
	}
	public major(){
        fm_frame=new fm_visual(this);
	}
	public String[] getDirectories(String name){
		File file = new File(name);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
			return new File(current, name).isDirectory();
			}
		});
		return directories;
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