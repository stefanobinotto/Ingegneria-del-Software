public class Pezzo {
	protected int att;
	protected int def;
	protected String tipo;
	
	public Pezzo(int a, int d, String t) {
		att = a;
		def = d;
		tipo = t;
	}
	
	public Pezzo() {
		this(0,0,"");
	}
	
	public int getAtt(String Tipo, String time) {
		return att;
	}
	public int getDef(String Tipo, String time) {
		return def;
	}
	public String getTipo() {
		return tipo;
	}
}
