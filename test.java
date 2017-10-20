import java.util.*;

public class test{
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		double x,y;
		Point p1,p2,p3,p4,in;
		Line l1,l2;
		System.out.println("Please input point");		

        System.out.println("Point 1");
		x = scan.nextDouble();
		y = scan.nextDouble();
		p1 = new Point(x, y);

		System.out.println("Point 2");
		x = scan.nextDouble();
        y = scan.nextDouble();
        p2 = new Point(x, y);

		System.out.println("Point 3");
		x = scan.nextDouble();
        y = scan.nextDouble();
        p3 = new Point(x, y);

		System.out.println("Point 4");
		x = scan.nextDouble();
        y = scan.nextDouble();
        p4 = new Point(x, y);

		l1 = new Line(p1, p2);
		l2 = new Line(p3, p4);
		
		in = intersection(l1, l2);

		System.out.println("Intersection is " + in.return_x() + " " + in.return_y());
	}
	static Point intersection(Line u, Line v){ 
    	Point res = u.a;
        double t = ((u.a.x-v.a.x)*(v.b.y-v.a.y)-(u.a.y-v.a.y)*(v.b.x-v.a.x)    )/((u.a.x-u.b.x)*(v.b.y-v.a.y)-(u.a.y-u.b.y)*(v.b.x-v.a.x));
        res.x += (u.b.x-u.a.x)*t;
        res.y += (u.b.y-u.a.y)*t;
        return res;
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
}
