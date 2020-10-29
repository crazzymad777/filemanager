import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


public class fm_filestree {
	JFrame frame;
	String titleframe;
	public fm_filestree(filemanager m, JFrame f, JPanel panel){
		mc=m;
		frame=f;
		titleframe=f.getTitle();

		loadTree();

		internalTree=new JTree(treeModel);
		internalTree.putClientProperty("JTree.lineStyle", "Angeled");
		internalTree.setBorder(new EmptyBorder(10, 10, 10, 10));
		event=new fm_three_events(mc,this);
		internalTree.addTreeWillExpandListener(event);
		internalTree.addTreeSelectionListener(event);
		internalTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeModel.addTreeModelListener(new fm_TreeModelListener(mc,this));

		renderer = new fm_renderer(mc);
		internalTree.setCellRenderer(renderer);

		threepanel = new JScrollPane(internalTree);
		threepanel.setBounds(10, 10, 200, 420);

		java.awt.Container container;
		container = (panel == null) ? f : panel;

		btnCreate = new JButton("Create directory");
		btnCreate.setBounds(10, 440, 200, 20);
		btnCreate.addActionListener(new fm_createdir_event(mc, this));

		container.add(btnCreate, java.awt.BorderLayout.SOUTH);
		container.add(threepanel, java.awt.BorderLayout.CENTER);
	}
	public void loadTree(){
		root=new DefaultMutableTreeNode();
		String[] dirs=mc.getRootDirectories();
		for (String name : dirs) {
			DefaultMutableTreeNode Drive = new DefaultMutableTreeNode(name,true);
			/*String[] directories = new File(name).list();
			if(directories!=null){
				for (String x : directories) {
					File f=new File(name+x);
					if(!f.isFile()){
						DefaultMutableTreeNode dir = new DefaultMutableTreeNode(x+"\\",true);
						Drive.add(dir);
					}else{
						DefaultMutableTreeNode dir = new DefaultMutableTreeNode(x,false);
						dir.setAllowsChildren(false);
						Drive.add(dir);
					}
				}
			}*/
			root.add(Drive);
		}

		treeModel = new DefaultTreeModel(root);
        treeModel.setAsksAllowsChildren(true);
        if(internalTree!=null) internalTree.setModel(treeModel);
	}
	public void SetCurrentPath(String text){
		boolean mainframe=false;
		if(this==mc.fm_frame.ft) mainframe=true;
		if(text==null) text="/";

		frame.setTitle(titleframe+": "+text);

		if(mainframe){
			mc.fm_frame.getCurrentPath().setText(text);
		}
		if(text.equals("~")) {
			current_path=null;
			if(mainframe) mc.fm_frame.getCreateFileButton().setEnabled(true);
		}else {
			current_path=Paths.get(text);
			if(mainframe) mc.fm_frame.getCreateFileButton().setEnabled(!current_path.toFile().isFile());
		}
		if(mainframe) mc.fm_frame.getTextPane().setText(new fm_fileinfo(current_path,mc).getData());
		renderer.open_row=internalTree.getRowForPath(LastTreePath);
	}
	public void addObject(DefaultMutableTreeNode parent,
						  Object child,
						  boolean shouldBeVisible, boolean bchild) {
		DefaultMutableTreeNode childNode = 
		new DefaultMutableTreeNode(child,bchild);
		
		if (parent == null) {
			TreePath parentPath = internalTree.getSelectionPath();
			if (parentPath == null) {
				parent = root;
	        } else {
	        	parent = (DefaultMutableTreeNode)
	                         (parentPath.getLastPathComponent());
	        }
		}
		
		treeModel.insertNodeInto(childNode, parent, 
                parent.getChildCount());
		
		if (shouldBeVisible) {
			internalTree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
	}
	public void removeNode(TreePath tp) {
        if (tp != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (tp.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
            }
        }
    }
	public void renameNode(TreePath tp,String obj) {
        if (tp != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (tp.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
            	currentNode.setUserObject(obj);
            	treeModel.nodeChanged(currentNode);
            }
        }
    }
	DefaultTreeModel treeModel;
    DefaultMutableTreeNode root;
	TreePath LastTreePath=null;
	JTree internalTree;
	JButton btnCreate;
	JScrollPane threepanel;
	fm_three_events event;
	fm_renderer renderer;
    Path current_path=null;
	filemanager mc;
}
