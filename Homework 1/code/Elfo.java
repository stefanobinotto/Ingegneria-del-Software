public class Elfo extends Pezzo {
	
	//costruttore
	public Elfo() {
		super(5, 2, "Elfo");
	}
	
	//override metodo getDef() della classe Pezzo
	public int getDef(String Tipo, String time) {

		//modificatore
		if (Tipo.equalsIgnoreCase("Bosco")) {
			return def*2;
		}		
		
		return def;
	}
}
