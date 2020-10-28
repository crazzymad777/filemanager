import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import static java.nio.file.StandardCopyOption.*;

public class fm_buttonbar_event implements ActionListener {
	major mc;
	fm_filestree ft;
	String sname;
	public fm_buttonbar_event(major m,fm_filestree f){
		mc=m;
		ft=f;
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand()=="Create"){
			String name = mc.ShowInputMessage("New file", "Enter name of new file:");
			if(name==null) return;
			String newpath;
			if(ft.current_path==null){
				newpath="/"+name;
			}else{
				newpath=ft.current_path+"/"+name;
			}
			File newfile=new File(newpath);
			try {
				if(newfile.createNewFile()){
					if(ft!=null){
						ft.addObject(null, newfile.getName(), true, false);
						ft.event.SyncPaths(ft.internalTree.getSelectionPath(), true);
					}
				}else{
					mc.ShowMessage("Failed to create file","Couldn't create file.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(ae.getActionCommand()=="Rename"){
			String name = mc.ShowInputMessage("Rename", "Enter new name file/directory:");
			if(name==null) return;
			if(ft.current_path==null) return;
			
			String newpath;
			if(ft.current_path==null){
				newpath="/"+name;
			}else{
				if(!ft.current_path.toFile().isFile()){
					newpath=ft.current_path+"/"+name;
				}else{
					newpath=ft.current_path.getParent().toString()+"/"+name;
				}
			}
			
			File file = ft.current_path.toFile();
			File file2 = new File(newpath);
			if (file2.exists()){
				mc.ShowMessage("Failed to rename file","File "+name+" exist.");
			}else if (!file.renameTo(file2)) {
				mc.ShowMessage("Failed to rename file","Couldn't rename file "+ft.current_path+" to "+newpath+".");
			}else{
				//ft.LastTreePath=ft.internalTree.getSelectionPath().getParentPath().pathByAddingChild(name);
				ft.renameNode(ft.internalTree.getSelectionPath(),name);
				ft.SetCurrentPath(file2.toString());
			}
		}else if(ae.getActionCommand()=="Move"){
			if(ft.current_path!=null){
				JFrame someframe=new JFrame("Move "+ft.current_path+" to");
				someframe.setBounds(100, 100, 230, 540);
				someframe.setLayout(null);
				someframe.getContentPane().setBackground(new Color(0xa4c5fc));
				fm_filestree ft2=new fm_filestree(mc,someframe);
				JButton btnMove=new JButton("Move to");
				btnMove.setBounds(10, 470, 200, 20);
				fm_buttonbar_event be=new fm_buttonbar_event(mc,ft2);
				be.sname=ft.current_path.toString();
				btnMove.addActionListener(be);
				someframe.add(btnMove);
				
				someframe.setVisible(true);
			}
		}else if(ae.getActionCommand()=="Copy"){
			if(ft.current_path!=null){
				JFrame someframe=new JFrame("Copy "+ft.current_path+" to");
				someframe.setBounds(100, 100, 230, 540);
				someframe.setLayout(null);
				someframe.getContentPane().setBackground(new Color(0xa4c5fc));
				fm_filestree ft2=new fm_filestree(mc,someframe);
				JButton btnCopy=new JButton("Copy to");
				btnCopy.setBounds(10, 470, 200, 20);
				fm_buttonbar_event be=new fm_buttonbar_event(mc,ft2);
				be.sname=ft.current_path.toString();
				btnCopy.addActionListener(be);
				someframe.add(btnCopy);
				
				someframe.setVisible(true);
			}
		}else if(ae.getActionCommand()=="Remove"){
			if(ft.current_path!=null){
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete "+ft.current_path+"?","Confirm",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
					File dfile=new File(ft.current_path.toString());
					if(dfile.delete()){
						TreePath tp=ft.LastTreePath;
						ft.removeNode(tp);
		    		}else{
		    			mc.ShowMessage("Failed to delete file","Couldn't delete file "+ft.current_path+".");
		    		}
				}
			}
		}else if(ae.getActionCommand()=="Copy to"){
			if(ft.current_path!=null){
				if(sname==null) return;
				String newpath;
				if(ft.current_path==null){
					newpath="/"+Paths.get(sname).toFile().getName();;
				}else{
					newpath=ft.current_path+"/"+Paths.get(sname).toFile().getName();
				}
				try {
					Files.copy(Paths.get(sname), Paths.get(newpath),COPY_ATTRIBUTES);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ft.frame.dispose();
		}else if(ae.getActionCommand()=="Move to"){
			if(ft.current_path!=null){
				if(sname==null) return;
				String newpath;
				if(ft.current_path==null){
					newpath="/"+Paths.get(sname).toFile().getName();;
				}else{
					newpath=ft.current_path+"/"+Paths.get(sname).toFile().getName();
				}
				try {
					if(!(new File(newpath).exists())){
						Files.move(Paths.get(sname), Paths.get(newpath));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ft.frame.dispose();
		}
	}
}
