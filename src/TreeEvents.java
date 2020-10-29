import java.util.Timer;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeEvents implements TreeSelectionListener,TreeWillExpandListener {
	Filemanager mc;
	FilesTree ft;
	public TreeEvents(Filemanager m, FilesTree f){
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
					ft.LastTreePath=path;
					SyncPaths(path,true);
				}
			}else{
				if(!node.isLeaf() || node.getAllowsChildren()){
					int new_row=ft.internalTree.getRowForPath(path);
					if(ft.renderer.loading_row==-1){
						ft.renderer.loading_row=new_row;
					}else if(ft.renderer.loading_row!=new_row){
						ft.renderer.loading_row=new_row;
					}
					ft.renderer.timer=new Timer();
					ft.renderer.timer.schedule(new TimerTask(mc,ft,new_row,path), 2000);
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
				int new_row=ft.internalTree.getRowForPath(path);
				if(ft.renderer.loading_row==-1){
					ft.renderer.loading_row=new_row;
				}else if(ft.renderer.loading_row!=new_row){
					ft.renderer.loading_row=new_row;
				}
				ft.renderer.timer.cancel();
				ft.renderer.timer.purge();
				ft.renderer.timer=new Timer();
				ft.renderer.timer.schedule(new TimerTask(mc,ft,new_row,path), 2000);
			}
		}
	}
	public String SyncPaths(TreePath path,boolean bSet){
		if(path!=null){
			int number=path.getPathCount();
			Object[] o=path.getPath();
			StringBuilder data= new StringBuilder();
			for(int i=1;i<number;i++){
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)o[i];
				data.append(node.getUserObject().toString());
			}
			if(number==1) data = new StringBuilder("~");
			if(bSet) ft.SetCurrentPath(data.toString());
			return data.toString();
		}
		return "/";
	}
}
