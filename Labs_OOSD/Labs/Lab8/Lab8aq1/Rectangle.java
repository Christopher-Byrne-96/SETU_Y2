package Lab8aq1;

public class Rectangle extends TwoDShape {
	 private double length;
	 private double width;
	

	public Rectangle(String name, String color, double length, double width) {
		super(name, color);
		this.length = length;
		this.width = width;
		
	}
	




	@Override
	public double area() {
		return length*width;
	}
	
  	public String toString()
  	{
  		return (super.toString() + "\nRectangle length = " + length + "\nRectangle width = " + width);
  	}
	
}
