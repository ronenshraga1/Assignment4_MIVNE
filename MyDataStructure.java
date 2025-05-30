import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {
    /*
     * You may add any fields that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */
    final private IndexableSkipList skiplist;
    final private ProbingHashTable<Integer,AbstractSkipList.SkipListNode> table;
    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items that may reside in the DS.
     */
    public MyDataStructure(int N) {
        skiplist = new IndexableSkipList(0.5);
        int base =N;
        int pow = 1;
        while (base != 1){
            base = base / 2;
            pow++;
        }
        table = new ProbingHashTable<>(new ModularHash(), pow, 0.75);
    }
    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */

    /***
     * The functions search in the skiplist if the node exists which takes expected ϴ(log(n))
     * if the value exists returns false and does not insert
     * if the value does not exists it insert the value to the skip list and hash table which.
     * the insertion takes θ(log(n)) expected
     */
    public boolean insert(int value) {
        AbstractSkipList.SkipListNode node = skiplist.find(value);
        if(node != null && node.key() != null &&node.key().equals(value)){
            return false;
        } else{
            node = skiplist.insert(value);
            table.insert(value,node);
            return true;
        }
    }

    /***
     * the functions search if the key exists in table with expected θ(1)
     * if does not exist return false
     * if it exists it deletes it both from table and skiplist.
     * table delete is expected θ(1)
     * skip list delete is expected θ(log(n)) (because of the search again)
     * total is expected θ(log(n))
     */
    public boolean delete(int value) {
       AbstractSkipList.SkipListNode node = table.search(value);
       if(node == null){
           return false;
       } else{
           table.delete(value);
           skiplist.delete(skiplist.search(value));
           return true;
       }
    }

    /***
     * returns if item exists or not in hash table
     * expected is θ(1)
     */
    public boolean contains(int value) {
        if(table.search(value) != null){
            return true;
        } else{
            return false;
        }
    }
    /***
     * return the index of the value in the skip list rank function
     * expected is θ(log(n))
     */
    public int rank(int value) {
        int rank = skiplist.rank(value);
        return rank -1;
    }
    /***
     * return the value key in the skip list select function
     * expected is θ(log(n))
     */

    public int select(int index) {
        return skiplist.select(index);
    }

    /***
     * search for lowest node in hash table with expected θ(1)
     * if does not exist return null
     * otherwise, create empty list and searches for all nodes in level 0 from low to high
     * in total it taks θ(|L|)
     *
     */
    public List<Integer> range(int low, int high) {
        AbstractSkipList.SkipListNode node = table.search(low);
        if(node == null){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        while (node!= null && node.key() != null &&node.key()<=high){
            list.add(node.key());
            node = node.getNext(0);
        }
        return list;
    }
}
