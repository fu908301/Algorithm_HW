import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class MainForm extends JFrame{
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> mid_points = new ArrayList<Point>();
    double x,y;
    Point center;

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
                center = center(points.get(0), points.get(1), points.get(2));
                mid_points = mid(points);
                draw_line(center,mid_points);
            }
        });
    }

    private MouseListener  m = new MouseListener(){
        public void mousePressed(MouseEvent e){
            x = e.getX();
            y = e.getY();
            Point p = new Point(x, y);
            points.add(p);
            Graphics2D g2d = (Graphics2D) panel.getGraphics();
            Shape s = new Ellipse2D.Double(x, y, 10,10);
            g2d.fill(s);
            System.out.println(x + " " + y );
        }
        public void mouseReleased(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mouseClicked(MouseEvent e){}
    };

    private Point intersection(Line u, Line v){
        Point res = u.a;
        double t = ((u.a.x-v.a.x)*(v.b.y-v.a.y)-(u.a.y-v.a.y)*(v.b.x-v.a.x))/((u.a.x-u.b.x)*(v.b.y-v.a.y)-(u.a.y-u.b.y)*(v.b.x-v.a.x));
        res.x += (u.b.x-u.a.x)*t;
        res.y += (u.b.y-u.a.y)*t;
        return res;
    }

    private Point center(Point a,Point b,Point c) {
        Line u = new Line(),v = new Line();
        u.a.x=(a.x+b.x)/2;
        u.a.y=(a.y+b.y)/2;
        u.b.x=u.a.x+(u.a.y-a.y);
        u.b.y=u.a.y-(u.a.x-a.x);
        v.a.x=(a.x+c.x)/2;
        v.a.y=(a.y+c.y)/2;
        v.b.x=v.a.x+(v.a.y-a.y);
        v.b.y=v.a.y-(v.a.x-a.x);
        return intersection(u,v);
    }

    private ArrayList<Point> mid(ArrayList<Point> input){
        Point temp;
        double tempx,tempy;
        ArrayList<Point> mid_p = new ArrayList<Point>();
        for(int i = 0; i < input.size() - 1; i++){
            for(int j = i + 1; j < input.size(); j++){
                tempx = (input.get(i).x + input.get(j).x) / 2;
                tempy = (input.get(i).y + input.get(j).y) / 2;
                temp = new Point(tempx,tempy);
                mid_p.add(temp);
            }
        }
        for(int i = 0; i < mid_p.size(); i++)
            System.out.println(mid_p.get(i).x + " " + mid_p.get(i).y);
        return mid_p;
    }

    private void draw_line(Point center,ArrayList<Point> input){
        Graphics2D g2d = (Graphics2D) panel.getGraphics();
        System.out.println("Mid points : " + input.size());
        for(int i = 0; i < input.size(); i++) {
            g2d.drawLine((int) center.x, (int) center.y, (int) input.get(i).x, (int) input.get(i).y);
        }
    }

    public static void main(String args[]){
        MainForm m;
        m = new MainForm();
    }
}

class Point{
    double x;
    double y;

    public Point(){
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void set_Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void print(){
        System.out.println(x + " " + y);
    }

    double return_x(){
        return x;
    }

    double return_y(){
        return y;
    }
}

class Line{
    Point a;
    Point b;

    public Line(){
        this.a = new Point();
        this.b = new Point();
    }

    public Line(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    public void set_Line(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    public void print(){
        System.out.print(a.return_x() + " " + a.return_y() + " " + b.return_x() + " " + b.return_y());
    }

    Point intersection(Line input){
        Point res = this.a;
        double t = ((this.a.x-input.a.x)*(input.b.y-input.a.y)-(this.a.y-input.a.y)*(input.b.x-input.a.x))/((this.a.x-this.b.x)*(input.b.y-input.a.y)-(this.a.y-this.b.y)*(input.b.x-input.a.x));
        res.x += (this.b.x-this.a.x)*t;
        res.y += (this.b.y-this.a.y)*t;
        return res;
    }
}