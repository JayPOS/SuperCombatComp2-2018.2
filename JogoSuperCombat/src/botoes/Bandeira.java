package botoes;

import javax.swing.*;

import janelas.JanelaJogo;
import principal.*;

import java.awt.*;

public class Bandeira extends JButton {

    private static int TAM = JanelaJogo.getTAM_BOTAO();
    public int time;

    public Bandeira(int time) {
        super("Bandeira");
        this.time = time;
        if (this.time == 0) {
        	this.setBackground(new Color(30, 99, 238));
        }
        else if (this.time == 1) {
        	this.setText("Inimigo");
        	this.setBackground(new Color(255, 59, 48));
        }
        if (this.time == 3) {
        	this.setText("Cabo Armeiro");
        	this.setBackground(new Color(30, 99, 238));
        }
        this.setPreferredSize(new Dimension(TAM, TAM));
        
    }

    public Bandeira(String text, Icon icon) {
        super(text, icon);
    }

	public Bandeira(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public int getTime() {
		return time;
	}
}