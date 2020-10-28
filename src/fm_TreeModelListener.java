import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;


public class fm_TreeModelListener implements TreeModelListener {
	fm_filestree ft;
	major mc;
	public fm_TreeModelListener(major m,fm_filestree f) {
		mc=m;
		ft=f;
	}
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
        
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode)(node.getChildAt(index));
	}
	@Override
	public void treeNodesInserted(TreeModelEvent arg0) {
		
	}
	@Override
	public void treeNodesRemoved(TreeModelEvent arg0) {
		
	}
	@Override
	public void treeStructureChanged(TreeModelEvent arg0) {

	}
}
