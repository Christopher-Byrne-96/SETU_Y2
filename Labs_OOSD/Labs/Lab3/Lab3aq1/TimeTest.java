package Lab3aq1;

public class TimeTest {

	public static void main(String[] args) {
		
		//Student Name 		:	Christopher Byrne
		//Student Id Num	: 	c00276260
		//Date 				: 	OCT 2022
		
		
		Time t = new Time (23, 59, 59);
		System.out.println(t.toMilitaryString());
		System.out.println(t.toString());
		t.tick();
		System.out.println(t.toMilitaryString());
		System.out.println(t.toString());
	}

}
