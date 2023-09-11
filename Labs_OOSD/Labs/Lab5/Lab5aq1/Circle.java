package Lab5aq1;

public class Circle extends Point {
	
	private int radius ;

	public Circle() {
		super();
		radius = 0;
	}
	
	public Circle(int x, int y, int r) {
		super(x,y);
		radius = r;
	}
	
	public void setRadius(int r) {
		this.radius = r;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public String toString() {
		return "X = " + this.getX() + " Y = " + this.getY() + " R = " + radius;
	}
	

	

}
