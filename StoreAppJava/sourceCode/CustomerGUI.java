//Written By		::	Christopher Byrne
//Date				::	March 2023
//Purpose			::	CA3 for Dr Jason Baron SETU
//						Design a java applet to manage customers purchases

//Window for customer log in 
//This displays a table of products that are in the databsse that an then be added to a cart and purhcased 


package CA3;

//Importing java classes
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//Start of class
public class CustomerGUI extends JFrame {
	
	//declaring variables
	private JTable productsTable;
    private DefaultTableModel table;
    private JLabel customerName;
    private JButton addCart;
    private JButton viewCart;
    private JButton logout;
    private Cart cart;
   
	//Constructor takes ID and name of logged in user as arguments
	public CustomerGUI(int userID, String name) throws SQLException {
		//Call to super constructor and passing window name through
		super("Customer View");
		
		//Initializing variables
		int custID = userID;
		cart = new Cart();
		customerName = new JLabel("Customer: " + name);
		addCart = new JButton("Add to Cart");
		viewCart = new JButton("View Cart");
		logout = new JButton("Logout");
		Connection connection = null;
		Statement statement = null;
		
		//This was sourced from stackoverflow when searching how to make a table non editable
		// https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
		table = new DefaultTableModel() {
			 @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		
		//Building the table
		productsTable = new JTable(table);
		JScrollPane scrollPane = new JScrollPane(productsTable);
		table.addColumn("Product ID");
		table.addColumn("Product Name");
		table.addColumn("Price");
		table.addColumn("Short Description");
		
		//Populating the table with a database query
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Products WHERE deleted = 0");
			while(rs.next()) {
				Object[] row = {rs.getInt("ID"), rs.getString("Name"), rs.getDouble("Price"), rs.getString("Short_Description")};
				table.addRow(row);
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
		
		//Add listeners
		addCart.addActionListener(new AddCartListener(productsTable, cart));
		viewCart.addActionListener(new ViewCartListener(cart, name, custID));
		logout.addActionListener(new CustomerLogoutListener(this));
		
		//Build the GUI
		JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addCart);
        buttons.add(viewCart);
        buttons.add(logout);
        panel.add(buttons, BorderLayout.SOUTH);
        panel.add(customerName, BorderLayout.NORTH);
        
        //Display the panel
        add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
	}

}

//Add Cart Listener
///================================================================================================================================
class AddCartListener implements ActionListener{
	
	//Declare variables
	private JTable table;
	private Cart cart;
	
	//Constructor to take cart and table as arguments
	public AddCartListener(JTable table, Cart cart) {
		this.table = table;
		this.cart = cart;
	}
	//Action to be performed
	public void actionPerformed(ActionEvent event) {
		//Adds the item to the cart
		int selectedRow = table.getSelectedRow();
		int productID = (int) table.getValueAt(selectedRow, 0);
		cart.addItem(productID);
		JOptionPane.showMessageDialog(null, "Item added to cart!");
	}
	
}

//Add Cart Listener
///================================================================================================================================
class ViewCartListener implements ActionListener{
	
	//Declaring Variables
	private Cart cart;
	private String name;
	private int custID;
	
	//Constructor takes cart the name and the id of the user as arguments
	public ViewCartListener(Cart cart, String name, int id) {
		
		//Innitialize variables
		this.cart = cart;
		this.name = name;
		this.custID = id;
	}
	
	//The action to be performed 
	public void actionPerformed(ActionEvent event) {
		
		//Creates the cart window
		try {
			new CartGUI(name, cart, custID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
///================================================================================================================================



}

	

