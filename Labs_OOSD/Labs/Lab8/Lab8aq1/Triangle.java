package Lab8aq1;

public class Triangle extends TwoDShape {
	
	private double height;
	private double base;

	public Triangle(String name, String color, double base, double height) {
		super(name, color);
		this.base = base;
		this.height = height;
		
	}

	@Override
	public double area() {
		return 0.5*base*height;
	}
	
	public String toString() {
		return (super.toString() + "\nTriangle base = " + base + "\nTriangle height = " + height);
		
	}
	

}
