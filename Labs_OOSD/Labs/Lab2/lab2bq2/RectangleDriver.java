package lab2bq2;


public class RectangleDriver {

	public static void main(String[] args) 
	{
	
		Rectangle rectA = new Rectangle();
		Rectangle rectB = new Rectangle(0.0 , 56.1);
		Rectangle rectC = new Rectangle(10.5 , 27.36);
		Rectangle rectD = new Rectangle();
		
		rectA.setLength(10.33);
		rectA.setWidth(5.46);
		
		rectD.setLength(-10);
		rectD.setWidth(40.0001);
		
		System.out.println( "Rectangle A =====================" );
		System.out.println( rectA.toString() );
		System.out.println( "=================================" );
		System.out.println( "Rectangle B =====================" );
		System.out.println( rectB.toString() );
		System.out.println( "=================================" );
		System.out.println( "Rectangle C =====================" );
		System.out.println( rectC.toString() );
		System.out.println( "=================================" );
		System.out.println( "Rectangle D =====================" );
		System.out.println( rectD.toString() );
		System.out.println( "=================================" );
	

	}

}
