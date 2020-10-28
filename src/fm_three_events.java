import java.util.Timer;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class fm_three_events implements TreeSelectionListener,TreeWillExpandListener {
	filemanager mc;
	fm_filestree ft;
	public fm_three_events(filemanager m, fm_filestree f){
		mc=m;
		ft=f;
	}
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = ft.internalTree.getSelectionModel().getSelectionPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		
		if(node==null){
			SyncPaths(path,true);
		}else if(!node.isLeaf() || node.getAllowsChildren()){
			if(ft.internalTree.isExpanded(path)){
				if(node.children().hasMoreElements()){
					if(path != null) {
						ft.LastTreePath=path;
						SyncPaths(path,true);
					}
				}
			}else{
				if(path != null) {
					if(!node.isLeaf() || node.getAllowsChildren()){
						int newrow=ft.internalTree.getRowForPath(path);
						if(ft.renderer.loading_row==-1){
							ft.renderer.loading_row=newrow;
						}else if(ft.renderer.loading_row!=newrow){
							ft.renderer.loading_row=newrow;
						}
						ft.renderer.timer=new Timer();
						ft.renderer.timer.schedule(new fm_timertask(mc,ft,newrow,path), 2000);
					}
				}
			}
		}else{
			ft.LastTreePath=path;
			SyncPaths(path,true);
		}
	}
	public void treeWillCollapse(TreeExpansionEvent treeExpansionEvent){
	TreePath path = treeExpansionEvent.getPath();
		if(path.getParentPath()==null){
			ft.loadTree();
			ft.SetCurrentPath("~");
		}else{
			TreePath parent_path = path.getParentPath();
			if(parent_path.getParentPath()==null){
				ft.SetCurrentPath("~");
				ft.loadTree();
			}else{  
				SyncPaths(path,true);
			}
		}
	}
	public void treeWillExpand(TreeExpansionEvent treeExpansionEvent) {
		TreePath path = treeExpansionEvent.getPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		
		ft.LastTreePath=path; 
		if(!node.isLeaf() || node.getAllowsChildren()){
			if(path.getParentPath()==null){
				ft.SetCurrentPath("~");
			}else{
				int newrow=ft.internalTree.getRowForPath(path);
				if(ft.renderer.loading_row==-1){
					ft.renderer.loading_row=newrow;
				}else if(ft.renderer.loading_row!=newrow){
					ft.renderer.loading_row=newrow;
				}
				ft.renderer.timer.cancel();
				ft.renderer.timer.purge();
				ft.renderer.timer=new Timer();
				ft.renderer.timer.schedule(new fm_timertask(mc,ft,newrow,path), 2000);
			}
		}
	}
	public String SyncPaths(TreePath path,boolean bSet){
		if(path!=null){
			int number=path.getPathCount();
			Object[] o=path.getPath();
			String data="";
			for(int i=1;i<number;i++){
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)o[i];
				data = data + node.getUserObject().toString();
			}
			if(number==1) data="~";
			if(bSet) ft.SetCurrentPath(data);
			return data;
		}
		return "/";
	}
}
