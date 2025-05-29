import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected SkipListNode head;
    final protected SkipListNode tail;
    protected int size = 0;

    public AbstractSkipList() {
        head = new SkipListNode(Integer.MIN_VALUE);
        tail = new SkipListNode(Integer.MAX_VALUE);
        increaseHeight();
    }

    public void increaseHeight() {
        head.addLevel(tail, null,size + 1);
        tail.addLevel(null, head,0);
    }
	
    abstract void decreaseHeight();

    abstract SkipListNode find(int key);

    abstract int generateHeight();

    public SkipListNode search(int key) {
        SkipListNode curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public SkipListNode insert(int key) {
        int nodeHeight = generateHeight();

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        SkipListNode prevNode = find(key);
        if (prevNode.key() == key) {
            return null;
        }

        SkipListNode newNode = new SkipListNode(key);
        int space = 1;
        int level;
        for (level = 0; level <= nodeHeight && prevNode != null; ++level) {
            SkipListNode nextNode = prevNode.getNext(level);
            newNode.addLevel(nextNode, prevNode,prevNode.getSpace(level) - space + 1);
            prevNode.setNext(level, newNode);
            prevNode.setSpace(level,space);
            nextNode.setPrev(level, newNode);

            while (prevNode != head && prevNode.height() == level) {
                prevNode = prevNode.getPrev(level);
                space = space + prevNode.getSpace(level);
            }
        }
        while (prevNode != null) {
            for (int i = level; i <= prevNode.height(); ++i) {
                prevNode.setSpace(i, prevNode.getSpace(i) + 1);
            }
            level = prevNode.height() + 1;
            prevNode = prevNode.getPrev(prevNode.height());
        }
        size = size + 1;
        return newNode;
    }

    public boolean delete(SkipListNode skipListNode) {
        if(skipListNode==null)
            return false;
        int level;
        SkipListNode previous = null, next = null;

        for ( level = 0; level <= skipListNode.height(); ++level) {
            previous = skipListNode.getPrev(level);
            next = skipListNode.getNext(level);


            previous.setNext(level, next);
            previous.setSpace(level, previous.getSpace(level) + skipListNode.getSpace(level) - 1);
            next.setPrev(level, previous);
        }
        while (previous != null) {
            for (int j = level; j <= previous.height(); ++j) {
                previous.setSpace(j, previous.getSpace(j) - 1);
            }
            level = previous.height() + 1;
            previous = previous.getPrev(previous.height());
        }

        size = size - 1;
        return true;
    }

    public SkipListNode predecessor(SkipListNode skipListNode) {
        return skipListNode.getPrev(0);
    }

    public SkipListNode successor(SkipListNode skipListNode) {
        return skipListNode.getNext(0);
    }

    public SkipListNode minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0);
    }

    public SkipListNode maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0);
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        SkipListNode curr = head.getNext(0);

        while (curr != tail) {
            if (curr.height >= level) {
                s.append(curr.key());
                s.append("    ");
            }
            else {
            	s.append("    ");
            	for (int i = 0; i < curr.key().toString().length(); i = i + 1)
            		s.append(" ");
            }

            curr = curr.getNext(0);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class SkipListNode extends Element<Integer, Object> {
        final private List<SkipListNode> next;
        final private List<SkipListNode> prev;
        final private List<Integer> space;
        private int height;

        public SkipListNode(int key) {
        	super(key);
            next = new ArrayList<>();
            prev = new ArrayList<>();
            space = new ArrayList<>();
            this.height = -1;
            
        }

        public SkipListNode getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public SkipListNode getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, SkipListNode next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, SkipListNode prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(SkipListNode next, SkipListNode prev,int size) {
            ++height;
            this.space.add(size);
            this.next.add(next);
            this.prev.add(prev);
        }
        public int getSpace(int level) {
            return this.space.get(level);
        }
        public void setSpace(int level, int space) {
            this.space.set(level, space);
        }


        public void removeLevel() {
            this.next.remove(height);
            this.prev.remove(height);
            --height;
        }

        public int height() { return height; }
    }
}
