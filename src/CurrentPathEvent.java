import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.tree.TreePath;

public class CurrentPathEvent implements ActionListener {
	Filemanager filemanager;
	FilesTree filesTree;
	public CurrentPathEvent(Filemanager filemanager, FilesTree filesTree){
		this.filemanager = filemanager;
		this.filesTree = filesTree;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if(filesTree.currentPath != null){
 		   Path path = filesTree.currentPath.getParent();
 		   if(path != null){
			   filesTree.SetCurrentPath(path.toString());
 		   }else{
			   filesTree.SetCurrentPath("~");
 		   }
 		   if(filesTree.LastTreePath != null){
	 		   TreePath treePath = filesTree.LastTreePath.getParentPath();
			   filesTree.internalTree.collapsePath(filesTree.LastTreePath);
			   filesTree.LastTreePath = treePath;
 		   }
 	   }
	}

}
