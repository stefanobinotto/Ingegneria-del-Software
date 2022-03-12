public class Nano extends Pezzo {
	public Nano() {
		super(2, 5, "Nano");
	}
	
	public int getAtt(String Tipo, String time) {
		if (Tipo.equalsIgnoreCase("Montagna")) {
			return att*2;
		}
		return att;
	}
}
