//Written By		::	Christopher Byrne
//Date				::	March 2023
//Purpose			::	CA3 for Dr Jason Baron SETU
//						Design a java applet to manage customers purchases

//The log in window that that allows user to log in as admin or customer depending on the type of account they have 


package CA3;


//import java classes
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

//Start of class
public class LoginWindow extends JFrame {
	
	//Declare variables
	private JLabel nameLabel;
	private JLabel passLabel;
	private JTextField userField;
	private JPasswordField passField;
	private JButton loginButton;
	private JLabel space;
	
	//Constructor to build window 
	public LoginWindow() {
		
		//Call to super class to set window name as login 
		super("Login");
		
		//username label
		nameLabel = new JLabel("username:");
		//pasword label
		passLabel = new JLabel("password:");
		// username text field
		userField = new JTextField(50);
		//password field
		passField = new JPasswordField(50);
		//login button
		loginButton = new JButton("Login");
		//close Button
		space = new JLabel("");
		
		//Adding listener
		loginButton.addActionListener(new LoginListner());
		
		//Building the GUI
		JPanel panel = new JPanel(new GridLayout(3,2));
		panel.add(nameLabel);
		panel.add(userField);
		panel.add(passLabel);
		panel.add(passField);
		panel.add(space);
		panel.add(loginButton);
		
		
		//Displaying window 
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
//Add Cart Listener
///================================================================================================================================
class LoginListner implements ActionListener {
	public void actionPerformed(ActionEvent login) {
		
		//Initializing variables by retrieving field values
		String username = userField.getText();
		String password = new String(passField.getPassword());
		Connection connection = null;
		Statement statement = null;
		
		//Checking the user exists in the database with a query
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT user_type, Name, ID FROM Users WHERE Username = '" + username + "' AND Password = '" + password + "'");
			
			if(resultSet.next()) {
				String userType = resultSet.getString("user_type");
				String name = resultSet.getString("Name");
				int userID = resultSet.getInt("ID");
				
				//If statement to see if the user is customer or admin and building next window based off the results
				if(userType.equals("customer")) {
					dispose();
					new CustomerGUI(userID, name);
				}
				else if(userType.equals("admin")) {
					dispose();
					new AdminGUI(name);
				}
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid Username/Password Combo!");
			}
			connection.close();
		}
		catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error connecting to the database!");
			}
		}
	}

}
