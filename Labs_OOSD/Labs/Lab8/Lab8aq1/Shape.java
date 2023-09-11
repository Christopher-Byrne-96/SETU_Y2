package Lab8aq1;

public abstract class Shape
{
	private String name;
	private String color;
	
	public Shape(String name, String color)
  	{ 
  		this.name = name; 
  		this.color = color; 
  	} 
  	
  	public String toString()
  	{
  		return ("---\nShape Name = " + this.name + "\nShape colour = " + this.color);
  	}
  	
  	public abstract double area();
}
