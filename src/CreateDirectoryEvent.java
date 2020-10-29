import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CreateDirectoryEvent implements ActionListener {
	Filemanager filemanager;
	FilesTree filesTree;
	public CreateDirectoryEvent(Filemanager filemanager, FilesTree filesTree){
		this.filemanager = filemanager;
		this.filesTree = filesTree;
	}
	@Override 
	public void actionPerformed(ActionEvent actionEvent){
		String name = filemanager.ShowInputMessage("New directory", "Enter name of new directory:");
		if(name == null) return;
		String newPath;
		if(filesTree.current_path==null){
			newPath = "/" + name;
		}else{
			newPath = filesTree.current_path + "/" + name;
		}
		File newFolder = new File(newPath);

		if(newFolder.mkdir()){
			filesTree.addObject(null, newFolder.getName()+"\\", true, true);
			filesTree.event.SyncPaths(filesTree.internalTree.getSelectionPath(), true);
		}else{
			filemanager.ShowMessage("Failed to create directory","Couldn't create directory.");
		}
	} 
}
