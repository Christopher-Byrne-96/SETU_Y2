package Lab8aq1;

public abstract class TwoDShape extends Shape
{
	public TwoDShape(String name, String color)
  	{ 
  		super(name, color); 
  	}
 
  	public abstract double area();
  	
  	public String toString()
  	{
  		return (super.toString());
  	}
}