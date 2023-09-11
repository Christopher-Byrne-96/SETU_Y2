package Lab12aq1;

import javax.swing.JFrame;

public class GuiDriver{



	public static void main(String[] args) {
	
		Gui gui = new Gui("GUI");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(400, 150);
		gui.setLocation(500, 500);
		gui.setVisible(true);
	}

}
