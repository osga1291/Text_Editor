import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import java.util.*;
public class TextEditor extends JFrame implements ActionListener{

    JTextArea text;
    JFrame frame;
    SpellChecker spell = new SpellChecker();
    TextEditor() {
        frame = new JFrame("editor");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {

        }
        text = new JTextArea();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem m1 = new JMenuItem("New");
        JMenuItem m2 = new JMenuItem("Open");
        JMenuItem m3 = new JMenuItem("Save");

        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);

        menu.add(m1);
        menu.add(m2);
        menu.add(m3);

        JMenu menu2 = new JMenu("Edit");

        JMenuItem m4 = new JMenuItem("cut");
        JMenuItem m5 = new JMenuItem("copy");
        JMenuItem m6 = new JMenuItem("paste");

        m4.addActionListener(this);
        m5.addActionListener(this);
        m6.addActionListener(this);

        menu2.add(m4);
        menu2.add(m5);
        menu2.add(m6);

        JMenuItem close = new JMenuItem("close");

        close.addActionListener(this);

        menuBar.add(menu);
        menuBar.add(menu2);
        menuBar.add(close);



        //frame.show();

        //public void LastWord(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                        UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                frame.add(new TestPane());
                frame.setJMenuBar(menuBar);
                frame.setSize(500,500);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.show();


            }
        });
    }

    public class TestPane extends JPanel{
        public TestPane(){
            setLayout( new BorderLayout());
            JTextArea ta = new JTextArea(10,20);
            add(new JScrollPane(ta));
            JLabel lastWord = new JLabel("...");
            add(lastWord, BorderLayout.SOUTH);

            ta.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkLastWord();
                }


                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkLastWord();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    checkLastWord();

                }
                protected void checkLastWord(){
                    try {
                        int start = Utilities.getWordStart(ta, ta.getCaretPosition());
                        int end = Utilities.getWordEnd(ta,ta.getCaretPosition());
                        String text = ta.getDocument().getText(start,end-start);
                        lastWord.setText(text);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }


    }
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();

        if(s.equals("cut")){
            text.cut();
        }
        else if (s.equals("copy")){
            text.copy();
        }
        else if (s.equals("paste")){
            text.paste();
        }

        else if(s.equals("Save")){
            JFileChooser j = new JFileChooser("f:");

            int r = j.showSaveDialog(null);

            if(r == JFileChooser.APPROVE_OPTION){
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try{
                    FileWriter wr = new FileWriter(fi,false);

                    BufferedWriter w = new BufferedWriter(wr);

                    w.write(text.getText());
                    w.flush();
                    w.close();
                }
                catch (Exception evt){
                    JOptionPane.showMessageDialog(frame,evt.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(frame,"the user cancelled the operation."); }
        else if(s.equals("Open")){
            JFileChooser j = new JFileChooser("f:");

            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION){
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try{
                    String s1 = "";

                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);

                    s1 = br.readLine();

                    while((s1 = br.readLine()) != null){
                        s1 = s1 + "\n" + s1;
                    }

                    text.setText(s1); }
                catch(Exception evt){
                    JOptionPane.showMessageDialog(frame,evt.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(frame,"the user cancelled the operation.");
        }
        else if(s.equals("New")){
            text.setText("");
        }
        else if (s.equals("close")){
            frame.setVisible(false);
        }

    }
}
