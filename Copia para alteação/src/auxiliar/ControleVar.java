package auxiliar;

import javax.swing.JButton;

public class ControleVar {
	
	public int debugador, click, qtd_pecas, dicaOn, contDicas;
	private int X, Y;
	public JButton Peca;

	public ControleVar() {
		this.debugador = 0;
		this.click = 0;
		this.qtd_pecas = 0;
		this.dicaOn = 0;
		this.contDicas = 0;
		this.X = 0;
		this.Y = 0;
		this.Peca = null;
	}
	
	public int modulo(int m) {
		if (m < 0) {
			return -m;
		}
		return m;
	}
	
	public void setXeY(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

}
