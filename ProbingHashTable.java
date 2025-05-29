import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Element<K,V>[] table;
    private int size = 0;

    /*
     * You should add additional private fields as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new Element[capacity];

    }
	
	public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public V search(K key) {
        int index = hashFunc.hash(key);
        for(int i = 0;i<capacity;i++){
            Element<K,V> e = table[index];
            if (e == null) {
                return null;
            }
            if (e.key() != null && e.key().equals(key)) {
                return e.satelliteData();
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    public void insert(K key, V value) {
        int j = hashFunc.hash(key);
        for (int i = 0; i < capacity; i++) {
            if (table[j] == null) {
                table[j] = new Element<K, V>(key, value);
                size++;
                rehash();
                break;
            }
            j = (j + 1) % capacity;
        }

    }
    private void rehash(){
        if((double)size / capacity > maxLoadFactor){
            int baseHash = capacity;
            int pow = 1;
            while (baseHash != 2) {
                baseHash = baseHash >> 1;
                pow++;
            }
            hashFunc = hashFactory.pickHash(pow + 1);
            capacity = capacity * 2;
            Element<K,V>[] tempTable = table;
            table = new Element[capacity];
            size = 0;
            for(Element<K,V> element: tempTable){
                if(element.key() != null) {
                    insert(element.key(), element.satelliteData());
                }
            }
        }
    }

    public boolean delete(K key) {
        int j = hashFunc.hash(key);
        for(int i =0;i<capacity;i++){
            if( table[j] != null && table[j].key() != null &&table[j].key().equals(key)) {
                Element<K,V> element = new Element<>(null);
                table[j] = element;
                size--;
                return true;
            }
            j = (j + 1) % capacity;
        }
        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
