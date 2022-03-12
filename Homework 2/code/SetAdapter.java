import java.io.*;
import java.lang.*;
import java.lang.ref.*;
import java.util.*;

import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.Iterator;

	/**
	* OBJECT ADAPTER: target ( = Set), Adaptee ( = Hashtable)
	*
	* @author Binotto Stefano
	* @version 05/2020
	*/

public class SetAdapter<E> implements Set<E> {
	
	private Hashtable<E,E> ht = new Hashtable<E,E>();
	
	
	/**
		Adds the specified element to this set if it is not already present
		@param e element to be added to this set
		@return true if this set did not already contain the specified element
		@throws NullPointerException if the specified element is null and this set does not permit null elements
	*/
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (contains(e)) {
			return false;
		}
		ht.put(e,e);
		return true;
	}
	
	/**
		Adds all of the elements in the specified collection to this set if they're not already present
		@param c collection containing elements to be added to this set
		@return true if this set changed as a result of the call
		@throws NullPointerException if the specified collection contains one or more null elements and this set does not permit null elements, or if the specified collection is null
	*/
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		boolean result = false;
		
		CollectionAdapter<E> tmp = (CollectionAdapter<E>) c;
		Iterator<E> iter = tmp.iterator();		
		while(iter.hasNext()) {
			if(result == true) {
				add(iter.next());
			} else {
				result = add(iter.next());
			}
		}
		return result;
	}
	
	/**
		Removes all of the elements from this collection. The collection will be empty after this method returns.

	*/
	public void clear() {
		ht.clear();
	}
	
	/**
		Returns true if this collection contains the specified element
		@param o element whose presence in this collection is to be tested
		@return true if this collection contains the specified element
		@throws NullPointerException if the specified element is null and this collection does not permit null elements
	*/
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return ht.contains(o) && ht.containsKey(o);
	}
	
	/**
		Returns true if this set contains all of the elements of the specified collection
		@param c collection to be checked for containment in this set
		@return true if this set contains all of the elements of the specified collection
		@throws NullPointerException if the specified collection contains one or more null elements and this set does not permit null elements, or if the specified collection is null
	*/
	public boolean containsAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		SetAdapter<E> tmp = (SetAdapter<E>) c;
		Iterator<E> iter = tmp.iterator();
		while(iter.hasNext()) {
			if (!contains(iter.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
		Compares the specified object with this set for equality. Returns true if the specified object is also a set, the two sets have the same size, and every member of the specified set is contained in this set
		@param o object to be compared for equality with this set
		@return true if the specified object is equal to this set
	*/
	public boolean equals(Object o) {
		if ( o.getClass() != this.getClass()) {
			return false;
		}
		SetAdapter<E> tmp = (SetAdapter<E>) o;
		if (this.size() != tmp.size()) {
			return false;
		}
		if (!this.containsAll(tmp)) {
			return false;
		}
		return true;
	}
	
	/**
		Returns the hash code value for this set. The hash code of a set is defined to be the sum of the hash codes of the elements in the set, where the hash code of a null element is defined to be zero
		@return the hash code value for this set
	*/
	public int hashCode() {
		int sum = 0;
		Enumeration<E> e = ht.elements();
		while (e.hasMoreElements()) {
			sum = sum + (e.nextElement()).hashCode();
		}
		return sum;
	}
	
	/**
		Returns true if this set contains no elements.
		@return true if this set contains no elements
	*/
	public boolean isEmpty() {
		return ht.isEmpty();
	}
	
	/**
		Returns an iterator over the elements in this set. The elements are returned in no particular order
		@return an iterator over the elements in this set
	*/
	public Iterator<E> iterator() {
		return new SetAdapterIterator<E>(ht);
	}
	
	/**
		Removes the specified element from this set if it is present.
		@param o object to be removed from this set, if present
		@return true if this set contained the specified element
		@throws NullPointerException if the specified element is null and this set does not permit null elements
	*/
	public boolean remove(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		
		if(contains(o)) {	
			ht.remove(o);
			return true;
		}
		return false;
	}
	
	/**
		Removes from this set all of its elements that are contained in the specified collection.
		@param c collection containing elements to be removed from this set
		@return true if this set changed as a result of the call
		@throws NullPointerException - if this set contains a null element and the specified collection does not permit null elements, or if the specified collection is null
	*/
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		boolean result = false;
		
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = e.iterator();
		while (iter.hasNext()) {
			if(result == true) {
				remove(iter.next());
			} else {
				result = remove(iter.next());
			} 
		}
		return result;
	}
	
	/**
		Retains only the elements in this set that are contained in the specified collection. In other words, removes from this set all of its elements that are not contained in the specified collection.
		@param c collection containing elements to be retained in this set
		@return true if this set changed as a result of the call
		@throws NullPointerException - if this set contains a null element and the specified collection does not permit null elements, or if the specified collection is null
	*/
	public boolean retainAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		boolean result = false;
		
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = this.iterator();
		
		while (iter.hasNext()) {
			E tmp = iter.next();
			if (! e.contains(tmp)) {
				iter.remove();
				result = true;
			}
		}
		return result;	
	}
	
	/**
		Returns the number of elements in this set
		@return the number of elements in this set
	*/
	public int size() {
		return ht.size();
	}
	
	/**
		Returns an array containing all of the elements in this set
		@return an array containing all the elements in this set
	*/
	public Object[] toArray() {
		Object[] tmp = new Object[size()];
	
		Enumeration e = ht.elements();
		
		int i= 0;
		while (e.hasMoreElements()) {
			tmp[i++] = e.nextElement();
		}
		return tmp;
	}
	
	/**
		Returns an immutable set containing zero elements.
	*/
	public static <E> Set<E> of() {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable set containing one element
	*/
	public static <E> Set<E> of(E e1) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing an arbitrary number of elements.
	*/
	public static <E> Set<E> of(E... elements) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing two elements
	*/
	public static <E> Set<E> of(E e1, E e2) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable set containing three elements
	*/
	public static <E> Set<E> of(E e1, E e2, E e3) {
		throw new UnsupportedOperationException();		
	}

	/**
		Returns an immutable set containing four elements
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing five elements
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing six elements.
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing seven elements.
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing eight elements.
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
		throw new UnsupportedOperationException();
	}

	/**
		Returns an immutable set containing nine elements.
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable set containing ten elements
	*/
	public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
		throw new UnsupportedOperationException();
	}

	/**
		Creates a Spliterator over the elements in this set
	*/
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array;
	*/
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	
	class SetAdapterIterator<E> implements Iterator<E> {
		
		private Hashtable<E,E> h;
		private Enumeration elem;
		private Object[] eArray;
		private int index;
		private boolean nextCheck = false;

		public SetAdapterIterator(Hashtable<E,E> e) {
			h = e;
			elem = e.keys();
			//Enumeration en = h.keys();
			eArray = new Object[e.size()];
			int i = 0;
			while(elem.hasMoreElements()) {
				eArray[i++] = elem.nextElement();
			}
			index = 0;
		}
		
		/**
			Returns true if the iteration has more elements
			@return true if the iteration has more elements
		*/
		public boolean hasNext() {
			return index < eArray.length;
		}
		
		/**
			Returns the next element in the iteration
			@return the next element in the iteration
			@throws NoSuchElementException if the iteration has no more elements
		*/
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			nextCheck = true;
			return (E) eArray[index++];
		}
		
		/**
			Removes from the underlying collection the last element returned by this iterator.This method can be called only once per call to next().
			@throws IllegalStateException if the next method has not yet been called, or the remove method has already been called after the last call to the next method
		*/
		public void remove() {
			if (!nextCheck) {
				throw new IllegalStateException();
			}
			// rimuovere l'elemento returnato da next() devo tornare indietro di 1 con index
			Object tmp = eArray[index-1];
			Object[] aTmp = new Object[eArray.length-1];
			for (int i = 0; i < index-1; i++) {
				aTmp[i] = eArray[i];
			}
			for (int i = index; i < eArray.length; i++) {
				aTmp[i-1] = eArray[i];
			}			
			h.remove(tmp);
			eArray = aTmp;
			index--;
			nextCheck = false;
		}
		
		/**
			Performs the given action for each remaining element until all elements have been processed or the action throws an exception
		*/
		public void forEachRemaining(Consumer<? super E> action) {
			throw new UnsupportedOperationException();
		}
	}	
}