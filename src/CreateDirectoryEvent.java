import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CreateDirectoryEvent implements ActionListener {
	Filemanager mc;
	FilesTree ft;
	public CreateDirectoryEvent(Filemanager m, FilesTree f){
		mc=m;
		ft=f;
	}
	@Override 
	public void actionPerformed(ActionEvent ae){
		String name = mc.ShowInputMessage("New directory", "Enter name of new directory:");
		if(name==null) return;
		String newpath;
		if(ft.current_path==null){
			newpath="/"+name;
		}else{
			newpath=ft.current_path+"/"+name;
		}
		File newfolder=new File(newpath);

		if(newfolder.mkdir()){
			ft.addObject(null, newfolder.getName()+"\\", true, true);
			ft.event.SyncPaths(ft.internalTree.getSelectionPath(), true);
		}else{
			mc.ShowMessage("Failed to create directory","Couldn't create directory.");
		}
	} 
}