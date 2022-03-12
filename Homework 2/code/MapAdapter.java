import java.io.*;
import java.lang.*;
import java.lang.ref.*;
import java.util.*;

import java.util.Enumeration;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.Iterator;

	/**
	* CLASS ADAPTER: target ( = Map), Adaptee ( = Hashtable)
	*
	* @author Binotto Stefano
	* @version 05/2020
	*/

public class MapAdapter<K,V> extends Hashtable<K,V> implements Map<K,V> {
	
	/**
		Removes all of the mappings from this map
	*/
	public void clear() {
		super.clear();
	}
	
	/**
		Attempts to compute a mapping for the specified key and its current mapped value (or null if there is no current mapping)
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}
	
	/**
		If the specified key is not already associated with a value (or is mapped to null), attempts to compute its value using the given mapping function and enters it into this map unless null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) {
		throw new UnsupportedOperationException();
	}
	
	/**
		If the value for the specified key is present and non-null, attempts to compute a new mapping given the key and its current mapped value
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns true if this map contains a mapping for the specified key
		@param key key whose presence in this map is to be tested
		@return true if this map contains a mapping for the specified key
		@throws ClassCastException if the key is of an inappropriate type for this map
		@throws NullPointerException if the specified key is null and this map does not permit null keys
	*/
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new NullPointerException();
		}
		return super.containsKey(key);
	}
	
	/**
		Returns true if this map maps one or more keys to the specified value
		@param value value whose presence in this map is to be tested
		@return true if this map maps one or more keys to the specified value
		@throws ClassCastException if the value is of an inappropriate type for this map
		@throws NullPointerException if the specified value is null and this map does not permit null values
	*/
	public boolean containsValue(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		return super.contains(value);
	}
	
	/**
		Returns an immutable Map.Entry containing the given key and value
		@param k the key
		@param v the value
		@return an Entry containing the specified key and value
		@throws NullPointerException if the key or value is null
	*/
	public static <K,V> Map.Entry<K,V> entry(K k, V v) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns a Set view of the mappings contained in this map
		Modifiche al set si ripercuotono sulla mappa e viceversa
		@return a Set view of the mappings contained in this map
	*/
	public Set<Map.Entry<K,V>> entrySet() {
		Set<Map.Entry<K,V>> tmp = new MapAdapterEntrySet<Map.Entry<K,V>>(this);
		return tmp;
	}
	
	/**
		Compares the specified object with this map for equality
		@param o object to be compared for equality with this map
		@return true if the specified object is equal to this map
	*/
	public boolean equals(Object o) {
		if ( o.getClass() != this.getClass()) {
			return false;
		}
		MapAdapter<K,V> tmp = (MapAdapter<K,V>) o;
		if (this.size() != tmp.size()) {
			return false;
		}
		Iterator<K> iter = tmp.iterator();
		while (iter.hasNext()) {
			K k = iter.next();
			V v = tmp.get(k);
			if (!containsKey(k) || !containsValue(v)) {
				return false;
			}
		}
		return true;
	}
	
	/**
		Performs the given action for each entry in this map until all entries have been processed or the action throws an exception
		@param action The action to be performed for each entry
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public void forEach(BiConsumer<? super K,? super V> action) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key
		@param key the key whose associated value is to be returned
		@return the value to which the specified key is mapped, or null if this map contains no mapping for the key
		@throws ClassCastException if the key is of an inappropriate type for this map
		@throws NullPointerException if the specified key is null and this map does not permit null keys
	*/
	public V get(Object key) {
		if (key == null) {
			throw new NullPointerException();
		}
		return super.get(key);
	}
	
	/**
		Returns the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
		@param key the key whose associated value is to be returned
		@param defaultValue the default mapping of the key
		@return the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
		@throws ClassCastException if the key is of an inappropriate type for this map
		@throws NullPointerException if the specified key is null and this map does not permit null keys
	*/
	public V getOrDefault(Object key, V defaultValue) {
		if (key == null) {
			throw new NullPointerException();
		}
		V value = get(key);
		if (value == null) {
			return defaultValue;
		}
		return value;		
	}
	
	/**
		Returns the hash code value for this map. The hash code of a map is defined to be the sum of the hash codes of each entry in the map's entrySet() view
		@return the hash code value for this map
	*/
	public int hashCode() {
		int sum = 0;
		
		Iterator<K> iter = iterator();
		
		while(iter.hasNext()) {
			K key = iter.next();
			Map.Entry<K,V> e = new MapEntry<K,V>(key, get(key));
			sum = sum + e.hashCode();
		}
		return sum;
	}
	
	/**
		Returns true if this map contains no key-value mappings
		@return true if this map contains no key-value mappings
	*/
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	/**
		Returns a Set view of the keys contained in this map
		Modifiche al set si ripercuotono sulla mappa e viceversa
		@return a set view of the keys contained in this map
	*/
	public Set<K> keySet() {
		
		Enumeration<K> e = super.keys();
		
		return new MapAdapterKeySet<K>(this, e);
	}
	
	/**
		If the specified key is not already associated with a value or is associated with null, associates it with the given non-null value
		@param key key with which the resulting value is to be associated
		@param value the non-null value to be merged with the existing value associated with the key or, if no existing value or a null value is associated with the key, to be associated with the key
		@param remappingFunction the remapping function to recompute a value if present
		@return the new value associated with the specified key, or null if no value is associated with the key
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public V merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing zero mappings
		@return an empty map
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of() {
		throw new UnsupportedOperationException();
	} 
	
	/**
		Returns an immutable map containing a single mapping
		@param k1 the mapping's key
		@param v1 the mapping's value
		@return a Map containing the specified mapping
		@throws NullPointerException if the key or the value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing two mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if the keys are duplicates
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing three mappings. See Immutable Map Static Factory Methods for details
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing four mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing five mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@param k5 the fifth mapping's key
		@param v5 the fifth mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing six mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@param k5 the fifth mapping's key
		@param v5 the fifth mapping's value
		@param k6 the sixth mapping's key
		@param v6 the sixth mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing seven mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@param k5 the fifth mapping's key
		@param v5 the fifth mapping's value
		@param k6 the sixth mapping's key
		@param v6 the sixth mapping's value
		@param k7 the seventh mapping's key
		@param v7 the seventh mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing eight mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@param k5 the fifth mapping's key
		@param v5 the fifth mapping's value
		@param k6 the sixth mapping's key
		@param v6 the sixth mapping's value
		@param k7 the seventh mapping's key
		@param v7 the seventh mapping's value
		@param k8 the eighth mapping's key
		@param v8 the eighth mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing nine mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@param k5 the fifth mapping's key
		@param v5 the fifth mapping's value
		@param k6 the sixth mapping's key
		@param v6 the sixth mapping's value
		@param k7 the seventh mapping's key
		@param v7 the seventh mapping's value
		@param k8 the eighth mapping's key
		@param v8 the eighth mapping's value
		@param k9 the ninth mapping's key
		@param v9 the ninth mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing ten mappings
		@param k1 the first mapping's key
		@param v1 the first mapping's value
		@param k2 the second mapping's key
		@param v2 the second mapping's value
		@param k3 the third mapping's key
		@param v3 the third mapping's value
		@param k4 the fourth mapping's key
		@param v4 the fourth mapping's value
		@param k5 the fifth mapping's key
		@param v5 the fifth mapping's value
		@param k6 the sixth mapping's key
		@param v6 the sixth mapping's value
		@param k7 the seventh mapping's key
		@param v7 the seventh mapping's value
		@param k8 the eighth mapping's key
		@param v8 the eighth mapping's value
		@param k9 the ninth mapping's key
		@param v9 the ninth mapping's value
		@param k10 the tenth mapping's key
		@param v10 the tenth mapping's value
		@return a Map containing the specified mappings
		@throws IllegalArgumentException if there are any duplicate keys
		@throws NullPointerException - if any key or value is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns an immutable map containing keys and values extracted from the given entries
		@param entries Map.Entrys containing the keys and values from which the map is populated
		@return a Map containing the specified mappings
		@throws IllegalArgumentException - if there are any duplicate keys
		@throws NullPointerException - if any entry, key, or value is null, or if the entries array is null
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public static <K,V> Map<K,V> ofEntries(Map.Entry<? extends K,? extends V>... entries) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Associates the specified value with the specified key in this mapAssociates the specified value with the specified key in this map (optional operation). If the map previously contained a mapping for the key, the old value is replaced by the specified value
		@param key key with which the specified value is to be associated
		@param value value to be associated with the specified key
		@return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key, if the implementation supports null values.)
		@throws NullPointerException if the specified key or value is null and this map does not permit null keys or values
	*/
	public V put(K key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException();
		}
		return super.put(key, value);
	}
	
	/**
		Copies all of the mappings from the specified map to this map
		@param m mappings to be stored in this map
		@throws NullPointerException if the specified map is null, or if this map does not permit null keys or values, and the specified map contains null keys or values
	*/
	public void putAll(Map<? extends K,? extends V> m) {
		if (m == null) {
			throw new NullPointerException();
		}
		MapAdapter<K,V> map = (MapAdapter<K,V>) m;
		Object[] keys = map.keySet().toArray();
		for(int i = 0; i< keys.length; i++) {
			put((K)keys[i], map.get((K)keys[i]));
		}
	}
	
	/**
		If the specified key is not already associated with a value (or is mapped to null) associates it with the given value and returns null, else returns the current value
		@param key key with which the specified value is to be associated 
		@param value value to be associated with the specified key
		@return the previous value associated with the specified key, or null if there was no mapping for the key. (A null return can also indicate that the map previously associated null with the key, if the implementation supports null values.)
		@throws NullPointerException - if the specified key or value is null, and this map does not permit null keys or values
	*/
	public V putIfAbsent(K key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException();
		}
		if ( !(super.containsKey(key)) || super.get(key) == null ) {
			return put(key,value);
		}
		return super.get(key);
	}
	
	/**
		Removes the mapping for a key from this map if it is present
		@param key key whose mapping is to be removed from the map
		@return the previous value associated with key, or null if there was no mapping for key
		@throws NullPointerException if the specified key is null and this map does not permit null keys
	*/
	public V remove(Object key) {
		if (key == null) {
			throw new NullPointerException();
		}
		if ( ! super.containsKey(key) ) {
			return null;
		}
		return super.remove(key);
	}
	
	/**
		Removes the entry for the specified key only if it is currently mapped to the specified value
		@param key key with which the specified value is associated
		@param value value expected to be associated with the specified key
		@return true if the value was removed, false altrimenti
		@throws NullPointerException if the specified key or value is null, and this map does not permit null keys or values
	*/
	public boolean remove(Object key, Object value) {
		if (key == null || value == null) {
			throw new NullPointerException();
		}
		if ( super.containsKey(key) && (super.get(key)).equals(value) ) {
			super.remove(key);
			return true;
		}
		return false;
	}
	
	/**
		Replaces the entry for the specified key only if it is currently mapped to some value
		@param key key with which the specified value is associated
		@param value value to be associated with the specified key
		@return the previous value associated with the specified key, or null if there was no mapping for the key. (A null return can also indicate that the map previously associated null with the key, if the implementation supports null values.)
		@throws NullPointerException if the specified key or value is null, and this map does not permit null keys or values
	*/
	public V replace(K key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException();
		}
		if ( super.containsKey(key) ) {
			return super.put(key, value);
		}
		return null;
	}
	
	/**
		Replaces the entry for the specified key only if currently mapped to the specified value
		@param key key with which the specified value is associated
		@param oldValue value expected to be associated with the specified key
		@param newValue value to be associated with the specified key
		@return true if the value was replaced
		@throws NullPointerException if a specified key or newValue is null, and this map does not permit null keys or values
		@throws NullPointerException if oldValue is null and this map does not permit null values
	*/
	public boolean replace(K key, V oldValue, V newValue) {
		if (key == null || newValue == null || oldValue == null) {
			throw new NullPointerException();
		}
		if ( super.containsKey(key) && (super.get(key)).equals(oldValue) ) {
			super.put(key,newValue);
			return true;
		}
		return false;
	}
	
	/**
		Replaces each entry's value with the result of invoking the given function on that entry until all entries have been processed or the function throws an exception
		@param function the function to apply to each entry
		@throws NullPointerException if the specified function is null, or the specified replacement value is null, and this map does not permit null values
		@throws UnsupportedOperationException if the put operation is not supported by this map
	*/
	public void replaceAll(BiFunction<? super K,? super V,? extends V> function) {
		throw new UnsupportedOperationException();
	}
	
	/**
		Returns the number of key-value mappings in this map
		@return the number of key-value mappings in this map
	*/
	public int size() {
		return super.size();
	}
	
	/**
		Returns a Collection view of the values contained in this map
		Modifiche alla collection si ripercuotono sulla mappa e viceversa
		@return a collection view of the values contained in this map

	*/
	public Collection<V> values() {
		
		Enumeration<V> e = super.elements();
		
		return new MapAdapterValues<V>(this, e);
	}
	
	/**
		Returns an iterator over the elements in this Map
		@return an iterator over the elements in this Map
	*/
	public Iterator<K> iterator() {
		return new MapAdapterIterator<K>(this);
	}
	
	/**
		Return a Map Entry
		@param k chiave
		@param v valore
		@return un map entry con chiave k e valore v
	*/
	public Map.Entry<K,V> mapentry(K k, V v) {
		return new MapEntry<K,V>(k,v);
	}
	
	
	/**
		Classe MapEntry per mapentry()
	*/
	static class MapEntry<K,V> implements Map.Entry<K,V> {
		private K key;
		private V value;

		//costruttore
		public MapEntry(K k, V v) {
			key = k;
			value = v;
		}
		/**
			Compares the specified object with this entry for equality. Returns true if the given object is also a map entry and the two entries represent the same mapping
			@param o object to be compared for equality with this map entry
			@return true if the specified object is equal to this map entry
		*/
		public boolean equals(Object o) {
			if( !(o instanceof Map.Entry)) {
				return false;
			}
			Map.Entry tmp = (Map.Entry) o;
			if( key.equals(tmp.getKey()) && value.equals(tmp.getValue()) ) {
				return true;
			}
			return false;
		}
		/**
			Returns the value corresponding to this entry
			@return the value corresponding to this entry
		*/
		public V getValue() {
			return value;
		}
		/**
			Returns the key corresponding to this entry
			@return the key corresponding to this entry
		*/
		public K getKey(){
			return key;
		}
		/**
			Replaces the value corresponding to this entry with the specified value
			@param v new value to be stored in this entry
			@return old value corresponding to the entry
			@throws NullPointerException if the backing map does not permit null values, and the specified value is nul
		*/
		public V setValue(V v) {
			V tmp = value;
			value = v;
			return tmp;
		}
		/**
			Returns the hash code value for this map entry. The hash code of a map entry e is defined to be:
			(e.getKey()==null   ? 0 : e.getKey().hashCode()) ^	(e.getValue()==null ? 0 : e.getValue().hashCode())
			@return the hash code value for this map entry
		*/
		public int hashCode() {
			return (key.hashCode()) ^ (value.hashCode());
		}
	}
	
	
	class MapAdapterIterator<K> implements Iterator<K> {
		
		private Hashtable<K,V> h;
		//private Enumeration elem;
		private Object[] eArrayKeys;
		private int index;
		private boolean nextCheck = false;

		public MapAdapterIterator(Hashtable<K,V> e) {
			h = e;
			Enumeration elem = e.keys();
			eArrayKeys = new Object[e.size()];
			int i = 0;
			while(elem.hasMoreElements()) {
				eArrayKeys[i++] = elem.nextElement();
			}
			index = 0;
		}
		
		/**
			Returns true if the iteration has more elements
			@return true if the iteration has more elements
		*/
		public boolean hasNext() {
			return index < eArrayKeys.length;
		}
		
		/**
			Returns the next element in the iteration
			@return the next element in the iteration
			@throws NoSuchElementException if the iteration has no more elements
		*/
		public K next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			nextCheck = true;
			return (K) eArrayKeys[index++];
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
			Object tmp = eArrayKeys[index-1];
			Object[] aTmp = new Object[eArrayKeys.length-1];
			for (int i = 0; i < index-1; i++) {
				aTmp[i] = eArrayKeys[i];
			}
			for (int i = index; i < eArrayKeys.length; i++) {
				aTmp[i-1] = eArrayKeys[i];
			}			
			h.remove(tmp);
			eArrayKeys = aTmp;
			index--;
			nextCheck = false;
		}
		
		/**
			Performs the given action for each remaining element until all elements have been processed or the action throws an exception
		*/
		public void forEachRemaining(Consumer<? super K> action) {
			throw new UnsupportedOperationException();
		}
	}
	
	
	/**
		Classe MapAdapterKeySet per keySet()
	*/
	class MapAdapterKeySet<K> implements Set<K> {
		
		private MapAdapter<K,V> Map;
		private SetAdapter<K> KeySet;

		//costruttore
		public MapAdapterKeySet(MapAdapter<K,V> mappa, Enumeration<K> e) {
			
			Map = mappa;
			KeySet = new SetAdapter<K>();
			
			while(e.hasMoreElements()) {
				KeySet.add(e.nextElement());
			}			
		}
		
		/**
			@throws UnsupportedOperationException
		*/
		public boolean add(K e) {
			throw new UnsupportedOperationException();
		}
	
		/**
			@throws UnsupportedOperationException
		*/
		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}
	
		/**
			Rimuovi tutti gli elementi dal set e dalla map
		*/
		public void clear() {
			update();
			Map.clear();
			KeySet.clear();
		}
		
		/**
			Return true se il set contiene la chiave specificata
			@return true se il set contiene la chiave specificata
		*/
		public boolean contains(Object o) {
			update();
			return KeySet.contains(o);
		}
		
		/**
			Return true se il set contiene tutti gli elementi specificati dentro la collection
			@param c collection che contiene gli elementi da controllare
			@return true se il set contiene tutti gli elementi della collection
		*/
		public boolean containsAll(Collection<?> c) {
			update();
			return KeySet.containsAll(c);
		}
		
		/**
			Compara l'oggetto specificato con il set.
			@param o oggetto da comparare
			@return true se l'oggetto specificato è uguale al set
		*/
		public boolean equals(Object o) {
			update();
			return KeySet.equals(o);
		}
		
		/**
			Return dell' hashcode del set.
			@return hashcod della set.
		*/
		public int hashCode() {
			update();
			return KeySet.hashCode();
		}
		
		/**
			Return true se il set non contiene elementi
			@return true se il set non contiene elementi
		*/
		public boolean isEmpty() {
			update();
			return KeySet.isEmpty();
		}
		
		/**
			Return un iterator sugli elementi del set in sequenza
			@return un iterator sugli elementi del set in sequenza
		*/
		public Iterator<K> iterator() {
			update();
			return new MapAdapterKeySetIterator<K>(Map, KeySet);
		}
		
		/**
			Rimuovi l'elemento specificato dal set
			@param o elemento da rimuovere
			@return l'elemento specificato dal set
		*/
		public boolean remove(Object o) {
			update();
			Map.remove(o);
			return KeySet.remove(o);
		}
		
		/**
			Rimuovi dal set tutti gli elementi contenuti nella collection specificata
			@param c collection con gli elementi da rimuovere
			@return true se il set è cambiato dopo la chiamata di questo metodo
		*/
		public boolean removeAll(Collection<?> c) {
			update();
			CollectionAdapter<K> tmp = (CollectionAdapter<K>) c;
			Iterator<K> iter = tmp.iterator();
			while(iter.hasNext()) {
				Map.remove(iter.next());
			}
			return KeySet.removeAll(c);
		}
		
		/**
			Conserva solo gli elementi di questo set che sono anche contenuti nella collection. Rimuovi quindi quelli che non sono contenuti nella collection
			@param c collection contenente gli elementi da conservare
			@return true se il set è cambiata dopo la chiamata di questo metodo
		*/
		public boolean retainAll(Collection<?> c) {
			update();
			CollectionAdapter<K> e = (CollectionAdapter<K>) c;
			
			Iterator<K> iter = KeySet.iterator();
			while(iter.hasNext()) {
				K tmp = iter.next();
				if (! e.contains(tmp)) {
					Map.remove(tmp);
				}
			}
			return KeySet.retainAll(c);
		}
		
		/**
			Return il numero di elementi di questo set
			@return il numero di elementi di questo set
		*/
		public int size() {
			update();
			return KeySet.size();
		}
		
		/**
			Return un array contenente tutti gli elementi contenuti in questo set, in ordine di inserimento. 
			@return un array contenente tutti gli elementi contenuti in questo set, in ordine di inserimento.
		*/
		public Object[] toArray() {
			update();
			return KeySet.toArray();
		}
		
		/**
		Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array;
		*/
		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}
		
		/**
			Metodo per tenere aggiornato il set a seguito di modifiche apportate alla mappa
		*/
		private void update() {
			if (Map.isEmpty()) {
				KeySet.clear();
			} else {
				
				SetAdapter<K> tmp = new SetAdapter<K>();
				
				Iterator<K> iter = Map.iterator();
				while(iter.hasNext()) {
					tmp.add(iter.next());
				}
				KeySet = tmp;
			}
		}
		
		//Iterator di MapAdapterKeySet
		class MapAdapterKeySetIterator<K> implements Iterator<K> {
		
			private MapAdapter<K,V> Map;
			private SetAdapter<K> Set;
		
			private Object[] eArrayKeys;
			private int index;
			private boolean nextCheck = false;

			public MapAdapterKeySetIterator(MapAdapter<K,V> map, SetAdapter<K> set) {
				Map = map;
				Set = set;
				eArrayKeys = Set.toArray();
				index = 0;
			}
			
			/**
				Returns true if the iteration has more elements
				@return true if the iteration has more elements
			*/
			public boolean hasNext() {
				return index < eArrayKeys.length;
			}
			
			/**
				Returns the next element in the iteration
				@return the next element in the iteration
				@throws NoSuchElementException if the iteration has no more elements
			*/
			public K next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				nextCheck = true;
				return (K) eArrayKeys[index++];
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
				Object tmp = eArrayKeys[index-1];
				Map.remove(tmp);
				Object[] aTmp = new Object[eArrayKeys.length-1];
				for (int i = 0; i < index-1; i++) {
					aTmp[i] = eArrayKeys[i];
				}
				for (int i = index; i < eArrayKeys.length; i++) {
					aTmp[i-1] = eArrayKeys[i];
				}			
				Set.remove(tmp);
				eArrayKeys = aTmp;
				index--;
				nextCheck = false;
			}
			
			/**
				Performs the given action for each remaining element until all elements have been processed or the action throws an exception
			*/
			public void forEachRemaining(Consumer<? super K> action) {
				throw new UnsupportedOperationException();
			}
		}
	}
	
	
	/**
		Classe MapAdapterEntrySet per entrySet()
	*/
	class MapAdapterEntrySet<E> implements Set<E> {
		
		private MapAdapter<K,V> Mappa;
		private SetAdapter<Map.Entry<K,V>> Set;

		//costruttore
		public MapAdapterEntrySet(MapAdapter<K,V> mappa) {
			Mappa = mappa;
			Set = new SetAdapter<Map.Entry<K,V>>();
			Iterator<K> iter = Mappa.iterator();
			while (iter.hasNext()) {
				K key = iter.next();
				V value = Mappa.get(key);
				Map.Entry<K,V> tmp = new MapEntry<K,V>(key, value);
				Set.add((Map.Entry<K,V>)tmp);
			}
		}
		
		/**
			@throws UnsupportedOperationException
		*/
		public boolean add(E e) {
			throw new UnsupportedOperationException();
		}
	
		/**
			@throws UnsupportedOperationException
		*/
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
	
		/**
			Rimuovi tutti gli elementi dal set e dalla mappa
		*/
		public void clear() {
			update();
			Mappa.clear();
			Set.clear();
		}
		
		/**
			Return true se il set contiene l'entry specificato
			@return true se il set contiene l'entry specificato
			@throws NullPointerException se o = null
		*/
		public boolean contains(Object o) {
			update();
			if (o == null) {
				throw new NullPointerException();
			}
			//o è un MapEntry<K,V>
			Map.Entry<K,V> tmp = (MapEntry<K,V>) o;
			K k = tmp.getKey();
			V v = tmp.getValue();
			Iterator<Map.Entry<K,V>> iter = Set.iterator();
			while(iter.hasNext()) {
				Map.Entry<K,V> tmp2 = (MapEntry<K,V>) iter.next();
				if (tmp2.equals(tmp)) {
					return true;
				}
			}
			return false;
		}
		
		/**
			Return true se il set contiene tutti gli entry specificati dentro la collection
			@param c collection che contiene gli entry da controllare
			@return true se il set contiene tutti gli entry della collection
			@throws NullPointerException se c = null
		*/
		public boolean containsAll(Collection<?> c) {
			update();
			if (c == null) {
				throw new NullPointerException();
			}
			Iterator<?> iter = c.iterator();
			while(iter.hasNext()) {
				if (!contains(iter.next())) {
					return false;
				}
			}
			return true ;
		}
		
		/**
			Compara l'oggetto specificato con il set.
			@param o oggetto da comparare
			@return true se l'oggetto specificato è uguale al set
		*/
		public boolean equals(Object o) {
			update();
			if ( o.getClass() != this.getClass()) {
				return false;
			}
			Set<Map.Entry<K,V>> to = (Set<Map.Entry<K,V>>) o;
			if (Set.size() != to.size()) {
				return false;
			}
			if (!containsAll(to)) {
				return false;
			}
			return true;
		}
		
		/**
			Return dell' hashcode del set.
			@return hashcode della set.
		*/
		public int hashCode() {
			update();
			int sum = 0;
			Iterator<Map.Entry<K,V>> iter = Set.iterator();
			while(iter.hasNext()) {
				Map.Entry<K,V> tmp = (MapEntry<K,V>) iter.next();
				sum = sum + tmp.hashCode();
			}
			return sum;
		}
		
		/**
			Return true se il set non contiene entry
			@return true se il set non contiene entry
		*/
		public boolean isEmpty() {
			update();
			return Set.isEmpty();
		}
		
		/**
			Return un iterator sugli entry del set in sequenza
			@return un iterator sugli entry del set in sequenza
		*/
		public Iterator<E> iterator() {
			update();
			return new MapAdapterEntrySetIterator<E>(Mappa, Set);
		}
		
		/**
			Rimuovi l'entry specificato dal set
			@param o entry da rimuovere
			@return l'entry specificato dal set
			@throws NullPointerException se o = null
		*/
		public boolean remove(Object o) {
			update();
			if (o == null) {
				throw new NullPointerException();
			}
			Map.Entry<K,V> tmp = (MapEntry<K,V>) o;
			K k = tmp.getKey();
			V v = tmp.getValue();
			Mappa.remove(k, v);
			return Set.remove(o);
		}
		
		/**
			Rimuovi dal set tutti gli entry contenuti nella collection specificata
			@param c collection con gli entry da rimuovere
			@return true se il set è cambiato dopo la chiamata di questo metodo
			@throws NullPointerException se c = null
		*/
		public boolean removeAll(Collection<?> c) {
			update();
			if (c == null) {
				throw new NullPointerException();
			}
			CollectionAdapter<Map.Entry<K,V>> tmp = (CollectionAdapter<Map.Entry<K,V>>) c;
			Iterator<Map.Entry<K,V>> iter = tmp.iterator();
			while(iter.hasNext()) {
				Map.Entry<K,V> t = iter.next();
				K k = t.getKey();
				V v = t.getValue();
				Mappa.remove(k, v);
			}
			return Set.removeAll(c);
		}
		
		/**
			Conserva solo gli entry di questo set che sono anche contenuti nella collection. Rimuovi quindi quelli che non sono contenuti nella collection
			@param c collection contenente gli entry da conservare
			@return true se il set è cambiata dopo la chiamata di questo metodo
			@throws NullPointerException se c = null
		*/
		public boolean retainAll(Collection<?> c) {
			update();
			if (c == null) {
				throw new NullPointerException();
			}
			
			CollectionAdapter<Map.Entry<K,V>> e = (CollectionAdapter<Map.Entry<K,V>>) c;
			
			Iterator<Map.Entry<K,V>> iter = Set.iterator();
			while(iter.hasNext()) {
				Map.Entry<K,V> tmp = (Map.Entry<K,V>) iter.next();
				if (! e.contains(tmp)) {
					K k = tmp.getKey();
					V v = tmp.getValue();
					Mappa.remove(k, v);
				}
			}
			return Set.retainAll(c);
		}
		
		/**
			Return il numero di entry di questo set
			@return il numero di entry di questo set
		*/
		public int size() {
			update();
			return Set.size();
		}
		
		/**
			Return un array contenente tutti gli entry contenuti in questo set, in ordine di inserimento. 
			@return un array contenente tutti gli entry contenuti in questo set, in ordine di inserimento.
		*/
		public Object[] toArray() {
			update();
			return Set.toArray();
		}
		
		/**
			Returns un array contenente tutti gli entry in questo set
		*/
		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}
		
		/**
			Metodo per tenere aggiornato il set a seguito di modifiche apportate alla mappa
		*/
		private void update() {
			if (Mappa.isEmpty()) {
				Set.clear();
			} else {
				
				SetAdapter<Map.Entry<K,V>> tmp = new SetAdapter<Map.Entry<K,V>>();
				
				Iterator<K> iter = Mappa.iterator();
				while(iter.hasNext()) {
					K k = iter.next();
					V v = Mappa.get(k);
					Map.Entry<K,V> me = new MapEntry<K,V>(k,v);
					tmp.add(me);
				}
				Set = tmp;
			}
		}
		
		//Iterator di MapAdapterEntrySet
		class MapAdapterEntrySetIterator<E> implements Iterator<E> {
		
			private MapAdapter<K,V> Mappa;
			private SetAdapter<Map.Entry<K,V>> Set;
		
			private Object[] eArrayEntries;
			private int index;
			private boolean nextCheck;

			public MapAdapterEntrySetIterator(MapAdapter<K,V> mappa, SetAdapter<Map.Entry<K,V>> set) {
				Mappa = mappa;
				Set = set;
				eArrayEntries = Set.toArray();
				index = 0;
				nextCheck = false;
			}
			
			/**
				Returns true if the iteration has more elements
				@return true if the iteration has more elements
			*/
			public boolean hasNext() {
				return index < eArrayEntries.length;
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
				return (E) eArrayEntries[index++];
			}
			
			/**
				Removes from the underlying collection the last element returned by this iterator.This method can be called only once per call to next().
				@throws IllegalStateException if the next method has not yet been called, or the remove method has already been called after the last call to the next method
			*/
			public void remove() {
				if (!nextCheck) {
					throw new IllegalStateException();
				}
				// rimuovere l'entry returnato da next() devo tornare indietro di 1 con index
				Map.Entry<K,V> tmp = (Map.Entry<K,V>) eArrayEntries[index-1];
				K k = tmp.getKey();
				V v = tmp.getValue();
				Mappa.remove(k, v);
				Object[] aTmp = new Object[eArrayEntries.length-1];
				for (int i = 0; i < index-1; i++) {
					aTmp[i] = eArrayEntries[i];
				}
				for (int i = index; i < eArrayEntries.length; i++) {
					aTmp[i-1] = eArrayEntries[i];
				}
				eArrayEntries = aTmp;
				Set.remove(tmp);
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
	
	
	/**
		Classe MapAdapterValues per values()
		
	*/
	class MapAdapterValues<V> implements Collection<V> {
		
		private MapAdapter<K,V> Map;
		private CollectionAdapter<V> Coll;

		//costruttore
		public MapAdapterValues(MapAdapter<K,V> mappa, Enumeration<V> e) {
			
			Map = mappa;
			Coll = new CollectionAdapter<V>();
			
			while(e.hasMoreElements()) {
				Coll.add(e.nextElement());
			}			
		}
		
		/**
			@throws UnsupportedOperationException
		*/
		public boolean add(V e){
			throw new UnsupportedOperationException();
		}
		
		/**
			@throws UnsupportedOperationException
		*/
		public boolean addAll(Collection<? extends V> c) {
			throw new UnsupportedOperationException();
		}
		
		/**
			Rimuovi tutti gli elementi dalla collection e dalla map
		*/
		public void clear() {
			update();
			Map.clear();
			Coll.clear();
		}
		
		/**
			Return true se la collection contiene l'elemento specificato
			@return true se la collection contiene l'elemento specificato
		*/
		public boolean contains(Object o) {
			update();
			return Coll.contains(o);
		}
		
		/**
			Return true se la collection contiene tutti gli elementi specificati dentro la collection
			@param c collection che contiene gli elementi da controllare
			@return true se la collection contiene tutti gli elementi della collection
		*/
		public boolean containsAll(Collection<?> c) {
			update();
			return Coll.containsAll(c);		
		}
		
		/**
			Compara l'oggetto specificato con la collection.
			@param o oggetto da comparare
			@return true se l'oggetto specificato è uguale alla collection
		*/
		public boolean equals(Object o) {
			update();
			return Coll.equals(o);
		}
		
		/**
			Return hashcode della collection.
			@return hashcode della collection.
		*/
		public int hashCode() {
			update();
			return Coll.hashCode();
		}
		
		/**
			Return true se la collection non contiene elementi
			@return true se la collection non contiene elementi
		*/
		public boolean isEmpty() {
			update();
			return Coll.isEmpty();
		}
		
		/**
			Return un iterator sugli elementi della collection in sequenza
			@return un iterator sugli elementi della collection in sequenza
		*/
		public Iterator<V> iterator() {
			return new CollectionAdapterValuesIterator<V>(Map, Coll);
		}
		
		/**
			Rimuovi l'elemento specificato dalla collection
			@param o elemento da rimuovere
			@return l'elemento specificato dalla collection
		*/
		public boolean remove(Object o) {
			update();
			Set<K> s = Map.keySet();
			Iterator<K> iter = s.iterator();
			while(iter.hasNext()) {
				K k = iter.next();
				if ((Map.get(k)).equals(o)) {
					Map.remove(k);
					break;
				}
			}
			return Coll.remove(o);
		}
		
		/**
			Rimuovi dalla collection tutti gli elementi contenuti nella collection specificata
			@param c collection con gli elementi da rimuovere
			@return true se la collection è cambiato dopo la chiamata di questo metodo

		*/
		public boolean removeAll(Collection<?> c) {
			update();
			CollectionAdapter<V> tmp = (CollectionAdapter<V>) c;
			Set<K> s = Map.keySet();
			Iterator<K> iter = s.iterator();
			while(iter.hasNext()) {
				K k = iter.next();
				if (tmp.contains(Map.get(k))) {
					Map.remove(k);
				}
			}
			return Coll.removeAll(c);
		}
		
		/**
			Conserva solo gli elementi di questa collection che sono anche contenuti nella collection specificata. Rimuovi quindi quelli che non sono contenuti nella collection specificata
			@param c collection contenente gli elementi da conservare
			@return true se la collection è cambiata dopo la chiamata di questo metodo
		*/
		public boolean retainAll(Collection<?> c) {
			update();
			CollectionAdapter<V> tmp = (CollectionAdapter<V>) c;
			Set<K> s = Map.keySet();
			Iterator<K> iter = s.iterator();
			while(iter.hasNext()) {
				K k = iter.next();
				if (! tmp.contains(Map.get(k))) {
					Map.remove(k);
				}
			}
			return Coll.retainAll(c);
		}
		
		/**
			Return il numero di elementi di questa collection
			@return il numero di elementi di questa collection
		*/
		public int size() {
			update();
			return Coll.size();
		}
		
		/**
			Return un array contenente tutti gli elementi contenuti in questa collection, in ordine di inserimento. 
			@return un array contenente tutti gli elementi contenuti in questa collection, in ordine di inserimento
		*/
		public Object[] toArray() {
			update();
			return Coll.toArray();
		}
		
		/**
			@throws UnsupportedOperationException
		*/
		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}
		
		/**
			Metodo per tenere aggiornato la collection a seguito di modifiche apportate alla mappa
		*/
		private void update() {
			if (Map.isEmpty()) {
				Coll.clear();
			} else {
				
				CollectionAdapter<V> tmp = new CollectionAdapter<V>();
				
				Iterator<K> iter = Map.iterator();
				while(iter.hasNext()) {
					tmp.add(Map.get(iter.next()));
				}
				Coll = tmp;
			}
		}
		
		//Iterator di CollectionAdapterValues
		class CollectionAdapterValuesIterator<V> implements Iterator<V> {
			
			private MapAdapter<K,V> Map;
			private CollectionAdapter<V> Coll;
		
			private Object[] eArrayValues;
			private int index;
			private boolean nextCheck = false;

			public CollectionAdapterValuesIterator(MapAdapter<K,V> map, CollectionAdapter<V> coll) {
				Map = map;
				Coll = coll;
				eArrayValues = Coll.toArray();
				index = 0;
			}
			
			/**
				Returns true if the iteration has more elements
				@return true if the iteration has more elements
			*/
			public boolean hasNext() {
				return index < eArrayValues.length;
			}
			
			/**
				Returns the next element in the iteration
				@return the next element in the iteration
				@throws NoSuchElementException if the iteration has no more elements
			*/
			public V next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				nextCheck = true;
				return (V) eArrayValues[index++];
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
				Object tmp = eArrayValues[index-1];
				
				Set<K> s = Map.keySet();
				Iterator<K> iter = s.iterator();
				while(iter.hasNext()) {
					K k = iter.next();
					if ((Map.get(k)).equals(tmp)) {
						Map.remove(k);
						break;
					}
				}
				Object[] aTmp = new Object[eArrayValues.length-1];
				for (int i = 0; i < index-1; i++) {
					aTmp[i] = eArrayValues[i];
				}
				for (int i = index; i < eArrayValues.length; i++) {
					aTmp[i-1] = eArrayValues[i];
				}			
				Coll.remove(tmp);
				eArrayValues = aTmp;
				index--;
				nextCheck = false;
			}
			
			/**
				Performs the given action for each remaining element until all elements have been processed or the action throws an exception
			*/
			public void forEachRemaining(Consumer<? super V> action) {
				throw new UnsupportedOperationException();
			}
		}
	}
}