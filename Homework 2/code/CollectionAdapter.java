import java.io.*;
import java.lang.*;
import java.lang.ref.*;
import java.util.*;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.function.Consumer;

	/**
	* CLASS ADAPTER: target ( = Collection), Adaptee ( = Vector)
	*
	* @author Binotto Stefano
	* @version 05/2020
	*/

public class CollectionAdapter<E> extends Vector<E> implements Collection<E> {
	
	/**
		Ensures that this collection contains the specified element. Returns true if this collection changed as a result of the call (questa collection permette duplicati)
		@param e element whose presence in this collection is to be ensured
		@return true if this collection changed as a result of the call
		@throws NullPointerException if the specified element is null and this collection does not permit null elements
	*/
	public boolean add(E e){
		if (e == null) {
			throw new NullPointerException();
		}
		super.addElement(e);
		return true;
	}
	
	/**
		Adds all of the elements in the specified collection to this collection
		@param c collection containing elements to be added to this collection
		@return true if this collection changed as a result
		@throws NullPointerException if the specified collection contains a null element and this collection does not permit null elements, or if the specified collection is null
	*/
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}	
		boolean result = false;
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = e.iterator();
		while(iter.hasNext()) {
			//richiama add di CollectionAdapter in modo da eseguire il controllo (e != null)
			add( iter.next());
			result = true;
		}
		return result;	
	}
	
	/**
		Removes all of the elements from this collection (optional operation). The collection will be empty after this method returns
	*/
	public void clear() {
		super.removeAllElements();
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
		return super.contains(o);
	}
	
	/**
		Returns true if this collection contains all of the elements in the specified collection
		@param c collection to be checked for containment in this collection
		@return true if this collection contains all of the elements in the specified collection
		@throws NullPointerException if the specified collection contains one or more null elements and this collection does not permit null elements (optional), or if the specified collection is null
		@see contains(Object)
	*/
	public boolean containsAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = e.iterator();
		
		while(iter.hasNext()) {
			E tmp = iter.next();
			if(!contains(tmp)) {
				return false;
			}
		}
		return true;		
	}
	
	/**
		Compares the specified object with this collection for equality
		@param o object to be compared for equality with this collection
		@return true if the specified object is equal to this collection
	*/
	public boolean equals(Object o) {
		if ( o.getClass() != this.getClass()) {
			return false;
		}
		CollectionAdapter<E> tmp = (CollectionAdapter<E>) o;
		if (size() != tmp.size()) {
			return false;
		}
		if (!containsAll(tmp)) {
			return false;
		}
		return true;
	}
	
	/**
		Returns the hash code value for this collection
		@return the hash code value for this collection
	*/
	public int hashCode() {
		int sum = 0;
		
		Iterator<E> iter = this.iterator();
		
		while(iter.hasNext()){
			sum = sum + (iter.next()).hashCode();
		}
		return sum;
	}
	
	/**
		Returns true if this collection contains no elements
		@return true if this collection contains no elements
	*/
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	/**
		Returns an iterator over the elements in this collection
		@return an Iterator over the elements in this collection
	*/
	public Iterator<E> iterator() {
		//Iterator<E> iter = new CollectionAdapterIterator<E>(this);
		//return iter;
		return new CollectionAdapterIterator<E>(this);
	}
	
	/**
		Returns a possibly parallel Stream with this collection as its source. It is allowable for this method to return a sequential stream
		@return a possibly parallel Stream over the elements in this collection
	*/
	public Stream<E> parallelStream() {
		throw new UnsupportedOperationException();
	}
	
	/**
		Removes a single instance of the specified element from this collection, if it is present
		@param o element to be removed from this collection, if present
		@return true if an element was removed as a result of this call
		@throws NullPointerException if the specified element is null and this collection does not permit null elements
	*/
	public boolean remove(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		return super.removeElement(o);
	}
	
	/**
		Removes all of this collection's elements that are also contained in the specified collection
		@param c collection containing elements to be removed from this collection
		@return true if this collection changed as a result of the call
		@throws NullPointerException - if this collection contains one or more null elements and the specified collection does not support null elements (optional), or if the specified collection is null

	*/
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		boolean result = false;
		
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = e.iterator();
		
		while(iter.hasNext()){
			if(result == true) {
				remove(iter.next());
			} else {
				result = remove(iter.next());
			}
		}
		return result;
	}
	
	/**
		Removes all of the elements of this collection that satisfy the given predicate
		@param filter a predicate which returns true for elements to be removed
		@return true if any elements were removed
		@throws NullPointerException - if the specified filter is null
	*/	
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Retains only the elements in this collection that are contained in the specified collection. In other words, removes from this collection all of its elements that are not contained in the specified collection
		@param c collection containing elements to be retained in this collection
		@return true if this collection changed as a result of the call
		@throws NullPointerException if this collection contains one or more null elements and the specified collection does not permit null elements (optional), or if the specified collection is null
	*/
	public boolean retainAll(Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		boolean result = false;
		
		CollectionAdapter<E> e = (CollectionAdapter<E>) c;
		Iterator<E> iter = iterator();
		
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
		Returns the number of elements in this collection.
	*/
	public int size() {
		return super.size();
	}
	
	/**
		Creates a Spliterator over the elements in this collection
		@return a Spliterator over the elements in this collection
	*/
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns a sequential Stream with this collection as its source
		@return a sequential Stream over the elements in this collection
	*/
	public Stream<E> stream() {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an array containing all of the elements in this collection
		Modifiche strutturali all’array non si propagano sull’originale
		Modifiche strutturali all’originale non si propagano sull’array
		
		@return an array containing all of the elements in this collection
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
		Returns an array containing all of the elements in this collection
		@param a the array into which the elements of this collection are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose
		@return an array containing all of the elements in this collection
		@throws NullPointerException if the specified array is null
	*/
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	
	class CollectionAdapterIterator<E> implements Iterator<E> {
		
		private Vector<E> coll;
		private int index;
		private boolean nextCheck = false;

		public CollectionAdapterIterator(Vector<E> c) {
			coll = c;
			index = 0;
		}
		
		/**
			Returns true if the iteration has more elements
			@return true if the iteration has more elements
		*/
		public boolean hasNext() {
			return index < coll.size();
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
			return (E) coll.elementAt(index++);
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
			coll.removeElementAt(--index);
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