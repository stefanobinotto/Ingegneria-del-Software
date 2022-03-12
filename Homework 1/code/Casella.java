public class Casella {
	
	private String tipologia;
	private Pezzo[] pezzo;
	private int size;
	
	//costruttore
	public Casella(String t) {
		pezzo = new Pezzo[5];
		size = 0;
		if (!t.equalsIgnoreCase("Pianura") && !t.equalsIgnoreCase("Bosco") && !t.equalsIgnoreCase("Montagna")) {
			throw new IllegalArgumentException("Il luogo non corrisponde a pianura/bosco/montagna");
		}
		tipologia = t;
	}
	
	//metodi base
	private int size() {
		return size;
	}
	private boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}	
	
		
	//aggiunta pedina
	public void addPezzo(Pezzo p) {
		//check
		if (size >= 5) {
				return;
			}
		pezzo[size] = p;
		size++;
	}
	
	//rimuovi pedina
	public void removePezzo(Pezzo p){
		//check
		if (isEmpty()) {
			return;
		}
		int index = Search(pezzo, p);
		pezzo[index] = null;
		size--;
	}	
	
	//return attacco totale della casella
	public int getTotAtt(String tempo) {
		//check
		if (size <= 0) {
			return 0;
		}
		int totAtt = 0;
		for (int i = 0; i <= size -1; i++) {
			totAtt = totAtt + (pezzo[i]).getAtt(tipologia, tempo);
		}
		return totAtt;
	}
	
	//return attacco difesa della casella
	public int getTotDef(String tempo) {
		//check
		if (size <= 0) {
			return 0;
		}
		int totDef = 0;
		for (int i = 0; i <= size -1; i++) {
			totDef = totDef + (pezzo[i]).getDef(tipologia, tempo);
		}
		return totDef;
	}
		
	//get numero elfi in casella
	public int contaElfo() {
		//check
		if (isEmpty()) {
			return 0;
		}
		int count = 0;
		for(int i = 0; i < size; i++) {
			if ((pezzo[i].getTipo()).equalsIgnoreCase("Elfo")) {
				count++;
			}
		}
		return count;
	}
	
	//return numero orchi in casella
	public int contaOrco() {
		//check
		if (isEmpty()) {
				return 0;
			}
		int count = 0;
		for(int i = 0; i < size; i++) {
			if ((pezzo[i].getTipo()).equalsIgnoreCase("Orco")) {
				count++;
			}
		}
		return count;
	}
	
	//return numero nani in casella
	public int contaNano() {
		if (isEmpty()) {
				return 0;
			}
		int count = 0;
		for(int i = 0; i < size; i++) {
			if ((pezzo[i].getTipo()).equalsIgnoreCase("Nano")) {
				count++;
			}
		}
		return count;
	}	
	
	
	//metodo toString
	public String toString() {
		//check
		if (isEmpty()) {
			return "casella di tipo: "+tipologia+" è vuota";
		}
		return "tipologia: "+ tipologia +", elfi: "+ contaElfo() +", orchi: "+ contaOrco() + ", nani: " + contaNano();
	}
	
	//algoritmo di ricerca
	private static int Search(Pezzo[] array, Pezzo p) {
		int i;
		int index = -1;
		for (i = 0; index == -1 && i < array.length; i++) {
			if (array[i] == p) {
				index = i;
			}
		}
		return index;
	}
}
