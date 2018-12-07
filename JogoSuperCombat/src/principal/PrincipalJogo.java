package principal;

import javax.swing.JFrame;
import janelas.*;
import java.awt.event.*;
import java.awt.EventQueue;
import botoes.*;
import javax.swing.*;

public class PrincipalJogo implements ActionListener {
	
	private JFrame controladorJanela;
	private static int modoJanela;

	
	private static int MENU = 0;
    private static int EDITOR = 1;
    private static int ALEATORIO = 2;
    private static int JOGAR = 3;


	public PrincipalJogo() {
		this.modoJanela = 1;
		
	}
	public void inicializaEditor(int tipo) {
		this.controladorJanela = new JanelaEditor2(tipo);
		this.controladorJanela.setLocationRelativeTo(null);
		this.controladorJanela.setVisible(true);
	}
	public void inicializaJogao() {
		this.controladorJanela.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			PrincipalJogo jogo = new PrincipalJogo();
			Object[] opcoes = {"Colocar peças", "Peças aleatórias"};
			jogo.controladorJanela = new JanelaEditor2(JOptionPane.showOptionDialog(null, 
					"Selecione como começar o jogo!", 
					"Super Combat", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, 0));
			jogo.controladorJanela.setLocationRelativeTo(null);
			jogo.controladorJanela.setVisible(true);
			
			
			while (jogo.controladorJanela.isVisible()) {
				jogo.controladorJanela.repaint();
			}
			jogo.controladorJanela.setVisible(false);
		}
		catch(Exception e) {
			
		}
		finally {
			System.out.println("Obrigado por jogar!");
		}
	}
	
	public static int getMENU() {
		return MENU;
	}
	public static int getEDITOR() {
		return EDITOR;
	}
	public static int getALEATORIO() {
		return ALEATORIO;
	}
	public static int getJOGAR() {
		return JOGAR;
	}
	public static int getModoJanela() {
		return modoJanela;
	}
	
	public static void setModoJanela(int modoJanela) {
		PrincipalJogo.modoJanela = modoJanela;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

}
