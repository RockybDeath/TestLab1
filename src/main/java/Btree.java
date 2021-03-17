public class Btree {
    private int T;
    private Node root = null;
    public Btree(int t) {
        T = t;
        root = new Node(t);
        root.setN(0);
        root.setLeaf(true);
    }

    public Node getRoot() {
        return root;
    }

    private Node search(Node x, int key) {
        int i = 0;
        if (x == null)
            return x;
        for (i = 0; i < x.getN(); i++) {
            if (key < x.getKey()[i]) {
                break;
            }
            if (key == x.getKey()[i]) {
                return x;
            }
        }
        if (x.getLeaf()) {
            return null;
        } else {
            return search(x.getChild()[i], key);
        }
    }
    public boolean contain(int k) {
        if (this.search(root, k) != null) {
            return true;
        } else {
            return false;
        }
    }
    public void remove(int key) {
        Node x = search(root, key);
        if (x == null) {
            return;
        }
        remove(root, key);
    }
    private void remove(Node x, int key) {
        int pos = x.Find(key);
        if (pos != -1) {
            if (x.getLeaf()) {
                int i = 0;
                for (i = 0; i < x.getN() && x.getKey()[i] != key; i++) {
                }
                for (; i < x.getN(); i++) {
                    if (i != 2 * T - 2) {
                        x.setKeyOnIndex(i, x.getKey()[i+1]);
                    }
                }
                x.setN(x.getN()-1);
                return;
            }
            if (!x.getLeaf()) {
                Node pred = x.getChild()[pos];
                int predKey = 0;
                if (pred.getN() >= T) {
                    for (;;) {
                        if (pred.getLeaf()) {
                            predKey = pred.getKey()[pred.getN() - 1];
                            break;
                        } else {
                            pred = pred.getChild()[pred.getN()];
                        }
                    }
                    remove(pred, predKey);
                    x.setKeyOnIndex(pos, predKey);
                    return;
                }
                Node nextNode = x.getChild()[pos + 1];
                if (nextNode.getN() >= T) {
                    int nextKey = nextNode.getKey()[0];
                    if (!nextNode.getLeaf()) {
                        nextNode = nextNode.getChild()[0];
                        for (;;) {
                            if (nextNode.getLeaf()) {
                                nextKey = nextNode.getKey()[nextNode.getN() - 1];
                                break;
                            } else {
                                nextNode = nextNode.getChild()[nextNode.getN()];
                            }
                        }
                    }
                    remove(nextNode, nextKey);
                    x.setKeyOnIndex(pos, nextKey);
                    return;
                }
                int temp = pred.getN() + 1;
                pred.setKeyOnIndex(pred.getN(),x.getKey()[pos]);
                pred.setN(pred.getN()+1);
                for (int i = 0, j = pred.getN(); i < nextNode.getN(); i++) {
                    pred.setKeyOnIndex(j++,nextNode.getKey()[i]);
                    pred.setN(pred.getN()+1);
                }
                for (int i = 0; i < nextNode.getN() + 1; i++) {
                    pred.setChildOnIndex(temp++,nextNode.getChild()[i]);
                }
                x.setChildOnIndex(pos, pred);
                for (int i = pos; i < x.getN(); i++) {
                    if (i != 2 * T - 2) {
                        x.setKeyOnIndex(i, x.getKey()[i+1]);
                    }
                }
                for (int i = pos + 1; i < x.getN() + 1; i++) {
                    if (i != 2 * T - 1) {
                        x.setChildOnIndex(i, x.getChild()[i+1]);
                    }
                }
                x.setN(x.getN()-1);
                if (x.getN() == 0) {
                    if (x == root) {
                        root = x.getChild()[0];
                    }
                    x = x.getChild()[0];
                }
                remove(pred, key);
                return;
            }
        } else {
            for (pos = 0; pos < x.getN(); pos++) {
                if (x.getKey()[pos] > key) {
                    break;
                }
            }
            Node tmp = x.getChild()[pos];
            if (tmp.getN() >= T) {
                remove(tmp, key);
                return;
            }
            if (true) {
                Node nb = null;
                int devider = -1;
                if (pos != x.getN() && x.getChild()[pos + 1].getN() >= T) {
                    devider = x.getKey()[pos];
                    nb = x.getChild()[pos + 1];
                    x.setKeyOnIndex(pos, nb.getKey()[0]);
                    tmp.setKeyOnIndex(tmp.getN()+1, devider);
                    tmp.setN(tmp.getN()+1);
                    tmp.setChildOnIndex(tmp.getN(), nb.getChild()[0]);
                    for (int i = 1; i < nb.getN(); i++) {
                        nb.setKeyOnIndex(i-1, nb.getKey()[i]);
                    }
                    for (int i = 1; i <= nb.getN(); i++) {
                        nb.setChildOnIndex(i-1, nb.getChild()[i]);
                    }
                    nb.setN(nb.getN()-1);
                    remove(tmp, key);
                    return;
                } else if (pos != 0 && x.getChild()[pos - 1].getN() >= T) {
                    devider = x.getKey()[pos - 1];
                    nb = x.getChild()[pos - 1];
                    x.setKeyOnIndex(pos-1, nb.getKey()[nb.getN()-1]);
                    Node child = nb.getChild()[nb.getN()];
                    nb.setN(nb.getN()-1);
                    for (int i = tmp.getN(); i > 0; i--) {
                        tmp.setKeyOnIndex(i, tmp.getKey()[i-1]);
                    }
                    tmp.setKeyOnIndex(0, devider);
                    for (int i = tmp.getN() + 1; i > 0; i--) {
                        tmp.setChildOnIndex(i, tmp.getChild()[i-1]);
                    }
                    tmp.setChildOnIndex(0, child);
                    tmp.setN(tmp.getN()+1);
                    remove(tmp, key);
                    return;
                } else {
                    Node lt = null;
                    Node rt = null;
                    boolean last = false;
                    if (pos != x.getN()) {
                        devider = x.getKey()[pos];
                        lt = x.getChild()[pos];
                        rt = x.getChild()[pos + 1];
                    } else {
                        devider = x.getKey()[pos - 1];
                        rt = x.getChild()[pos];
                        lt = x.getChild()[pos - 1];
                        last = true;
                        pos--;
                    }
                    for (int i = pos; i < x.getN() - 1; i++) {
                        x.setKeyOnIndex(i, x.getKey()[i+1]);
                    }
                    for (int i = pos + 1; i < x.getN(); i++) {
                        x.setChildOnIndex(i, x.getChild()[i+1]);
                    }
                    x.setN(x.getN()-1);
                    lt.setKeyOnIndex(lt.getN(), devider);
                    lt.setN(lt.getN()+1);
                    for (int i = 0, j = lt.getN(); i < rt.getN() + 1; i++, j++) {
                        if (i < rt.getN()) {
                            lt.setKeyOnIndex(j, rt.getKey()[i]);
                        }
                        lt.setChildOnIndex(j, rt.getChild()[i]);
                    }
                    lt.setN(lt.getN()+rt.getN());
                    if (x.getN()== 0) {
                        if (x == root) {
                            root = x.getChild()[0];
                        }
                        x = x.getChild()[0];
                    }
                    remove(lt, key);
                    return;
                }
            }
        }
    }
    private void split(Node x, int pos, Node y) {
        Node z = new Node(this.T);
        z.setLeaf(y.getLeaf());
        z.setN(T-1);
        for (int j = 0; j < T - 1; j++) {
            z.setKeyOnIndex(j, y.getKey()[j+T]);
        }
        if (!y.getLeaf()) {
            for (int j = 0; j < T; j++) {
                z.setChildOnIndex(j, y.getChild()[j+T]);
            }
        }
        y.setN(T-1);
        for (int j = x.getN(); j >= pos + 1; j--) {
            x.setChildOnIndex(j+1, x.getChild()[j]);
        }
        x.setChildOnIndex(pos+1, z);
        for (int j = x.getN() - 1; j >= pos; j--) {
            x.setKeyOnIndex(j+1, x.getKey()[j]);
        }
        x.setKeyOnIndex(pos, y.getKey()[T-1]);
        x.setN(x.getN()+1);
    }
    public void insert(final int key) {
        Node r = root;
        if (r.getN() == 2 * T - 1) {
            Node s = new Node(this.T);
            root = s;
            s.setLeaf(false);
            s.setN(0);
            s.setChildOnIndex(0, r);
            split(s, 0, r);
            insertValue(s, key);
        } else {
            insertValue(r, key);
        }
    }
    private void insertValue(Node x, int k) {
        if (x.getLeaf()) {
            int i = 0;
            for (i = x.getN() - 1; i >= 0 && k < x.getKey()[i]; i--) {
                x.setKeyOnIndex(i+1, x.getKey()[i]);
            }
            x.setKeyOnIndex(i+1, k);
            x.setN(x.getN()+1);
        } else {
            int i = 0;
            for (i = x.getN() - 1; i >= 0 && k <= x.getKey()[i]; i--) {
            }
            i++;
            Node tmp = x.getChild()[i];
            if (tmp.getN() == 2 * T - 1) {
                split(x, i, tmp);
                if (k > x.getKey()[i]) {
                    i++;
                }
            }
            insertValue(x.getChild()[i], k);
        }

    }
    public void clear(){
        root = new Node(T);
        root.setN(0);
        root.setLeaf(true);
    }
    public boolean show() {
        if(root == null) return false;
        if(root.getN() == 0) return false;
        else {
            int j = 0;
            show(root, j);
            return true;
        }
    }
    private void show(Node x, int j) {
        System.out.print("This is root - "+j+".||| ");
        for (int i = 0; i < x.getN(); i++) {
            System.out.print(x.getKey()[i] + " ");
        }
        System.out.println("");
        j++;
        if (!x.getLeaf()) {
            for (int i = 0; i < x.getN() + 1; i++) {
                show(x.getChild()[i], j);
            }
        }
    }
}
