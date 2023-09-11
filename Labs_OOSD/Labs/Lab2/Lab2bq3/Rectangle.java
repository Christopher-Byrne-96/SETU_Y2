package Lab2bq3;

public class Rectangle 
{
	//----------------------------------------------------------------------------------
	//-------VARIABLE DECLARATION-------------------------------------------------------
	//----------------------------------------------------------------------------------
		
		private double length;
		private double width;
		private double area;
		private double perimeter;
		private String printW;
		private String printL;
		private String printR;
		
	//----------------------------------------------------------------------------------
	//-------OBJECT CONSTRUCTER---------------------------------------------------------
	//----------------------------------------------------------------------------------

		public Rectangle() 
		{
			length = 1.0;
			width = 1.0;
			area = 0.0;
			perimeter = 0.0;
		}
		
		public Rectangle(double wNum, double lNum)
		{
			if(lNum >0.0 && lNum <= 40.0)
			{
				length = lNum;
			}
			else 
			{
				length = 1.0;
			}
			
			if(wNum >0.0 && wNum <= 40.0)
			{
				width = wNum;
			}
			else
			{
				width = 1.0;
			}
		}
		
	//----------------------------------------------------------------------------------
	//-------SETTER METHODS-------------------------------------------------------------
	//----------------------------------------------------------------------------------
		
		public void setLength(double lNum)  // Set length
		{
			if(lNum > 0.0 && lNum <= 40.0)
			{
				length = lNum;
			}
		}
		
		public void setWidth(double wNum)  // Set width
		{
			if(wNum > 0.0 && wNum <= 40.0)
			{
				width = wNum;
			}
		}
		
	//----------------------------------------------------------------------------------
	//-------GETTER METHODS-------------------------------------------------------------
	//----------------------------------------------------------------------------------
		
		public double getLength()  // Get Length
		{
			return length;
		}
		
		public double getWidth()  // Get Width
		{
			return width;
		}
		
		public double getArea()  // Get Width
		{
			area = length*width;
			return area;
		}
		
		public double getPerimeter()// Get Width
		{
			perimeter = (length*2) + (width*2);
			return perimeter;
		}

	//----------------------------------------------------------------------------------
	//-------TO STRING METHOD-----------------------------------------------------------
	//----------------------------------------------------------------------------------
		
		
		public String toString()
		{
			return "Length = " + length + ", Width = " + width + ", Area = " + getArea() + ", Perimieter = " + getPerimeter();
		}
		
	//----------------------------------------------------------------------------------
	//-------PRINT RECTANGLE METHOD-----------------------------------------------------
	//----------------------------------------------------------------------------------
			
		public String printRectangle()
		{
			printW = "";
			printL = "";
			printR = "";
			width = getWidth() - getWidth()%1;
			length = getLength() - getLength()%1;
			
			//Creating the line to draw for the width
			for(int i = 0; i < width; i++)
			{
				printW = printW + "*";
			}
			
			//Creating the line to draw for the Length
			for(int i = 0; i < width; i++)
			{
				if(i == 0)
				{
					printL = printL + "*";
				}
				else if(i == width-1)
				{
					printL = printL + "*";
				}
				else
				{
					printL = printL + " ";
				}
				
			}
			
			//Print to screen the finished rectangle 
			for(int i = 0; i < length; i++)
			{
				if(i == 0)
				{
					printR = printR + printW + "\r\n";
				}
				else if(i == length-1)
				{
					printR = printR + printW + "\r\n";
				}
				else
				{
					printR = printR + printL + "\r\n";
				}
			}
			
			return printR;
		}
			



}
