import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import static java.nio.file.StandardCopyOption.*;

public class ButtonbarEvent implements ActionListener {
	Filemanager filemanager;
	FilesTree filesTree;
	String currentPath;

	public ButtonbarEvent(Filemanager filemanager, FilesTree filesTree){
		this.filemanager = filemanager;
		this.filesTree = filesTree;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
			case "Create": {
				String name = filemanager.ShowInputMessage("New file", "Enter name of new file:");
				if (name == null) return;
				String newPath;
				if (filesTree.current_path == null) {
					newPath = "/" + name;
				} else {
					newPath = filesTree.current_path + "/" + name;
				}
				File newFile = new File(newPath);
				try {
					if (newFile.createNewFile()) {
						if (filesTree != null) {
							filesTree.addObject(null, newFile.getName(), true, false);
							filesTree.event.SyncPaths(filesTree.internalTree.getSelectionPath(), true);
						}
					} else {
						filemanager.ShowMessage("Failed to create file", "Couldn't create file.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			case "Rename": {
				String name = filemanager.ShowInputMessage("Rename", "Enter new name file/directory:");
				if (name == null) return;
				if (filesTree.current_path == null) return;

				String newPath;
				if (!filesTree.current_path.toFile().isFile()) {
					newPath = filesTree.current_path + "/" + name;
				} else {
					newPath = filesTree.current_path.getParent().toString() + "/" + name;
				}

				File file = filesTree.current_path.toFile();
				File file2 = new File(newPath);
				if (file2.exists()) {
					filemanager.ShowMessage("Failed to rename file", "File " + name + " exist.");
				} else if (!file.renameTo(file2)) {
					filemanager.ShowMessage("Failed to rename file", "Couldn't rename file " + filesTree.current_path + " to " + newPath + ".");
				} else {
					//ft.LastTreePath=ft.internalTree.getSelectionPath().getParentPath().pathByAddingChild(name);
					filesTree.renameNode(filesTree.internalTree.getSelectionPath(), name);
					filesTree.SetCurrentPath(file2.toString());
				}
				break;
			}
			case "Move":
				if (filesTree.current_path != null) {
					JFrame frame = new JFrame("Move " + filesTree.current_path + " to");
					frame.setBounds(100, 100, 230, 540);
					frame.setLayout(null);

					FilesTree ft2 = new FilesTree(filemanager, frame, null);
					JButton btnMove = new JButton("Move to");
					btnMove.setBounds(10, 470, 200, 20);
					ButtonbarEvent buttonEvent = new ButtonbarEvent(filemanager, ft2);
					buttonEvent.currentPath = filesTree.current_path.toString();
					btnMove.addActionListener(buttonEvent);
					frame.add(btnMove);

					frame.setVisible(true);
				}
				break;
			case "Copy":
				if (filesTree.current_path != null) {
					JFrame frame = new JFrame("Copy " + filesTree.current_path + " to");
					frame.setBounds(100, 100, 230, 540);
					frame.setLayout(null);

					FilesTree ft2 = new FilesTree(filemanager, frame, null);
					JButton btnCopy = new JButton("Copy to");
					btnCopy.setBounds(10, 470, 200, 20);
					ButtonbarEvent be = new ButtonbarEvent(filemanager, ft2);
					be.currentPath = filesTree.current_path.toString();
					btnCopy.addActionListener(be);
					frame.add(btnCopy);

					frame.setVisible(true);
				}
				break;
			case "Remove":
				if (filesTree.current_path != null) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete " + filesTree.current_path + "?", "Confirm", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						File file = new File(filesTree.current_path.toString());
						if (file.delete()) {
							TreePath treePath = filesTree.LastTreePath;
							filesTree.removeNode(treePath);
						} else {
							filemanager.ShowMessage("Failed to delete file", "Couldn't delete file " + filesTree.current_path + ".");
						}
					}
				}
				break;
			case "Copy to":
				if (filesTree.current_path != null) {
					if (currentPath == null) return;
					String newPath;
					newPath = filesTree.current_path + "/" + Paths.get(currentPath).toFile().getName();
					try {
						Files.copy(Paths.get(currentPath), Paths.get(newPath), COPY_ATTRIBUTES);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				filesTree.frame.dispose();
				break;
			case "Move to":
				if (filesTree.current_path != null) {
					if (currentPath == null) return;
					String newPath;
					newPath = filesTree.current_path + "/" + Paths.get(currentPath).toFile().getName();
					try {
						if (!(new File(newPath).exists())) {
							Files.move(Paths.get(currentPath), Paths.get(newPath));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				filesTree.frame.dispose();
				break;
		}
	}
}
