import java.io.File;
import java.util.Timer;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeEvents implements TreeSelectionListener,TreeWillExpandListener {
	Filemanager filemanager;
	FilesTree filesTree;
	public TreeEvents(Filemanager filemanager, FilesTree filesTree){
		this.filemanager = filemanager;
		this.filesTree = filesTree;
	}
	public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
		TreePath path = filesTree.internalTree.getSelectionModel().getSelectionPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();

		if(node==null){
			SyncPaths(path,true);
		}else if(!node.isLeaf() || node.getAllowsChildren()){
			if(filesTree.internalTree.isExpanded(path)){
				if(node.children().hasMoreElements()){
					filesTree.LastTreePath=path;
					SyncPaths(path,true);
				}
			}else{
				if(!node.isLeaf() || node.getAllowsChildren()){
					int new_row= filesTree.internalTree.getRowForPath(path);
					if(filesTree.renderer.loadingRow ==-1){
						filesTree.renderer.loadingRow =new_row;
					}else if(filesTree.renderer.loadingRow !=new_row){
						filesTree.renderer.loadingRow =new_row;
					}
					filesTree.renderer.timer=new Timer();
					filesTree.renderer.timer.schedule(new TimerTask(filemanager, filesTree,new_row,path), 2000);
				}
			}
		}else{
			filesTree.LastTreePath=path;
			SyncPaths(path,true);
		}
	}
	public void treeWillCollapse(TreeExpansionEvent treeExpansionEvent){
	TreePath path = treeExpansionEvent.getPath();
		if(path.getParentPath()==null){
			filesTree.loadTree();
			filesTree.SetCurrentPath("/");
		}else{
			TreePath parent_path = path.getParentPath();
			if(parent_path.getParentPath()==null){
				filesTree.SetCurrentPath("/");
				filesTree.loadTree();
			}else{  
				SyncPaths(path,true);
			}
		}
	}
	public void treeWillExpand(TreeExpansionEvent treeExpansionEvent) {
		TreePath path = treeExpansionEvent.getPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		
		filesTree.LastTreePath=path;
		if(!node.isLeaf() || node.getAllowsChildren()){
			if(path.getParentPath()==null){
				filesTree.SetCurrentPath("/");
			}else{
				int new_row= filesTree.internalTree.getRowForPath(path);
				if(filesTree.renderer.loadingRow ==-1){
					filesTree.renderer.loadingRow =new_row;
				}else if(filesTree.renderer.loadingRow !=new_row){
					filesTree.renderer.loadingRow =new_row;
				}
				filesTree.renderer.timer.cancel();
				filesTree.renderer.timer.purge();
				filesTree.renderer.timer=new Timer();
				filesTree.renderer.timer.schedule(new TimerTask(filemanager, filesTree,new_row,path), 2000);
			}
		}
	}
	public String SyncPaths(TreePath path, boolean bSet){
		if(path!=null){
			int number = path.getPathCount();
			Object[] object = path.getPath();
			StringBuilder data = new StringBuilder(filemanager.unix ? "/" : "");
			//StringBuilder data = new StringBuilder();
			for(int i=1;i<number;i++){
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)object[i];
				data.append(node.getUserObject().toString());
				if(data.charAt(data.length()-1) != File.separatorChar)
					data.append(File.separator);
			}
			if(number == 1) data = new StringBuilder("/");
			if(bSet) filesTree.SetCurrentPath(data.toString());
			return data.toString();
		}
		return "/";
	}
}
