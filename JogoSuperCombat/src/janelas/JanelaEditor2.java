package janelas;

import botoes.*;
import principal.PrincipalJogo;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import auxiliar.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Exception;
import javax.swing.JOptionPane;


public class JanelaEditor2 extends JFrame implements ComponentListener, ActionListener {
	
	private JPanel tabuleiro;
	private JPanel background;
	private JPanel detalhes;
	private JPanel escolha;
	private GridLayout tabuleirao;
	private BorderLayout teste;
	
	private JanelaJogo jogao;
	
	private Bandeira bandeira = new Bandeira(2);
	private Soldado soldado = new Soldado(2);
	private Bomba bomba = new Bomba(2);
	private Espiao espiao = new Espiao(2);
	private CaboArmeiro cabo = new CaboArmeiro(2);
	private Marechal marechal = new Marechal(2);
	private Vazio vazio = new Vazio();
	private Desfazer desfazer = new Desfazer();
	private Debug debug = new Debug();
	
	private Jogar jogar = new Jogar();
	
	public int debugador = 0;
	
	private final int COMPRIMENTO = 800;
	private final int LARGURA = 600;
	private final int TAM_BOTAO = LARGURA/10;
	private final int QTD_BOTAO = 5;
	
//	private ControleBotao controle[] = new ControleBotao[] {};
	
	private List<Integer> listaPosi = new ArrayList<Integer>();
	private Constantes constante = new Constantes();
	
	private int CONT_BANDEIRA = 0;
	private int CONT_BOMBAS = 0;
	private int CONT_ESPIAO = 0;
	private int CONT_SOLDADO = 0;
	private int CONT_CABO = 0;
	private int CONT_MARECHAL = 0;
	
	private int controleBotao; 
	
	private JPanel[][] panels = new JPanel[QTD_BOTAO][QTD_BOTAO];
	private JButton[][] botoes = new JButton[QTD_BOTAO][QTD_BOTAO];
	
	public JanelaEditor2(int tipo) {
			super("Super Combat");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(150, 50, COMPRIMENTO, LARGURA);
			
			// Chamadas de construtores:
			this.addComponentListener(this);
			this.setResizable(false);
			
			this.controleBotao = -1;
			
			escolha = new JPanel();
			escolha.setLayout(new GridLayout(1, 2));
			tabuleirao = new GridLayout(QTD_BOTAO, QTD_BOTAO);
			teste = new BorderLayout();
			background = new JPanel();
			tabuleiro = new JPanel();
			detalhes = new JPanel();
			detalhes.setBackground(new Color(145, 255, 117));
			detalhes.setPreferredSize(new Dimension(COMPRIMENTO/5, LARGURA));
			tabuleiro.setPreferredSize(new Dimension(TAM_BOTAO*10, TAM_BOTAO*10));
			
			// Começarei aqui a estilizar o editor
			
			background.setLayout(teste);
			tabuleiro.setBorder(new EmptyBorder(5, 5, 5, 5));
			tabuleiro.setLayout(tabuleirao);
			tabuleiro.setBackground(new Color(145, 255, 117));
			detalhes.setBorder(new EmptyBorder(10, 35, 10, 35));
			detalhes.setLayout(new GridLayout(7, 1, 10, 10));
			background.add(tabuleiro, BorderLayout.CENTER);
			background.add(detalhes, BorderLayout.WEST);
			background.add(this.escolha, BorderLayout.SOUTH);
			this.escolha.add(this.jogar, 0);
			this.escolha.add(debug, 1);
			
			
	//		background.add(new JLabel("   "), BorderLayout.SOUTH);
	//		background.add(new JPanel());
			
			// Adicionando inimigos!!!
			
			
			for (int i = 0; i < QTD_BOTAO; i++) {
	            for (int j = 0; j < QTD_BOTAO; j++) {
	                panels[i][j] = new JPanel();
	                panels[i][j].setBorder(new EmptyBorder(0 , 0, 0, 0));
	                botoes[i][j] = new Vazio();
	                botoes[i][j].addActionListener(this);
	                panels[i][j].add(botoes[i][j]);
	                tabuleiro.add(botoes[i][j], i*5+j);
	            }
	        }
			
			
			this.inimigosAleatorios();
			this.LagoAleatorio();

			// Adicionando botoes no de escolha
	        detalhes.add(bandeira);
	        detalhes.add(bomba);
	        detalhes.add(espiao);
	        detalhes.add(soldado);
	        detalhes.add(cabo);
	        detalhes.add(marechal);
	        detalhes.add(desfazer);
	        
	        
	        // Adicionando ActionListener para os botoes do Editor
	        
	        this.bandeira.addActionListener(this);
	        this.bomba.addActionListener(this);
	        this.espiao.addActionListener(this);
	        this.soldado.addActionListener(this);
	        this.cabo.addActionListener(this);
	        this.marechal.addActionListener(this);
	        this.desfazer.addActionListener(this);
	        this.jogar.addActionListener(this);
	        this.debug.addActionListener(this);
	        
	        this.getContentPane().add(background);
			this.revalidate();
	//		this.repaint();
	}

	

	
	public int getCOMPRIMENTO() {
		return COMPRIMENTO;
	}




	public int getLARGURA() {
		return LARGURA;
	}




	public int getTAM_BOTAO() {
		return TAM_BOTAO;
	}




	public int getQTD_BOTAO() {
		return QTD_BOTAO;
	}

	public int getControleBotao() {
		return controleBotao;
	}

	// Selecionador de peças!!!
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.bandeira) {
			this.controleBotao = constante.BANDEIRA;
		}
		else if(e.getSource() == this.bomba) {
			this.controleBotao = constante.BOMBA;
		}
		else if (e.getSource() == this.espiao) {
			this.controleBotao = constante.ESPIAO;
		}
		else if (e.getSource() == this.soldado) {
			this.controleBotao = constante.SOLDADO;
		}
		else if (e.getSource() == this.cabo) {
			this.controleBotao = constante.CABO;
		}
		else if (e.getSource() == this.marechal) {
			this.controleBotao = constante.MARECHAL;
		}
		else if (e.getSource() == this.desfazer) {
			this.controleBotao = 0;
		}
		
		else if (e.getSource() == this.jogar) {
			this.dispose();
			jogao = new JanelaJogo(botoes);
			jogao.setLocationRelativeTo(this);
			jogao.setVisible(true);
		}
		else if (e.getSource() == this.debug) {
			this.Debugao();
		}
		
		// Abaixo é o algoritmo para checar a o lugar selecionado e trocar pela peça desejada!
		
		for (int i = 3; i < QTD_BOTAO; i++) {
            for (int j = 0; j < QTD_BOTAO; j++) {
            	if (e.getSource() == this.botoes[i][j]) {
            		switch(this.controleBotao) {
            		case 0:
            			if (botoes[i][j] instanceof Bandeira) {
            				tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Vazio();
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			
	            			this.CONT_BANDEIRA--;
            			}
            			else if (botoes[i][j] instanceof Bomba) {
            				tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Vazio();
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			
	            			this.CONT_BOMBAS--;
            			}
            			else if (botoes[i][j] instanceof Espiao) {
            				tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Vazio();
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			
	            			this.CONT_ESPIAO--;
            			}
            			else if (botoes[i][j] instanceof Soldado) {
            				tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Vazio();
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			
	            			this.CONT_SOLDADO--;
            			}
            			else if (botoes[i][j] instanceof CaboArmeiro) {
            				tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Vazio();
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			
	            			this.CONT_CABO--;
            			}
            			else if (botoes[i][j] instanceof Marechal) {
            				tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Vazio();
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			
	            			this.CONT_MARECHAL--;
            			}
            			break;
            			
            		case 1:
            			if (this.CONT_BANDEIRA < constante.LIM_BANDEIRA){
	            			tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Bandeira(0);
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			this.CONT_BANDEIRA++;
            			}
            			break;
            		case 2:
            			if (this.CONT_BOMBAS < constante.LIM_BOMBAS) {
	            			tabuleiro.remove(botoes[i][j]);
	            			botoes[i][j] = new Bomba(0);
	            			tabuleiro.add(botoes[i][j], i*5+j);
	            			this.botoes[i][j].addActionListener(this);
	            			this.revalidate();
	            			this.CONT_BOMBAS++;
            			}
            			break;
            		case 3:
            			if (this.CONT_ESPIAO < constante.LIM_ESPIAO) {
            				tabuleiro.remove(botoes[i][j]);
            				botoes[i][j] = new Espiao(0);
                			tabuleiro.add(botoes[i][j], i*5+j);
                			this.botoes[i][j].addActionListener(this);
                			this.revalidate();
                			this.CONT_ESPIAO++;
            			}
            			break;
            		case 4:
            			if (this.CONT_SOLDADO < constante.LIM_SOLDADOS) {
            				tabuleiro.remove(botoes[i][j]);
            				botoes[i][j] = new Soldado(0);
                			tabuleiro.add(botoes[i][j], i*5+j);
                			this.botoes[i][j].addActionListener(this);
                			this.revalidate();
                			this.CONT_SOLDADO++;
            			}
            			break;
            		case 5:
            			if (this.CONT_CABO < constante.LIM_CABO) {
            				tabuleiro.remove(botoes[i][j]);
            				botoes[i][j] = new CaboArmeiro(0);
                			tabuleiro.add(botoes[i][j], (i*5+j));
                			this.botoes[i][j].addActionListener(this);
                			this.revalidate();
                			this.CONT_CABO++;
            			}
            			break;
            		case 6:
            			if (this.CONT_MARECHAL < constante.LIM_MARECHAL) {
            				tabuleiro.remove(botoes[i][j]);
            				botoes[i][j] = new Marechal(0);
                			tabuleiro.add(botoes[i][j], i*5+j);
                			this.botoes[i][j].addActionListener(this);
                			this.revalidate();
                			this.CONT_MARECHAL++;
            			}
            			break;
//            		case 0:
//            			tabuleiro.remove(botoes[i][j]);
//            			tabuleiro.add(new Bandeira(), i*5+j);
//            			this.revalidate();
//            			break;
            		}
            	}
            }
           
		}
		System.out.println(this.controleBotao);
	}
	// função de gerador de inimigos!
	public void inimigosAleatorios() {
		this.listaPosi.add(constante.BANDEIRA);
		this.listaPosi.add(constante.MARECHAL);
		this.listaPosi.add(constante.SOLDADO);
		this.listaPosi.add(constante.ESPIAO);
		for (int i = 0; i < 2; i++) {
			this.listaPosi.add(constante.BOMBA);
			this.listaPosi.add(constante.SOLDADO);
			this.listaPosi.add(constante.CABO);
		}
		Collections.shuffle(listaPosi);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				this.tabuleiro.remove(botoes[i][j]);
				if (listaPosi.get(i*5+j) == constante.BANDEIRA) {
					botoes[i][j] = new Bandeira(1);
	                botoes[i][j].addActionListener(this);
	                tabuleiro.add(botoes[i][j], i*5+j);
				}
				else if (this.listaPosi.get(i*5+j) == constante.BOMBA) {
					botoes[i][j] = new Bomba(1);
	                botoes[i][j].addActionListener(this);
	                tabuleiro.add(botoes[i][j], i*5+j);
				}
				else if (this.listaPosi.get(i*5+j) == constante.ESPIAO) {
					botoes[i][j] = new Espiao(1);
	                botoes[i][j].addActionListener(this);
	                tabuleiro.add(botoes[i][j], i*5+j);
				}
				else if (this.listaPosi.get(i*5+j) == constante.SOLDADO) {
					botoes[i][j] = new Soldado(1);
	                botoes[i][j].addActionListener(this);
	                tabuleiro.add(botoes[i][j], i*5+j);
				}
				else if (this.listaPosi.get(i*5+j) == constante.CABO) {
					botoes[i][j] = new CaboArmeiro(1);
	                botoes[i][j].addActionListener(this);
	                tabuleiro.add(botoes[i][j], i*5+j);
				}
				else if (this.listaPosi.get(i*5+j) == constante.MARECHAL) {
					botoes[i][j] = new Marechal(1);
	                botoes[i][j].addActionListener(this);
	                tabuleiro.add(botoes[i][j], i*5+j);
				}
				this.revalidate();
			}
		}
		this.listaPosi.removeAll(listaPosi);
	}
	
	
	public void LagoAleatorio() {
		for (int i = 1; i < 4; i++) {
			this.listaPosi.add(i);
		}
		Collections.shuffle(listaPosi);
		
		this.tabuleiro.remove(botoes[2][this.listaPosi.get(0)]);
		botoes[2][this.listaPosi.get(0)] = new Lago();
		this.tabuleiro.add(botoes[2][this.listaPosi.get(0)], 10+this.listaPosi.get(0));
		this.revalidate();
		this.listaPosi.removeAll(listaPosi);
	}
	
	public void Debugao() {
		debugador++;
		if (debugador == 1) {
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
		else if (debugador == 2) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					this.botoes[i][j].setText("Inimigo");
				}
			}
			debugador = 0;
		}
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
