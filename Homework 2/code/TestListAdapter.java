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
	@safe.summary classe per testare la classe ListAdapter
	@safe.suitedesign viene testato il funzionamento di tutti i singoli metodi di ListAdapter, ListAdapterIteratore e ListAdapterListIterator anche nei casi limiti. Poi viene testata la classe interna SubList con i relativi iteratori. Di questa classe interna viene inoltre testata la capacita' di traslare alla lista le modifiche apportate alla sottolista e viceversa. I test sono stati sviluppati usando tre comandi JUnit: AssertEquals, AssertThrows e AssertNotSame.
	@author Binotto Stefano
	@version 8/6/2020
*/
public class TestListAdapter {
	
	private ListAdapter<String> c;
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
		c = new ListAdapter<String>();
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
		System.out.println("**** " + counter + " test eseguiti su ListAdapter ****");
	}
	
	/**
		Test add(,)
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se effettivamente viene aggiunto un elemento alla list
	*/
	@Test
	public void testAdd() {
		c.add(0, "pippo");
		
		assertEquals(true, c.contains("pippo"));
	}
	/**
		Test add(,)
		@safe.precondition una list vuota
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezzione NullPointerException quando si cerca di aggiungere un elemento null
	*/
	@Test
	public void testAddNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.add(0, null);
		});
	}
	/**
		Test add(,)
		@safe.precondition una list vuoto
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezzione IndexOutOfBoundsException quando si cerca di aggiungere un elemento con indice fuori limite
	*/
	@Test
	public void testAddIndexOutOfBoundsException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.add(-8, "pippo");
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.add(2, "pippo");
		});
	}
	/**
		Test add()
		@safe.precondition una list vuota
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se effettivamente viene aggiunto un elemento alla list
	*/
	@Test
	public void testAdd2() {
		assertEquals(true, c.add("pippo"));
	}
	/**
		Test add()
		@safe.precondition una list vuota
		@safe.postcondition none
		@safe.testcases controllare se viene lanciata l'eccezzione NullPointerException quando si cerca di aggiungere un elemento null
	*/
	@Test
	public void testAddNullPointerException2() {
		assertThrows(NullPointerException.class, () -> {
			c.add(null);
		});
	}
	/**
		Test addAll(,) e addAll()
		@safe.precondition una list vuota
		@safe.postcondition tre elementi dentro la list
		@safe.testcases controllare se vengono aggiunti alla list tutti gli elementi dentro la collection passata
	*/
	@Test
	public void testaddAll() {
		CollectionAdapter<String> tmp = new CollectionAdapter<String>();
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		assertEquals(true, c.addAll(0, tmp));
		assertEquals(true, c.addAll(tmp));
	}
	/**
		Test addAll(,) e addAll()
		@safe.precondition una list vuota
		@safe.postcondition una list vuota
		@safe.testcases controllare se la list non cambia quando viene passata una collection vuota
	*/
	@Test
	public void testaddAll2() {
		CollectionAdapter<String> tmp = new CollectionAdapter<String>();
		
		assertEquals(false, c.addAll(0, tmp));
		assertEquals(false, c.addAll(tmp));
	}
	/**
		Test addAll(,) e addAll()
		@safe.precondition una list vuota
		@safe.testcases controllare se viene lanciata l'eccezione NullPointerException quando si cerca di aggiungere una collection null
	*/
	@Test
	public void testAddAllNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.addAll(0, null);
		});
		assertThrows(NullPointerException.class, () -> {
			c.addAll(null);
		});
	}
	/**
		Test addAll(,)
		@safe.precondition una list vuota
		@safe.testcases controllare se viene lanciata l'eccezione NullPointerException quando si cerca di aggiungere una collection con indice fuori limiti
	*/
	@Test
	public void testAddAllIndexOutOfBoundsException() {
		CollectionAdapter<String> tmp = new CollectionAdapter<String>();
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.addAll(2, tmp);
		});
	}
	/**
		Test clear()
		@safe.precondition una list con un elemento
		@safe.postcondition una list  vuoto
		@safe.testcases controllare se una list con degli elementi si svuota chiamando clear()
	*/
	@Test
	public void testClear() {
		c.add("pippo");
		c.clear();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test contains()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se contains() trova l'elemento aggiunto alla list e non trova quello casuale
	*/
	@Test
	public void testContains() {
		c.add("pippo");
		
		assertEquals(true, c.contains("pippo"));
		assertEquals(false, c.contains("dd"));
	}
	/**
		Test contains()
		@safe.precondition una list vuota
		@safe.testcases controllare se viene lanciata l'eccezzione NullPointerException quando si cerca dentro la list un elemento null
	*/
	@Test
	public void testContainsNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			c.contains(null);
		});
	}
	/**
		Test containsAll()
		@safe.precondition una list con quattro elementi
		@safe.postcondition una list con quattro elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata dentro la list
	*/
	@Test
	public void testContainsAll() {
		ListAdapter<String> tmp = new ListAdapter<String>();
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		
		c.add("paperone");
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(true, c.containsAll(tmp));
	}
	/**
		Test containsAll()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se containsAll() restituisce false passando una collection con elementi diversi da quelli della list
	*/
	@Test
	public void testContainsAll2() {
		ListAdapter<String> tmp = new ListAdapter<String>();
		tmp.add("pippo");
		tmp.add("pluto");
		tmp.add("paperino");
		tmp.add("paperone");
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(false, c.containsAll(tmp));
	}
	/**
		Test containsAll()
		@safe.precondition una list con quattro elementi
		@safe.postcondition una list con quattro elementi
		@safe.testcases controllare se containsAll() restituisce true anche passando una collection vuota
	*/
	@Test
	public void testContainsAll3() {
		ListAdapter<String> tmp = new ListAdapter<String>();
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("paperone");
		
		assertEquals(true, c.containsAll(tmp));	
	}
	/**
		Test containsAll()
		@safe.precondition una list vuota
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
		@safe.precondition una list con due elementi
		@safe.postcondition una list con due elementi
		@safe.testcases controllare se l'oggetto passato e' uguale alla list
	*/
	@Test
	public void testEquals() {
		ListAdapter<String> t = new ListAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("pippo");
		c.add("pluto");
		
		assertEquals(true, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se l'oggetto passato e' diverso dalla list, poiche' la classe dell'oggetto non corrisponde a quella della list
	*/
	@Test
	public void testEquals2() {
		//tipo diverso
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo");
		
		c.add("pippo");
		
		assertEquals(false, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se l'oggetto passato e' diverso dalla list, poiche' non hanno la stessa dimensione
	*/
	@Test
	public void testEquals3() {
		ListAdapter<String> t = new ListAdapter<String>();
		t.add("pippo");
		t.add("pluto");
		
		c.add("pippo");
		
		assertEquals(false, c.equals(t));
	}
	/**
		Test equals()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se l'oggetto passato e' diverso dalla list, poiche' contiene un diverso elemento
	*/
	@Test
	public void testEquals4() {
		ListAdapter<String> t = new ListAdapter<String>();
		t.add("pluto");
		
		c.add("pippo");
		
		assertEquals(false, c.equals(t));
	} 
	/**
		Test get()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se viene restituito l'elemento relativo all'indice specificato
	*/
	@Test
	public void testGet() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		assertEquals("pluto", c.get(1));
	}
	/**
		Test get()
		@safe.precondition una list con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciato IndexOutOfBoundsException() se viene specificato un indice fuori limite
	*/
	@Test
	public void testGetIndexOutOfBoundsException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
	
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.get(6);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.get(-8);
		});
	}
	/**
		Test hashCode()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se hashcode() genera l'hashcode corretto
	*/
	@Test
	public void testHashCode() {

		c.add("pippo");
		int hashcode = 31 * 1 + 106673622; // 106673622 = hashcode di pippo
		
		assertEquals(hashcode, c.hashCode());
	}
	/**
		Test hashCode()
		@safe.precondition un set con un elemento
		@safe.postcondition none
		@safe.testcases controllare se hashcode() restituisce 0 se il set e' vuoto
	*/
	@Test
	public void testHashCode3() {
		c.add("");
		int hashcode = 31 * 1 + 0; // = 31
		assertEquals(hashcode, c.hashCode());
	}
	/**
		Test indexOf()
		@safe.precondition una list con quattro elementi
		@safe.postcondition una list con quattro elementi
		@safe.testcases controllare se indexOf() restituisce il primo indice relativo al valore specificato
	*/
	@Test
	public void testIndexOf() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pluto");
		
		assertEquals(1, c.indexOf("pluto"));
	}
	/**
		Test indexOf()
		@safe.precondition una list con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciato NullPointerException() quando si passa null come parametro
	*/
	@Test
	public void testIndexOfNullPointerException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
	
		assertThrows(NullPointerException.class, () -> {
			c.indexOf(null);
		});
	}
	/**
		Test isEmpty()
		@safe.precondition none
		@safe.postcondition none
		@safe.testcases controllare se isEmpty() conferma che la list e' vuota o riempita
	*/
	@Test
	public void testIsEmpty() { 
		c.clear();
		assertEquals(true, c.isEmpty());
		c.add("pippo");
		assertEquals(false, c.isEmpty());
	}
	/**
		Test lastIndexOf()
		@safe.precondition una list con quattro elementi
		@safe.postcondition una list con quattro elementi
		@safe.testcases controllare se lastIndexOf() restituisce l'ultimo indice relativo al valore specificato
	*/
	@Test
	public void testLastIndexOf() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pluto");
		
		assertEquals(3, c.lastIndexOf("pluto"));
	}
	/**
		Test lastIndexOf()
		@safe.precondition una list con quattro elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciato NullPointerException() quando si passa null come parametro
	*/
	@Test
	public void testLastIndexOfNullPointerException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pluto");
	
		assertThrows(NullPointerException.class, () -> {
			c.lastIndexOf(null);
		});
	}
	/**
		Test listIterator()
		@safe.precondition una list con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciato IndexOutOfBoundsException() quando si passa un indice fuori limite
	*/
	@Test
	public void testListIteratorIndexOutOfBoundsException() {
		c.add("paperino");
		c.add("paperino");
		c.add("pluto");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.listIterator(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.listIterator(3);
		});
	}
	/**
		Test remove()
		@safe.precondition una list con un elemento
		@safe.postcondition una list vuota
		@safe.testcases controllare se a seguito della chiamata di remove() la list e' cambiata
	*/
	@Test
	public void testRemove() {
		c.add("pippo");
		assertEquals("pippo", c.remove(0));
	}	
	/**
		Test listIterator()
		@safe.precondition una list con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se viene lanciato IndexOutOfBoundsException() quando si passa un indice fuori limite
	*/
	@Test
	public void testRemoveIndexOutOfBoundsException() {
		c.add("paperino");
		c.add("paperino");
		c.add("pluto");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.remove(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.remove(3);
		});
	}
	/**
		Test remove()
		@safe.precondition una list con un elemento
		@safe.postcondition una list vuota
		@safe.testcases controllare se a seguito della chiamata di remove() la list e' cambiato
	*/
	@Test
	public void testRemove3() {
		c.add("pluto");
		assertEquals(true, c.remove("pluto"));
		assertEquals(false, c.remove("pippo"));
	}
	/**
		Test remove()
		@safe.precondition una list vuota
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di remove() con un oggetto null, viene lanciato NullPointerException()
	*/
	@Test
	public void testRemoveNullPointerException3() {
		c.add("paperino");
		c.add("paperino");
		c.add("pluto");
		
		assertThrows(NullPointerException.class, () -> {
			c.remove(null);
		});
	}
	/**
		Test removeAll()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll(), la list e' cambiato
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
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection con elementi non contenuti in list, quest'ultimo non e' cambiato
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
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se a seguito della chiamata di removeAll() passando una collection vuota, la list non e' cambiata
	*/
	@Test
	public void testRemoveAll3() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		
		c.add("paperino");
		
		assertEquals(false, c.removeAll(t));
	}
	/**
		Test removeAll()
		@safe.precondition una list vuota
		@safe.postcondition una list vuota
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
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), la list e' cambiata
	*/
	@Test
	public void testRetainAll() {
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("paperino");
		t.add("pluto");
		
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(true, c.retainAll(t));	
	}
	/**
		Test retainAll()
		@safe.precondition una list con due elementi
		@safe.postcondition un set con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), la list non e' cambiata
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
		@safe.precondition una list con un elemento
		@safe.postcondition una list vuota
		@safe.testcases controllare se a seguito della chiamata di retainAll() con collection vuota, la list e' vuota
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
		@safe.precondition una list con un elemento
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
		Test set()
		@safe.precondition una list con tre elementi
		@safe.postcondition una lista con tre elementi
		@safe.testcases controllare se set restituisce l'elemento rimpiazzato e se inserisce l'elemento specificato nella posizione specificata
	*/
	@Test
	public void testSet() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals("pluto", c.set(1,"paperone"));
		assertEquals("paperone", c.get(1));
	}
	/**
		Test set()
		@safe.precondition una list con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di set() passando un elemento null, viene lanciato NullPointerException
	*/
	@Test
	public void testSetNullPointerException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertThrows(NullPointerException.class, () -> {
			c.set(1, null);
		});
	}
	/**
		Test set()
		@safe.precondition una list con tre elementi
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di set() passando un indice fuori limite, viene lanciato IndexOutOfBoundsException
	*/
	@Test
	public void testSetIndexOutOfBoundsException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.set(-1, "paperone");
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.set(3, "paperone");
		});
	}
	/**
		Test size()
		@safe.precondition una list con due elementi
		@safe.postcondition una list con due elementi
		@safe.testcases controllare se size() restituisce il numero di elementi della list
	*/
	@Test
	public void testSize() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		assertEquals(3, c.size());
	}
	/**
		Test subList()
		@safe.precondition una list con sei elementi
		@safe.postcondition una list con sei elementi
		@safe.testcases controllare se subList() restituisce una lista con gli stessi elementi compresi tra gli estremi indicati e dimensione (to - from) 
	*/
	@Test
	public void testSubList() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("nano");
		c.add("elfo");
		c.add("orco");
		
		List<String> t = c.subList(2, 4);//il To e' escluso
		
		assertEquals(true, c.containsAll(t));
		assertEquals(2, t.size());		
	}
	/**
		Test set()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se a seguito della chiamata di subList() passando degli estremi fuori limite, viene lanciato IndexOutOfBoundsException
	*/
	@Test
	public void testSubListIndexOutOfBoundsException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("nano");
		c.add("elfo");
		c.add("orco");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.subList(-1, 3);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.subList(3, 80);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			c.subList(5, 3);
		});
	}
	/**
		Test toArray()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi della list
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
	
	//Iterator di List
	/**
		Test hasNext()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento della list
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
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se hasNext() conferma che siamo all'ultimo elemento della list
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
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se next() restituisce l'elemento della list 
	*/
	@Test
	public void testNext() {
		c.add("pippo");
		
		Iterator<String> iter = c.iterator();
		
		assertEquals("pippo", iter.next());
	}
	/**
		Test next()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se chiamando next() alla fine della list viene lanciato NoSuchElementException
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
		@safe.precondition una list con un elemento
		@safe.postcondition una list vuota
		@safe.testcases controllare se la list, dopo aver chiamato remove(), risulta vuota
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
		@safe.precondition una list con un elemento
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
		@safe.precondition una list con due elementi
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//ListIterator di list
	/**
		Test hasNext()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se hasNext() conferma che non siamo all'ultimo elemento della list
	*/
	@Test
	public void testhasNext3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		
		assertEquals(true, iter.hasNext());	
	}
	/**
		Test hasNext()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se hasNext() conferma che siamo all'ultimo elemento della list
	*/
	@Test
	public void testHasNext4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		
		iter.next();
		iter.next();
		iter.next();
		
		assertEquals(false, iter.hasNext());		
	}
	/**
		Test hasNext()
		@safe.precondition una list con sei elementi
		@safe.postcondition una list con sei elementi
		@safe.testcases controllare se hasNext() conferma che siamo all'ultimo elemento della list
	*/
	@Test
	public void testHasNext5() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator(5);
		iter.next();
		
		assertEquals(false, iter.hasNext());		
	}
	/**
		Test next()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se next() restituisce l'elemento della list
	*/
	@Test
	public void testNext2() {
		c.add("pippo");
		
		ListIterator<String> iter = c.listIterator();
		
		assertEquals("pippo", iter.next());
	}
	/**
		Test next()
		@safe.precondition una list con un elemento
		@safe.postcondition una list con un elemento
		@safe.testcases controllare se next() restituisce l'elemento della list
	*/
	@Test
	public void testNext3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator(3);
		
		assertEquals("pippo2", iter.next());
	}
	/**
		Test next()
		@safe.precondition una list con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando next() alla fine della list viene lanciato NoSuchElementException
	*/
	@Test
	public void testNextNoSuchElementException2() {
		c.add("pippo");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
	}
	/**
		Test hasPrevious()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se chiamando hasPrevious() conferma che siamo all'inizio della lista
	*/
	@Test
	public void testHasPrevious() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		
		assertEquals(false, iter.hasPrevious());	
	}
	/**
		Test hasPrevious()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se chiamando hasPrevious() conferma che non siamo all'inizio della lista
	*/
	@Test
	public void testHasPrevious2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();	
		
		assertEquals(true, iter.hasPrevious());
	}
	/**
		Test previous()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se previous() restituisce il secondo elemento della list
	*/
	@Test
	public void testPrevious() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		
		assertEquals("pluto", iter.previous());
	}
	/**
		Test previous()
		@safe.precondition una list con un elemento
		@safe.postcondition none
		@safe.testcases controllare se chiamando previous() all'inizio della list viene lanciato NoSuchElementException
	*/
	@Test
	public void testPreviousNoSuchElementException() {
		c.add("pippo");
		
		ListIterator<String> iter = c.listIterator();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.previous();
		});
	}
	/**
		Test nextIndex()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se chiamando nextIndex() viene restituito l'indice dell'elemento che sarebbe restituito da next()
	*/
	@Test
	public void testNextIndex() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		
		assertEquals(1, iter.nextIndex());
	}
	/**
		Test previousIndex()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se chiamando previousIndex() viene restituito l'indice dell'elemento che sarebbe restituito da previous()
	*/
	@Test
	public void testPreviousIndex() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		
		assertEquals(1, iter.previousIndex());
	}
	/**
		Test remove()
		@safe.precondition una list con due elementi
		@safe.postcondition la list vuoto
		@safe.testcases controllare se la list, dopo aver chiamato remove(), risulta vuoto
	*/
	@Test
	public void testRemove4() {
		c.add("pippo");
		c.add("pluto");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una list con due elementi
		@safe.postcondition una list vuoto
		@safe.testcases controllare se la list, dopo aver chiamato remove(), risulta vuoto
	*/
	@Test
	public void testRemove5() {
		c.add("pippo");
		c.add("pluto");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		
		iter.previous();
		iter.remove();
		iter.previous();
		iter.remove();
		
		assertEquals(true, c.isEmpty());
	}
	/**
		Test remove()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamo remove() subito dopo averlo richiamato remove()
	*/
	@Test
	public void testRemoveIllegalStateException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	/**
		Test remove()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamo remove() subito dopo aver chiamato add()
	*/
	@Test
	public void testRemoveIllegalStateException2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		iter.next();
		iter.add("casa");
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}/**
		Test remove()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se lancia IllegalStateException() se chiamo remove() senza aver chiamato next() o previous()
	*/
	@Test
	public void testRemoveIllegalStateException3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	
	/**
		Test add()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con quattro elementi
		@safe.testcases controllare se la list, dopo aver chiamato add() risulta modificata
	*/
	@Test
	public void testAdd3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.add("pippo2");
		
		assertEquals(4, c.size());
	}
	/**
		Test add()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con quattro elementi
		@safe.testcases controllare se la list, dopo aver chiamato add(), fa slittare tutti gli elementi alla destra dell'elemento aggiunto
	*/
	@Test
	public void testAdd7() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.add("pippo2");
		
		assertEquals("pippo2", c.get(0));
		assertEquals("pippo", c.get(1));
	}
	/**
		Test set()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se la list, dopo aver chiamato next(), rimpiazza l'elemento all'indice specificato
	*/
	@Test
	public void testSet2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.set("pippo2");
		
		assertEquals(3, c.size());
		assertEquals(0, c.indexOf("pippo2"));
	}
	/**
		Test set()
		@safe.precondition una list con tre elementi
		@safe.postcondition una list con tre elementi
		@safe.testcases controllare se la list, dopo aver chiamato previous(), rimpiazza l'elemento all'indice specificato
	*/
	@Test
	public void testSet3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		iter.previous();
		iter.set("pippo2");
		
		assertEquals(3, c.size());
		assertEquals(1, c.indexOf("pippo2"));
	}
	/**
		Test set()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se chiamando set() subito dopo aver chiamato remove() lancia IllegalStateException()
	*/
	@Test
	public void testSetIllegalStateException() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.set("pippo2");
		});
	}
	/**
		Test set()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se chiamando set() subito dopo aver chiamato add() lancia IllegalStateException()
	*/
	@Test
	public void testSetIllegalStateException2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator();
		iter.next();
		iter.next();
		iter.next();
		iter.add("casa");
		
		assertThrows(IllegalStateException.class, () -> {
			iter.set("pippo2");
		});
	}
	/**
		Test set()
		@safe.precondition una list con sei elementi
		@safe.postcondition none
		@safe.testcases controllare se chiamando set() senza aver prima chiamato next() o previous() lancia IllegalStateException()
	*/
	@Test
	public void testSetIllegalStateException3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListIterator<String> iter = c.listIterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.set("pippo2");
		});
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
		TEST DELLA CLASSE INTERNA SUBLIST
		****	LE ECCEZZIONI SONO GIA' STATE TESTATE PRECEDENTEMENTE DEI TEST DI LISTADAPTER
		@safe.summary ancora da fare
		@safe.suitedesign ancora da fare
		@author Binotto Stefano
	*/
	
	/**
		Test add()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sette elementi e una sublist con quattro elementi
		@safe.testcases controllare se effettivamente viene aggiunto un elemento alla sublist e alla lista
	*/
	@Test
	public void testAdd4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.add("casa"));
		assertEquals(5, c.indexOf("casa"));
	}
	/**
		Test add()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sette elementi e una sublist con quattro elementi
		@safe.testcases controllare se effettivamente viene aggiunto un elemento alla sublist e alla lista
	*/
	@Test
	public void testAdd6() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		tmp.add(2,"casa");
		
		assertEquals(4, c.indexOf("casa"));
	}
	/**
		Test addAll(,) e addAll()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con dodici elementi e una sublist nove  elementi
		@safe.testcases controllare se vengono aggiunti alla sublist e alla list tutti gli elementi dentro la collection
	*/
	@Test
	public void testAddAll3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("casa");
		t.add("casa2");
		t.add("casa3");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.addAll(0, t));
		assertEquals(2, c.indexOf("casa"));
		assertEquals(true, tmp.addAll(t));
		assertEquals(8, c.lastIndexOf("casa"));
	}
	/**
		Test clear()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list e una sublist vuoti
		@safe.testcases controllare se, chiamando clear(), la sublist si svuota mentre la list elimina gli elementi contenuti precedentemente dalla sublist
	*/
	@Test
	public void testClear2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		tmp.clear();
		
		assertEquals(true, tmp.isEmpty());
		assertEquals("paperino2", c.get(2));
	}
	/**
		TESTO CLEAR PER VEDERE SE MODIFICHE ALLA LISTA MADRE SI RIPERCUOTANO ANCHE SULLA SUBLIST
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list e una sublist vuoti
		@safe.testcases controllare se, chiamando clear() sulla list, anche la sublist si svuota
	*/
	@Test
	public void testClear4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		c.clear();
		
		assertEquals(true, tmp.isEmpty());
	}
	/**
		TESTO SE MODIFICHE ALLA LISTA MADRE SI RIPERCUOTANO ANCHE SULLA SUBLIST
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con cinque elementi e una sublist con due elementi
		@safe.testcases controllare se, chiamando remove() sulla list, anche dalla sublist si rimuove l'elemento specificato
	*/
	@Test
	public void testRemove10() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		c.remove("paperino");
		
		assertEquals(false, tmp.contains("paperino"));
	}
	/**
		Test contains()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se contains() trova l'elemento aggiunto alla sublist e non trova quello casuale
	*/
	@Test
	public void testContains3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.contains("pippo2"));
		assertEquals(false, tmp.contains("casuale"));
	}
	/**
		Test containsAll()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se containsAll() trova tutti gli elementi della collection specificata dentro la sublist
	*/
	@Test
	public void testContainsAll4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListAdapter<String> t = new ListAdapter<String>();
		
		t.add("paperino");
		t.add("pippo2");
		t.add("pluto2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.containsAll(t));
	}
	/**
		Test containsAll()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se containsAll() restituisce false passando una collection con elementi diversi da quelli della sublist
	*/
	@Test
	public void testContainsAll5() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListAdapter<String> t = new ListAdapter<String>();
		
		t.add("pippo");
		t.add("pippo2");
		t.add("pluto2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(false, tmp.containsAll(t));
	}
	/**
		Test containsAll()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se containsAll() restituisce true passando anche una collection vuota
	*/
	@Test
	public void testContainsAll6() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		ListAdapter<String> t = new ListAdapter<String>();
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.containsAll(t));
	}
	/**
		Test equals()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se l'oggetto passato e' uguale alla sublist
	*/
	@Test
	public void testEquals5() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		List<String> t = new ListAdapter<String>();
		t.add("paperino");
		t.add("pippo2");
		t.add("pluto2");
		
		assertEquals(true, tmp.equals(t));
	}
	/**
		Test equals()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se l'oggetto passato e' diverso dalla sublist, poiche' la dimensione dell'oggetto e' diversa da quella della sublist
	*/
	@Test
	public void testEquals6() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		ListAdapter<String> t = new ListAdapter<String>();
		t.add("paperino2");
		t.add("pippo2");
		
		assertEquals(false, tmp.equals(t));
	}
	/**
		Test get()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se viene restituito l'elemento relativo all'indice specificato dentro sublist
	*/
	@Test
	public void testGet2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals("paperino", tmp.get(0));
	}
	/**
		Test hashCode()
		@safe.precondition una list con un elemento e una sublist con un elemento
		@safe.postcondition una list con un elemento e una sublist con un elemento
		@safe.testcases controllare se hashcode() genera l'hashcode corretto
	*/
	@Test
	public void testHashCode2() {
		c.add("pippo");
		
		List<String> tmp = c.subList(0, 1);
		int hashcode = 31 * 1 + 106673622;
		
		assertEquals(hashcode, tmp.hashCode());
	}
	/**
		Test indexOf()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se indexOf() restituisce il primo indice relativo al valore specificato in subList
	*/
	@Test
	public void testIndexOf2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(1, tmp.indexOf("pippo2"));
	}
	/**
		Test isEmpty()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con tre elementi e una sublist vuota
		@safe.testcases controllare se isEmpty() conferma che la sublist e' vuota o riempita
	*/
	@Test
	public void testIsEmpty2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		tmp.clear();
		
		assertEquals(true, tmp.isEmpty());
		tmp.add("pippo");
		assertEquals(false, tmp.isEmpty());
	}
	/**
		Test lastIndexOf()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se lastIndexOf() restituisce l'ultimo indice relativo al valore specificato in sublist
	*/
	@Test
	public void testLastIndexOf2() {
		c.add("pippo");
		c.add("pluto");
		c.add("pluto2");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(2, tmp.lastIndexOf("pluto2"));
	}
	/**
		Test remove()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con cinque elementi e una sublist con due elementi
		@safe.testcases controllare se, chiamando remove() sulla sublist, anche dalla list si rimuove l'elemento specificato
	*/
	@Test
	public void testRemove6() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals("pippo2", tmp.remove(1));
		assertEquals("pluto2", c.get(3));
	}
	/**
		Test remove()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con cinque elementi e una sublist con due elementi
		@safe.testcases controllare se, chiamando remove() sulla sublist, anche dalla list si rimuove l'elemento specificato
	*/
	@Test
	public void testRemove8() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.remove("pippo2"));
		assertEquals("pluto2", c.get(3));
	}
	/**
		Test removeAll()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con quattro elementi e una sublist con un elemento
		@safe.testcases controllare se, chiamando removeAll() sulla sublist, anche dalla list si rimuove la collection specificata
	*/
	@Test
	public void testRemoveAll4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo2");
		t.add("pluto2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.removeAll(t));
		assertEquals(1, tmp.size());
		assertEquals("pluto2", c.get(3));
	}
	/**
		Test retainAll()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con cinque elementi e una sublist con due elementi
		@safe.testcases controllare se a seguito della chiamata di retainAll(), la list e la sublist cambiano
	*/
	@Test
	public void testRetainAll4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		CollectionAdapter<String> t = new CollectionAdapter<String>();
		t.add("pippo2");
		t.add("pluto2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(true, tmp.retainAll(t));
		assertEquals("paperino2", c.get(4));
	}
	/**
		Test set()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se set restituisce l'elemento rimpiazzato e se inserisce l'elemento specificato nella posizione specificata in sublist e list
	*/
	@Test
	public void testSet4() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals("paperino", tmp.set(0, "casa"));
		assertEquals(2, c.indexOf("casa"));
	}
	/**
		Test size()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se size() restituisce il numero di elementi della sublist
	*/
	@Test
	public void testSize2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		assertEquals(3, tmp.size());
	}
	/**
		Test toArray()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con sei elementi e una sublist con tre elementi
		@safe.testcases controllare se toArray() genera un array non vuoto, con gli elementi della sublist
	*/
	@Test
	public void testToArray2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		Object[] t = tmp.toArray();
		
		assertEquals(3, t.length);
		assertEquals(true, tmp.contains(t[0]));
		assertEquals(true, tmp.contains(t[1]));
		assertEquals(true, tmp.contains(t[2]));
	}
	
	//ITERATOR DI SUBLIST
	/**
		Test remove()
		@safe.precondition una list con sei elementi e una sublist con tre elementi
		@safe.postcondition una list con cinque elementi e una sublist con due elementi
		@safe.testcases controllare se, dopo aver chiamato remove(), viene tolto l'elemento restituito da next() nella sublist e nella list 
	*/
	@Test
	public void testRemoveIterator2() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		
		List<String> tmp = c.subList(2, 5);
		
		Iterator<String> iter = tmp.iterator();
		iter.next();
		iter.next();
		iter.remove();
		
		assertEquals("pluto2", tmp.get(1));
		assertEquals("pluto2", c.get(3));
	}
	
	//LISTITERATOR DI SUBLIST
	/**
		Test remove()
		@safe.precondition una list con dodici elementi e una sublist con otto elementi
		@safe.postcondition una list con undici elementi e una sublist con sette elementi
		@safe.testcases controllare se, dopo aver chiamato remove() preceduto da next(), sublist e list rimuovono l'elemento restituito da next()
	*/
	@Test
	public void testRemoveListIterator() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		c.add("pippo3");
		c.add("pluto3");
		c.add("paperino3");
		c.add("pippo4");
		c.add("pluto4");
		c.add("paperino4");
		
		List<String> tmp = c.subList(2, 10);
		
		ListIterator<String> iter = tmp.listIterator();
		iter.next();
		iter.remove();
		
		assertEquals("pippo2", tmp.get(0));
		assertEquals("pippo2", c.get(2));
	}
	/**
		Test remove()
		@safe.precondition una list con dodici elementi e una sublist con otto elementi
		@safe.postcondition una list con undici elementi e una sublist con sette elementi
		@safe.testcases controllare se, dopo aver chiamato remove() preceduto da previous(), sublist e list rimuovono l'elemento restituito da previous()
	*/
	@Test
	public void testRemoveListIterator3() {
		c.add("pippo");
		c.add("pluto");
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		c.add("pippo3");
		c.add("pluto3");
		c.add("paperino3");
		c.add("pippo4");
		c.add("pluto4");
		c.add("paperino4");
		
		List<String> tmp = c.subList(2, 10);
		
		ListIterator<String> iter = tmp.listIterator();
		iter.next();
		iter.previous();
		iter.remove();
		
		assertEquals("pippo2", tmp.get(0));
		assertEquals("pippo2", c.get(2));
	}
	/**
		Test add()
		@safe.precondition una list con dodici elementi e una sublist con otto elementi
		@safe.postcondition una list con tredici elementi e una sublist con nove elementi
		@safe.testcases controllare se sublist e list aggiungono un elemento nella posizione precedente all'elemento che sarebbe restituito da next() o successivo all'elemento che sarebbbe restituito da previous()
	*/
	@Test
	public void testAddListIterator() {
		c.add("pippo");
		c.add("pluto");
		
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		c.add("pippo3");
		c.add("pluto3");
		c.add("paperino3");
		c.add("pippo4");
		c.add("pluto4");
		c.add("paperino4");
		
		List<String> tmp = c.subList(2, 10);
		
		ListIterator<String> iter = tmp.listIterator();
		iter.add("casa");
		
		assertEquals("casa", tmp.get(0));
		assertEquals("casa", c.get(2));
	}
	/**
		Test add()
		@safe.precondition una list con dodici elementi e una sublist con otto elementi
		@safe.postcondition una list con tredici elementi e una sublist con nove elementi
		@safe.testcases controllare se sublist e list aggiungono un elemento nella posizione precedente all'elemento che sarebbe restituito da next() o successivo all'elemento che sarebbbe restituito da previous()
	*/
	@Test
	public void testAddListIterator2() {
		c.add("pippo");
		c.add("pluto");
		
		c.add("paperino");
		c.add("pippo2");
		
		c.add("pluto2");
		c.add("paperino2");
		c.add("pippo3");
		c.add("pluto3");
		c.add("paperino3");
		c.add("pippo4");
		c.add("pluto4");
		c.add("paperino4");
		
		List<String> tmp = c.subList(2, 10);
		//	uso un listIterator() con indicie diverso da 0
		ListIterator<String> iter = tmp.listIterator(2);
		iter.add("casa");
		
		assertEquals("casa", tmp.get(2));
		assertEquals("casa", c.get(4));
	}
	/**
		Test set()
		@safe.precondition una list con dodici elementi e una sublist con otto elementi
		@safe.postcondition una list con dodici elementi e una sublist con otto elementi
		@safe.testcases controllare se la sublist e list, dopo aver chiamato previous(), rimpiazzano l'elemento restituito da previous()
	*/
	@Test
	public void testSetListIterator() {
		c.add("pippo");
		c.add("pluto");
		
		c.add("paperino");
		c.add("pippo2");
		c.add("pluto2");
		c.add("paperino2");
		c.add("pippo3");
		c.add("pluto3");
		c.add("paperino3");
		c.add("pippo4");
		c.add("pluto4");
		c.add("paperino4");
		
		List<String> tmp = c.subList(2, 10);
		
		ListIterator<String> iter = tmp.listIterator();
		iter.next();
		iter.previous();
		iter.set("casa");
		
		assertEquals("casa", tmp.get(0));
		assertEquals("casa", c.get(2));
	}
	/**
		Test set()
		@safe.precondition una list con dodici elementi e una sublist con otto elementi
		@safe.postcondition una list con dodici elementi e una sublist con otto elementi
		@safe.testcases controllare se la sublist e list, dopo aver chiamato next(), rimpiazzano l'elemento restituito da next()
	*/
	@Test
	public void testSetListIterator2() {
		c.add("pippo");
		c.add("pluto");
		
		c.add("paperino");
		c.add("pippo2");
		
		c.add("pluto2");
		c.add("paperino2");
		c.add("pippo3");
		c.add("pluto3");
		c.add("paperino3");
		c.add("pippo4");
		c.add("pluto4");
		c.add("paperino4");
		
		List<String> tmp = c.subList(2, 10);
		
		ListIterator<String> iter = tmp.listIterator(2);
		iter.next();
		iter.set("casa");
		
		assertEquals("casa", c.get(4));
		assertEquals("casa", tmp.get(2));
	}	
}