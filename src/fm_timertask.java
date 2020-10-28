import java.io.File;
import java.util.TimerTask;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class fm_timertask extends TimerTask {
	major mc;
	fm_filestree ft;
	TreePath path;
	int row;
	public fm_timertask(major m,fm_filestree f,int i,TreePath t) {
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
