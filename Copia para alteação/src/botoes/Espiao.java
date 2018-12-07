package botoes;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

import janelas.JanelaJogo;

public class Espiao extends JButton {
	
	  private static int TAM = JanelaJogo.getTAM_BOTAO();
	  private int time;

	public Espiao(int time) {
		super("Espiao");
		this.time = time;
		if (this.time == 0) {
        	this.setBackground(new Color(30, 99, 238));
        }
        else if (this.time == 1) {
        	this.setText("Inimigo");
        	this.setBackground(new Color(255, 59, 48));
        }
		this.setPreferredSize(new Dimension(TAM, TAM));
		// TODO Auto-generated constructor stub
	}

	public Espiao(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public Espiao(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}
	
	public int getTime() {
		return time;
	}

}
