package com.snehit.datastructure;

/**
 * 
 * @author snehitgajjar
 *
 * @param <K> key can be any datatype defined by user
 * @param <V> any datatype defined by user
 */
public class HashMap<K,V> {
	
	static final int DEFAULT_SIZE=16;
	
	static float DEFAULT_LOAD_FACTOR = 0.75f;
	
	private int thresold;
	final float loadfactor;
	
	transient Entry<K,V> buckets[];
	
	public int size;
	
	/**
	 * 
	 * @author snehitgajjar
	 *
	 *	This class keeps track of key, pair value and linked list of same
	 *
	 * @param <K>
	 * @param <V>
	 */
	static class Entry<K,V> {
		
		K key;
		V value;
		Entry<K,V> next;
		
		public Entry(){}
		public Entry(K key, V value){
			this.key = key;
			this.value=value;
		}
		
	}
	
	public HashMap(){
		this(DEFAULT_SIZE,DEFAULT_LOAD_FACTOR);
	}
	
	
	
	public HashMap(int size, float loadFactor){
		
		if(size<0)
			throw new IllegalArgumentException("Size must be greater than zero");
		
		if(!(loadFactor>0 && loadFactor<1) && loadFactor!=Float.NaN)
			throw new IllegalArgumentException("Load factor must be in between 0 to 1");
		
		if(size ==0)
			size =1;
		
			
		buckets= (Entry<K,V>[])new Entry[size];
		this.loadfactor = loadFactor;
		
		thresold = (int)(size *loadFactor);
		
	}
	
	/**
	 * 
	 * @param key key for entry
	 * @param value value for entry
	 * @return returns existing value back
	 */
	public V put(K key, V value){
		
		int index = hash(key);
		
		Entry<K,V> entry = buckets[index];
			
		while(entry!=null){
			
			if(entry.key.equals(key)){
				V v = entry.value;
				entry.value = value;
				return v;
			}
			else{
				
				entry = entry.next;	
			}
			
			
		}
		
		
		if(++size> thresold){
			
			rehash();
			index = hash(key);
		}
		
		addEntry( key,value ,index);
		
		
		return value;
	}
	
	
	
	/**
	 * 
	 * @param key key object to find value
	 * @return returns appropriate value to key otherwise null
	 */
	public V get(K key){
		
		int index = hash(key);
		
		Entry<K,V> e = buckets[index];
		
		while(e!=null){
			
			if(e.key.equals(key)){
				return e.value;
			}
			
			e = e.next;
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	private void addEntry(K key, V value, int index) {
		
			Entry<K,V> entry = new Entry<K,V>(key,value);
			entry.next = buckets[index];
			buckets[index] = entry;
		
	}



	private void rehash() {
		
		Entry<K,V>[] oldEntry = buckets;	
		
		int newcapacity = (buckets.length * 2) +1;
		
		thresold = (int )(newcapacity * loadfactor);
		
		buckets =(Entry<K,V>[]) new Entry[newcapacity];
		
		
		for(int i=0;i<buckets.length;i++){
			
			Entry<K,V> e = oldEntry[i];
			
			while(e!=null){
				int index = hash(e.key);
				Entry<K,V> e1 = buckets[index];
				Entry<K,V> next = e.next;
				e.next = buckets[index];
				buckets[index] = e;
				e = next;
			}
			
		}
		
	}



	public int hash(Object key){
		return key==null ? 0 : Math.abs(key.hashCode() % buckets.length);
	}

}
