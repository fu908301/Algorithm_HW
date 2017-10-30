import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.ArrayList;
import  java.math.*;

public class MainForm extends JFrame{
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JButton setButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton LOADButton;
    private JButton NEXTTESTButton;
    private JButton SAVEButton;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> mid_points = new ArrayList<Point>();
    private double x,y;
    private Point center;
    private int times = 0;
    private File Load_F;
    private BufferedReader br;
    public MainForm(){
        super("TEST");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(600, 600, 600, 600);
        getContentPane().add(panel);
        addMouseListener(m);
        setVisible(true);
        Point A = new Point(),B = new Point(),C = new Point();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(times == 2){
                    two_way();
                }
                else if(times == 3){
                    if(points.get(0).x == points.get(1).x && points.get(0).y == points.get(1).y && points.get(0).x == points.get(2).x && points.get(0).y == points.get(2).y){}
                    else if(which_T(points) == 0) {
                        same_line();
                    }
                    else if(which_T(points) > 0){
                        A.x = points.get(0).x;
                        A.y = points.get(0).y;
                        B.x = points.get(2).x;
                        B.y = points.get(2).y;
                        C.x = points.get(1).x;
                        C.y = points.get(1).y;
                        three_way(A,B,C);
                    }
                    else if(which_T(points) < 0){
                        A.x = points.get(0).x;
                        A.y = points.get(0).y;
                        B.x = points.get(1).x;
                        B.y = points.get(1).y;
                        C.x = points.get(2).x;
                        C.y = points.get(2).y;
                        three_way(A,B,C);
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

        LOADButton.addActionListener(new ActionListener(){
            String Line;
            String[] tempArray= new String[2];
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int returnValue = fc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    Load_F = fc.getSelectedFile();
                    try{
                        FileReader fr = new FileReader(Load_F);
                        br = new BufferedReader(fr);
                        while((Line = br.readLine())!= null){
                            if(!Line.contains("#") && Line.length() > 0){
                                System.out.println(Line);
                                times = Integer.parseInt(Line);
                                for(int i = 0; i < times; i++){
                                    Line = br.readLine();
                                    tempArray = Line.split("\\s");
                                    x = Double.parseDouble(tempArray[0]);
                                    y = Double.parseDouble(tempArray[1]);
                                    Point p = new Point(x, y);
                                    points.add(p);
                                    Graphics2D g2d = (Graphics2D) panel.getGraphics();
                                    Shape s = new Ellipse2D.Double(x, y, 10,10);
                                    g2d.fill(s);
                                    System.out.println(x + " " + y );
                                }
                                break;
                            }
                        }
                    }catch(FileNotFoundException FNF){
                        FNF.getStackTrace();
                    }catch(IOException IOE){
                        IOE.fillInStackTrace();
                    }

                }
            }
        });
        NEXTTESTButton.addActionListener(new ActionListener(){
            String Line;
            String[] tempArray= new String[2];
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    while((Line = br.readLine())!= null){
                        if(!Line.contains("#")){
                            times = Integer.parseInt(Line);
                            for(int i = 0; i < times; i++){
                                Line = br.readLine();
                                tempArray = Line.split("\\s");
                                x = Double.parseDouble(tempArray[0]);
                                y = Double.parseDouble(tempArray[1]);
                                Point p = new Point(x, y);
                                points.add(p);
                                Graphics2D g2d = (Graphics2D) panel.getGraphics();
                                Shape s = new Ellipse2D.Double(x, y, 10,10);
                                g2d.fill(s);
                                System.out.println(x + " " + y );
                            }
                            break;
                        }
                    }
                }catch(IOException IOE){
                    IOE.fillInStackTrace();
                }

            }
        });
    }
    private void two_way(){
        Point p0 = points.get(0), p1 = points.get(1),pmid = new Point(),p2 = new Point(),p3 = new Point() ,p4 = new Point(),p5 = new Point();
        if(p0.x == p1.x && p0.y == p1.y){
            System.out.println("The same point.");
        }
        else{
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
    }

    private void two_way(Point input0,Point input1){
        Point p0 = input0, p1 = input1,pmid = new Point(),p2 = new Point(),p3 = new Point() ,p4 = new Point(),p5 = new Point();
        if(p0.x == p1.x && p0.y == p1.y){
            System.out.println("The same point!");
        }
        else {
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
            draw_line(pmid, p2);
            draw_line(pmid, p3);
            draw_line(pmid, p4);
            draw_line(pmid, p5);
            System.out.println("");
        }
    }

    private void three_way(Point A, Point B, Point C) {
        Point BA,CB,AC,P1,P2,P3;
        center = center(A,B,C);
        BA = new Point(B.x - A.x, B.y - A.y);
        CB = new Point(C.x - B.x, C.y - B.y);
        AC = new Point(A.x - C.x, A.y - C.y);
        P1 = new Point(center.x + -1*(BA.y), center.y + BA.x);
        P2 = new Point(center.x + -1*(CB.y), center.y + CB.x);
        P3 = new Point(center.x + -1*(AC.y), center.y + AC.x);
        draw_line(center,P1);
        draw_line(center,P2);
        draw_line(center,P3);
        length(center,P1);
        length(center,P2);
        length(center,P3);
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

    private double which_T(ArrayList<Point> input){
        double _return;
        _return = (input.get(1).x - input.get(0).x) * (input.get(2).y - input.get(0).y) - (input.get(2).x - input.get(0).x) * (input.get(1).y - input.get(0).y);
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