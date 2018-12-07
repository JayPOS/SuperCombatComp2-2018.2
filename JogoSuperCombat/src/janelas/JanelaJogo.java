package janelas;

import botoes.*;

import javax.swing.border.EmptyBorder;

import auxiliar.ControleVar;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import auxiliar.Constantes;
import auxiliar.ValorIA;

public class JanelaJogo extends JFrame implements ComponentListener, ActionListener {
	
	private JPanel tabuleiro;
	private JPanel background;
	private JPanel detalhes;
	private GridLayout tabuleirao;
	private BorderLayout teste;
	
	
	private Bandeira bandeira = new Bandeira(2);
	private Soldado soldado = new Soldado(2);
	private Bomba bomba = new Bomba(2);
	private Espiao espiao = new Espiao(2);
	private CaboArmeiro cabo = new CaboArmeiro(2);
	private Marechal marechal = new Marechal(2);
	private Vazio vazio = new Vazio();
	private Desfazer desfazer = new Desfazer();
	private Debug debug = new Debug();
	private Dica dica = new Dica();
	
	public ControleVar auxiliar = new ControleVar();
	public Random random = new Random();
	
	private static int COMPRIMENTO = 800;
	private static int LARGURA = 600;
	private static int TAM_BOTAO = LARGURA/10;
	private static int QTD_BOTAO = 5;
	
	private Peça[][] botoes;

	public JanelaJogo(Peça botoes[][]) {
		super("Super Combat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, COMPRIMENTO, LARGURA);
		
		// Chamadas de construtores:
		this.addComponentListener(this);
		this.setResizable(false);
		tabuleirao = new GridLayout(QTD_BOTAO, QTD_BOTAO);
		teste = new BorderLayout();
		background = new JPanel();
		tabuleiro = new JPanel();
		detalhes = new JPanel();
		detalhes.setBackground(new Color(145, 255, 117));
		detalhes.setPreferredSize(new Dimension(COMPRIMENTO/5, LARGURA));
		tabuleiro.setPreferredSize(new Dimension(TAM_BOTAO*10, TAM_BOTAO*10));
		// Come�arei aqui
		
		background.setLayout(teste);
		tabuleiro.setLayout(tabuleirao);
		detalhes.setBorder(new EmptyBorder(30, 35, 10, 35));
		detalhes.setLayout(new GridLayout(3, 1, 10, 10));
		background.add(tabuleiro, BorderLayout.CENTER);
		background.add(detalhes, BorderLayout.WEST);
		
		
//		background.add(new JLabel("   "), BorderLayout.SOUTH);
//		background.add(new JPanel());
		this.botoes = botoes;
		
		for (int i = 0; i < QTD_BOTAO; i++) {
            for (int j = 0; j < QTD_BOTAO; j++) {
            	tabuleiro.add(botoes[i][j]);
                botoes[i][j].addActionListener(this);
            }
        }
		this.dica.addActionListener(this);
		this.debug.addActionListener(this);
		this.debug.setPreferredSize(new Dimension(50, 50));
        detalhes.add(this.debug);
        detalhes.add(dica);
        
        
        this.getContentPane().add(background);
		this.revalidate();
//		this.repaint();
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dica) {
			 auxiliar.dicaOn = 1;
			 System.out.println(auxiliar.dicaOn);
			 
		}
		if (e.getSource() == debug) {
			this.Debugao();
		}
		if (auxiliar.dicaOn == 1) {
			this.Dica(e);
		}
		else if (auxiliar.dicaOn == 0){
			if (auxiliar.turno == 1) {
				this.movimentosIA();
			}
			else if (auxiliar.turno == 0) {
				this.movimentos(e);
				
			}
		}
		
	}
	
	public void movimentos(ActionEvent k) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (k.getSource() == botoes[i][j] && auxiliar.click == 0){
					if (botoes[i][j] instanceof Vazio) {
						System.out.println("Vazio");
					}
					else {
						auxiliar.setXeY(i, j);
						System.out.println("i Salvo = " + i + "\nj Salvo = " + j);
						auxiliar.click++;
						System.out.println("Entrei3");
					}
				}
				if (k.getSource() == botoes[i][j] && auxiliar.click == 1) {
					System.out.println("Entrei5");
					System.out.println("i = " + i + "\nj = " + j);
					
					// Checa se a pe�a pode mover e move! ----------------------------
					
					if (botoes[i][j] instanceof Vazio) {
						
					// Checa se o espiao pode mover e se puder move! ----------------------
						
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao
								&& ((Espiao) botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
							System.out.println("Entrei1");
							if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0) {
	            				tabuleiro.remove(botoes[i][j]);
	            				botoes[i][j] = new Espiao(0);
	                			tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								
								
							} 
							else if (auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
	            				tabuleiro.remove(botoes[i][j]);
	            				botoes[i][j] = new Espiao(0);
	                			tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								
								
							}
						}
						
			// Checa se o Marechal pode mover e se puder move! ----------------------
						
						else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal
								&& botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
							System.out.println("Entrei2");
							if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0) {
	            				tabuleiro.remove(botoes[i][j]);
	            				botoes[i][j] = new Marechal(0);
	                			tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								
							} 
							else if (auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
	            				tabuleiro.remove(botoes[i][j]);
	            				botoes[i][j] = new Marechal(0);
	                			tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								
								
							}
							auxiliar.turno = 1;
						}
						
						// Checa se o Cabo pode mover e se puder move! ----------------------
						
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro
								&& botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
							System.out.println("opa");
							if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
									auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
								tabuleiro.remove(botoes[i][j]);
								this.botoes[i][j] = new CaboArmeiro(0);
								tabuleiro.add(botoes[i][j], i*5+j);
								this.botoes[i][j].addActionListener(this);
								
								tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
								this.botoes[auxiliar.getX()][auxiliar.getY()] =  new Vazio();
								this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
								tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
								this.revalidate();
								
							
							}
							auxiliar.turno = 1;
						}
						
						// Checa se o Soldado pode mover e se puder move! ----------------------
						
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado
								&& botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
							if (auxiliar.modulo(i-auxiliar.getX()) == 0) {
								tabuleiro.remove(botoes[i][j]);
								this.botoes[i][j] = new Soldado(0);
								tabuleiro.add(botoes[i][j], i*5+j);
								this.botoes[i][j].addActionListener(this);
								
								tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
								this.botoes[auxiliar.getX()][auxiliar.getY()] =  new Vazio();
								this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
								tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
								this.revalidate();
								
								
							}
							else if (auxiliar.modulo(j-auxiliar.getY()) == 0) {
								tabuleiro.remove(botoes[i][j]);
								this.botoes[i][j] = new Soldado(0);
								tabuleiro.add(botoes[i][j], i*5+j);
								this.botoes[i][j].addActionListener(this);
								
								tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
								this.botoes[auxiliar.getX()][auxiliar.getY()] =  new Vazio();
								this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
								tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
								this.revalidate();
								 
							}
						}
						auxiliar.click = 0;
						auxiliar.turno = 1;
						break;
					}
					
		// SE O BOTAO SELECIONADO FOR OU BANDEIRA DO TIME AZUL OU BOMBA DO TIME AZUL. A ESCOLHA DA PE�A E RESETADA ------------
					
					else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Bandeira && 
						botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0 ||
								botoes[auxiliar.getX()][auxiliar.getY()] instanceof Bomba && 
								((Bomba)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
						auxiliar.click = 0;
						System.out.println("click:" + auxiliar.click);
						break;
					}
					
					// ATAQUE AO ESPIAOOOOO -------------------------------------------------------
					// --------------------------------------------------------------------------
					// ---------------------------------------------------------------------------- !!
					
					
					else if (botoes[i][j] instanceof Espiao && 
							botoes[i][j].getTime() == 1) {
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Bandeira &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0 || botoes[i][j] instanceof Vazio) {
							
						}
						else {
							if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
									auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
								
								tabuleiro.remove(botoes[i][j]);
								
								if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									botoes[i][j] = new Marechal(0);
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									botoes[i][j] = new Soldado(0);
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									botoes[i][j] = new CaboArmeiro(0);
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									botoes[i][j] = new Vazio();
								}
	            				
	            				
	            				
	            				
	                			tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								auxiliar.click = 0;
								 
								
							} 
						}
						auxiliar.turno = 1;
					}
					
					/*ATAQUE À BANDEIRA -----------------------------------------------
					 * ----------------------------------------------------------------------
					 * -------------------------------------------------------- !!!!*/
					
					else if (botoes[i][j] instanceof Bandeira &&
								botoes[i][j].getTime() == 1) {
						if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
								auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
							if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0
								|| botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0
								|| botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0
								|| botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
										
					// CONDIÇÃO PARA GANHAR O JOGO!!! ---------------------------------------------
									if (JOptionPane.showConfirmDialog(this, "Você ganhou o jogo! Parabéns!", "Ganhou!",
												JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
										this.dispose();
									}
									
								
								}
							
						}
					}
					
					/* ATAQUE À BOMBA -----------------------------------------------------------
					 * ------------------------------------------------------------------------------
					 * -------------------------------------------------------------------------------- !!!*/
					
					else if (botoes[i][j] instanceof Bomba &&
							botoes[i][j].getTime() == 1) {
						if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
								auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
							
							tabuleiro.remove(botoes[i][j]);
							
							if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal &&
									botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0
									|| botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado &&
									botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0
									|| botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao &&
									botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
								
								
								botoes[i][j] = new Vazio();
								tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								auxiliar.click = 0;
								 
							}
							else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro &&
								botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
								botoes[i][j] = new CaboArmeiro(0);
								
								tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								auxiliar.click = 0;
								 
							}
						}
						auxiliar.turno = 1;
					}
					
					
					/*ATAQUE AO SOLDADO -----------------------------------------------------
					 * -----------------------------------------------------------------------
					 * -------------------------------------------------------------------------- !!!*/
					
					else if (botoes[i][j] instanceof Soldado &&
							botoes[i][j].getTime() == 1) {
						if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
								auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
						
							if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
			        			
			    				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
			    				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
			        			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
			        			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
			
								this.revalidate();
								auxiliar.click = 0;
								 
							}
							else {
								tabuleiro.remove(botoes[i][j]);
								if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									
									botoes[i][j] = new Marechal(0);
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									botoes[i][j] = new CaboArmeiro(0);
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado &&
										botoes[auxiliar.getX()][auxiliar.getY()].getTime() == 0) {
									botoes[i][j] = new Vazio();
								}
								tabuleiro.add(botoes[i][j], i*5+j);
			        			this.botoes[i][j].addActionListener(this);
			        			
			    				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
			    				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
			        			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
			        			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
			
								this.revalidate();
								auxiliar.click = 0;
								 
							}
						}
						auxiliar.turno = 1;
					}
					
					/*ATAQUE AO CABO ------------------------------------------------------------------------
					 * --------------------------------------------------------------------------------------
					 * ------------------------------------------------------------------------------------!!*/
					
					else if (botoes[i][j] instanceof CaboArmeiro &&
							((CaboArmeiro)botoes[i][j]).getTime() == 1) {
						if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
								auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
						
							if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao &&
									((Espiao)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0 ||
									botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado &&
										((Soldado)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
			            			
		        				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
		        				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
		
								this.revalidate();
								auxiliar.click = 0;
								 
							}
							else {
								tabuleiro.remove(botoes[i][j]);
								if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal &&
										((Marechal)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
									
									botoes[i][j] = new Marechal(0);
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro &&
										((CaboArmeiro)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
									botoes[i][j] = new Vazio();
								}
								tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								auxiliar.click = 0;
								 
							}
						}
						auxiliar.turno = 1;
					}
					
					
					
					else if (botoes[i][j] instanceof Marechal &&
							((Marechal)botoes[i][j]).getTime() == 1) {
						
						if (auxiliar.modulo(i-auxiliar.getX()) == 1 && auxiliar.modulo(j-auxiliar.getY()) == 0 || 
								auxiliar.modulo(i-auxiliar.getX()) == 0 && auxiliar.modulo(j-auxiliar.getY()) == 1) {
							if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado &&
									((Soldado)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0 ||
										botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro &&
										((CaboArmeiro)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
			            			
		        				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
		        				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
		
								this.revalidate();
								auxiliar.click = 0;
								auxiliar.turno = 1;
							}
							else {
								tabuleiro.remove(botoes[i][j]);
								if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal &&
										((Marechal)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
									
									botoes[i][j] = new Vazio();
								}
								else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao &&
									((Espiao)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
									botoes[i][j] =  new Espiao(0);
								}
								tabuleiro.add(botoes[i][j], i*5+j);
	                			this.botoes[i][j].addActionListener(this);
	                			
	            				tabuleiro.remove(botoes[auxiliar.getX()][auxiliar.getY()]);
	            				botoes[auxiliar.getX()][auxiliar.getY()] = new Vazio();
		            			tabuleiro.add(botoes[auxiliar.getX()][auxiliar.getY()], auxiliar.getX()*5+auxiliar.getY());
		            			this.botoes[auxiliar.getX()][auxiliar.getY()].addActionListener(this);
	
								this.revalidate();
								auxiliar.click = 0;
								 
							}
						}
						auxiliar.turno = 1;
					}
					
				}
			}
		}
	}
	
	
	public void movimentosIA() {
		int ind = random.nextInt(25);
		int moveType = random.nextInt(2);
		boolean control = false;
		while (auxiliar.turno == 1) {
			if (botoes[ind/5][ind%5].getTime() == 1 && botoes[ind/5][ind%5].getMovel() == 1) {
				
				// PRA FRENTE
				
				if (moveType == 0) {
					if (ind/5 < 4) {
						if (botoes[ind/5][ind%5] instanceof Marechal) {
							System.out.println("Achei Marechal");
							if (botoes[(ind/5)+1][ind%5] instanceof Vazio){
								System.out.println("Achei Vazio");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Marechal && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei Marechal");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)-1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Espiao && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei E");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bandeira && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bomba && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei Bomba");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Soldado && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei S");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)-1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei C");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							this.revalidate();
							control = true;
						}
						else if (botoes[ind/5][ind%5] instanceof CaboArmeiro) {
							System.out.println("Achei Cabo");
							if (botoes[(ind/5)+1][ind%5] instanceof Vazio){
								System.out.println("Achei V");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Marechal && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei M");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Espiao && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei E");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bandeira && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bomba && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei B");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Soldado && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei S");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei C");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							this.revalidate();
							control = true;
						}
						else if (botoes[ind/5][ind%5] instanceof Soldado) {
							System.out.println("Achei Soldado");
							if (botoes[(ind/5)+1][ind%5] instanceof Vazio){
								System.out.println("Achei V");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Soldado(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Soldado");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Marechal && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei M");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Espiao && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei E");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Soldado(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Soldado");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bandeira && botoes[(ind/5)+1][ind%5].getTime() == 0) {

							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bomba && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei B");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Soldado && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei S");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei C");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							this.revalidate();
							control = true;
						}
						else if (botoes[ind/5][ind%5] instanceof Espiao) {
							System.out.println("Achei Espiao");
							if (botoes[(ind/5)+1][ind%5] instanceof Vazio){
								System.out.println("Achei V");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Espiao(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Espiao");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Marechal && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei M");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Espiao(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Espiao");
								}
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Espiao && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei E");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)+1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bandeira && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Bomba && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei B");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[(ind/5)-1][ind%5]);
								botoes[(ind/5)+1][ind%5] = new Vazio();
								botoes[(ind/5)+1][ind%5].addActionListener(this);
								tabuleiro.add(botoes[(ind/5)+1][ind%5], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof Soldado && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei S");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[(ind/5)+1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)+1][ind%5].getTime() == 0) {
								System.out.println("Achei C");
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							this.revalidate();
							control = true;
						}
					}
					ind = random.nextInt(25);
					moveType = 1;
				}
				
				// PRA DIREITA
				
				else if (moveType == 1) {
					if (ind%5 < 4) {
						if (botoes[ind/5][ind%5] instanceof Marechal) {
							if (botoes[ind/5][(ind%5)+1] instanceof Vazio) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Marechal && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Soldado && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Espiao && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Marechal(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Marechal");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bomba && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bandeira && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								
							}
							this.revalidate();
							control = true;
						}
						else if (botoes[ind/5][ind%5] instanceof CaboArmeiro) {
							
							if (botoes[ind/5][(ind%5)+1] instanceof Vazio) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Marechal && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Soldado && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Espiao && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bomba && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new CaboArmeiro(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Cabo Armeiro");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bandeira && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								
							}
							this.revalidate();
							control = true;
						}
						else if (botoes[ind/5][ind%5] instanceof Soldado) {
							
							if (botoes[ind/5][(ind%5)+1] instanceof Vazio) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Soldado(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Soldado");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Marechal && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Soldado && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Espiao && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Soldado(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Soldado");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bomba && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bandeira && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								
							}
							this.revalidate();
							control = true;
						}
						else if (botoes[ind/5][ind%5] instanceof Espiao) {
							
							if (botoes[ind/5][(ind%5)+1] instanceof Vazio) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Espiao(1);
								if (auxiliar.debugador == 1) {
									botoes[ind/5][(ind%5)+1].setText("Espiao");
								}
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Marechal && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Soldado && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Espiao && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bomba && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								tabuleiro.remove(botoes[ind/5][ind%5]);
								botoes[ind/5][ind%5] = new Vazio();
								botoes[ind/5][ind%5].addActionListener(this);
								tabuleiro.add(botoes[ind/5][ind%5], ind);
								tabuleiro.remove(botoes[ind/5][(ind%5)+1]);
								botoes[ind/5][(ind%5)+1] = new Vazio();
								botoes[ind/5][(ind%5)+1].addActionListener(this);
								tabuleiro.add(botoes[ind/5][(ind%5)+1], ind+5);
								auxiliar.turno = 0;
							}
							else if (botoes[ind/5][(ind%5)+1] instanceof Bandeira && botoes[ind/5][(ind%5)+1].getTime() == 0) {
								
							}
							this.revalidate();
							control = true;
						}
					}
				}
				
				//PRA ESQUEDA
				
				else if (moveType == 2) {
					if (ind%5 > 0) {
						if (botoes[ind/5][ind%5] instanceof Marechal) {
							
							if (botoes[ind/5][(ind%5)-1] instanceof Vazio) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Marechal && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Soldado && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Espiao && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Bomba && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Bandeira && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
						}
						else if (botoes[ind/5][ind%5] instanceof CaboArmeiro) {
							
							if (botoes[ind/5][(ind%5)-1] instanceof Vazio) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Marechal && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Soldado && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Espiao && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Bomba && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Bandeira && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
						}
						else if (botoes[ind/5][ind%5] instanceof Soldado) {
							
						}							
						if (botoes[ind/5][(ind%5)-1] instanceof Vazio) {
							
						}
						else if (botoes[ind/5][(ind%5)-1] instanceof Marechal && botoes[ind/5][(ind%5)-1].getTime() == 0) {
							
						}
						else if (botoes[ind/5][(ind%5)-1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)-1].getTime() == 0) {
							
						}
						else if (botoes[ind/5][(ind%5)-1] instanceof Soldado && botoes[ind/5][(ind%5)-1].getTime() == 0) {
							
						}
						else if (botoes[ind/5][(ind%5)-1] instanceof Espiao && botoes[ind/5][(ind%5)-1].getTime() == 0) {
							
						}
						else if (botoes[ind/5][(ind%5)-1] instanceof Bomba && botoes[ind/5][(ind%5)-1].getTime() == 0) {
							
						}
						else if (botoes[ind/5][(ind%5)-1] instanceof Bandeira && botoes[ind/5][(ind%5)-1].getTime() == 0) {
							
						}
						else if (botoes[ind/5][ind%5] instanceof Espiao) {
							
							if (botoes[ind/5][(ind%5)-1] instanceof Vazio) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Marechal && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof CaboArmeiro && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Soldado && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Espiao && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Bomba && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
							else if (botoes[ind/5][(ind%5)-1] instanceof Bandeira && botoes[ind/5][(ind%5)-1].getTime() == 0) {
								
							}
						}
					}
				}
				
				// PRA TRAS
				
				else if (moveType == 3) {
					if (ind/5 > 0) {
						if (botoes[ind/5][ind%5] instanceof Marechal) {
							
							if (botoes[(ind/5)-1][ind%5] instanceof Vazio) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Marechal && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Soldado && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Espiao && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bomba && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bandeira && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
						}
						else if (botoes[ind/5][ind%5] instanceof CaboArmeiro) {
							
							if (botoes[(ind/5)-1][ind%5] instanceof Vazio) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Marechal && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Soldado && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Espiao && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bomba && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bandeira && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
						}
						else if (botoes[ind/5][ind%5] instanceof Soldado) {
							
							if (botoes[(ind/5)-1][ind%5] instanceof Vazio) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Marechal && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Soldado && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Espiao && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bomba && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bandeira && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
						}
						else if (botoes[ind/5][ind%5] instanceof Espiao) {
							
							if (botoes[(ind/5)-1][ind%5] instanceof Vazio) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Marechal && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof CaboArmeiro && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Soldado && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Espiao && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bomba && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
							else if (botoes[(ind/5)-1][ind%5] instanceof Bandeira && botoes[(ind/5)-1][ind%5].getTime() == 0) {
								
							}
						}
					}
				}
				moveType = random.nextInt(2);
			}
			else {
				ind = random.nextInt(25);
			}
		}
	}
	
	
	public void Debugao() {
		auxiliar.debugador++;
		if (auxiliar.debugador == 1) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (botoes[i][j] instanceof Bandeira && botoes[i][j].getTime() == 1) {
						botoes[i][j].setText(bandeira.getText());
					}
					else if (botoes[i][j] instanceof Bomba && botoes[i][j].getTime() == 1) {
						botoes[i][j].setText(bomba.getText());
					}
					else if (botoes[i][j] instanceof Espiao && botoes[i][j].getTime() == 1) {
						botoes[i][j].setText(espiao.getText());
					}
					else if (botoes[i][j] instanceof Soldado && botoes[i][j].getTime() == 1) {
						botoes[i][j].setText(soldado.getText());
					}
					else if (botoes[i][j] instanceof CaboArmeiro && botoes[i][j].getTime() == 1) {
						botoes[i][j].setText(cabo.getText());
					}
					else if (botoes[i][j] instanceof Marechal && botoes[i][j].getTime() == 1) {
						botoes[i][j].setText(marechal.getText());
					}
				}
			}
			this.revalidate();
		}
		else if (auxiliar.debugador == 2) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (botoes[i][j].getTime() == 1) {
						this.botoes[i][j].setText("Inimigo");
					}
				}
			}
			auxiliar.debugador = 0;
		}
	}
	public void Dica(ActionEvent k) {
		int contagem = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (k.getSource() == botoes[i][j]) {
					if (auxiliar.contDicas >= 2) {
						JOptionPane.showConfirmDialog(this, "Dicas Esgotadas!", "Dica!",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						auxiliar.dicaOn = 0;
					}
					else {
						auxiliar.contDicas++;
						for (int x = 0; x < 5; x++) {
							contagem++;
							if (botoes[x][j] instanceof Bomba && ((Bomba)botoes[x][j]).getTime() == 1) {
								JOptionPane.showConfirmDialog(this, "Tem Bomba!", "Dica!",
										JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
						if (contagem == 5) {
							JOptionPane.showConfirmDialog(this, "Não tem Bomba!", "Dica!",
									JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						}
						auxiliar.dicaOn = 0;
					}
				}
			}
		}
		
	}
	

	public static int getCOMPRIMENTO() {
		return COMPRIMENTO;
	}

	public static int getLARGURA() {
		return LARGURA;
	}

	public static int getTAM_BOTAO() {
		return TAM_BOTAO;
	}
	public JButton[][] getBotoes() {
		return botoes;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(this.getWidth() < COMPRIMENTO) {
			
		}
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
