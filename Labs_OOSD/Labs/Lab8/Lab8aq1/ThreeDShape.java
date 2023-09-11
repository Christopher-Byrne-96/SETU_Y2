package Lab8aq1;

public abstract class ThreeDShape extends Shape
{
	public ThreeDShape(String name, String color)
  	{ 
  		super(name, color); 
  	}
 
  	public abstract double volume();
  	
  	public String toString()
  	{
  		return (super.toString());
  	}
}
