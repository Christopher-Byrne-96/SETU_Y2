package Lab8aq1;

public class Sphere extends ThreeDShape{
	

	private double radius;
	
	public Sphere(String name, String color, double radius) {
		super(name, color);
		this.radius = radius;
	
	}

	@Override
	public double volume() {
		return (4/3)*3.14*(radius*radius*radius);
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}

  	public String toString()
  	{
  		return (super.toString() + "\nSphere radius = " + radius);
  	}
}
