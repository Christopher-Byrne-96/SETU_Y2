//Written By		::	Christopher Byrne
//Date				::	March 2023
//Purpose			::	CA3 for Dr Jason Baron SETU
//						Design a java applet to manage customers purchases

//Design for showing user the cart after adding products to it


package CA3;

//importing java classes
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


//Start of class
public class CartGUI extends JFrame{
	
	
	//Declaring Variables
	private JTable itemsTable;
    private DefaultTableModel table;
    private JLabel customerName;
    private JLabel cost;
    private JButton pay;
    private Cart cart;
    private int custID;
    
    
  //GUI contructor method takes logged in name of user, the cart object, and id for arguments
    public CartGUI(String name, Cart carts, int custID) throws SQLException {
    	
    	//calling super method with string as window name 
    	super("My Cart");
    	
    	//Innitializing variables
    	cart = carts;
    	this.custID = custID;
    	pay = new JButton("Pay Now");
    	customerName = new JLabel("Customer: " + name);
    	int[] itemList = cart.returnList();
    	double totalPrice = 0;
    	Connection connection = null;
		Statement statement = null;
    	
    	//This was sourced from the link below while researching how to disabled editing on a JTable
    	// https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
    	table = new DefaultTableModel() {
    		@Override
    		public boolean isCellEditable(int row, int column) {
    			//all cells false
    			return false;
    			}
    		};
    	itemsTable = new JTable(table);
    	JScrollPane scrollPane = new JScrollPane(itemsTable);
    	
    	//Building out the table
    	table.addColumn("ProductID");
    	table.addColumn("Product Name");
    	table.addColumn("Price");
    	
    	//Populating table with database query
    	try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			statement = connection.createStatement();
			for (int i=0; i<itemList.length; i++) {
				int id = itemList[i];
				if(id > 0) {
					ResultSet rs = statement.executeQuery("SELECT Name, Price FROM Products WHERE ID = '" + id + "'" );
					while(rs.next()) {
					Object[] row = {id, rs.getObject("Name"), rs.getDouble("Price")};
					totalPrice = totalPrice + rs.getDouble("Price");
					table.addRow(row);
					}
				}
			}
		}
    	// Catch exception on failure to connect to DB
		catch (SQLException e){
			JOptionPane.showMessageDialog(null,"Error connecting to database!" );
			e.printStackTrace();
		}
    	//Close connection
    	finally {
			connection.close();
			statement.close();
		}
    	
    	
    	//Adding listeners
    	pay.addActionListener(new PayNowListener(cart, custID, this));
    	
    	//Adding Building out the GUI
    	cost = new JLabel("Items total: " + totalPrice);
		JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(cost);
        bottom.add(pay);
        panel.add(bottom, BorderLayout.SOUTH);
        panel.add(customerName, BorderLayout.NORTH);
        
        //Displaying the panel
        add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    	
    }
}

//PayNow Listener
///================================================================================================================================
class PayNowListener implements ActionListener{
	
	//Declare variables
	private Cart cart;
	int custID;
	private CartGUI window;
	
	//Contructor method takes the cart object, the id, and the window as arguments
	public PayNowListener(Cart cart, int id, CartGUI cartGUI) {
		
		//Initializing those variables
		this.cart = cart;
		custID = id;
		window = cartGUI;
	}
	
	public void actionPerformed(ActionEvent event) {
		
		int[] itemList = cart.returnList();
		
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ca3","root","");
			for (int i=0; i<itemList.length; i++) {
				int id = itemList[i];
				if(id > 0) {
					PreparedStatement pstat = connection.prepareStatement("INSERT INTO Invoices ( Customer_ID, Product_ID) VALUES (?,?)");
					pstat.setInt(1, custID);
					pstat.setInt(2, id);
					pstat.executeUpdate();
				}
			}
			JOptionPane.showMessageDialog(null, "Invoice has been added!");
			cart.reset();
			window.dispose();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null,"Error connecting to database!" );
			e.printStackTrace();
		}
	}
}
