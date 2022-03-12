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
	TEST DELLA CLASSE COLLECTIONADAPTER
	
	@safe.summary classe per testare la classe CollectionAdapter.
	@safe.suitedesign viene testato il funzionamento di tutti i singoli metodi di CollectionAdapter e CollectionAdapterIterator anche nei casi limiti. I test sono stati sviluppati usando tre comandi JUnit: AssertEquals, AssertThrows e AssertNotSame.
	@author Binotto Stefano
	@version 8/6/2020
*/
public class TestCollectionAdapter {
	
	private CollectionAdapter<String> c;
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
		Inizializzare la collection, su cui vengono testati i metodi, prima di ogni test
	*/
	@Before
	public void setUp() {
		c = new CollectionAdapter<String>();
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
		System.out.println("*** "+counter+" test eseguiti su CollectionAdapter ****");
	}
	
	
	/**
		Test add()
		@safe.precondition una collection vuota
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se effettivamente viene aggiunto un elemento alla collection
	*/
	@Test
	public void testAdd() {
		String s = "ciao";
		assertEquals(true, c.add(s));
		assertEquals(true, c.contains(s));
	}
	/**
		Test add()
		@safe.precondition una collection vuota
		@safe.postcondition none
		@safe.testcases controllare se aggiungendo un elemento null viene lanciato NullPointerException()
	*/
	@Test
	public void testAddNullPointerException() {
		String s = null;
		assertThrows(NullPointerException.class, () -> {
			c.add(s);
		});
	}
	/**
		Test addAll()
		@safe.precondition una collection vuota
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se effettivamente vengono aggiunti tre elementi alla collection
	*/
	@Test
	public void testAddAll() {
		Collection<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		t.add("paperino");
		assertEquals(true, c.addAll(t));
		assertEquals(true, c.containsAll(t));
	}
	/**
		Test addAll()
		@safe.precondition una collection vuota
		@safe.postcondition una collection vuota
		@safe.testcases controllare se la collection e' rimasta invaraiata
	*/
	@Test
	public void testAddAll2() {
		Collection<String> t = new CollectionAdapter<String>();
		
		assertEquals(false, c.addAll(t));
		assertEquals(true, c.isEmpty());
	}
	/**
		Test addAll()
		@safe.precondition una collection vuota
		@safe.testcases controllare se chiamare addAll con un null lancia NullPointerException()
	*/
	@Test
	public void testAddAllNullPointerException() {
		Collection<String> t = null;
		assertThrows(NullPointerException.class, () -> {
			c.addAll(t);
		});
	}
	/**
		Test clear()
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection vuota
		@safe.testcases controllare se dopo la chiamata di clear() la collection risulta vuota
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
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se contains() trova l'elemento aggiunto nella collection e non trova uno non inserito
	*/
	@Test
	public void testContains() {
		c.add("pluto");
		assertEquals(true, c.contains("pluto"));
		assertEquals(false, c.contains("pepe"));
	}
	/**
		Test contains()
		@safe.precondition una collection vuota
		@safe.postcondition none
		@safe.testcases controllare se passando un null dentro contains() lancia NullPointerException()
	*/
	@Test
	public void testContainsNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.contains(null);
		});
	}
	/**
		Test containsAll()
		@safe.precondition una collection con tre elementi 
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata
	*/
	@Test
	public void testContainsAll() {
		Collection<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		t.add("paperino");
		c.addAll(t);
		assertEquals(true, c.containsAll(t));
	}
	/**
		Test containsAll()
		@safe.precondition una collection con due elementi
		@safe.postcondition una collection con due elementi
		@safe.testcases controllare se containsAll() trova elementi della collection specificata che non sono stati inseriti nella nostra collection
	*/
	@Test
	public void testContainsAll2() {
		Collection<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		c.addAll(t);
		Collection<String> t2 = new CollectionAdapter<String>();
		t2.add("pippo");
		t2.add("pluto");
		t2.add("paperino");
		assertEquals(false, c.containsAll(t2));
	}
	/**
		Test containsAll()
		@safe.precondition una collection vuota
		@safe.postcondition una collection vuota
		@safe.testcases controllare se containsAll() restituisce funziona pur passando come parametro una collection vuota
	*/
	@Test
	public void testContainsAll4() {
		Collection<String> t = new CollectionAdapter<String>();
		c.addAll(t);
		assertEquals(true, c.containsAll(t));
	}
	/**
		Test containsAll()
		@safe.precondition una collection vuota 
		@safe.postcondition none
		@safe.testcases controllare se viene lanciato NullPointerException() se si passa come parametro un null
	*/
	@Test
	public void testContainsAllNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.containsAll(null);
		});
	}
	/**
		Test equals()
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata
	*/
	@Test
	public void testEquals() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		c.add("pippo");
		assertEquals(true, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition una collection con due elementi
		@safe.postcondition una collection con due elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata
	*/
	@Test
	public void testEquals2() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		c.add("pippo");
		c.add("pluto");
		assertEquals(false, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition una collection con due elementi
		@safe.postcondition una collection con due elementi
		@safe.testcases controllare se equals() funziona pur passando come parametro una collection vuota
	*/
	@Test
	public void testEquals3() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		
		c.add("pippo");
		c.add("pluto");
		assertEquals(false, c.equals(t));
	}
	/**
		Test hashCode()
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se hashCode() trova l'hash code corretto della collection
	*/
	@Test
	public void testHashCode() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(1331455720, c.hashCode());
	}
	/**
		Test hashCode()
		@safe.precondition una collection vuota
		@safe.postcondition una collection vuota
		@safe.testcases controllare se hashCode() restituisce 0 se la collection e' vuota
	*/
	@Test
	public void testHashCode2() {		
		assertEquals(0, c.hashCode());
	}
	/**
		Test isEmpty()
		@safe.precondition una collection vuota
		@safe.postcondition una collection vuota
		@safe.testcases controllare se isEmpty() conferma che la collection e' vuota
	*/
	@Test
	public void testIsEmpty() {
		c.clear();
		assertEquals(true, c.isEmpty());
	}
	
	/**
		Test remove()
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se la collection, dopo aver chiamato remove(), risultano cambiato
	*/
	@Test
	public void testRemove() {
		c.add(0, "pippo");
		c.add(1, "pluto");
		c.add(2, "paperino");
		
		assertEquals(true, c.remove("pippo"));
		assertEquals(true, c.remove("pluto"));
		assertEquals(true, c.remove("paperino"));
		assertEquals(false, c.remove("pippo"));
		assertEquals(true, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una collection con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveNullPointerException2() {
		c.add(0, "pippo");
		c.add(1, "pluto");
		c.add(2, "paperino");
		
		assertThrows(NullPointerException.class, () -> {
			c.remove(null);
		});
	}	
	/**
		Test removeAll()
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection vuota
		@safe.testcases controllare se removeAll() elimina tutti gli elementi specificati nella collection passata
	*/
	@Test
	public void testRemoveAll() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		c.add("pluto");
		
		assertEquals(true, c.removeAll(t));
	}
	/**
		Test removeAll()
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection con elementi non contenuti nella nostra collection, quest'ultima non e' cambiato
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
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection vuota, la nostra collection non e' cambiata
	*/
	@Test
	public void testRemoveAll3() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		
		c.add("paperino");
		
		assertEquals(false, c.removeAll(t));
	}
	/**
		Test removeAll()
		@safe.precondition una collection vuota
		@safe.postcondition none
		@safe.testcases controllare se passand null removeAll)() lancia NullPointerException()
	*/
	@Test
	public void testRemoveAllNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.removeAll(null);
		});
	}
	/**
		Test retainAll()
		@safe.precondition una collection con due elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se a seguito della chiamata di retainAll(), la collection e' cambiata
	*/
	@Test
	public void testRetainAll() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		c.add("pluto");
		c.add("pippo");
		
		assertEquals(true, c.retainAll(t));
	}
	/**
		Test retainAll()
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se a seguito della chiamata di retainAll(), la collection non e' cambiata
	*/
	@Test
	public void testRetainAll2() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		c.add("pippo");	
		
		assertEquals(false, c.retainAll(t));
	}
	/**
		Test retainAll()
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se a seguito della chiamata di retainAll() con collection vuota, la nostra collection e' vuota
	*/
	@Test
	public void testRetainAll3() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		
		c.add("pippo");	
		c.retainAll(t);
		assertEquals(true, c.isEmpty());
	}
	/**
		Test retainAll()
		@safe.precondition una collection vuota
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
		@safe.precondition una collection con due elementi
		@safe.postcondition una collection con due elementi
		@safe.testcases controllare se size() restituisca il numero di elementi della collection
	*/
	@Test
	public void testSize() {
		c.add("pippo");
		c.add("pippo");	
		
		assertEquals(2, c.size());
	}
	/**
		Test size()
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi della collection
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
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento della collection
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
		@safe.precondition una collection con tre elementi
		@safe.postcondition una collection con tre elementi
		@safe.testcases controllare se hasNext() conferma che siamo all'ultimo elemento della collection
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
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se next() restituisce l'elemento della collection
	*/
	@Test
	public void testNext() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		
		assertEquals("pippo", iter.next());
	}
	/**
		Test next()
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection con un elemento
		@safe.testcases controllare se chiamando next alla fine della collection viene lanciato NoSuchElementException
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
		@safe.precondition una collection con un elemento
		@safe.postcondition una collection vuoto
		@safe.testcases controllare se la collection, dopo aver chiamato remove(), risulta vuota
	*/
	@Test
	public void testRemove2() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		iter.next();
		iter.remove();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una collection con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando remove() senza aver prima chiamato next() fa lanciare IllegalStateException()
	*/
	@Test
	public void testRemoveIllegalStateException() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition una collection con due elemento
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