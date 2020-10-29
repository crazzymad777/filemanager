import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class TimerTask extends java.util.TimerTask {
	Filemanager mc;
	FilesTree ft;
	TreePath path;
	int row;
	public TimerTask(Filemanager m, FilesTree f, int i, TreePath t) {
		mc=m;
		ft=f;
		row=i;
		path=t;
	}

	@Override
	public void run() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		
		String data=ft.event.SyncPaths(path,true);
		
		if(!node.children().hasMoreElements()){
			String[] directories = new File(data).list();
			if(directories!=null){
				for (String x : directories) {
					File f=new File(data+x);
					if(!f.isFile()){
						ft.addObject(node,x+"\\",true,true);
					}else{
						ft.addObject(node,x,true,false);
					}
				}
			}
		}
		ft.internalTree.expandRow(ft.renderer.loading_row);
		ft.renderer.loading_row=-1;
	}
}
