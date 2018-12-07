package botoes;

import java.awt.*;
import javax.swing.*;

import janelas.JanelaJogo;

public class Vazio extends JButton {

    private static int TAM = JanelaJogo.getTAM_BOTAO();

    public Vazio() {
    	super("Vazio");
    	this.setBackground(new Color(29, 252, 66));
    	this.setPreferredSize(new Dimension(TAM, TAM));
    	
    }

    public Vazio(String text) {
        super(text);
    }

    public Vazio(String text, Icon icon) {
        super(text, icon);
    }
}