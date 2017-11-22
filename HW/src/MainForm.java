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
import java.util.Collections;

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
    private JButton STEPButton;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> mid_points = new ArrayList<Point>();
    private ArrayList<Line> line = new ArrayList<Line>();
    private ArrayList<Line> convex_hull = new ArrayList<Line>();
    private double x,y;
    private Point center;
    private int times = 0;
    private File Load_F;
    private BufferedReader br;
    public MainForm(){
        super("TEST");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(700, 700, 700, 700);
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
                   three_way(points);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
                points.clear();
                mid_points.clear();
                line.clear();
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
            String[] tempArray= new String[5];
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
                            if(Line.contains("P")){
                                tempArray = Line.split("\\s");
                                x = Double.parseDouble(tempArray[1]);
                                y = Double.parseDouble(tempArray[2]);
                                Point P = new Point(x,y);
                                P.draw_point(panel);
                            }
                            else if(Line.contains("E")){
                                tempArray = Line.split("\\s");
                                double p1x = Double.parseDouble(tempArray[1]);
                                double p1y = Double.parseDouble(tempArray[2]);
                                double p2x = Double.parseDouble(tempArray[3]);
                                double p2y = Double.parseDouble(tempArray[4]);
                                Point P1 = new Point(p1x, p1y);
                                Point P2 = new Point(p2x, p2y);
                                draw_line(P1, P2);
                            }
                            else if(!Line.contains("#") && Line.length() > 0){
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
        SAVEButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                sort();
                try{
                    FileWriter fw = new FileWriter("Test.txt");
                    for(int i = 0; i < points.size(); i++){
                        fw.write("P " + " " + points.get(i).x + " " + points.get(i).y + "\r\n");
                    }
                    fw.flush();
                    if(line.size() > 0)
                        for(int i = 0; i < line.size(); i++){
                            fw.write("E " + " " + line.get(i).a.x + " " + line.get(i).a.y + " " + line.get(i).b.x + " " + line.get(i).b.y + "\r\n");
                        }
                    fw.flush();
                    fw.close();
                }catch (IOException IOE){
                    IOE.fillInStackTrace();
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
        STEPButton.addActionListener(new ActionListener(){
            String Line;
            String[] tempArray= new String[2];
            @Override
            public void actionPerformed(ActionEvent e){

            }
        });
    }

    private void two_way(){
        Point p0 = points.get(0), p1 = points.get(1),pmid = new Point();
        double temp_x,temp_y;
        if(p0.x == p1.x && p0.y == p1.y){
            System.out.println("The same point.");
        }
        else{
            pmid.x = (p0.x + p1.x) / 2;
            pmid.y = (p0.y + p1.y) / 2;
            temp_x = -1 * (p1.y - p0.y);
            temp_y = (p1.x - p0.x);
            Point temp1 = new Point(pmid.x + temp_x, pmid.y + temp_y);
            Point temp2 = new Point(pmid.x - temp_x, pmid.y - temp_y);
            draw_line(pmid, temp1);
            draw_line(pmid, temp2);
            Point tempP1 = length(pmid, temp1, 1);
            Point tempP2 = length(pmid, temp2, 1);
            Line templ = new Line(tempP1, tempP2);
            line.add(templ);
        }
    }

    private Line two_way(Point input0,Point input1,int tag){
        Point p0 = input0, p1 = input1,pmid = new Point(),p2 = new Point(),p3 = new Point() ,p4 = new Point(),p5 = new Point();
        double temp_x,temp_y;
        Line templ = new Line();
        if(p0.x == p1.x && p0.y == p1.y){
            System.out.println("The same point!");
        }
        else {
            pmid.x = (p0.x + p1.x) / 2;
            pmid.y = (p0.y + p1.y) / 2;
            temp_x = -1 * (p1.y - p0.y);
            temp_y = (p1.x - p0.x);
            Point temp1 = new Point(pmid.x + temp_x, pmid.y + temp_y);
            Point temp2 = new Point(pmid.x - temp_x, pmid.y - temp_y);
            if(tag == 1){
                draw_line(pmid, temp1);
                draw_line(pmid, temp2);
            }
            length(pmid, temp1, 1);
            length(pmid, temp2, 1);
            System.out.println("");
            Point tempP1 = length(pmid, temp1, 1);
            Point tempP2 = length(pmid, temp2, 1);
            templ = new Line(tempP1, tempP2);
            line.add(templ);
        }
        return templ;
    }

    private void three_way(ArrayList<Point> input) {
        Point A = new Point(),B = new Point(),C = new Point(),BA,CB,AC,P1,P2,P3;
        if(input.get(0).x == input.get(1).x && input.get(0).y == input.get(1).y && input.get(0).x == input.get(2).x && input.get(0).y == input.get(2).y){}
        else if(which_T(input) == 0) {
            same_line();
        }
        else if(which_T(input) > 0){
            A.x = input.get(0).x;
            A.y = input.get(0).y;
            B.x = input.get(2).x;
            B.y = input.get(2).y;
            C.x = input.get(1).x;
            C.y = input.get(1).y;
        }
        else if(which_T(input) < 0){
            A.x = input.get(0).x;
            A.y = input.get(0).y;
            B.x = input.get(1).x;
            B.y = input.get(1).y;
            C.x = input.get(2).x;
            C.y = input.get(2).y;
        }
        center = new Point();
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
        System.out.print("Center ");
        center.print();
        System.out.println("P1 ");
        P1.print();
        System.out.println("P2");
        P2.print();
        System.out.println("P3");
        P3.print();
        Point temp_P1 = length(center,P1, 1);
        Point temp_P2 = length(center,P2, 1);
        Point temp_P3 = length(center,P3, 1);
        Line temp_L1 = new Line(center, temp_P1);
        Line temp_L2 = new Line(center, temp_P2);
        Line temp_L3 = new Line(center, temp_P3);
        line.add(temp_L1);
        line.add(temp_L2);
        line.add(temp_L3);
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
        two_way(other1.a,other1.b, 1);
        two_way(other2.a,other2.b, 1);
    }

    private double which_T(ArrayList<Point> input){
        double _return;
        _return = (input.get(1).x - input.get(0).x) * (input.get(2).y - input.get(0).y) - (input.get(2).x - input.get(0).x) * (input.get(1).y - input.get(0).y);
        return _return;
    }

    private Point length(Point p1, Point p2, int tag){
        double disx,disy,finalx = p2.x,finaly = p2.y;
        disx = p2.x - p1.x;
        disy = p2.y - p1.y;
        finalx = finalx + 1000 * disx;
        finaly = finaly + 1000 * disy;
        Point finalp = new Point(finalx, finaly);
        System.out.println("final ");
        finalp.print();
        if(tag == 1)
            draw_line(p2,finalp);
        return finalp;
    }

    private MouseListener  m = new MouseListener(){
        public void mousePressed(MouseEvent e){
            x = e.getX();
            y = e.getY();
            Point p = new Point(x, y);
            points.add(p);

            Graphics2D g2d = (Graphics2D) panel.getGraphics();
            Shape s = new Ellipse2D.Double(x, y, 8,8);
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
    private void sort(){
        for(int i = 0; i < points.size() - 1; i++){
            for(int j = 0; j < points.size() - 1 - i; j++){
                if(points.get(j).y > points.get(j+1).y){
                    Collections.swap(points, j, j+1);
                }
            }
        }
        for(int i = 0; i < points.size() - 1; i++){
            for(int j = 0; j < points.size() - 1 - i; j++){
                if(points.get(j).x > points.get(j+1).x){
                    Collections.swap(points, j, j+1);
                }
            }
        }
        if(line.size() > 1)
            for(int i = 0; i < points.size() - 1; i++){
                for(int j = 0; j < points.size() - 1 - i; j++){
                    if(line.get(j).b.y > line.get(j+1).b.y){
                        Collections.swap(line, j, j+1);
                    }
                }
            }
        if(line.size() > 1)
            for(int i = 0; i < points.size() - 1; i++){
                for(int j = 0; j < points.size() - 1 - i; j++){
                    if(line.get(j).b.x > line.get(j+1).b.x){
                        Collections.swap(line, j, j+1);
                    }
                }
            }
        if(line.size() > 1)
            for(int i = 0; i < points.size() - 1; i++){
                for(int j = 0; j < points.size() - 1 - i; j++){
                    if(line.get(j).a.y > line.get(j+1).a.y){
                        Collections.swap(line, j, j+1);
                    }
                }
            }
        if(line.size() > 1)
            for(int i = 0; i < points.size() - 1; i++){
                for(int j = 0; j < points.size() - 1 - i; j++){
                    if(line.get(j).a.x > line.get(j+1).a.x){
                        Collections.swap(line, j, j+1);
                    }
                }
            }
    }
    // 小於。用以找出最低最左邊的點。
    boolean compare(Point a, Point b) {
        return (a.y < b.y) || (a.y == b.y && a.x < b.x);
    }

    // 向量OA叉積向量OB。大於零表示從OA到OB為逆時針旋轉。
    double cross(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    // 兩點距離的平方
    double length2(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
    boolean far(Point o, Point a, Point b) {
        return length2(o, a) > length2(o, b);
    }

    void convex_hull(ArrayList<Point> P_Left, ArrayList<Point> P_Right){
        ArrayList<Point> P = new ArrayList<Point>();
        ArrayList<Point> points2 = new ArrayList<Point>();//use for convex hull
        for(int i = 0; i < P_Left.size(); i++){
            P.add(P_Left.get(i));
        }
        for(int i = 0; i < P_Right.size(); i++){
            P.add(P_Right.get(i));
        }

        int start = 0;
        for (int i = 0; i < P.size(); i++)
            if (compare(P.get(i),P.get(start)))
                start = i;
        int m = 0;
        points2.add(P.get(start));
        while (true){
            m++;
            int next = start;
            for(int i = 0; i < P.size(); i++){
                double c = cross(points2.get(m-1), P.get(i), P.get(next));
                if(c > 0 || (c == 0 && far(points2.get(m-1), P.get(i), P.get(next))))
                    next = i;
            }

            if(next == start) break;
            points2.add(P.get(next));
        }

        draw_line(points2.get(0), points2.get(points2.size()-1));
        Line temp = new Line(points2.get(0), points2.get(points2.size()-1));
        convex_hull.add(temp);
        for(int i = 0; i < points2.size() - 1; i++){
            temp = new Line(points2.get(i), points2.get(i+1));
            convex_hull.add(temp);
            draw_line(points2.get(i), points2.get(i+1));
            System.out.println(points2.get(i).x + " " + points2.get(i).y + "      " + points2.get(i+1).x + " " + points2.get(i+1).y);
        }
    }
    //在divid and conquer 之前先把點按照x軸做排序
    ArrayList<Point> divid_sort(ArrayList<Point> input){
        for(int i = 0; i < input.size() - 1; i++){
            for(int j = 0; j < input.size() - 1 - i; j++){
                if(input.get(j).x > input.get(j+1).x){
                    Collections.swap(input, j, j+1);
                }
            }
        }
        return input;
    }

    void divid_conquer(int times,ArrayList<Point> input){
        int left = 0,right = 0;
        ArrayList<Point> P_Left = new ArrayList<Point>();
        ArrayList<Point> P_Right = new ArrayList<Point>();
        if(times > 3 && times % 2 != 0){
            left = times / 2 + 1;
            right = times / 2;
        }
        else if(times > 3 && times % 2 == 0){
            left = times / 2 ;
            right = times / 2;
        }
        for(int i = 0; i < left; i++){
            P_Left.add(input.get(i));
        }
        for(int i = left; i < left + right; i++){
            P_Left.add(input.get(i));
        }
        if(left > 3){
            divid_conquer(left,P_Left);
        }
        if(right > 3){
            divid_conquer(right,P_Right);
        }

    }
    void merge(ArrayList<Point> P_Left, ArrayList<Point> P_Right){
        Line line1 = new Line(), line2 = new Line(),convexlinetop,convexlinedown;
        ArrayList<Line> temp = new ArrayList<Line>();
        if(P_Left.size() == 2){
            line1 = two_way(P_Left.get(0), P_Left.get(1),0);
        }
        if(P_Right.size() == 2){
            line2 = two_way(P_Right.get(0), P_Right.get(1),0);
        }
        convex_hull(P_Left,P_Right);
        for(int i = 0; i < convex_hull.size(); i++){
            for(int j = 0; j < P_Left.size(); j++){
                for(int k = 0; k < P_Right.size(); k++ ){
                    if((convex_hull.get(i).a.x == P_Left.get(j).x && convex_hull.get(i).a.y == P_Left.get(j).y && convex_hull.get(i).b.x == P_Right.get(k).x && convex_hull.get(i).b.y == P_Right.get(k).y) || (convex_hull.get(i).b.x == P_Left.get(j).x && convex_hull.get(i).b.y == P_Left.get(j).y && convex_hull.get(i).a.x == P_Right.get(k).x && convex_hull.get(i).a.y == P_Right.get(k).y)){
                        temp.add(convex_hull.get(i));
                    }
                }
            }
        }
        if(temp.get(0).a.y > temp.get(1).a.y){
            convexlinetop = new Line(temp.get(0).a,temp.get(0).b);
            convexlinedown = new Line(temp.get(1).a,temp.get(1).b);
        }
        else {
            convexlinetop = new Line(temp.get(1).a,temp.get(1).b);
            convexlinedown = new Line(temp.get(0).a,temp.get(0).b);
        }
        Line left = two_way(P_Left.get(0), P_Left.get(1),0);
        Line Right = two_way(P_Right.get(0), P_Right.get(1),0);

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

    public double return_x(){
        return x;
    }

    public double return_y(){
        return y;
    }
    public void draw_point(JPanel panel){
        Graphics2D g2d = (Graphics2D) panel.getGraphics();
        Shape s = new Ellipse2D.Double(x, y, 10,10);
        g2d.fill(s);
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