package janelas;

import javax.swing.JButton;
import javax.swing.JFrame;
import botoes.*;
import principal.PrincipalJogo;

import auxiliar.*;
import java.util.*;

import javax.swing.JOptionPane;


public class EditorAleatorio extends JFrame {
	
	public JButton botoes[][];
	private List<Integer> listaPosi = new ArrayList<Integer>();
	private Constantes constante =  new Constantes();
	private JanelaJogo jogao;
	
	public EditorAleatorio() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				botoes[i][j] =  new Vazio();
			}
		}
		jogao = new JanelaJogo(botoes, 0);
		jogao.setVisible(true);
		
	}
	
}
