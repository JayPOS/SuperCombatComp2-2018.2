package botoes;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import janelas.JanelaJogo;

public abstract class Peça extends JButton {
	private int time;
	private int movel;
	private final static int TAM = JanelaJogo.getTAM_BOTAO();
	
	public Peça(String peça, int time, int movel) {
		super(peça);
		this.time = time;
		this.movel = movel;
		if (this.time == 0) {
        	this.setBackground(new Color(30, 99, 238));
        }
        else if (this.time == 1) {
        	this.setText("Inimigo");
        	this.setBackground(new Color(255, 59, 48));
        }
        else if (this.time == 2) {
        	this.setBackground(new Color(29, 252, 66));
        }
        else if (this.time == 3) {
        	this.setBackground(Color.CYAN);
        }
		this.setPreferredSize(new Dimension(TAM, TAM));
	}

	public int getTime() {
		return time;
	}

	public int getMovel() {
		return movel;
	}
}
