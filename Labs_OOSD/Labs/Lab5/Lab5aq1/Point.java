package Lab5aq1;

public class Point {
	
	private int x;
	private int y;
	
	public Point() {
		x = 0;
		y = 0;
	}
	
	public Point(int a, int b) {
		x = a;
		y = b;
	}
	
	public void setX(int a) {
		x = a;
	}
	
	public void setY(int b) {
		y = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "X = " + x + " Y = " +  y;
	}

}
