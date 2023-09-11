// Student:		Christopher Byrne
// Student Num:	C00276260
// Program:		Set Background Color of jFrame window 



package Lab10aq1;

import javax.swing.JFrame;

public class ChangeColourDriver {

	public static void main(String[] args) {
	
		ChangeColour changeColour = new ChangeColour("Button");
		changeColour.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		changeColour.setSize(500, 500);
		changeColour.setVisible(true);

	}//end main

}//end class
