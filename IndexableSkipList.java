public class IndexableSkipList extends AbstractSkipList {
    final protected double p;	// p is the probability for "success" in the geometric process generating the height of each node.
    public IndexableSkipList(double probability) {
        super();
        this.p = probability;
    }
	
	@Override
    public void decreaseHeight() {
        head.removeLevel();
        tail.removeLevel();
    }

    @Override
    public SkipListNode find(int key) {
        SkipListNode p = head;
        for (int i =head.height();i>=0;i--){
            while (p.getNext(i) != null && p.getNext(i).key() <= key)
                p = p.getNext(i);
        }
        return p;
    }

    @Override
    public int generateHeight() {
        int height = 0;
        while (Math.random() >= this.p) {
            height++;
        }
        return height;
    }

    public int rank(int key) {
        SkipListNode current = head;
        int rankCount  = 0;
        for (int i = head.height(); i >= 0; i--) {
            while (current.getNext(i) != null && current.getNext(i).key() < key) {
                rankCount += current.getSpace(i);
                current = current.getNext(i);
            }
        }
        return rankCount;
    }

    public int select(int index) {
        SkipListNode current = head;
        index = index + 1;
        for(int i = head.height();i>=0;i--){
            while (index >= current.getSpace(i)){
                index = index -current.getSpace(i);
                current = current.getNext(i);
            }
        }
        return current.key();
    }

}
