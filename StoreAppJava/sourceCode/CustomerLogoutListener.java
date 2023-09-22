//Written By		::	Christopher Byrne
//Date				::	March 2023
//Purpose			::	CA3 for Dr Jason Baron SETU
//						Design a java applet to manage customers purchases


//This listener was originally in the CustomerGUI Class but stopped working until moved to its own class


package CA3;

//import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Start of class
public class CustomerLogoutListener implements ActionListener {

	//Declare variables
private CustomerGUI window;
	
//Constructor takes CustomerGUI window as argument
	public CustomerLogoutListener(CustomerGUI customerGUI) {
		//Initializing the varibale
		window = customerGUI;
	}

	//Action to be performed 
	public void actionPerformed(ActionEvent e) {
		
		//New login window and close the old window
		new LoginWindow();
		window.dispose();
	}

}
