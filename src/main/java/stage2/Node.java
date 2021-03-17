package stage2;

public class Node {
    private int n;
    private int key[];
    private Node child[];
    private boolean leaf = true;
    public Node(int t){
        this.key = new int[2 * t - 1];
        this.child = new Node[2*t];
    }

    public void setKey(int[] key) {
        this.key = key;
    }

    public int Find(int k) {
        for (int i = 0; i < this.n; i++) {
            if (this.key[i] == k) {
                return i;
            }
        }
        return -1;
    }

    public int[] getKey() {
        return key;
    }

    public int getN() {
        return n;
    }

    public Node[] getChild() {
        return child;
    }
    public boolean getLeaf(){
        return this.leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    public void setKeyOnIndex(int index, int value){
        this.key[index] = value;
    }
    public void setChildOnIndex(int index, Node node){
        this.child[index] = node;
    }
    public void setN(int n) {
        this.n = n;
    }
}
