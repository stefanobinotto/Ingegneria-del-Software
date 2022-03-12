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
	TEST DELLA CLASSE SETADAPTER
	
	@safe.summary classe per testare la classe SetAdapter.
	@safe.suitedesign viene testato il funzionamento di tutti i singoli metodi di SetAdapter e SetAdapterIterator anche nei casi limiti. I test sono stati sviluppati usando tre comandi JUnit: AssertEquals, AssertThrows e AssertNotSame.
	@author Binotto Stefano
	@version 8/6/2020
*/

public class TestSetAdapter {
	
	private SetAdapter<String> c;
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
		c = new SetAdapter<String>();
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
		System.out.println("*** "+counter+" test eseguiti su SetAdapter ****");
	}
	
	
	/**
		Test add()
		@safe.precondition un set con un elemento
		@safe.postcondition un elemento dentro il set
		@safe.testcases controllare se effettivamente viene aggiunto un elemento al set
	*/
	@Test
	public void testAdd() {
		String s = "ciao";
		assertEquals(true, c.add(s));
	}
	/**
		Test add()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se effettivamente non vengono aggiunti al set elementi già presenti
	*/
	@Test
	public void testAdd2() {
		String s = "ciao";
		c.add(s);
		assertEquals(false, c.add(s));
	}
	/**
		Test add()
		@safe.precondition un set vuoto
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezzione NullPointerException quando si cerca di aggiungere un elemento null
	*/
	@Test
	public void testAddNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.add(null);
		});
	}
	/**
		Test addAll()
		@safe.precondition un set vuoto
		@safe.postcondition quattro elementi dentro il set
		@safe.testcases controllare se vengono aggiunti al set tutti gli elementi dentro la collection passata
	*/
	@Test
	public void testAddAll() {
		CollectionAdapter<String> tmp = new CollectionAdapter<String>();
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		assertEquals(true, c.addAll(tmp));
	}
	/**
		Test addAll()
		@safe.precondition un set vuoto
		@safe.postcondition un set vuoto
		@safe.testcases controllare se il set non cambia quando viene passata una collection vuota
	*/
	@Test
	public void testAddAll2() {
		CollectionAdapter<String> tmp = new CollectionAdapter<String>();
		
		assertEquals(false, c.addAll(tmp));
	}
	/**
		Test addAll()
		@safe.precondition un set vuoto
		@safe.testcases controllare se viene lanciata l'eccezione NullPointerException quando si cerca di aggiungere una collection null
	*/
	@Test
	public void testAddAllNullPointerException() {
		CollectionAdapter<String> tmp = null;
		
		assertThrows(NullPointerException.class, () -> {
			c.addAll(tmp);
		});
	}
	/**
		Test clear()
		@safe.precondition un set con tre elementi
		@safe.postcondition un set vuoto
		@safe.testcases controllare se un set con degli elementi si svuota chiamando clear()
	*/
	@Test
	public void testClear() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.clear();
		assertEquals(true, c.isEmpty());
	}
	/**
		Test contains()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se contains() trova l'elemento corretto nel set e non trova quello casuale
	*/
	@Test
	public void testContains() {
		String s = "pippo";
		c.add(s);
		assertEquals(true, c.contains(s));	
		assertEquals(false, c.contains("dddd"));			
	}
	/**
		Test contains()
		@safe.precondition un set vuoto
		@safe.testcases controllare se viene lanciata l'eccezione NullPointerException quando si cerca dentro il set un elemento null
	*/
	@Test
	public void testContainsNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.contains(null);
		});
	}
	/**
		Test containsAll()
		@safe.precondition un set con quattro elementi
		@safe.postcondition un set con quattro elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata dentro il set
	*/
	@Test
	public void testContainsAll() {
		SetAdapter<String> tmp = new SetAdapter<String>();
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("paperone");
		
		assertEquals(true, c.containsAll(tmp));	
	}
	/**
		Test containsAll()
		@safe.precondition un set con quattro elementi
		@safe.postcondition un set con quattro elementi
		@safe.testcases controllare se containsAll() restituisce true anche passando una collection vuota
	*/
	@Test
	public void testContainsAll2() {
		SetAdapter<String> tmp = new SetAdapter<String>();
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("paperone");
		
		assertEquals(true, c.containsAll(tmp));	
	}
	/**
		Test containsAll()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se containsAll() restituisce false passando una collection con elementi diversi da quelli di set
	*/
	@Test
	public void testContainsAll3() {
		SetAdapter<String> tmp = new SetAdapter<String>();
		
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		c.add("pippo");
		
		assertEquals(false, c.containsAll(tmp));	
	}
	/**
		Test containsAll()
		@safe.precondition un set vuoto
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezzione NullPointerException() quando si passa una collection null
	*/
	@Test
	public void testContainsAllNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.containsAll(null);
		});
	}
	/**
		Test equals()
		@safe.precondition un set con due elementi
		@safe.postcondition un set con due elemento
		@safe.testcases controllare se l'oggetto passato e' uguale al set
	*/
	@Test
	public void testEquals() {
		SetAdapter<String> t = new SetAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("pippo");
		c.add("pluto");
		
		assertEquals(true, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se l'oggetto passato e' diverso dal set, poiche' la classe dell'oggetto non corrisponde a quela dell set
	*/
	@Test
	public void testEquals2() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		
		c.add("pippo");
		
		assertEquals(false, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se l'oggetto passato e' diverso dal set, poiche' non hanno la stessa dimensione
	*/
	@Test
	public void testEquals3() {
		SetAdapter<String> t = new SetAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("pippo");
		
		assertEquals(false, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se l'oggetto passato e' diverso dal set, poiche' contiene un diverso elemento
	*/
	@Test
	public void testEquals4() {
		SetAdapter<String> t = new SetAdapter<String>();
		t.add("pluto");
		
		c.add("pippo");
		
		assertEquals(false, c.equals(t));
	}
	/**
		Test hashCode()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se hashcode() genera l'hashcode corretto
	*/
	@Test
	public void testHashCode() {
		c.add("pippo");
		
		assertEquals(106673622, c.hashCode());
	}
	/**
		Test hashCode()
		@safe.precondition un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se hashcode() restituisce 0 se il set e' vuoto
	*/
	@Test
	public void testHashCode4() {
		c.add("");
		assertEquals(0, c.hashCode());
	}
	/**
		Test isEmpty()
		@safe.precondition un set vuoto
		@safe.postcondition un set vuoto
		@safe.testcases controllare se isEmpty() conferma che il set  e' vuoto
	*/
	@Test
	public void testIsEmpty() {
		c.clear();
		assertEquals(true, c.isEmpty());
	}
	/**
		Test isEmpty()
		@safe.precondition un set con un elemento
		@safe.postcondition un set vuoto
		@safe.testcases controllare se isEmpty() conferma che il set non e' vuoto
	*/
	@Test
	public void testIsEmpty2() {
		c.add("pippo");
		assertEquals(false, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition un set con un elemento
		@safe.postcondition un set vuoto
		@safe.testcases controllare se a seguito della chiamata di remove() il set e' cambiato
	*/
	@Test
	public void testRemove() {
		c.add("pippo");
		assertEquals(true, c.remove("pippo"));
	}
	/**
		Test remove()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se a seguito della chiamata di remove() con un oggetto non contenuto nel set, quest'ultimo non e' cambiato
	*/
	@Test
	public void testRemove2() {
		c.add("pluto");
		assertEquals(false, c.remove("pippo"));
	}
	/**
		Test remove()
		@safe.precondition un set vuoto
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di remove() con un oggetto null, viene lanciato NullPointerException
	*/
	@Test
	public void testRemoveNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.remove(null);
		});
	}
	/**
		Test removeAll()
		@safe.precondition un set con tre elementi
		@safe.postcondition un set vuoto
		@safe.testcases controllare se a seguito della chiamata di removeAll(), il set e' cambiato
	*/
	@Test
	public void testRemoveAll() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(true, c.removeAll(t));
	}
	/**
		Test removeAll()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection con elementi non contenuti in set, quest'ultimo non e' cambiato
	*/
	@Test
	public void testRemoveAll2() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("paperino");
		
		assertEquals(false, c.removeAll(t));
	}
	/**
		Test removeAll()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection vuota, il set non e' cambiato
	*/
	@Test
	public void testRemoveAll3() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		
		c.add("paperino");
		
		assertEquals(false, c.removeAll(t));
	}
	/**
		Test removeAll()
		@safe.precondition un set vuoto
		@safe.postcondition un set vuoto
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection null viene lanciato NullPointerException
	*/
	@Test
	public void testRemoveAllNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.removeAll(null);
		});
	}
	/**
		Test retainAll()
		@safe.precondition un set con tre elementi
		@safe.postcondition un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), il set e' cambiato
	*/
	@Test
	public void testRetainAll() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(true, c.retainAll(t));	
	}
	/**
		Test retainAll()
		@safe.precondition un set con due elementi
		@safe.postcondition un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), il set non e' cambiato
	*/
	@Test
	public void testRetainAll2() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		t.add("paperino");
		
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(false, c.retainAll(t));	
	}
	/**
		Test retainAll()
		@safe.precondition un set con un elemento
		@safe.postcondition un set vuoto
		@safe.testcases controllare se a seguito della chiamata di retainAll() con collection vuota, il set e' vuoto
	*/
	@Test
	public void testRetainAll3() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();

		c.add("paperino");
		c.retainAll(t);
		assertEquals(true, c.isEmpty());	
	}
	/**
		Test retainAll()
		@safe.precondition un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di retainAll() passando una collection null, viene lanciato NullPointerException
	*/
	@Test
	public void testRetainAllNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.retainAll(null);
		});
	}
	/**
		Test size()
		@safe.precondition un set con due elementi
		@safe.postcondition un set con due elementi
		@safe.testcases controllare se size() restituisce il numero di elementi del set
	*/
	@Test
	public void testSize() {
		c.add("pippo");
		c.add("pluto");
		
		assertEquals(2, c.size());
	}
	/**
		Test toArray()
		@safe.precondition un set con tre elementi
		@safe.postcondition un set con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi del set
	*/
	@Test
	public void testToArray() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		Object[] o = c.toArray();
		
		assertEquals(3, o.length);
		assertEquals(true, c.contains(o[0]));
		assertEquals(true, c.contains(o[1]));
		assertEquals(true, c.contains(o[2]));
	}
	//Test Iterator
	/**
		Test hasNext()
		@safe.precondition un set con tre elementi
		@safe.postcondition un set con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento del set
	*/
	@Test
	public void testHasNext() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		Iterator<String> iter = c.iterator();
		
		assertEquals(true, iter.hasNext());		
	}
	/**
		Test hasNext()
		@safe.precondition un set con tre elementi
		@safe.postcondition un set con tre elementi
		@safe.testcases controllare se hasNext() conferma che siamo all'ultimo elemento del set
	*/
	@Test
	public void testHasNext2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		Iterator<String> iter = c.iterator();
		
		iter.next();
		iter.next();
		iter.next();
		
		assertEquals(false, iter.hasNext());		
	}
	/**
		Test next()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se next() restituisce l'elemento del set
	*/
	@Test
	public void testNext() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		
		assertEquals("pippo", iter.next());
	}
	/**
		Test next()
		@safe.precondition un set con un elemento
		@safe.postcondition un set con un elemento
		@safe.testcases controllare se chiamando next alla fine del set viene lanciato NoSuchElementException
	*/
	@Test
	public void testNextNoSuchElementException() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}
	/**
		Test remove()
		@safe.precondition un set con un elemento
		@safe.postcondition un set vuoto
		@safe.testcases controllare se il set, dopo aver chiamato remove(), risulta vuoto
	*/
	@Test
	public void testRemoveIterator() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		iter.next();
		iter.remove();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveIteratorIllegalStateException() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition un set con due elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamiamo remove() subito dopo averlo richiamato
	*/
	@Test
	public void testRemoveIteratorIllegalStateException2() {
		c.add("pippo");
		c.add("pluto");
		
		Iterator<String> iter = c.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}	
}