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


public class FilesTree {
	JFrame frame;
	String frameTitle;
	public FilesTree(Filemanager filemanager, JFrame frame, JPanel panel){
		this.filemanager = filemanager;
		this.frameTitle = frame.getTitle();
		this.frame = frame;

		loadTree();

		internalTree = new JTree(treeModel);
		internalTree.putClientProperty("JTree.lineStyle", "Angeled");
		internalTree.setBorder(new EmptyBorder(10, 10, 10, 10));
		event = new TreeEvents(this.filemanager,this);
		internalTree.addTreeWillExpandListener(event);
		internalTree.addTreeSelectionListener(event);
		internalTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeModel.addTreeModelListener(new FilesTreeModelListener(this.filemanager,this));

		renderer = new TreeRenderer(this.filemanager);
		internalTree.setCellRenderer(renderer);

		treePanel = new JScrollPane(internalTree);
		treePanel.setBounds(10, 10, 200, 420);

		java.awt.Container container;
		container = (panel == null) ? frame : panel;

		btnCreate = new JButton("Create directory");
		btnCreate.setBounds(10, 440, 200, 20);
		btnCreate.addActionListener(new CreateDirectoryEvent(this.filemanager, this));

		container.add(btnCreate, java.awt.BorderLayout.SOUTH);
		container.add(treePanel, java.awt.BorderLayout.CENTER);
	}
	public void loadTree(){
		boolean unix = filemanager.unix;
		rootNode = new DefaultMutableTreeNode(unix ? "/" : "", true);
		String[] dirs = filemanager.getRootsOrInRoot();
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
			rootNode.add(Drive);
		}

		treeModel = new DefaultTreeModel(rootNode);
        treeModel.setAsksAllowsChildren(true);
        if(internalTree!=null) internalTree.setModel(treeModel);
	}
	public void SetCurrentPath(String text){
		boolean mainframe = false;
		if(this == filemanager.mainForm.filesTree) mainframe = true;
		if(text == null) text="/";

		frame.setTitle(frameTitle + ": " + text);

		if(mainframe){
			filemanager.mainForm.getCurrentPath().setText(text);
		}
		if(text.equals("/")) {
			currentPath = null;
			if(mainframe) filemanager.mainForm.getCreateFileButton().setEnabled(true);
		} else {
			currentPath = Paths.get(text);
			if(mainframe) filemanager.mainForm.getCreateFileButton().setEnabled(!currentPath.toFile().isFile());
		}
		if(mainframe) filemanager.mainForm.getTextPane().setText(new FileInfo(currentPath, filemanager).getData());
		renderer.openedRow = internalTree.getRowForPath(LastTreePath);
	}
	public void addObject(DefaultMutableTreeNode parent,
						  Object child,
						  boolean shouldBeVisible, boolean bchild) {
		DefaultMutableTreeNode childNode = 
		new DefaultMutableTreeNode(child,bchild);
		
		if (parent == null) {
			TreePath parentPath = internalTree.getSelectionPath();
			if (parentPath == null) {
				parent = rootNode;
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
	public void renameNode(TreePath treePath, String object) {
        if (treePath != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (treePath.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
            	currentNode.setUserObject(object);
            	treeModel.nodeChanged(currentNode);
            }
        }
    }

	DefaultTreeModel treeModel;
    DefaultMutableTreeNode rootNode;
	TreePath LastTreePath = null;
	JTree internalTree;
	JButton btnCreate;
	JScrollPane treePanel;
	TreeEvents event;
	TreeRenderer renderer;
    Path currentPath = null;
	Filemanager filemanager;
}
