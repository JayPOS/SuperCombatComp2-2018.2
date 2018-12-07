package botoes;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class Lago extends JButton {

	public Lago() {
		super("Lago");
		this.setBackground(Color.CYAN);
	}

	public Lago(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public Lago(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Lago(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public Lago(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

}
