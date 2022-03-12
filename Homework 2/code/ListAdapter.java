import java.io.*;
import java.lang.*;
import java.lang.ref.*;
import java.util.*;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.Comparator;

	/**
	*	LISTADAPTER
	* CLASS ADAPTER: target ( = List), Adaptee ( = Vector)
	*
	* @author Binotto Stefano
	* @version 05/2020
	*/

public class ListAdapter<E> extends Vector<E> implements List<E> {
	
	/**
		Inserts the specified element at the specified position in this list. Shifts the element currently at that position (if any) and any subsequent elements to the right
		@param index index at which the specified element is to be inserted
		@param element element to be inserted
		@throws NullPointerException if the specified element is null and this list does not permit null elements
		@throws IndexOutOfBoundsException if the index is out of range 
	*/
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size() ) {
			throw new IndexOutOfBoundsException();
		}
		super.insertElementAt(element, index);
	}
	
	/**
		Appends the specified element to the end of this list 
		@param e - element to be appended to this list
		@return true if this list changed
		@throws NullPointerException if the specified element is null and this list does not permit null elements
	*/
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		super.addElement(e);
		return true;
	}
	
	/**
		Inserts all of the elements in the specified collection into this list at the specified position
		@param index index at which to insert the first element from the specified collection
		@param c collection containing elements to be added to this list
		@return true if this list changed as a result of the call
		@throws NullPointerException - if the specified collection contains one or more null elements and this list does not permit null elements, or if the specified collection is null
		@throws IndexOutOfBoundsException - if the index is out of range
	*/
	public boolean addAll(int index, Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size() ) {
			throw new IndexOutOfBoundsException();
		}
		
		boolean result = false;
		
		CollectionAdapter<E> tmp = (CollectionAdapter<E>) c;
		Iterator<E> iter = tmp.iterator();
		
		while(iter.hasNext()) {
			add(index++, iter.next());
			result = true;
		}
		return result;
	}
	
	/**
		Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator
		@param c collection containing elements to be added to this list
		@return true if this list changed as a result of the call
		@throws NullPointerException - if the specified collection contains one or more null elements and this list does not permit null elements, or if the specified collection is null
	*/
	public boolean addAll(Collection<? extends E> c) {
		if(c == null) {
			throw new NullPointerException();
		}
		boolean result = false;
		
		CollectionAdapter<E> tmp = (CollectionAdapter<E>) c;
		Iterator<E> iter = tmp.iterator();
		
		while(iter.hasNext()) {
			add(iter.next());
			result = true;
		}
		return result;
	}
	
	/**
		Removes all of the elements from this list (optional operation). The list will be empty after this call returns
	*/
	public void clear() {
		super.removeAllElements();
	}
	
	/**
		Returns true if this list contains the specified element
		@param o - element whose presence in this list is to be tested
		@return true if this list contains the specified element
		@throws NullPointerException if the specified element is null and this list does not permit null elements
	*/
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return super.contains(o);
	}
	
	/**
		Returns true if this list contains all of the elements of the specified collection
		@param c collection to be checked for containment in this list
		@return true if this list contains all of the elements of the specified collection
		@throws NullPointerException - if the specified collection contains one or more null elements and this list does not permit null elements (optional), or if the specified collection is null
	*/
	public boolean containsAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		ListAdapter<E> tmp = (ListAdapter<E>) c;
		Iterator<E> iter = tmp.iterator();
		while(iter.hasNext()) {
			if(!contains(iter.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
		Compares the specified object with this list for equality. Returns true if and only if the specified object is also a list, both lists have the same size, and all corresponding pairs of elements in the two lists are equal
		@param o the object to be compared for equality with this list
		@return true if the specified object is equal to this list
	*/
	public boolean equals(Object o) {
		if ( o.getClass() != this.getClass()) {
			return false;
		}
		ListAdapter<E> tmp = (ListAdapter<E>) o;
		if (this.size() != tmp.size()) {
			return false;
		}
		if (!this.containsAll(tmp)) {
			return false;
		}
		return true;
	}
	
	/**
		Returns the element at the specified position in this list
		@param index index of the element to return
		@return the element at the specified position in this list
		@throws IndexOutOfBoundsException if the index is out of range
	*/
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return super.elementAt(index);
	}
	
	/**
		Returns the hash code value for this list. The hash code of a list is defined to be the result of the following calculation
		@return the hash code value for this list
	*/
	public int hashCode() {
		int hashcode = 1;
		for (E e : this) {
			hashcode = 31 * hashcode + (e == null ? 0 : e.hashCode());
		}
		return hashcode;
	}
	
	/**
		Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
		@param o element to search for
		@return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element
		@throws NullPointerException - if the specified element is null and this list does not permit null elements
	*/
	public int indexOf(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return super.indexOf(o);
	}
	
	/**
		Returns true if this list contains no elements.
		@return true if this list contains no elements
	*/
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	/**
		Returns an iterator over the elements in this list in proper sequence.
		@return an iterator over the element in this list in proper sequence
	*/
	public Iterator<E> iterator() {
		return new ListAdapterIterator<E>(this);
	}
	
	/**
		Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element
		@param o element to search for
		@return the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element
		@throws NullPointerException - if the specified element is null and this list does not permit null elements
	*/
	public int lastIndexOf(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return super.lastIndexOf(o);
	}
	
	/**
		Returns a list iterator over the elements in this list (in proper sequence).
		@return a list iterator over the elements in this list (in proper sequence)
	*/
	public ListIterator<E> listIterator() {
		return new ListAdapterListIterator<E>(this,0); 
	}
	
	/**
		Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list. The specified index indicates the first element that would be returned by an initial call to next
		@param index index of the first element to be returned from the list iterator (by a call to next)
		@return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list
		@throws IndexOutOfBoundsException - if the index is out of range
	*/
	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return new ListAdapterListIterator<E>(this, index);
	}
	
	/**
		Returns an immutable list containing zero elements
		@return an empty List
	*/
	public static <E> List<E> of() {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable list containing one element
	*/
	public static <E> List<E> of(E e1) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing an arbitrary number of elements
	*/
	public static <E> List<E> of(E... elements) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing two elements
	*/
	public static <E> List<E> of(E e1, E e2) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing three elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing four elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable list containing five elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing six elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing seven elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing eight elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
		throw new UnsupportedOperationException();
	}
	
	/**	
		Returns an immutable list containing nine elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable list containing ten elements
	*/
	public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Removes the element at the specified position in this list
		@param index the index of the element to be removed
		@return the element previously at the specified position
		@throws IndexOutOfBoundsException if the index is out of range
	*/
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E tmp = get(index);
		super.removeElementAt(index);
		return tmp;
	}
	
	/**
		Removes the first occurrence of the specified element from this list, if it is present
		@param o element to be removed from this list, if present
		@return true if this list contained the specified element
		@throws NullPointerException if the specified element is null and this list does not permit null elements

	*/
	public boolean remove(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return super.removeElement(o);
	}
	
	/**
		Removes from this list all of its elements that are contained in the specified collection
		@param c collection containing elements to be removed from this list
		@return true if this list changed as a result of the call
		@throws NullPointerException if this list contains a null element and the specified collection does not permit null elements, or if the specified collection is null
	*/
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		boolean result = false;
		
		CollectionAdapter<E> tmp = (CollectionAdapter<E>) c;
		Iterator<E> iter = tmp.iterator();
		
		while (iter.hasNext()) {
			if (result == true) {
				remove(iter.next());
			} else {
				result = remove(iter.next());
			}
		}
		return result;
	}
	
	/**
		Replaces each element of this list with the result of applying the operator to that element
	*/
	public void replaceAll(UnaryOperator<E> operator) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Retains only the elements in this list that are contained in the specified collection. In other words, removes from this list all of its elements that are not contained in the specified collection.
		@param c collection containing elements to be retained in this list
		@return true if this list changed as a result of the call
		@throws NullPointerException if this list contains a null element and the specified collection does not permit null elements, or if the specified collection is null
	*/
	public boolean retainAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		boolean result = false;
		
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = this.iterator();
		
		while(iter.hasNext()) {
			E tmp = iter.next();
			if(! e.contains(tmp)) {
				iter.remove();
				result = true;
			}
		}
		return result;
	}
	
	/**
		Replaces the element at the specified position in this list with the specified element
		@param index index of the element to replace
		@param element element to be stored at the specified position
		@return the element previously at the specified position
		@throws NullPointerException if the specified element is null and this list does not permit null elements
		@throws IndexOutOfBoundsException if the index is out of range

	*/
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E tmp = get(index);
		super.setElementAt(element, index);
		return tmp;
	}
	
	/**
		Returns the number of elements in this list
		@return the number of elements in this list
	*/
	public int size() {
		return super.size();
	}
	
	/**
		Sorts this list according to the order induced by the specified Comparator.
	*/
	public void sort(Comparator<? super E> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Creates a Spliterator over the elements in this list
	*/
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the returned list is empty.)
		Le modifiche strutturali sono propagate dalla sublist alla lista di provenienza e viceversa
	
		@param fromIndex low endpoint (inclusive) of the subList
		@param toIndex high endpoint (exclusive) of the subList
		@return a view of the specified range within this list
		@throws IndexOutOfBoundsException for an illegal endpoint index value
	*/
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		
		List<E> tmp = new SubList<E>(this, fromIndex, toIndex);
		
		return tmp;
	}
	
	/**
		Returns an array containing all of the elements in this list in proper sequence (from first to last element)
		Modifiche strutturali all'array si ripercuotono sulla lista e viceversa
		@return an array containing all of the elements in this list in proper sequence
	*/
	public Object[] toArray() {
		Object[] tmp = new Object[size()];
		Enumeration e = super.elements();
		
		int i = 0;
		while (e.hasMoreElements()) {
			tmp[i++] = e.nextElement();
		}
		return tmp;
	}
	
	/**
		Returns an array containing all of the elements in this list in proper sequence
	*/
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	
	class ListAdapterIterator<E> implements Iterator<E> {
		
		private Vector<E> vlist;
		protected int index;
		boolean nextCheck = false;

		public ListAdapterIterator(Vector<E> c) {
			vlist = c;
			index = 0;
		}
		
		/**
			Returns true if the iteration has more elements
			@return true if the iteration has more elements
		*/
		public boolean hasNext() {
			return index < vlist.size();
		}
		
		/**
			Returns the next element in the iteration
			@return the next element in the iteration
			@throws NoSuchElementException if the iteration has no more elements
		*/
		public E next() {
			if(! hasNext()) {
				throw new NoSuchElementException();
			}
			nextCheck = true;
			return (E) vlist.elementAt(index++);
		}
		
		/**
			Removes from the underlying collection the last element returned by this iterator.This method can be called only once per call to next().
			@throws IllegalStateException if the next method has not yet been called, or the remove method has already been called after the last call to the next method
		*/
		public void remove() {
			if (nextCheck == false) {
				throw new IllegalStateException();
			}
			// rimuovere l'elemento returnato da next() devo tornare indietro di 1 con index
			vlist.removeElementAt(--index);
			nextCheck = false;
		}
		
		/**
			Performs the given action for each remaining element until all elements have been processed or the action throws an exception
		*/
		public void forEachRemaining(Consumer<? super E> action) {
			throw new UnsupportedOperationException();
		}	
	}
	
	
	class ListAdapterListIterator<E> implements ListIterator<E> {
		
		private Vector<E> vlist;
		protected int pos;
		protected String actionCalled;
		private boolean check;

		public ListAdapterListIterator(Vector<E> c, int index) {
			vlist = c;
			if(index < 0 || index > vlist.size()) {
				throw new IndexOutOfBoundsException();
			}
			check = true;
			pos = index;
		}
		
		/**
			Returns true if the iteration has more elements
			@return true if the iteration has more elements
		*/
		public boolean hasNext() {
			return pos < vlist.size();
		}
		
		/**
			Returns the next element in the iteration
			@return the next element in the iteration
			@throws NoSuchElementException if the iteration has no more elements
		*/
		public E next() {
			if(! hasNext()) {
				throw new NoSuchElementException();
			}
			actionCalled = "next()";
			check = false;
			return (E) vlist.elementAt(pos++);
		}

		/**
			Returns true if this list iterator has more elements when traversing the list in the reverse direction
			@return true if the list iterator has more elements when traversing the list in the reverse direction
		*/
		public boolean hasPrevious() {
			return pos > 0;
		}
		
		/**
			Returns the previous element in the list and moves the cursor position backwards
			@return the previous element in the list
			@throws NoSuchElementException if the iteration has no previous element
		*/
		public E previous() {
			if (! hasPrevious()) {
				throw new NoSuchElementException();
			}
			actionCalled = "previous()";
			check = false;
			return vlist.elementAt(--pos);
		}
		
		/**
			Returns the index of the element that would be returned by a subsequent call to next(). (Returns list size if the list iterator is at the end of the list.)
			@return the index of the element that would be returned by a subsequent call to next, or list size if the list iterator is at the end of the list
		*/
		public int nextIndex() {
			return pos;
		}
		
		/**
			Returns the index of the element that would be returned by a subsequent call to previous(). (Returns -1 if the list iterator is at the beginning of the list.)
			@return the index of the element that would be returned by a subsequent call to previous, or -1 if the list iterator is at the beginning of the list
		*/
		public int previousIndex() {
			return pos - 1;
		}
		
		/**
			Removes from the underlying collection the last element returned by this iterator.This method can be called only once per call to next().
			@throws IllegalStateException if neither next nor previous have been called, or remove or add have been called after the last call to next or previous
		*/
		public void remove() {
			//check della chiamata di add o remove
			if (check) {
				throw new IllegalStateException();
			} 
			check = true;
			if (actionCalled.equals("previous()")) {
				vlist.removeElementAt(pos);
			} else if (actionCalled.equals("next()")) {
				vlist.removeElementAt(--pos);
			} else {
				throw new IllegalStateException();
			}
		}
		
		/**
			Inserts the specified element into the list. The element is inserted immediately before the element that would be returned by next(), if any, and after the element that would be returned by previous(), if any
			@param e the element to insert
		*/
		public void add(E e) {
			check = true;
			vlist.insertElementAt(e, pos);
			//pos++;			
		}
		
		/**
			Replaces the last element returned by next() or previous() with the specified element. This call can be made only if neither remove() nor add(E) have been called after the last call to next or previous.
			@param e the element with which to replace the last element returned by next or previous
			@throws IllegalStateException if neither next nor previous have been called, or remove or add have been called after the last call to next or previous
		*/
		public void set(E e) {
			//check della chiamata di add o remove
			if (check) {
				throw new IllegalStateException();
			}
			
			if (actionCalled.equals("previous()")) {
				vlist.setElementAt(e, pos);
			} else if (actionCalled.equals("next()")) {
				vlist.setElementAt(e, --pos);
			} else {
				throw new IllegalStateException();
			}
		}
		
		/**
			Performs the given action for each remaining element until all elements have been processed or the action throws an exception
		*/
		public void forEachRemaining(Consumer<? super E> action) {
			throw new UnsupportedOperationException();
		}	
	}
	
	
	/**
	* Classe Interna SUBLIST
	*/
	class SubList<E> extends ListAdapter<E> implements List<E> {
		private ListAdapter<E> List;
		private ListAdapter<E> subList;
		private int from, to;
		
		public SubList(ListAdapter<E> l, int From, int To) {
			List = l;
			from = From;
			to = To;
			subList = new ListAdapter<E> ();
			for (int i = from; i < to; i++) {
				subList.add(List.get(i));
			}
		}
		
		/**
			Aggiunge l'elemento specificato alla fine della sublist e alla posizione relativa della lista madre
			@param e elemento da aggiungere
			@return true se sublist viene modificata
		*/
		public boolean add(E e) {
			update();
			List.add(from + subList.size(), e);
			return subList.add(e);
		}
		
		/**
			Aggiunge l'elemento specificato alla posizione specificata della sublist e alla relativa posizione della lista madre
			@param element elemento da aggiungere
			@param index indice dell'elemento da aggiungere
			@param element elemento dell'elemento da aggiungere
		*/
		public void add(int index, E element) {
			update();
			List.add(from + index, element);
			subList.add(index, element);
		}
	
		/**
			Aggiunge tutti gli elementi della collection nella sublist e della lista madre
			@param c collection che contiene gli elementi da aggiungere
			@param index indice da cui iniziare ad aggiungere il primo elemento
			@return true se la sublist viene modificata
		*/
		public boolean addAll(int index, Collection<? extends E> c) {
			update();
			List.addAll(from + index, c);
			return subList.addAll(index, c);
		}
		
		/**
			Aggiunge tutti gli elementi della collection alla fine della sublist e alla posizione relativa della lista madre
			@param c collection che contiene gli elementi da aggiungere
		*/
		public boolean addAll(Collection<? extends E> c) {
			update();
			List.addAll(from + subList.size(), c);
			return subList.addAll(c);
		}
		
		/**
			Rimuovi tutti gli elementi dalla sublist, mentre rimuovi dalla lista madre solo gli elementi contenuti precedentemente dalla sublist
		*/
		public void clear() {
			update();
			Iterator<E> iter = subList.iterator();
			while(iter.hasNext()) {
				List.remove(iter.next());
			}
			subList.clear();
		}
		
		/**
			Return true se la sublist contiene l'elemento specificato
			@return true se la sublist contiene l'elemento specificato 
		*/
		public boolean contains(Object o) {
			update();
			return subList.contains(o);
		}
		
		/**
			Return true se la sublist contiene tutti gli elementi specificati dentro la collection
			@param c collection che contiene gli elementi da controllare
			@return true se la sublist contiene tutti gli elementi della collection
		*/
		public boolean containsAll(Collection<?> c) {
			update();
			return subList.containsAll(c);
		}
		
		/**
			Compara l'oggetto specificato con la sublist.
			@param o oggetto da comparare
			@return true se l'oggetto specificato è uguale alla sublist
		*/
		public boolean equals(Object o) {
			update();
			return subList.equals(o);
		}
		
		/**
			Return l'elemento corrispondente all'indice nella sublist
			@param index indice dell'elemento da restituire
			@return l'elemento in quella specifica posizione
		*/
		public E get(int index) {
			update();
			return subList.get(index);
		}
		
		/**
			Return dell' hashcode della sublist.
			@return hashcod della sublist.
		*/
		public int hashCode() {
			update();
			return subList.hashCode();
		}
		
		/**
			Return l'indice del primo riscontro dell'elemento specificato nella sublist
			@param o elemento di cui cercare l'indice, o -1 se non esiste tale elemento nella sublista
			@return l'indice del primo riscontro dell'elemento specificato nella sublist
		*/
		public int indexOf(Object o) {
			update();
			return subList.indexOf(o);
		}
		
		/**
			Return true se la sublist non contiene elementi
			@return true se la sublist non contiene elementi
		*/
		public boolean isEmpty() {
			update();
			return subList.isEmpty();
		}
		
		/**
			Return un iterator sugli elementi della sublist in sequenza
			@return un iterator sugli elementi della sublist in sequenza
		*/
		public Iterator<E> iterator() {
			update();
			return new SubListIterator<E>(List, subList);
		}
		
		/**
			Return l'indice dell'ultimo riscontro dell'elemento specificato nella sublist, o -1 se la sublist non contiene questo elemento
			@param o elemento da cercare
			@return indice dell'ultimo riscontro dell'elemento specificato nella sublist, o -1 se la sublist non contiene questo elemento
		*/
		public int lastIndexOf(Object o) {
			update();
			return subList.lastIndexOf(o);
		}
		
		/**
			Return un list iterator sugli elementi della sublist in sequenza
			@return un list iterator sugli elementi della sublist in sequenza
		*/
		public ListIterator<E> listIterator() {
			update();
			return new  SubListListIterator<E>(List, subList,0); 
		}
		
		/**
			Return un list iterator sugli elementi della sublist in sequenza, partendo da una posizione specifica nella sublist. L'indice specificato indica il primo elementoche sarebe restituito dalla prima chiamata di next()
			@param index indice del primo elementoche viene restituito da next()
			@return un list iterator sugli elementi della sublist in sequenza, partendo da una posizione specifica nella sublist
		*/
		public ListIterator<E> listIterator(int index) {
			update();
			return new  SubListListIterator<E>(List, subList, index); 
		}
		
		/**
			Rimuovi l'elemento alla posizione specificata nella sublist
			@param index indice dell'elemento da rimuovere
			@return elemento precedentemente memorizzato alla posizione specificata
		*/
		public E remove(int index) {
			update();
			List.remove(from + index);
			return subList.remove(index);
		}
		
		/**
			Rimuovi l'elemento specificato dalla sublist, se presente
			@param o l'elemento che deve essere rimosso dalla sublist, se presente
			@return true se la sublist contiene questo elemento
		*/
		public boolean remove(Object o) {
			update();
			int i = subList.indexOf(o);
			if (i != -1) {
				List.remove(from + i);
			}
			return subList.remove(o);
		}
		
		/**
			Rimuovi dalla sublist tutti gli elementi contenuti nella collection specificata
			@param c collection con gli elementi da rimuovere
			@return true se la sublista è cambiata dopo la chiamata di questo metodo
		*/
		public boolean removeAll(Collection<?> c) {
			update();
			CollectionAdapter<E> tmp = (CollectionAdapter<E>) c;
			Iterator<E> iter = tmp.iterator();
			while(iter.hasNext()) {
				int i = subList.indexOf(iter.next());
				if (i != -1) {
					List.remove(from + i);
				}
			}
			return subList.removeAll(c);
		}
		
		/**
			Conserva solo gli elementi di questa sublist che sono anche contenuti nella collection. Rimuovi quindi quelli che non sono contenuti nella collection
			@param c collection contenente gli elementi da conservare
			@return true se la sublista è cambiata dopo la chiamata di questo metodo
		*/
		public boolean retainAll(Collection<?> c) {
			update();
			CollectionAdapter<E> e = (CollectionAdapter<E>) c;
			
			Iterator<E> iter = subList.iterator();
			while(iter.hasNext()) {
				E tmp = iter.next();
				if (! e.contains(tmp)) {
					int i = subList.indexOf(tmp);
					List.remove(from + i);
				}
			}
			return subList.retainAll(c); 
		}
		
		/**
			Rimpiazza l'elemento nella posizione specificata con l'elemento specificato 
			@param index indie dell'elemento da rimpiazzare
			@param element nuovo elemento da memorizzare
			@return l'elemento tolto dalla posizione specificata nella subList
		*/
		public E set(int index, E element) {
			update();
			List.set(from + index, element);
			return subList.set(index, element);
		}
		
		/**
			Return il numero di elementi di questa sublist
			@return il numero di elementi di questa sublist
		*/
		public int size() {
			update();
			return subList.size();
		}
		
		/**
			@throws UnsupportedOperationException operazione non consentita
		*/
		public List<E> subList(int fromIndex, int toIndex) {
			update();
			throw new UnsupportedOperationException();
		}
		
		/**
			Return un array contenente tutti gli elementi contenuti in questa sublist, in ordine di inserimento. 
			@return un array contenente tutti gli elementi contenuti in questa sublist, in ordine di inserimento.
		*/
		public Object[] toArray() {
			update();
			return subList.toArray();
		}
		
		/**
			Metodo per tenere aggiornata la sublist a seguito di modifiche apportate alla lista madre
		*/
		private void update() {
			if (List.isEmpty()) {
				subList.clear();
			}
			for (int index = 0; index < subList.size(); index++) {
				E subListTmp = subList.get(index);
				E ListTmp = List.get(from + index);
				if(! subListTmp.equals(ListTmp) ) {
					subList.set(index, ListTmp);
				}
			}
		}

		class SubListIterator<E> extends ListAdapterIterator<E> {
		
			private ListAdapter<E> List;

			public SubListIterator(ListAdapter<E> lista, ListAdapter<E> sottolista) {
				super(sottolista);
				List = lista;
			}
			
			/**
				Rimuovi l'ultimo elemento restituito da questo iteratore.Questo metodo può essere chiamato una sola volta dopo aver chiamato next().
			*/
			public void remove() {
				super.remove();
				List.remove(from + index - 1);
			}
		}

		class SubListListIterator<E> extends ListAdapterListIterator<E> {
			
			private ListAdapter<E> List;
			
			public SubListListIterator(ListAdapter<E> lista, ListAdapter<E> sottolista, int indice) {
				super(sottolista, indice);
				List = lista;
			}
			
			public void remove() {
				super.remove();
				//List.remove(from + pos);
				if (actionCalled.equals("previous()")) {
					List.remove(from + pos);
				} else if (actionCalled.equals("next()")) {
					List.remove(from + pos - 1);
				}
			}
			
			public void add(E e) {
				super.add(e);
				List.add(from + pos , e);
			}
			
			public void set(E e) {
				super.set(e);
				List.set(from + pos, e);
			}
		}	
	}
}