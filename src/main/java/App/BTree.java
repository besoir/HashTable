package App;

public class BTree {
    private int nodeCount = 0;
    private int height = 0;
    private final int MAX = 4;
    private Node root;
    private class Node {
        int numChildren;
        Entry[] children = new Entry[MAX];

        public Node(int k) {
            numChildren = k;
        }
    }

    private class Entry {
        String name, url;
        Node next;

        public Entry(String n, String u, Node x) {
            name = n;
            url = u;
            next = x;
        }
    }

    public BTree() {
        root = new Node(0);
    }

    //method to add an element
    public void add(String k, String ur) {
        if(k == null) throw new IllegalArgumentException("add() needs a real value");
    }

    public Node get(String key, String ur) {
        if(key == null) throw new IllegalArgumentException("get() needs a real value");
        Node in = insert(key, ur, root, height);
    }

    private Node insert(String k, String u, Node curr, int ht) {
        int cow = 0;
        Entry e = new Entry(k, u, null);

        //if this is a leaf node we want to place it in the right spot in the children
        if(ht == 0) {
            for(cow = 0; cow < curr.numChildren; cow++) {
                if(k.compareTo(curr.children[cow].name) < 0) break;
            }
        }

        for(int i = curr.numChildren; i > cow; i--)
            curr.children[i] = curr.children[i--];
        curr.children[cow] = e;
        curr.numChildren++;
        if(curr.numChildren < MAX) return null;
        else return split(curr);
    }

    //method to search for an element
    //private Node search(String eKey, Node curr) {
        //if()
    //}



    //splits a two node into a one node with two children
    private Node split(Node q) {
        Node pew = new Node(MAX/2);
        pew.numChildren = MAX/2;
        for(int i = 0; i < MAX/2; i++) {
            pew.children[i] = q.children[MAX/2+i];
        }
        return pew;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodeCount;
    }
}
