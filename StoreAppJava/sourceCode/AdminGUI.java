//Written By		::	Christopher Byrne
//Date				::	March 2023
//Purpose			::	CA3 for Dr Jason Baron SETU
//						Design a java applet to manage customers purchases

//The design of the java gui to be shown if admin login
// This file displays a combo box of items and allows the admin to update product info or delete items

package CA3;

//import java classes
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;


//Start of class
public class AdminGUI extends JFrame {
	
	//Declare variables
	private JLabel userName;
	private JLabel prodID;
	private JLabel prodName;
	private JLabel prodPrice;
	private JLabel prodDesc;
	private JLabel checkBox;
	private JTextField showID;
	private JTextField enterName;
	private JTextField enterPrice;
	private JTextField enterDesc;
	private JComboBox<String> prodList;
	private JButton updateProd;
	private JButton delProd;
	private JButton logout;
	private JCheckBox toggle;
	
	//GUI contructor method takes the name of the logged in user as argument
	public AdminGUI(String name) throws SQLException {
		
		//Call constructor on super class and pas through window name 
		super("Admin View");
		
		//Initializing variables and gui components
		String adminName = name;
		userName = new JLabel("Admin: " + name);
		prodID = new JLabel("Product ID");
		prodName = new JLabel("Product Name");
		prodPrice = new JLabel("Price");
		prodDesc = new JLabel("Short Description");
		checkBox = new JLabel("Toggle Edit Mode");
		showID = new JTextField(50);
		enterName = new JTextField(50);
		enterPrice = new JTextField(50);
		enterDesc = new JTextField(50);
		prodList = new JComboBox<String>();
		updateProd = new JButton("Update");
		delProd = new JButton("Delete");
		logout = new JButton("Logout");
		toggle = new JCheckBox();		
    	Connection connection = null;
		Statement statement = null;
		
		//Initializing state of GUI components
		showID.setEditable(false);
		enterName.setEditable(false);
		enterPrice.setEditable(false);
		enterDesc.setEditable(false);
		updateProd.setEnabled(false);
		
		//Set dimensions of combo box
		prodList.setPreferredSize(new Dimension(500, 30));
		
		//Populate combobox with database query
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT Name FROM Products WHERE deleted = 0");
			while(rs.next()) {
				String listName = rs.getString("Name");
				prodList.addItem(listName);
			}
		}
		// Catch exception on failure to connect to DB
		catch (SQLException e){
			JOptionPane.showMessageDialog(null,"Error connecting to database!" );
		}
		//Close connection
		finally {
			connection.close();
			statement.close();
		}
		
		//Adding listeners
		prodList.addItemListener(new ItemChangeListener());
		toggle.addItemListener(new CheckBoxListener());
		updateProd.addActionListener(new UpdateListener(adminName, this));
		delProd.addActionListener(new DeleteListener(adminName, this));
		logout.addActionListener(new AdminLogoutListener(this));
		
		//Building the gui
		JPanel panel = new JPanel(new BorderLayout());
		JPanel combo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		combo.add(prodList);
		combo.add(checkBox);
		combo.add(toggle);
		JPanel r1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r1.add(prodID);
		r1.add(showID);
		JPanel r2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r2.add(prodName);
		r2.add(enterName);
		JPanel r3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r3.add(prodPrice);
		r3.add(enterPrice);
		JPanel r4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r4.add(prodDesc);
		r4.add(enterDesc);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(updateProd);
		buttons.add(delProd);
		buttons.add(logout);
		
		center.add(combo);
		center.add(r1);
		center.add(r2);
		center.add(r3);
		center.add(r4);
		
		panel.add(userName, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);
		
		
		//Setting up the panel 
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
		
	}
	
	//Combo Listener
	///================================================================================================================================
	//https://stackoverflow.com/questions/58939/jcombobox-selection-change-listener 
	class ItemChangeListener implements ItemListener{
	    @Override
	    public void itemStateChanged(ItemEvent event) {
	    	int id = 0;
	    	String prodDesc = "";
	    	double prodPrice = 0.0;
	    	
	       if (event.getStateChange() == ItemEvent.SELECTED) {
	          Object item = event.getItem();
	          String prodName = (String) item;
	          try {
	  			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
	  			Statement statement = connection.createStatement();
	  			ResultSet rs = statement.executeQuery("SELECT ID, Price, Short_Description FROM Products WHERE Name = '" + prodName + "'" );
	  					while(rs.next()) {
	  						id = rs.getInt("ID");
	  						prodPrice = rs.getDouble("Price");
	  						prodDesc = rs.getString("Short_Description");
	  					}
	  			}
	  		catch (SQLException e){
	  			JOptionPane.showMessageDialog(null,"Error connecting to database!" );
	  			e.printStackTrace();
	  		}
	          showID.setText(id + "");
	          enterName.setText(prodName);
	          enterPrice.setText(prodPrice + "");
	          enterDesc.setText(prodDesc);
	       }
	    }       
	}
	///================================================================================================================================
	
	
	///CheckBox Listener
	///==================================================================================================================================
	class CheckBoxListener implements ItemListener {
		//https://stackoverflow.com/questions/3599908/how-to-check-that-a-jcheckbox-is-checked
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				enterName.setEditable(true);
				enterPrice.setEditable(true);
				enterDesc.setEditable(true);
				delProd.setEnabled(false);
				updateProd.setEnabled(true);
			}
			else {
				enterName.setEditable(false);
				enterPrice.setEditable(false);
				enterDesc.setEditable(false);
				delProd.setEnabled(true);
				updateProd.setEnabled(false);
			}
		}
	}
///================================================================================================================================
	
	
///Update Listener
///==================================================================================================================================
class UpdateListener implements ActionListener {
	private String name;
	private AdminGUI window;
	public UpdateListener(String name, AdminGUI adminGUI) {
		this.name = name;
		window = adminGUI;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String prodID = showID.getText();
		String prodName = enterName.getText();
		String prodPrice = enterPrice.getText();
		String prodDesc = enterDesc.getText();
		
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			PreparedStatement pstat = connection.prepareStatement("UPDATE Products SET Name = ?, Price = ?, Short_Description = ? WHERE ID = ?");
			pstat.setString(1, prodName);
			pstat.setString(2, prodPrice);
			pstat.setString(3, prodDesc);
			pstat.setString(4, prodID);
			pstat.executeUpdate();
			JOptionPane.showMessageDialog(null, "Product details have been updated!");
		}
		catch (SQLException ex){
			JOptionPane.showMessageDialog(null,"Error connecting to database!" );
			ex.printStackTrace();
		}
		try {
			new AdminGUI(name);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		window.dispose();
		
		
	}
}
///================================================================================================================================


///Delete Listener
///==================================================================================================================================
class DeleteListener implements ActionListener {
	private String name;
	private AdminGUI window;
	public DeleteListener(String name, AdminGUI adminGUI) {
		this.name = name;
		window = adminGUI;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String prodID = showID.getText();
		
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			PreparedStatement pstat = connection.prepareStatement("UPDATE Products SET deleted = 1 WHERE ID = ?");
			pstat.setString(1, prodID);
			pstat.executeUpdate();
			JOptionPane.showMessageDialog(null, "Product details have been deleted!");
		}
		catch (SQLException ex){
			JOptionPane.showMessageDialog(null,"Error connecting to database!" );
			ex.printStackTrace();
		}
		try {
			new AdminGUI(name);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		window.dispose();
		
		
	}
}
///================================================================================================================================


///Logout Listener
///==================================================================================================================================

class AdminLogoutListener implements ActionListener {
	
	private AdminGUI window;
	public AdminLogoutListener(AdminGUI adminGUI) {
		window = adminGUI;
	}
	
	public void actionPerformed(ActionEvent e) {
		new LoginWindow();
		window.dispose();
	}
}
///==================================================================================================================================
}
