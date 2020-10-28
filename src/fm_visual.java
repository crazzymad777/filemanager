import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class fm_visual extends JFrame {
    public fm_visual(major m){
	    super("Filemanger");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 800, 500);
	    setLayout(null);
	    mc=m;

		ft=new fm_filestree(mc,this);
		
		cpath=new JButton("~");
		cpath.addActionListener(new fm_cpath_event(mc,ft));
		cpath.setBounds(220, 10, 550, 20);
		
		label=new JTextPane();
		label.setContentType("text/html");
		label.setEditable(false);
		label.setBackground(null); 
		label.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		filespanel = new JScrollPane(label);
		filespanel.setBackground(new Color(255,255,255));
		filespanel.setBounds(220, 70, 550, 390);
		
		btnCreateFile=new JButton("Create");
		button_event=new fm_buttonbar_event(mc,ft);
		btnCreateFile.addActionListener(button_event);
		btnCreateFile.setBounds(220, 40, 100, 20);
		
		JButton btnRename=new JButton("Rename");
		btnRename.addActionListener(button_event);
		btnRename.setBounds(330, 40, 100, 20);

		JButton btnMove=new JButton("Move");
		btnMove.addActionListener(button_event);
		btnMove.setBounds(440, 40, 100, 20);
		
		JButton btnCopy=new JButton("Copy");
		btnCopy.addActionListener(button_event);
		btnCopy.setBounds(550, 40, 100, 20);
		
		JButton btnRemove=new JButton("Remove");
		btnRemove.addActionListener(button_event);
		btnRemove.setBounds(660, 40, 110, 20);

		add(cpath);
		add(filespanel);
		add(btnCreateFile);
		add(btnRename);
		add(btnRemove);
		add(btnMove);
		add(btnCopy);
		add(btnRemove);

		getContentPane().setBackground(new Color(0xa4c5fc));
		setVisible(true);
    }
    JButton btnCreateFile;
    JButton cpath;
    fm_filestree ft;
    major mc;
    JScrollPane filespanel;
    JTextPane label;
    fm_three_events event;
    fm_buttonbar_event button_event;
}
