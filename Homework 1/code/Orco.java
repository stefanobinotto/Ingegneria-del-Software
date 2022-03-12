public class Orco extends Pezzo {
	public Orco() {
		super(4, 4, "Orco");
	}
	
	//modificatori
	public int getAtt(String Tipo, String time) {
		if (time.equalsIgnoreCase("Giorno")) {
			return att/2;
		} else if(time.equalsIgnoreCase("Notte")) {
			return att+(att/2);
		}
		return att;
	}
	
	public int getDef(String Tipo, String time) {
		if (time.equalsIgnoreCase("Giorno")) {
			return def/2;
		} else if(time.equalsIgnoreCase("Notte")) {
			return def+(def/2);
		}
		return def;
	}
}
