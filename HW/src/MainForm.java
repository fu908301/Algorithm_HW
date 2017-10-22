import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class MainForm extends JFrame{
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    int x,y;

    public MainForm(){
        super("TEST");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(800, 800, 800, 800);
        getContentPane().add(panel);
        addMouseListener(m);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test success");
            }
        });
    }

    private MouseListener  m = new MouseListener(){
        public void mousePressed(MouseEvent e){
            x = e.getX();
            y = e.getY();
            Graphics2D g2d = (Graphics2D) panel.getGraphics();
            Shape s = new Ellipse2D.Float(x, y, 10,10);
            g2d.fill(s);
            System.out.println(x + " " + y );
        }
        public void mouseReleased(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mouseClicked(MouseEvent e){}
    };
    public static void main(String args[]){
        MainForm m;
        m = new MainForm();
    }
}
