import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import java.lang.UnsupportedOperationException;
import java.io.*;
import java.lang.*;
import java.lang.ref.*;
import java.util.*;

/**
	TEST DELLA CLASSE MAPADAPTER
	
	@safe.summary classe per testare la classe MapAdapter.
	@safe.suitedesign viene testato il funzionamento di tutti i singoli metodi di MapAdapter e MapAdapterIterator anche nei casi limiti. Poi vengono testate le classi interne MapAdapterKeySet, MapAdapterEntrySet e MapAdapterValues con i relativi iteratori. Di queste classi interne viene inoltre testata la capacita' di traslare alla mappa le modifiche apportate ad un oggetto di una di queste classi e viceversa. I test sono stati sviluppati usando tre comandi JUnit: AssertEquals, AssertThrows e AssertNotSame.
	@author Binotto Stefano
	@version 8/6/2020
*/

public class TestMapAdapter {
	
	private MapAdapter<Integer,String> c;
	private static int counter;
	
	//metodi before
	/**
		Inizializzare il contatore di test eseguiti
	*/
	@BeforeClass
	public static void beforeClass() {
		counter = 0;
	}
	/**
		Inizializzare il set, su cui vengono testati i metodi, prima di ogni test
	*/
	@Before
	public void setUp() {
		c = new MapAdapter<Integer, String>();
		counter++;
	}
	/**
		Stampare numero del test appena eseguito
	*/
	@After
	public void after() {
		System.out.println("Test numero "+counter+ " corretto");
	}
	/**
		Stampare numero totale di test eseguiti
	*/
	@AfterClass
	public static void printCount() {
		System.out.println("*** "+counter+" test eseguiti su MapAdapter ****");
	}
	
	/**
		Test clear()
		@safe.precondition una map con un elemento
		@safe.postcondition una map vuota
		@safe.testcases controllare se una map con degli elementi si svuota chiamando clear()
	*/
	@Test
	public void testClear() {
		c.put(0,"pippo");
		c.clear();
		
		assertEquals(true, c.isEmpty());
	} 
	/**
		Test containsKey()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se containsKey() conferma la presenza di una chiave specifica
	*/
	@Test
	public void testContainsKey() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertEquals(true, c.containsKey(2));
		assertEquals(false, c.containsKey(4));
	}
	/**
		Test containsKey()
		@safe.precondition una map con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si cerca una chiave null
	*/
	@Test
	public void testContainsKeyNullpointerException() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertThrows(NullPointerException.class, () -> {
			c.containsKey(null);
		});
	}
	/**
		Test containsValue()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se containsValue() conferma la presenza di un valore specifico
	*/
	@Test
	public void testContainsValue() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertEquals(true, c.containsValue("pippo"));
		assertEquals(false, c.containsValue("casa"));
	}
	/**
		Test containsValue()
		@safe.precondition una map con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si cerca un valore null
	*/
	@Test
	public void testContainsValueNullpointerException() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertThrows(NullPointerException.class, () -> {
			c.containsValue(null);
		});
	}
	/**
		Test entrySet()
		@safe.precondition una map con due elementi
		@safe.postcondition una map con due elementi
		@safe.testcases controllare se entrySet() restituisce un set contenente gli entry della map
	*/
	@Test
	public void testEntrySet() {
		c.put(2, "pippo");
		c.put(3, "pluto");
		Set<Map.Entry<Integer,String>> t = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = t.iterator();
		int i = (iter.next()).getKey();
		assertEquals(true, c.containsKey(i));
	}	
	/**
		Test equals()
		@safe.precondition una map con true elementi
		@safe.postcondition una map con true elementi
		@safe.testcases controllare se l'oggetto passato e' uguale alla map
	*/
	@Test
	public void testEquals() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		MapAdapter<Integer, String> tmp = new MapAdapter<Integer, String>();
		
		tmp.put(0,"pippo");
		tmp.put(1,"pluto");
		tmp.put(2,"paperino");
		
		assertEquals(true, c.equals(tmp));	
	}
	/**
		Test equals()
		@safe.precondition una map con true elementi
		@safe.postcondition una map con true elementi
		@safe.testcases controllare se l'oggetto passato e' diverso dalla map, poiche' la classe dell'oggetto non corrisponde a quella della map
	*/
	@Test
	public void testEquals2() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		ListAdapter<String> tmp = new ListAdapter<String>();
		
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		assertEquals(false, c.equals(tmp));	
	}
	/**
		Test equals()
		@safe.precondition una map con due elementi
		@safe.postcondition una map con due elementi
		@safe.testcases controllare se l'oggetto passato e' diverso dalla map, poiche' la dimensione dell'oggetto non corrisponde a quela della map
	*/
	@Test
	public void testEquals3() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		
		MapAdapter<Integer, String> tmp = new MapAdapter<Integer, String>();
		
		tmp.put(0,"pippo");
		tmp.put(1,"pluto");
		tmp.put(2,"paperino");
		
		assertEquals(false, c.equals(tmp));	
	}
	/**
		Test equals()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se l'oggetto passato e' diverso dalla map, poiche' contengono valori diversi
	*/
	@Test
	public void testEquals5() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		MapAdapter<Integer, String> tmp = new MapAdapter<Integer, String>();
		
		tmp.put(0,"pippo2");
		tmp.put(1,"pluto2");
		tmp.put(2,"paperino2");
		
		assertEquals(false, c.equals(tmp));	
	}
	/**
		Test get()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se get() restituisce la chiave esatta 
	*/
	@Test
	public void testGet() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertEquals("pluto", c.get(1));
	}
	/**
		Test get()
		@safe.precondition una map con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave null
	*/
	@Test
	public void testGetNullpointerException() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertThrows(NullPointerException.class, () -> {
			c.get(null);
		});
	}
	/**
		Test getOrDefault()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se getOrDefault() restituisce il valore a cui e' associata la chiave specificata
	*/
	@Test
	public void testGetOrDefault() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertEquals("pluto", c.getOrDefault(1,"errore"));
	}
	/**
		Test getOrDefault()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se getOrDefault() restituisce il valore di default se specifico una chiave non contenuta nella mappa
	*/
	@Test
	public void testGetOrDefault2() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertEquals("errore", c.getOrDefault(5,"errore"));
	}
	/**
		Test getOrDefault()
		@safe.precondition una map con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave null
	*/
	@Test
	public void testGetOrDefaultNullpointerException() {
		c.put(0,"pippo");
		c.put(1,"pluto");
		c.put(2,"paperino");
		
		assertThrows(NullPointerException.class, () -> {
			c.getOrDefault(null,"errore");
		});
	}
	/**
		Test hashCode()
		@safe.precondition una map con un elemento
		@safe.postcondition una map con un elemento
		@safe.testcases controllare se hashCode() genera l'hashcode corretto
	*/
	@Test
	public void testHashCode() {

		c.put(1,"pippo");
		int hashcode = 1^106673622;
		assertEquals(hashcode, c.hashCode());
	}
	/**
		Test isEmpty()
		@safe.precondition una map vuota
		@safe.postcondition una map vuota
		@safe.testcases controllare se isEmpty() conferma che la map e' vuota
	*/
	@Test
	public void testIsEmpty() {
		c.clear();
		assertEquals(true, c.isEmpty());
	}
	/**
		Test keySet()
		@safe.precondition una map con due elementi
		@safe.postcondition una map con due elementi
		@safe.testcases controllare se keySet() restituisce un set contenente le chiavi della map
	*/
	@Test
	public void testKeySet() {
		c.put(2, "pippo");
		c.put(3, "pluto");
		Set<Integer> t = c.keySet();
		Iterator<Integer> iter = t.iterator();
		assertEquals(true, c.containsKey(iter.next()));
	}
	/**
		Test put()
		@safe.precondition una map con un elemento
		@safe.postcondition una map con un elemento
		@safe.testcases controllare se effettivamente viene aggiunto un elemento alla map
	*/
	@Test
	public void testPut() {
		c.put(0,"pippo");
		
		assertEquals(false, c.isEmpty());
	}
	/**
		Test put()
		@safe.precondition una map con un elemento
		@safe.postcondition una map con un elemento
		@safe.testcases controllare se chiamando put() con una chiave gia' accoppiata viene restituito il vecchio valore
	*/
	@Test
	public void testPut2() {
		c.put(0,"pippo");
		
		assertEquals("pippo", c.put(0,"pippo2"));
	}
	/**
		Test put()
		@safe.precondition una map vuota
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave o valore null
	*/
	@Test
	public void testPutNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.put(null,"pluto");
		});
		assertThrows(NullPointerException.class, () -> {
			c.put(0, null);
		});
	}
	/**
		Test putAll()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con due elementi
		@safe.testcases controllare se vengono aggiunti alla map tutti gli elementi dentro la map passata
	*/
	@Test
	public void testPutAll() {
		Map<Integer, String> t = new MapAdapter<Integer, String>();
		t.put(0, "pippo");
		t.put(1, "pluto");
		
		c.putAll(t);
		
		assertEquals(true, c.containsValue("pluto"));
		assertEquals(true, c.containsValue("pippo"));
	}
	/**
		Test putAll()
		@safe.precondition una map vuota
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una map null
	*/
	@Test
	public void testPutAllNullPointerException() {
		Map<Integer, String> t = new MapAdapter<Integer, String>();
		t.put(0, "pippo");
		t.put(1, "pluto");
		
		assertThrows(NullPointerException.class, () -> {
			c.putAll(null);
		});
	}
	/**
		Test putIfAbsent()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con due elementi
		@safe.testcases controllare se la map viene modificata e se restituisce null se la chiave non e' associata a nessun valore
	*/
	@Test
	public void testPutIfAbsent() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		
		assertEquals("pippo", c.putIfAbsent(0,"pippo2"));
		assertEquals(null, c.putIfAbsent(3,"pippo2"));
	}
	/**
		Test putIfAbsent()
		@safe.precondition un mappa con due elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave o valore null
	*/
	@Test
	public void testPutIfAbsentAllNullPointerException() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		assertThrows(NullPointerException.class, () -> {
			c.putIfAbsent(null, "paperino");
		});
		assertThrows(NullPointerException.class, () -> {
			c.putIfAbsent(2, null);
		});
	}
	/**
		Test remove()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con un elemento
		@safe.testcases controllare se a seguito della chiamata di remove() la map e' cambiata
	*/
	@Test
	public void testRemove() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertEquals(null, c.remove(5));
		assertEquals("pippo", c.remove(0));
	}
	/**
		Test remove()
		@safe.precondition un mappa con due elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave null
	*/
	@Test
	public void testRemoveNullPointerException() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertThrows(NullPointerException.class, () -> {
			c.remove(null);
		});
	}
	/**
		Test remove()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con un elemento
		@safe.testcases controllare se a seguito della chiamata di remove() la map e' cambiata o meno in base alle chiavi o valori specificati
	*/
	@Test
	public void testRemove2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertEquals(true, c.remove(0, "pippo"));
		assertEquals(1, c.size());
		assertEquals(false, c.remove(1, "pippo"));
		assertEquals(false, c.remove(0, "pluto"));
		assertEquals(false, c.remove(4, "pluto"));
		assertEquals(false, c.remove(1, "paperino"));
	}
	/**
		Test remove()
		@safe.precondition un mappa con due elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave o valore null
	*/
	@Test
	public void testRemoveNullPointerEXception2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertThrows(NullPointerException.class, () -> {
			c.remove(null, "paperino");
		});
		assertThrows(NullPointerException.class, () -> {
			c.remove(1, null);
		});
	}
	/**
		Test replace()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con due elementi
		@safe.testcases controllare se modifico valori senza modificare la dimensione della map
	*/
	@Test
	public void testReplace() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertEquals("pippo", c.replace(0, "pippo2"));
		assertEquals(null, c.replace(2, "pippo2"));
	}
	/**
		Test replace()
		@safe.precondition un mappa con due elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave o valore null
	*/
	@Test
	public void testReplaceNullPointerException() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertThrows(NullPointerException.class, () -> {
			c.replace(null, "paperino");
		});
		assertThrows(NullPointerException.class, () -> {
			c.replace(1, null);
		});
	}
	/**
		Test replace()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con due elementi
		@safe.testcases controllare se modifico valori senza modificare la dimensione della map solo se la chiave e' mappata con un valore specifico
	*/
	@Test
	public void testReplace2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertEquals(true, c.replace(0, "pippo", "pippo2"));
		assertEquals(false, c.replace(0, "pluto", "pippo2"));
	}
	/**
		Test replace()
		@safe.precondition un mappa con due elementi
		@safe.postcondition un mappa con due elementi
		@safe.testcases controllare se lancia l'eccezione NullPointerException() quando si inserisce una chiave, nuovo valore o vecchio valore null
	*/
	@Test
	public void testReplaceNullPointerException2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertThrows(NullPointerException.class, () -> {
			c.replace(null, "pippo", "pippo2");
		});
		assertThrows(NullPointerException.class, () -> {
			c.replace(0, null, "pippo2");
		});
		assertThrows(NullPointerException.class, () -> {
			c.replace(0, "pippo", null);
		});
	}
	/**
		Test size()
		@safe.precondition una map con due elementi
		@safe.postcondition una map con due elementi
		@safe.testcases controllare se size() restituisce il numero di elementi della map
	*/
	@Test
	public void testSize() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		assertEquals(2, c.size());
	}
	/**
		Test values()
		@safe.precondition una map con due elementi
		@safe.postcondition una map con due elementi
		@safe.testcases controllare se values() restituisce una collection di valori
	*/
	@Test
	public void testValues() {
		c.put(2, "pippo");
		c.put(3, "pluto");
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		String v = iter.next();
		assertEquals(true, c.containsValue(v));
	}

	//test iterator di MapAdapter
	
	/**
		Test hasNext()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento della map
	*/
	@Test
	public void testHasNext() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Iterator<Integer> iter = c.iterator();
		
		assertEquals(true, iter.hasNext());		
	}
	/**
		Test hasNext()
		@safe.precondition una map con tre elementi
		@safe.postcondition una map con tre elementi
		@safe.testcases controllare se hasNext() conferma che siamo all'ultimo elemento della map
	*/
	@Test
	public void testHasNext2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Iterator<Integer> iter = c.iterator();
		
		iter.next();
		iter.next();
		iter.next();
		
		assertEquals(false, iter.hasNext());		
	}
	/**
		Test next()
		@safe.precondition una map con un elemento
		@safe.postcondition una map con un elemento
		@safe.testcases controllare se next() restituisce l'elemento della map
	*/
	@Test
	public void testNext() {
		c.put(0, "pippo");
		
		Iterator<Integer> iter = c.iterator();
		
		assertEquals(0, (int)iter.next());
	}
	/**
		Test next()
		@safe.precondition una map con un elemento
		@safe.postcondition una map con un elemento
		@safe.testcases controllare se chiamando next alla fine della map viene lanciato NoSuchElementException
	*/
	@Test
	public void testNextNoSuchElementException() {
		c.put(0, "pippo");
		
		Iterator<Integer> iter = c.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}
	/**
		Test remove()
		@safe.precondition una map con un elemento
		@safe.postcondition una map vuota
		@safe.testcases controllare se la map, dopo aver chiamato remove(), risulta vuota
	*/
	@Test
	public void testRemoveIterator() {
		c.put(0, "pippo");
		
		Iterator<Integer> iter = c.iterator();
		iter.next();
		iter.remove();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una map con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveIteratorIllegalStateException() {
		c.put(0, "pippo");
		
		Iterator<Integer> iter = c.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition una map con due elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamiamo remove() subito dopo averlo richiamato
	*/
	@Test
	public void testRemoveIteratorIllegalStateException2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		
		Iterator<Integer> iter = c.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	
	
	/**
		TEST DELLA CLASSE MAPADAPTERKEYSET
		
		LE ECCEZZIONI SONO GIA' STATE TESTATE PRECEDENTEMENTE
	
		@safe.summary ancora da fare
		@safe.suitedesign ancora da fare
		@author Binotto Stefano
	*/
	
	/**
		Test clear()
		@safe.precondition una map vuota e un set vuoto
		@safe.postcondition una map vuota e un set vuoto
		@safe.testcases controllare se il set e la map si svuotano chiamando clear()
	*/
	@Test
	public void testClear2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		tmp.clear();
		
		assertEquals(true, tmp.isEmpty());
		assertEquals(true, c.isEmpty());
	}
	/**
		TESTO CLEAR PER VEDERE SE MODIFICHE AL SET SI RIPERCUOTANO ANCHE SULLA MAPPA
		
		@safe.precondition una map con tre elementi e un set vuoto
		@safe.postcondition una map vuota e un set vuoto
		@safe.testcases controllare se chiamando clear sulla map viene modificato anche il set
	*/
	@Test
	public void testClear3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		c.clear();
		
		assertEquals(true, tmp.isEmpty());
	}
	/**
		Test contains()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se contains() trova l'elemento aggiunto nel set e non trova quello casuale
	*/
	@Test
	public void testContains() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		assertEquals(true, tmp.contains(1));
		assertEquals(false, tmp.contains(5));
	}
	/**
		Test containsAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata dentro il set
	*/
	@Test
	public void testContainsAll() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		SetAdapter<Integer> t = new SetAdapter<Integer>();
		t.add(0);
		t.add(1);
		t.add(2);
		Set<Integer> tmp = c.keySet();
		
		assertEquals(true, tmp.containsAll(t));
		t.add(12);
		assertEquals(false, tmp.containsAll(t));
	}
	/**
		Test equals()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se l'oggetto passato e' uguale al set
	*/
	@Test
	public void testEquals6() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		SetAdapter<Integer> t = new SetAdapter<Integer>();
		t.add(0);
		t.add(1);
		t.add(2);
		
		assertEquals(true, tmp.equals(t));
		t.add(89);
		assertEquals(false, tmp.equals(t));
	}
	/**
		Test hashCode()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se hashcode() genera l'hashcode corretto
	*/
	@Test
	public void testHashCode3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		int hashcode = 0 + 1 + 2;
		assertEquals(hashcode, tmp.hashCode());
	}
	/**
		Test isEmpty()
		@safe.precondition una map e un set vuoto
		@safe.postcondition una map e un set vuoto
		@safe.testcases controllare se isEmpty() conferma che il set e' vuoto
	*/
	@Test
	public void testIsEmpty2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		tmp.clear();
		assertEquals(true, tmp.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con un elemento
		@safe.testcases controllare se a seguito della chiamata di remove() il set e la map sono cambiati
	*/
	@Test
	public void testRemove3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		assertEquals(true, tmp.remove(0));
		assertEquals(true, tmp.remove(1));
		assertEquals(false, tmp.remove(0));
		assertEquals(false, tmp.contains(1));
		assertEquals(false, c.containsKey(1));
	}
	/**
		TESTO SE LE MODIFICHE ALLA MAPPA SI RIPERCUOTANO ANCHE SUL SET
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di remove() sulla map le modifiche si straferiscono anche sul set
	*/
	@Test
	public void testRemove4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		c.remove(0,"pippo");
		assertEquals(false, tmp.contains(0));
	}
	/**
		Test removeAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll(), il set e la map sono cambiati
	*/
	@Test
	public void testRemoveAll() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		Collection<Integer> t = new CollectionAdapter<Integer>();
		t.add(0);
		t.add(1);
		
		assertEquals(true, tmp.removeAll(t));
		assertEquals(false, tmp.contains(0));
		assertEquals(false, tmp.contains(1));
		assertEquals(false, c.containsKey(0));
		assertEquals(false, c.containsKey(1));
	}
	/**
		Test retainAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), il set  e la map sono cambiati
	*/
	@Test
	public void testRetainAll() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		Collection<Integer> t = new CollectionAdapter<Integer>();
		t.add(0);
		t.add(1);
		
		assertEquals(true, tmp.retainAll(t));
		assertEquals(false, tmp.contains(2));
		assertEquals(false, c.containsKey(2));
	}
	/**
		Test size()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se size() restituisce il numero di elementi del set
	*/
	@Test
	public void testSize2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		assertEquals(3, tmp.size());
	}
	/**
		Test toArray()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi del set
	*/
	@Test
	public void testToArray() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		Object[] o = tmp.toArray();
		
		assertEquals(3, o.length);
		assertEquals(true, tmp.contains(o[0]));
		assertEquals(true, tmp.contains(o[1]));
		assertEquals(true, tmp.contains(o[2]));
	}
	
	//Iterator di MapAdapterKeySet
	/**
		Test hasNext()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento o che siamo arrivati all'ultimo elemento del set
	*/
	@Test
	public void testHasNext3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		Iterator<Integer> iter = tmp.iterator();
		
		assertEquals(true, iter.hasNext());
		iter.next();
		iter.next();
		iter.next();
		assertEquals(false, iter.hasNext());
	}
	/**
		Test next()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se next() restituisce l'elemento del set
	*/
	@Test
	public void testNext2() {
		c.put(0, "pippo");
		
		Set<Integer> tmp = c.keySet();
		Iterator<Integer> iter = tmp.iterator();
		
		assertEquals(0, (int)iter.next());
	}
	/**
		Test next()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se chiamando next alla fine del set viene lanciato NoSuchElementException
	*/
	@Test
	public void testNextNoSuchElementException2() {
		c.put(0, "pippo");
		
		Set<Integer> tmp = c.keySet();
		Iterator<Integer> iter = tmp.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}
	/**
		Test remove()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition una map e un set vuoti
		@safe.testcases controllare se il set e la map, dopo aver chiamato remove(), non contengono piu' la chiave rimossa
	*/
	@Test
	public void testRemoveIterator2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		
		Iterator<Integer> iter = tmp.iterator();
		iter.next();
		iter.next();
		iter.remove();
		
		assertEquals(false, tmp.contains(1));
		assertEquals(false, c.containsKey(1));
	}
	/**
		Test remove()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveIllegalStateException() {
		c.put(0, "pippo");
		
		Set<Integer> tmp = c.keySet();
		Iterator<Integer> iter = tmp.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamiamo remove() subito dopo averlo richiamato
	*/
	@Test
	public void testRemoveIllegalStateException2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Integer> tmp = c.keySet();
		Iterator<Integer> iter = tmp.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	
	/**
		TEST DELLA CLASSE MAPADAPTERENTRYSET
		
		LE ECCEZZIONI SONO GIA' STATE TESTATE PRECEDENTEMENTE
		
		@safe.summary ancora da fare
		@safe.suitedesign ancora da fare
		@author Binotto Stefano
	*/
	
	/**
		TESTO CLEAR PER VEDERE SE MODIFICHE ALLA MAPPA SI RIPERCUOTANO ANCHE SULLA COLLECTION
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition un set e una map vuoti
		@safe.testcases controllare se le modifiche su map mosificano anche il set
	*/
	@Test
	public void testClear4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		c.clear();
		
		assertEquals(true, tmp.isEmpty());
	}
	/**
		Test clear()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition un set e una map vuoti
		@safe.testcases controllare se un set e una map con degli elementi si svuotano chiamando clear()
	*/
	@Test
	public void testClear7() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		tmp.clear();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test contains()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se contains() trova l'elemento corretto nel set e non trova quello casuale
	*/
	@Test
	public void testContains2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e2 = c.mapentry(4,"paperino");
		Map.Entry<Integer,String> e3 = c.mapentry(2,"pape");
		
		assertEquals(true, tmp.contains(e1));
		assertEquals(false, tmp.contains(e2));
		assertEquals(false, tmp.contains(e3));
	}
	/**
		Test contains()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezione NullPointerException quando si cerca dentro il set un elemento null
	*/
	@Test
	public void testContainsNullPointerException() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		assertThrows(NullPointerException.class, () -> {
			tmp.contains(null);
		});
	}
	/**
		Test containsAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se containsAll() trova o meno tutti gli elementi della collection specificata dentro il set
	*/
	@Test
	public void testContainsAll2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Collection<Map.Entry<Integer,String>> t = new CollectionAdapter<Map.Entry<Integer,String>>();
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e2 = c.mapentry(1,"pluto");
		Map.Entry<Integer,String> e3 = c.mapentry(2,"paperino");
		t.add(e1);
		t.add(e2);
		t.add(e3);
		
		assertEquals(true, tmp.containsAll(t));
		
		Collection<Map.Entry<Integer,String>> t2 = new CollectionAdapter<Map.Entry<Integer,String>>();
		Map.Entry<Integer,String> e4 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e5 = c.mapentry(4,"pluto");
		Map.Entry<Integer,String> e6 = c.mapentry(2,"pepe");
		t2.add(e4);
		t2.add(e5);
		t2.add(e6);
		
		assertEquals(false, tmp.containsAll(t2));
	}
	/**
		Test containsAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezzione NullPointerException() quando si passa una collection null
	*/
	@Test
	public void testContainsAllNullPointerException() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Collection<Map.Entry<Integer,String>> t = new CollectionAdapter<Map.Entry<Integer,String>>();
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		t.add(e1);
		
		assertThrows(NullPointerException.class, () -> {
			tmp.containsAll(null);
		});
	}
	/**
		Test equals()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se l'oggetto passato e' uguale al set
	*/
	@Test
	public void testEquals7() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		MapAdapter<Integer,String> c2 = new MapAdapter<Integer, String>();
		c2.put(0, "pippo");
		c2.put(1, "pluto");
		c2.put(2, "paperino");
		Set<Map.Entry<Integer,String>> tmp2 = c2.entrySet();
		
		assertEquals(true, tmp.equals(tmp2));
		
		MapAdapter<Integer,String> c3 = new MapAdapter<Integer, String>();
		c3.put(0, "pippo");
		c3.put(1, "pepe");
		c3.put(4, "paperino");
		Set<Map.Entry<Integer,String>> tmp3 = c3.entrySet();
		
		assertEquals(false, tmp.equals(tmp3));
	}
	/**
		Test hashCode()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition una map e un set con un elemento
		@safe.testcases controllare se hashcode() genera l'hashcode corretto
	*/
	@Test
	public void testHashCode4() {
		c.put(0, "pippo");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		SetAdapter<Map.Entry<Integer,String>> t = new SetAdapter<Map.Entry<Integer,String>>();
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		t.add(e1);
		int hashcode = 0^106673622;
		assertEquals(hashcode, tmp.hashCode());
		tmp.clear();
		assertEquals(0, tmp.hashCode());
	}
	/**
		Test isEmpty()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set vuoti
		@safe.testcases controllare se isEmpty() conferma che il set e' vuota
	*/
	@Test
	public void testIsEmpty3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		tmp.clear();
		
		assertEquals(0, tmp.size());
	}
	/**
		Test remove()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di remove() il set e la map sono cambiati
	*/
	@Test
	public void testRemove5() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e2 = c.mapentry(5,"pluto");
		Map.Entry<Integer,String> e3 = c.mapentry(2,"pepe");
		
		assertEquals(true, tmp.remove(e1));
		assertEquals(false, tmp.remove(e1));
		assertEquals(false, tmp.remove(e2));
		assertEquals(false, tmp.remove(e3));
		assertEquals(false, c.containsKey(0));
	}
	/**
		Test remove()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di remove() con un oggetto null, viene lanciato NullPointerException
	*/
	@Test
	public void testRemoveNullPointerEXception3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e2 = c.mapentry(5,"pluto");
		Map.Entry<Integer,String> e3 = c.mapentry(2,"pepe");
		
		assertThrows(NullPointerException.class, () -> {
			tmp.remove(null);
		});
	}
	/**
		TESTO SE LE MODIFICHE AL SET SI RIPERCUOTANO ANCHE SULLA MAPPA
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con due elementi
		@safe.testcases controllare se modifiche alla map si ripercuotono anche al set
	*/
	@Test
	public void testRemove6() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Map.Entry<Integer,String> e1 = c.mapentry(1,"pluto");
		
		c.remove(1,"pluto");
		
		assertEquals(false, tmp.contains(e1));
	}
	/**
		Test removeAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set vuoti
		@safe.testcases controllare se a seguito della chiamata di removeAll(), il set e' cambiato
	*/
	@Test
	public void testRemoveAll2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Collection<Map.Entry<Integer,String>> t = new CollectionAdapter<Map.Entry<Integer,String>>();
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e2 = c.mapentry(1,"pluto");
		Map.Entry<Integer,String> e3 = c.mapentry(2,"paperino");
		t.add(e1);
		t.add(e2);
		t.add(e3);
		
		assertEquals(true, tmp.removeAll(t));
		assertEquals(true, tmp.isEmpty());
		assertEquals(true, c.isEmpty());
	}
	/**
		Test removeAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection null viene lanciato NullPointerException
	*/
	@Test
	public void testRemoveAllNullPointerException() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		assertThrows(NullPointerException.class, () -> {
			tmp.removeAll(null);
		});
	}
	/**
		Test retainAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), il set e la map sono cambiate
	*/
	@Test
	public void testRetainAll2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		Collection<Map.Entry<Integer,String>> t = new CollectionAdapter<Map.Entry<Integer,String>>();
		Map.Entry<Integer,String> e1 = c.mapentry(0,"pippo");
		Map.Entry<Integer,String> e2 = c.mapentry(1,"pluto");
		Map.Entry<Integer,String> e3 = c.mapentry(2,"paperino");
		t.add(e1);
		t.add(e2);
		
		assertEquals(true, tmp.retainAll(t));
		assertEquals(false, tmp.contains(e3));
		assertEquals(false, c.containsValue("paperino"));
	}
	/**
		Test retainAll()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di retainAll() passando una collection null, viene lanciato NullPointerException
	*/
	@Test
	public void testRetainAllNullPointerEXception() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		assertThrows(NullPointerException.class, () -> {
			tmp.retainAll(null);
		});
	}
	/**
		Test size()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se size() restituisce il numero di elementi del set
	*/
	@Test
	public void testSize3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		
		assertEquals(3, tmp.size());
	}
	/**
		Test toArray()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi del set
	*/
	@Test
	public void testToArray2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Object[] o = tmp.toArray();
		
		assertEquals(3, o.length);
		assertEquals(true, tmp.contains(o[0]));
		assertEquals(true, tmp.contains(o[1]));
		assertEquals(true, tmp.contains(o[2]));
	}
	
	//Iterator di MapAdapterEntrySet
	/**
		Test hasNext()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento del set
	*/
	@Test
	public void testHasNext4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = tmp.iterator();
		
		assertEquals(true, iter.hasNext());
		iter.next();
		iter.next();
		iter.next();
		assertEquals(false, iter.hasNext());
	}
	/**
		Test next()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition una map e un set con un elemento
		@safe.testcases controllare se next() restituisce l'elemento del set
	*/
	@Test
	public void testNext3() {
		c.put(0, "pippo");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = tmp.iterator();
		
		Map.Entry<Integer,String> e = c.mapentry(0,"pippo");
		
		assertEquals(e, iter.next());
	}
	/**
		Test next()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando next alla fine del set viene lanciato NoSuchElementException
	*/
	@Test
	public void testNextNoSuchElementException3() {
		c.put(0, "pippo");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = tmp.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}
	/**
		Test remove()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition una map e un set con due elementi
		@safe.testcases controllare se il set e la map, dopo aver chiamato remove(), risulta cambiate
	*/
	@Test
	public void testRemoveIterator3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = tmp.iterator();
		iter.next();
		iter.next();
		iter.remove();
		
		Map.Entry<Integer,String> e = c.mapentry(1,"pluto");
		
		assertEquals(false, tmp.contains(e));
		assertEquals(false, c.containsKey(e.getKey()));
	}
	/**
		Test remove()
		@safe.precondition una map e un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveIteratorIllegalStateException3() {
		c.put(0, "pippo");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = tmp.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition una map e un set con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamiamo remove() subito dopo averlo richiamato
	*/
	@Test
	public void testRemoveIteratorIllegalStateException4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Set<Map.Entry<Integer,String>> tmp = c.entrySet();
		Iterator<Map.Entry<Integer,String>> iter = tmp.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}

	/**
		TEST DELLA CLASSE MAPADAPTERVALUES
		
		LE ECCEZZIONI SONO GIA' STATE TESTATE PRECEDENTEMENTE
		
		@safe.summary ancora da fare
		@safe.suitedesign ancora da fare
		@author Binotto Stefano
	*/
	
	/**
		Test clear()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map vuoti
		@safe.testcases controllare se dopo la chiamata di clear() la collection e la map risultano vuoti
	*/
	@Test
	public void testClear5() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		tmp.clear();
		
		assertEquals(true, tmp.isEmpty());
		assertEquals(true, c.isEmpty());
	}
	/**
		TESTO CLEAR PER VEDERE SE LE MODIFICHE ALLA MAPPA SI RIPERCUOTO ANCHE SULLA COLLECTION
		
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map vuoti
		@safe.testcases controllare se una chiamata di clear() su map si ripercuote anche sulla collection
	*/
	@Test
	public void testClear6() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		c.clear();
		
		assertEquals(true, tmp.isEmpty());
	}
	/**
		Test contains()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se contains() trova l'elemento corretto nella collection e non trova uno non inserito
	*/
	@Test
	public void testContains3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		assertEquals(true, tmp.contains("pippo"));
		assertEquals(false, tmp.contains("pepe"));
	}
	/**
		Test containsAll()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con tre elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata
	*/
	@Test
	public void testContainsAll3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		
		assertEquals(true, tmp.containsAll(t));
		
		t.add("pluto");
		t.add("paperino");
		t.add("pepe");
		assertEquals(false, tmp.containsAll(t));
	}
	/**
		Test equals()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con tre elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata
	*/
	@Test
	public void testEquals8() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		t.add("paperino");
		
		assertEquals(true, tmp.equals(t));
		t.add("pepo");
		assertEquals(false, tmp.equals(t));
	}
	/**
		Test hashCode()
		@safe.precondition una collection e una map con un elemento
		@safe.postcondition una collection e una map con un elemento
		@safe.testcases controllare se hashCode() trova l'hash code corretto della collection
	*/
	@Test
	public void testHashCode5() {
		c.put(0, "pippo");
		
		Collection<String> tmp = c.values();
		int hashcode = 106673622;
		
		assertEquals(hashcode, tmp.hashCode());
	}
	/**
		Test isEmpty()
		@safe.precondition una collection e una map con un elemento
		@safe.postcondition una collection e una map vuoti
		@safe.testcases controllare se isEmpty() conferma che la collection e' vuota
	*/
	@Test
	public void testIsEmpty4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		assertEquals(false, tmp.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con due elementi
		@safe.testcases controllare se la collection e la map, dopo aver chiamato remove(), risultano cambiati
	*/
	@Test
	public void testRemove7() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		assertEquals(true, tmp.remove("pippo"));
		assertEquals(false, tmp.remove("pepe"));
		assertEquals(false, c.containsValue("pippo"));
		assertEquals(false, tmp.remove("pippo"));
	}
	/**
		Test remove()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveNullPointerException2() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		assertThrows(NullPointerException.class, () -> {
			tmp.remove(null);
		});
	}
	/**
		TESTO SE LE MODIFICHE ALLA COLLECTION SI RIPERCUOTANO ANCHE SULLA MAPPA
		
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con due elementi
		@safe.testcases controllare se modifiche alla map si ripercuotono anche sulla collection
	*/
	@Test
	public void testRemove8() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		c.remove(0,"pippo");
		
		assertEquals(false, tmp.contains("pippo"));
	}
	/**
		Test removeAll()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con due elementi
		@safe.testcases controllare se removeAll() elimina tutti gli elementi della collection e della map specificati nella collection passata
	*/
	@Test
	public void testRemoveAll3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		
		assertEquals(true, tmp.removeAll(t));
		assertEquals(false, tmp.contains("pippo"));
		assertEquals(false, c.containsValue("pippo"));
	}
	/**
		Test retainAll()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), la collection e la map sono cambiati
	*/
	@Test
	public void testRetainAll3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		assertEquals(true, tmp.retainAll(t));
		assertEquals(false, tmp.contains("paperino"));
		assertEquals(false, c.containsValue("paperino"));
	}
	/**
		Test size()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con tre elementi
		@safe.testcases controllare se size() restituisca il numero di elementi della collection
	*/
	@Test
	public void testSize4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		
		assertEquals(3, tmp.size());
	}
	/**
		Test size()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi della collection
	*/
	@Test
	public void testToArray3() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		Object[] o = tmp.toArray();
		
		assertEquals(3, o.length);
		assertEquals(true, tmp.contains(o[0]));
		assertEquals(true, tmp.contains(o[1]));
		assertEquals(true, tmp.contains(o[2]));		
	}
	
	//Iterator di MapAdapterValues<E>
	/**
		Test hasNext()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento o che siamo arrivati all'ultimo elemento della collection
	*/
	@Test
	public void testHasNext5() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		
		assertEquals(true, iter.hasNext());
		iter.next();
		iter.next();
		iter.next();
		assertEquals(false, iter.hasNext());
	}
	/**
		Test next()
		@safe.precondition una collection e una map con un elemento
		@safe.postcondition una collection e una map con un elemento
		@safe.testcases controllare se next() restituisce l'elemento della collection
	*/
	@Test
	public void testNext4() {
		c.put(0, "pippo");
		
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		
		assertEquals("pippo", iter.next());
	}
	/**
		Test next()
		@safe.precondition una collection e una map con un elemento
		@safe.postcondition una collection e una map con un elemento
		@safe.testcases controllare se chiamando next alla fine della collection viene lanciato NoSuchElementException
	*/
	@Test
	public void testNextNoSuchElementException4() {
		c.put(0, "pippo");
		
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}
	/**
		Test remove()
		@safe.precondition una collection e una map con tre elementi
		@safe.postcondition una collection e una map con due elementi
		@safe.testcases controllare se la collection e la map, dopo aver chiamato remove(), risultano cambiati
	*/
	@Test
	public void testRemoveIterator4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		iter.next();
		iter.next();
		iter.remove();
		
		assertEquals(false, tmp.contains("pluto"));
		assertEquals(false, c.containsKey("pluto"));
	}
	/**
		Test remove()
		@safe.precondition una collection e una map con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveIllegalStateException3() {
		c.put(0, "pippo");
		
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition una collection e una map con un elemento
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamiamo remove() subito dopo averlo richiamato
	*/
	@Test
	public void testRemoveIllegalStateException4() {
		c.put(0, "pippo");
		c.put(1, "pluto");
		c.put(2, "paperino");
		
		Collection<String> tmp = c.values();
		Iterator<String> iter = tmp.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
}