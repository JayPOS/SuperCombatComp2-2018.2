package janelas;

import botoes.*;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import auxiliar.ControleVar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
	
	public ControleVar auxiliar = new ControleVar();
	
	private static int COMPRIMENTO = 800;
	private static int LARGURA = 600;
	private static int TAM_BOTAO = LARGURA/10;
	private static int QTD_BOTAO = 5;
	
	private JButton[][] botoes;

	public JanelaJogo(JButton botoes[][]) {
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
		// Começarei aqui
		
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
            for (int j = 0; j < QTD_BOTAO; j++) {;
                tabuleiro.add(botoes[i][j]);
                botoes[i][j].addActionListener(this);
            }
        }
		
		this.debug.addActionListener(this);
		this.debug.setPreferredSize(new Dimension(50, 50));
        detalhes.add(this.debug);
        
        
        this.getContentPane().add(background);
		this.revalidate();
//		this.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == debug) {
			this.Debugao();
		}
		this.movimentos(e);
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
					
					// Checa se a peça pode mover e move! ----------------------------
					
					if (botoes[i][j] instanceof Vazio) {
						
					// Checa se o espiao pode mover e se puder move! ----------------------
						
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Espiao) {
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
						
						else if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Marechal) {
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
						}
						
						// Checa se o Cabo pode mover e se puder move! ----------------------
						
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof CaboArmeiro) {
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
						}
						
						// Checa se o Soldado pode mover e se puder move! ----------------------
						
						if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Soldado) {
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
						break;
					}
					
		// SE O BOTAO SELECIONADO FOR OU BANDEIRA DO TIME AZUL OU BOMBA DO TIME AZUL. A ESCOLHA DA PEÇA E RESETADA ------------
					
					if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Bandeira && 
						((Bandeira)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0 ||
								botoes[auxiliar.getX()][auxiliar.getY()] instanceof Bomba && 
								((Bomba)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 0) {
						auxiliar.click = 0;
						System.out.println("click:" + auxiliar.click);
						break;
					}
//					if (botoes[auxiliar.getX()][auxiliar.getY()] instanceof Bandeira && 
//							((Bandeira)botoes[auxiliar.getX()][auxiliar.getY()]).getTime() == 1) {
//						
//					}
				}
			}
		}
	}
	
	public void Debugao() {
		auxiliar.debugador++;
		if (auxiliar.debugador == 1) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					if (botoes[i][j] instanceof Bandeira) {
						botoes[i][j].setText(bandeira.getText());
					}
					else if (botoes[i][j] instanceof Bomba) {
						botoes[i][j].setText(bomba.getText());
					}
					else if (botoes[i][j] instanceof Espiao) {
						botoes[i][j].setText(espiao.getText());
					}
					else if (botoes[i][j] instanceof Soldado) {
						botoes[i][j].setText(soldado.getText());
					}
					else if (botoes[i][j] instanceof CaboArmeiro) {
						botoes[i][j].setText(cabo.getText());
					}
					else if (botoes[i][j] instanceof Marechal) {
						botoes[i][j].setText(marechal.getText());
					}
				}
			}
			this.revalidate();
		}
		else if (auxiliar.debugador == 2) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					this.botoes[i][j].setText("Inimigo");
				}
			}
			auxiliar.debugador = 0;
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
