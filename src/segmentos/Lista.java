package segmentos;

import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Lista extends JFrame
{
    private ScrollPane scr;
    private JTextArea jta;
    private Container c;

    public Lista(String s,int max,int x,int y) throws HeadlessException 
    {
        jta=new JTextArea(s,45,(8)*max);
        scr=new ScrollPane();scr.add(jta);scr.setSize(x-15, y-35);
        c=getContentPane();
        c.setLayout(null);c.add(scr);
        scr.setBounds(5, 5, x-15, y-35);
        setVisible(true);
        setSize(x, y);
        setTitle("Lista");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
