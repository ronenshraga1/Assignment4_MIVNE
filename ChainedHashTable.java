import java.util.List;
import java.util.ArrayList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private List<Element<K,V>>[] table;
    private int size;

    /*
     * You should add additional private fields as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new List[this.capacity];

    }

    public V search(K key) {
        int index = hashFunc.hash(key);
        List<Element<K,V>> list = table[index];
        V value = null;
        for(Element<K,V> elem:list){
            if(elem.key().equals(key)){
                value = elem.satelliteData();
            }
        }
        return value;
    }

    public void insert(K key, V value) {
        int index = hashFunc.hash(key);
        List<Element<K,V>> list;
        if(table[index] == null){
            list = new ArrayList<>();
        } else{
            list = table[index];
        }
        list.add(new Element<K,V>(key,value));
        table[index] = list;
        size++;
        rehash();

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
            List<Element<K, V>>[] tempTable = table;
            table = new List[capacity];
            size = 0;
            for(int i =0;i<capacity /2;i++){
                if(tempTable[i] != null){
                    for (Element<K, V> element : tempTable[i])
                        insert(element.key(), element.satelliteData());
                }
            }
        }
    }

    public boolean delete(K key) {
        int index = hashFunc.hash(key);
        List<Element<K,V>> list= table[index];
        if(list == null){
            return false;
        } else{
            for(int i =0;i<list.size();i++){
                if(list.get(i).key().equals(key)){
                    size--;
                    list.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
