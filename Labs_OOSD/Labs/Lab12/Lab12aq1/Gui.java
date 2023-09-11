package Lab12aq1;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	
	private JCheckBox snapToGrid = new JCheckBox("Snap to Grid");
	private JCheckBox showGrid = new JCheckBox("Show Grid");
	
	private JTextField fieldX = new JTextField();
	private JTextField fieldY = new JTextField();
	
	private JButton buttonOK = new JButton("Ok");
	private JButton buttonCancel = new JButton("Cancel");
	private JButton buttonHelp = new JButton("Help");
	
	private JPanel checkPanel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JPanel xPanel;
	private JPanel yPanel;
	
	Dimension newSize = new Dimension(90, 30);
	
	public Gui(String title) {
		super(title);
		
		this.getContentPane().setLayout(new BorderLayout(5, 5));
		
		checkPanel = new JPanel();
		checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
		checkPanel.add(snapToGrid);
		checkPanel.add(showGrid);
		add(checkPanel, BorderLayout.WEST);
		
		textPanel = new JPanel();
		xPanel = new JPanel();
		yPanel = new JPanel();
		xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
		yPanel.setLayout(new BoxLayout(yPanel, BoxLayout.X_AXIS));
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		fieldX.setMaximumSize(newSize);
		fieldX.setMinimumSize(newSize);
		fieldX.setPreferredSize(newSize);		    
		fieldY.setMaximumSize(newSize);
		fieldY.setMinimumSize(newSize);
		fieldY.setPreferredSize(newSize);
		xPanel.add(new JLabel("X"));
		xPanel.add(fieldX);
		yPanel.add(new JLabel("Y"));
		yPanel.add(fieldY);
		textPanel.add(xPanel);
		textPanel.add(yPanel);
		add(textPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(buttonOK);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonPanel.add(buttonCancel);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonPanel.add(buttonHelp);
		buttonPanel.add(Box.createHorizontalGlue());

		JButton[] buttons = {buttonOK, buttonCancel, buttonHelp};
		for (JButton button : buttons) {
		    button.setMaximumSize(newSize);
		    button.setMinimumSize(newSize);
		    button.setPreferredSize(newSize);
		}
		add(buttonPanel, BorderLayout.EAST);
	}
	
	

}
