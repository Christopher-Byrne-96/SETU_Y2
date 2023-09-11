package Lab8aq1;

public class Cylinder extends ThreeDShape{
	
	private double height;
	private double radius;

	public Cylinder(String name, String color, double radius, double height) {
		super(name, color);
		this.height = height;
		this.radius = radius;
	}

	@Override
	public double volume() {
		return (3.14*(radius*radius))*(height);
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}
	
  	public String toString()
  	{
  		return (super.toString() + "\nCylinder radius = " + radius + "\nCylinder height = " + height);
  	}
	

}
