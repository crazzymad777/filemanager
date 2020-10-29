import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class TimerTask extends java.util.TimerTask {
	Filemanager filemanager;
	FilesTree filesTree;
	TreePath treePath;
	int row;

	public TimerTask(Filemanager filemanager, FilesTree filesTree, int row, TreePath treePath) {
		this.filemanager = filemanager;
		this.filesTree = filesTree;
		this.treePath = treePath;
		this.row = row;
	}

	@Override
	public void run() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();

		String data = filesTree.event.SyncPaths(treePath,true);

		if(!node.children().hasMoreElements()){
			String[] directories = new File(data).list();
			if(directories != null){
				for (String x : directories) {
					File file = new File(data+x);
					if(!file.isFile()){
						filesTree.addObject(node,x+"\\",true,true);
					}else{
						filesTree.addObject(node,x,true,false);
					}
				}
			}
		}
		filesTree.internalTree.expandRow(filesTree.renderer.loadingRow);
		filesTree.renderer.loadingRow = -1;
	}
}
