package Lab8aq1;

public class Circle extends TwoDShape{
	private double radius;

	public Circle(String name, String color, double radius) {
		super(name, color);
		this.radius = radius;
		
	}

	@Override
	public double area() {
		
		return 3.14*(radius*radius);
	}
	
  	public String toString()
  	{
  		return (super.toString() + "\nCircle radius = " + radius);
  	}
}
