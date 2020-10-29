import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;


public class FilesTreeModelListener implements TreeModelListener {
	Filemanager filemanager;
	FilesTree filesTree;

	public FilesTreeModelListener(Filemanager filemanager, FilesTree filesTree) {
		this.filemanager = filemanager;
		this.filesTree = filesTree;
	}
	@Override
	public void treeNodesChanged(TreeModelEvent treeModelEvent) {
		DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(treeModelEvent.getTreePath().getLastPathComponent());

        int index = treeModelEvent.getChildIndices()[0];
        node = (DefaultMutableTreeNode)(node.getChildAt(index));
	}
	@Override
	public void treeNodesInserted(TreeModelEvent treeModelEvent) {
		
	}
	@Override
	public void treeNodesRemoved(TreeModelEvent treeModelEvent) {
		
	}
	@Override
	public void treeStructureChanged(TreeModelEvent treeModelEvent) {

	}
}
