package Lab11aq1;


//Converts a Fahrenheit temperature entered by the user to Celsius, or vice versa

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConvertTemp extends JFrame {
	
	private JTextField fahrenField = new JTextField();
	private JTextField celsiusField = new JTextField();
	
	// Constructor
	public ConvertTemp(String title)	{
		// Set title for frame and choose layout
		super(title);
		getContentPane().setLayout(new GridLayout(2, 2));

		// Add Fahrenheit label and text field to frame
		add(new JLabel("Fahrenheit"));
		add(fahrenField);
		// Attach listener to text field
		fahrenField.addActionListener(new FahrenheitListener());
		// Add Celsius label and text field to frame
		add(new JLabel("Celsius"));
		add(celsiusField);
		// Attach listener to text field
		celsiusField.addActionListener(new CelciusListener());
		// Attach window listener
		addWindowListener(new WindowCloser());
	}//end costructor
	
	//Celcius Listener
	class CelciusListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
		String cString = celsiusField.getText();
		double cel = Double.parseDouble(cString);
		double fahr = cel*9/5 + 32;
		fahr = fahr - (fahr%.01);
		fahrenField.setText(fahr + "");
		}
	}
	
	 //Fahrenheit Listener
	class FahrenheitListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String fString = fahrenField.getText();
			double fahr = Double.parseDouble(fString);
			double cel = (fahr - 32)* 5/9;
			cel = cel - (cel%.01);
			celsiusField.setText(cel + "");
		}

	}


	// Listener for window
	class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			System.exit(0);
		}
	}
}

