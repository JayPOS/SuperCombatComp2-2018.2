package auxiliar;

public enum ControleBotao {
	BANDEIRA(1),
	BOMBA(2), 
	ESPIAO(3),
	SOLDADO(4),
	CABO(5),
	MARECHAL(6);
	
	private int controle;

	ControleBotao(int control) {
		this.controle = control;
	}
	
	public int getControle() {
		return this.controle;
	}
	
	
}
