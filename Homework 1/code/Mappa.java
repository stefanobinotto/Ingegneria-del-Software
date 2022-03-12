import java.io.*;
import java.util.Scanner;

public class Mappa {

	private Casella[][] matrix;
	private int size;
	private String time;
	private int elementi;
	
	//costruttore				 					
	public Mappa() {
		matrix = new Casella[5][5];
		size = 5*5;
		elementi = 0;
		time = "Giorno";
	}
	
	//metodi base
	private int size() {
		return elementi;
	}
	private boolean isEmpty() {
		if (elementi == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//metodi per settare o ritornare il tempo (giorno/notte)
	private void setTime(String t) {
		//check
		if (!t.equalsIgnoreCase("giorno") && !t.equalsIgnoreCase("notte")) {
			throw new IllegalArgumentException("Il tempo non corrisponde a giorno/notte");
		}
		time = t;
	}
	private String getTime() {
		return time;
	}
	

	//metodo di input pezzi in mappa
	private void Input(String s) throws IOException {
		
		//aggiungere i tipi alle caselle della mappa
		setTipo();
		
		//lettura da file
		FileReader reader = new FileReader(s+".txt");
		
		//check
		if (reader == null) {
			throw new FileNotFoundException("File non trovato");
		}
		
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
		
			String line = in.nextLine();
			Scanner tok = new Scanner(line);
			
			while (tok.hasNext()) {
			int X, Y;
			String tipo ;
				try {
					X = tok.nextInt();
					Y = tok.nextInt();
					tipo = tok.next();
					
					//aggiunta pezzo alla mappa solo se coordinate accettabili
					if (X >= 0 && X <= 4 && Y >= 0 && Y <= 4) {
					
						if (tipo.equalsIgnoreCase("Elfo")) {
							matrix[X][Y].addPezzo(new Elfo());
							elementi++;
						}
						if (tipo.equalsIgnoreCase("Nano")) {
							matrix[X][Y].addPezzo(new Nano());
							elementi++;
						}
						if (tipo.equalsIgnoreCase("Orco")) {
							matrix[X][Y].addPezzo(new Orco());
							elementi++;
						}
					}
					
					/*
					Se tipo non corrisponde ad Elfo/Nano/Orco oppure le coordinate in input vanno fuori limite allora si ignora il pezzo e si passa a quello successivo
					*/
					
				} catch (Exception e) {
				
					//interruzione
					System.out.println("Formato di input (X/Y/tipo) di una pedina non accettabile");
					System.exit(1);
				}
			}
			tok.close();
		}
		in.close();
		reader.close();
	}

	//Set Tipo
	private void setTipo() {

		//set Montagna
		for (int i = 0; i < 5; i++) {
			matrix[i][0] = new Casella("Montagna");
		}
		for (int i = 0; i < 5; i++) {
			matrix[i][4] = new Casella("Montagna");
		}
		//set Bosco
		for (int i = 0; i < 5; i++) {
			matrix[i][1] = new Casella("Bosco");
		}
		for (int i = 0; i < 5; i++) {
			matrix[i][3] = new Casella("Bosco");
		}
		//set Pianura
		for (int i = 0; i < 5; i++) {
			matrix[i][2] = new Casella("Pianura");
		}
	}
	
	
	// (1) Il numero di pezzi presenti sulla mappa per ciascuna tipologia
	private int totElfi() {
		if (isEmpty()) {
			return 0;
		}
		int Elfi = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Elfi = Elfi + (matrix[i][j].contaElfo());
			}
		}
		return Elfi;
	}
	private int totOrchi() {
		if (isEmpty()) {
			return 0;
		}
		int Orchi = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Orchi = Orchi + (matrix[i][j].contaOrco());
			}
		}
		return Orchi;
	}
	private int totNani() {
		if (isEmpty()) {
			return 0;
		}
		int Nani = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Nani = Nani + (matrix[i][j].contaNano());
			}
		}
		return Nani;
	}


	// (2) La casella con il maggior valore di difesa di giorno
	private int maxDefDay() {
	
		int max = matrix[0][0].getTotDef("Giorno");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (max < matrix[i][j].getTotDef("Giorno")) {
					max = matrix[i][j].getTotDef("Giorno");
				}
			}
		}
		return max;
	}
	
	
	// (3) La casella con il maggior valore di difesa di notte
	private int maxDefNight() {
	
		int max = matrix[0][0].getTotDef("Notte");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (max < matrix[i][j].getTotDef("Notte")) {
					max = matrix[i][j].getTotDef("Notte");
				}
			}
		} 
		return max;
	}


	// (4) La casella con il maggior valore di attacco di giorno
	private int maxAttDay() {
	
		
		int max = matrix[0][0].getTotAtt("Giorno");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (max < matrix[i][j].getTotAtt("Giorno")) {
					max = matrix[i][j].getTotAtt("Giorno");
				}
			}
		}
		return max;
	}


	// (5) La casella con il maggior valore di attacco di notte	
	private int maxAttNight() {
	
		int max = matrix[0][0].getTotAtt("Notte");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (max < matrix[i][j].getTotAtt("Notte")) {
					max = matrix[i][j].getTotAtt("Notte");
				}
			}
		}
		return max;
	}


	// (6) La casella con il maggior numero di pezzi dello stesso tipo
	private int maxTipo(String tipo) {
	
		//check
		if (!tipo.equalsIgnoreCase("Nano") && !tipo.equalsIgnoreCase("Elfo") && !tipo.equalsIgnoreCase("Orco")) {
			throw new IllegalArgumentException("Il tipo del pezzo e' diverso da elfo/nano/orco");
		}
		
		int max = 0;
		//Caso tipo = Nano
		if (tipo.equalsIgnoreCase("Nano")) {
			max = matrix[0][0].contaNano();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (max < matrix[i][j].contaNano()) {
						max = matrix[i][j].contaNano();
					}
				}
			}
		}

		//Caso tipo = Elfo
		if (tipo.equalsIgnoreCase("Elfo")) {
			max = matrix[0][0].contaElfo();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (max < matrix[i][j].contaElfo()) {
						max = matrix[i][j].contaElfo();
					}
				}
			}
		}
		
		//Caso tipo = Orco
		if (tipo.equalsIgnoreCase("Orco")) {
			max = matrix[0][0].contaOrco();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (max < matrix[i][j].contaOrco()) {
						max = matrix[i][j].contaOrco();
					}
				}
			}
		}
		return max;
	}
	
	
	//Stampa domande
	public void Gioca() throws java.io.IOException {
		
		//scelta del file, lettura da file e inserimento pedine
		try {
			System.out.println("Inserisci input1, input2 o input3: ");
			Scanner in = new Scanner(System.in);
			String input = in.next();
			System.out.println();
			Input(input);
			in.close();
		} catch (Exception e) {
			//interruzione
			System.out.println("File non esistente!!!");
			System.exit(1);
		}
				
		//check
		if (elementi == 0) {
			System.out.println("*** Mappa vuota ***");
			return;
		}
		
		System.out.println("La mappa contiene "+size()+" pezzi ed e' "+getTime());
		System.out.println();
		
		//domanda 1
		System.out.println(" (1) Il numero di pezzi presenti sulla mappa per ciascuna tipologia:\n "+ totElfi() +" elfi, "+ totOrchi()+" orchi, "+ totNani() +" nani.");
		System.out.println();
		
		//domande da 2 a 5
		System.out.println(" (2) Le caselle con il maggior valore di attacco di notte sono: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxAttNight() == matrix[i][j].getTotAtt("Notte")) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ) "+matrix[i][j].toString());
				}
			}
		}
		System.out.println();
		
		System.out.println(" (3) Le caselle con il maggior valore di attacco di giorno sono: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxAttDay() == matrix[i][j].getTotAtt("Giorno")) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ), "+matrix[i][j].toString());
				}
			}
		}
		System.out.println();
		
		System.out.println(" (4) Le caselle con il maggior valore di difesa di notte sono: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxDefNight() == matrix[i][j].getTotDef("Notte")) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ), "+matrix[i][j].toString());
				}
			}
		}
		System.out.println();
		
		System.out.println(" (5) Le caselle con il maggior valore di difesa di giorno sono: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxDefDay() == matrix[i][j].getTotDef("Giorno")) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ), "+matrix[i][j].toString());
				}
			}
		}
		System.out.println();
		
		//domanda 6
		System.out.println(" (6) Le caselle con il maggior numero di pezzi dello stesso tipo sono: ");
		System.out.println(" - Elfo: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxTipo("Elfo") == matrix[i][j].contaElfo()) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ), "+matrix[i][j].toString());
				}
			}
		}
		System.out.println(" - Orco: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxTipo("Orco") == matrix[i][j].contaOrco()) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ), "+matrix[i][j].toString());
				}
			}
		}
		System.out.println(" - Nano: ");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (maxTipo("Nano") == matrix[i][j].contaNano()) {
					System.out.println("Coordinate: ( "+i+" , "+j+" ), "+matrix[i][j].toString());
				}
			}
		}
	}	
}
