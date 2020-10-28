import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.tree.TreePath;

public class fm_cpath_event implements ActionListener {
	filemanager mc;
	fm_filestree ft;
	public fm_cpath_event(filemanager m, fm_filestree f){
		mc=m;
		ft=f;
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ft.current_path!=null){
 		   Path somepath=ft.current_path.getParent();
 		   if(somepath!=null){
 			   ft.SetCurrentPath(somepath.toString());
 		   }else{
 			   ft.SetCurrentPath("~");
 		   }
 		   if(ft.LastTreePath!=null){
	 		   TreePath tp=ft.LastTreePath.getParentPath();
	 		   ft.internalTree.collapsePath(ft.LastTreePath);
	 		   ft.LastTreePath=tp;
 		   }
 	   }
	}

}
