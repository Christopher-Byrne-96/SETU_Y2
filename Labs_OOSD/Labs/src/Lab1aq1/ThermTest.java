package Lab1aq1;

//Student Name 		:	Christopher Byrne
//Student Id Num	: 	c00276260
//Date 				: 	September 2022
//Purpose 			:	To demonstrate creating and initializing of Class instances 

public class ThermTest 
{

	public static void main(String[] args) 
	{

		Thermometer thermA = new Thermometer();		// Create an instance of our Thermometer class
		Thermometer thermB = new Thermometer(10.0);		// Create a second instance of the Thermometer class
		double tempB = thermB.getCelsius();					
		
		System.out.println("Temp. of Thermometer A is " + thermA.getCelsius() );
		thermA.setCelsius(20.0);
		System.out.println("Temp. of Thermometer A is " + thermA.getCelsius() );
		System.out.println("Temp. of Thermometer B is " + tempB );
		
		
	}

}
