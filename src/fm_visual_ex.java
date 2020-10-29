import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class fm_visual_ex {
    public fm_visual_ex(filemanager m) {
        mc = m;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setTitle("Filemanager");

        frame.setContentPane(panel1);
        ft = new fm_filestree(mc, frame, side_panel);
        frame.setVisible(true);
        frame.setSize(800, 500);

        // Handle events
        button_event = new fm_buttonbar_event(mc, ft);
        PATHButton.addActionListener(new fm_cpath_event(mc, ft));
        createButton.addActionListener(button_event);
        removeButton.addActionListener(button_event);
        renameButton.addActionListener(button_event);
        moveButton.addActionListener(button_event);
        copyButton.addActionListener(button_event);
    }

    fm_buttonbar_event button_event;
    fm_filestree ft;
    filemanager mc;

    public JButton getCurrentPath() {
        return PATHButton;
    }

    public JTextPane getTextPane() {
        return textPane1;
    }

    public JButton getCreateFileButton() {
        return createButton;
    }

    private JPanel panel1;
    private JTree tree1;
    private JButton createDirectoryButton;
    private JButton PATHButton;
    private JButton createButton;
    private JButton renameButton;
    private JButton moveButton;
    private JButton copyButton;
    private JButton removeButton;
    private JTextPane textPane1;
    private JPanel side_panel;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(10, 10));
        panel1.setAlignmentX(0.5f);
        panel1.setFocusable(false);
        panel1.setMinimumSize(new Dimension(800, 500));
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(800, 500));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        side_panel = new JPanel();
        side_panel.setLayout(new BorderLayout(0, 10));
        side_panel.setAlignmentX(0.0f);
        side_panel.setAlignmentY(0.0f);
        panel1.add(side_panel, BorderLayout.WEST);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(10, 10));
        panel2.setAlignmentX(0.0f);
        panel2.setAlignmentY(0.0f);
        panel1.add(panel2, BorderLayout.CENTER);
        PATHButton = new JButton();
        PATHButton.setText("PATH");
        panel2.add(PATHButton, BorderLayout.NORTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setInheritsPopupMenu(false);
        scrollPane1.setMinimumSize(new Dimension(250, 375));
        scrollPane1.setOpaque(true);
        scrollPane1.setPreferredSize(new Dimension(350, 375));
        panel2.add(scrollPane1, BorderLayout.SOUTH);
        textPane1 = new JTextPane();
        textPane1.setContentType("text/html");
        textPane1.setEditable(false);
        scrollPane1.setViewportView(textPane1);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel3.setMinimumSize(new Dimension(650, 30));
        panel3.setPreferredSize(new Dimension(650, 30));
        panel2.add(panel3, BorderLayout.CENTER);
        createButton = new JButton();
        createButton.setHideActionText(false);
        createButton.setHorizontalTextPosition(11);
        createButton.setMaximumSize(new Dimension(200, 30));
        createButton.setText("Create");
        panel3.add(createButton);
        renameButton = new JButton();
        renameButton.setMaximumSize(new Dimension(200, 30));
        renameButton.setText("Rename");
        panel3.add(renameButton);
        moveButton = new JButton();
        moveButton.setMaximumSize(new Dimension(200, 30));
        moveButton.setText("Move");
        panel3.add(moveButton);
        copyButton = new JButton();
        copyButton.setMaximumSize(new Dimension(200, 30));
        copyButton.setText("Copy");
        panel3.add(copyButton);
        removeButton = new JButton();
        removeButton.setMaximumSize(new Dimension(200, 30));
        removeButton.setText("Remove");
        panel3.add(removeButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}