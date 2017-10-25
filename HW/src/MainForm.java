import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import  java.math.*;

public class MainForm extends JFrame{
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JButton setButton;
    private JTextField textField1;
    private JTextField textField2;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> mid_points = new ArrayList<Point>();
    private double x,y;
    private Point center;
    private int times = 0;
    public MainForm(){
        super("TEST");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(600, 600, 600, 600);
        getContentPane().add(panel);
        addMouseListener(m);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(times == 2){
                    two_way();
                }
                else if(times == 3){
                    if(which_T(points) == -2) {
                        same_line();
                    }
                    if(which_T(points) == 0) {
                        three_way();
                    }
                    else if(which_T(points) == 1){
                        three_way_2();
                    }
                    else if(which_T(points) == 2){

                    }
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
                points.clear();
                mid_points.clear();
                times = 0;
            }
        });
        setButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                x = (double)Integer.parseInt(textField1.getText());
                y = (double)Integer.parseInt(textField2.getText());
                Point p = new Point(x, y);
                points.add(p);
                Graphics2D g2d = (Graphics2D) panel.getGraphics();
                Shape s = new Ellipse2D.Double(x, y, 10,10);
                g2d.fill(s);
                System.out.println(x + " " + y );
                times++;
            }
        });
    }
    private void two_way(){
        Point p0 = points.get(0), p1 = points.get(1),pmid = new Point(),p2 = new Point(),p3 = new Point() ,p4 = new Point(),p5 = new Point();
        pmid.x = (p0.x + p1.x) / 2;
        pmid.y = (p0.y + p1.y) / 2;
        p2.x = 600;
        p2.y = pmid.y - (p0.x - p1.x) * (600 - pmid.x) / (p0.y - p1.y);
        p3.x = 0;
        p3.y = pmid.y - (p0.x - p1.x) * (0 - pmid.x) / (p0.y - p1.y);
        p4.x = pmid.x - (p0.y - p1.y) * (600 - pmid.y) / (p0.x - p1.x);
        p4.y = 600;
        p5.x = pmid.x - (p0.y - p1.y) * (0 - pmid.y) / (p0.x - p1.x);
        p5.y = 0;
        draw_line(pmid,p2);
        draw_line(pmid,p3);
        draw_line(pmid,p4);
        draw_line(pmid,p5);
    }

    private void two_way(Point input0,Point input1){
        Point p0 = input0, p1 = input1,pmid = new Point(),p2 = new Point(),p3 = new Point() ,p4 = new Point(),p5 = new Point();
        pmid.x = (p0.x + p1.x) / 2;
        pmid.y = (p0.y + p1.y) / 2;
        p2.x = 600;
        p2.y = pmid.y - (p0.x - p1.x) * (600 - pmid.x) / (p0.y - p1.y);
        p3.x = 0;
        p3.y = pmid.y - (p0.x - p1.x) * (0 - pmid.x) / (p0.y - p1.y);
        p4.x = pmid.x - (p0.y - p1.y) * (600 - pmid.y) / (p0.x - p1.x);
        p4.y = 600;
        p5.x = pmid.x - (p0.y - p1.y) * (0 - pmid.y) / (p0.x - p1.x);
        p5.y = 0;
        draw_line(pmid,p2);
        draw_line(pmid,p3);
        draw_line(pmid,p4);
        draw_line(pmid,p5);
    }

    private void three_way(){
        center = center(points.get(0), points.get(1), points.get(2));
        mid_points = mid(points);
        draw_line(center,mid_points);
        length(center,mid_points.get(0));
        length(center,mid_points.get(1));
        length(center,mid_points.get(2));
    }

    private void three_way_2(){
        center = center(points.get(0), points.get(1), points.get(2));
        mid_points = mid(points);
        Line l0 = new Line(center,mid_points.get(0));
        Line l1 = new Line(center,mid_points.get(1));
        Line l2 = new Line(center,mid_points.get(2));
        Line shorest = new Line(),other1 = new Line(),other2 = new Line();
        if(l0.length() < l1.length() && l0.length() < l2.length()){
            shorest = new Line(l0);
            other1 = new Line(l1);
            other2 = new Line(l2);
        }
        else if(l1.length() < l0.length() && l1.length() < l2.length()){
            shorest = new Line(l1);
            other1 = new Line(l0);
            other2 = new Line(l2);
        }
        else if(l2.length() < l1.length() && l2.length() < l0.length()){
            shorest = new Line(l2);
            other1 = new Line(l0);
            other2 = new Line(l1);
        }
        draw_line(other1.a,other1.b);
        length(other1.a,other1.b);
        draw_line(other2.a,other2.b);
        length(other2.a,other2.b);
        double x,y;
        x = 2 * shorest.a.x - shorest.b.x;
        y = 2 * shorest.a.y - shorest.b.y;
        Point temp = new Point(x,y);
        draw_line(shorest.a, temp);
        length(shorest.a, temp);
    }

    private void same_line(){
        Line l0,l1,l2,max = new Line(),other1 = new Line(),other2 = new Line();
        l0 = new Line(points.get(0),points.get(1));
        l1 = new Line(points.get(0),points.get(2));
        l2 = new Line(points.get(1),points.get(2));
        if(l0.length() > l1.length() && l0.length() > l2.length()){
            max = new Line(l0);
            other1 = new Line(l1);
            other2 = new Line(l2);
        }
        else if(l1.length() > l0.length() && l1.length() > l2.length()){
            max = new Line(l1);
            other1 = new Line(l0);
            other2 = new Line(l2);
        }
        else if(l2.length() > l1.length() && l2.length() > l0.length()){
            max = new Line(l2);
            other1 = new Line(l0);
            other2 = new Line(l1);
        }
        two_way(other1.a,other1.b);
        two_way(other2.a,other2.b);
    }

    private void special_T(){

    }
    private int which_T(ArrayList<Point> input){
        Line l1,l2,l3;
        int _return = -1;
        double le1,le2,le3,temp;
        l1 = new Line(input.get(0),input.get(1));
        l2 = new Line(input.get(0),input.get(2));
        l3 = new Line(input.get(1),input.get(2));
        le1 = l1.length();
        le2 = l2.length();
        le3 = l3.length();
        if(le1 < le2){
            temp = le1;
            le1 = le2;
            le2 = temp;
        }
        if(le1 < le3){
            temp = le1;
            le1 = le3;
            le3 = temp;
        }
        if((input.get(0).x - input.get(1).x) / (input.get(0).y - input.get(1).y) == (input.get(1).x - input.get(2).x) / (input.get(1).y - input.get(2).y))
            _return = -2;
        else if(Math.pow(le2,2) + Math.pow(le3,2) > Math.pow(le1,2))
            _return = 0;
        else if(Math.pow(le2,2) + Math.pow(le3,2) < Math.pow(le1,2))
            _return = 1;
        else if(Math.pow(le2,2) + Math.pow(le3,2) == Math.pow(le1,2))
            _return = 2;
        return _return;
    }
    private void length(Point p1, Point p2){
        double disx,disy,finalx = p2.x,finaly = p2.y;
        disx = p2.x - p1.x;
        disy = p2.y - p1.y;
        while(true){
            if(finalx >= 600 || finaly >=600 || finalx <= 0 || finaly <= 0)
                break;
            finalx += disx;
            finaly += disy;
        }
        Point finalp = new Point(finalx, finaly);
        draw_line(p2,finalp);
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
            times++;
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
    private void draw_line(Point p1,Point p2){
        Graphics2D g2d = (Graphics2D) panel.getGraphics();
        g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
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

    public Line(Line input){
        this.a = input.a;
        this.b = input.b;
    }

    public void set_Line(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    public void print(){
        System.out.print(a.return_x() + " " + a.return_y() + " " + b.return_x() + " " + b.return_y());
    }

    double length(){
        return Math.sqrt(Math.pow(this.a.x - this.b.x,2) + Math.pow(this.a.y - this.b.y,2));
    }
}