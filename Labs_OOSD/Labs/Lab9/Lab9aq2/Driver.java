package Lab9aq2;

public class Driver {

	public static void main(String[] args) {
	
	RoadVehicle[] myArr = new RoadVehicle[] {new Car("Yaris", 4, 2), new HGV(4000, 12, 3)};
	
	System.out.println(myArr[0].calculateDuty());	// for type car this should equal 100
	System.out.println(myArr[1].calculateDuty());	// for type HGV this should equal 150
			 
	}

}
