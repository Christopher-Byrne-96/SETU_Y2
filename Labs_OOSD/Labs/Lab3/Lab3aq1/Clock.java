package Lab3aq1;

import java.util.Calendar;

public class Clock {
	
	//Student Name 		:	Christopher Byrne
	//Student Id Num	: 	c00276260
	//Date 				: 	OCT 2022

	public static void main(String[] args) {
		
		
		long v = 0;
		
		Calendar cal = Calendar.getInstance();
		
		int i = cal.get(Calendar.MINUTE);
		int m = i;
		
		Time t = new Time (cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

		loop: while(true)
		{
		
			
			long s = System.currentTimeMillis()/1000;
			
			if(s > v) {
				v = s;
				t.tick();
				System.out.println(t.toString() + " is the bloody time!");
				m = t.getMinute();
			}
			
			if(i != m) {
				break loop;
			}
			
			
			
		}
		
		Runtime.getRuntime().exit(0);
	}
	
}
