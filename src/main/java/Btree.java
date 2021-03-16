public class Btree {
    private int T;
    private Node root;
    public Btree(int t) {
        T = t;
        root = new Node(t);
        root.setN(0);
        root.setLeaf(true);
    }
    private Node Search(Node x, int key) {
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
            return Search(x.getChild()[i], key);
        }
    }
    public boolean Contain(int k) {
        if (this.Search(root, k) != null) {
            return true;
        } else {
            return false;
        }
    }
    public void Remove(int key) {
        Node x = Search(root, key);
        if (x == null) {
            return;
        }
        Remove(root, key);
    }
    private void Remove(Node x, int key) {
        int pos = x.Find(key);
        if (pos != -1) {
            if (x.getLeaf()) {
                int i = 0;
                for (i = 0; i < x.getN() && x.getKey()[i] != key; i++) {
                }
                for (; i < x.getN(); i++) {
                    if (i != 2 * T - 2) {
                        x.setKeyOnIndex(i, x.getKey()[i+1]);
//                        x.key[i] = x.key[i + 1];
                    }
                }
                x.setN(x.getN()-1);
//                x.n--;
                return;
            }
            if (!x.getLeaf()) {
                Node pred = x.getChild()[pos];
                int predKey = 0;
                if (pred.getN() >= T) {
                    for (;;) {
                        if (pred.getLeaf()) {
//                            System.out.println(pred.n);
                            predKey = pred.getKey()[pred.getN() - 1];
                            break;
                        } else {
                            pred = pred.getChild()[pred.getN()];
                        }
                    }
                    Remove(pred, predKey);
                    x.setKeyOnIndex(pos, predKey);
//                    x.key[pos] = predKey;
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
                    Remove(nextNode, nextKey);
                    x.setKeyOnIndex(pos, nextKey);
//                    x.key[pos] = nextKey;
                    return;
                }

                int temp = pred.getN() + 1;
                pred.setKeyOnIndex(pred.getN(),x.getKey()[pos]);
                pred.setN(pred.getN()+1);
//                pred.key[pred.n++] = x.key[pos];
                for (int i = 0, j = pred.getN(); i < nextNode.getN(); i++) {
                    pred.setKeyOnIndex(j++,nextNode.getKey()[i]);
                    pred.setN(pred.getN()+1);
//                    pred.key[j++] = nextNode.key[i];
//                    pred.n++;
                }
                for (int i = 0; i < nextNode.getN() + 1; i++) {
                    pred.setChildOnIndex(temp++,nextNode.getChild()[i]);
//                    pred.child[temp++] = nextNode.child[i];
                }

                x.setChildOnIndex(pos, pred);
//                x.child[pos] = pred;
                for (int i = pos; i < x.getN(); i++) {
                    if (i != 2 * T - 2) {
                        x.setKeyOnIndex(i, x.getKey()[i+1]);
//                        x.key[i] = x.key[i + 1];
                    }
                }
                for (int i = pos + 1; i < x.getN() + 1; i++) {
                    if (i != 2 * T - 1) {
                        x.setChildOnIndex(i, x.getChild()[i+1]);
//                        x.child[i] = x.child[i + 1];
                    }
                }
                x.setN(x.getN()-1);
//                x.n--;
                if (x.getN() == 0) {
                    if (x == root) {
                        root = x.getChild()[0];
                    }
                    x = x.getChild()[0];
                }
                Remove(pred, key);
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
                Remove(tmp, key);
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
//                    x.key[pos] = nb.key[0];
//                    tmp.key[tmp.n++] = devider;
//                    tmp.child[tmp.n] = nb.child[0];
                    for (int i = 1; i < nb.getN(); i++) {
                        nb.setKeyOnIndex(i-1, nb.getKey()[i]);
//                        nb.key[i - 1] = nb.key[i];
                    }
                    for (int i = 1; i <= nb.getN(); i++) {
                        nb.setChildOnIndex(i-1, nb.getChild()[i]);
//                        nb.child[i - 1] = nb.child[i];
                    }
                    nb.setN(nb.getN()-1);
//                    nb.n--;
                    Remove(tmp, key);
                    return;
                } else if (pos != 0 && x.getChild()[pos - 1].getN() >= T) {

                    devider = x.getKey()[pos - 1];
                    nb = x.getChild()[pos - 1];
                    x.setKeyOnIndex(pos-1, nb.getKey()[nb.getN()-1]);
//                    x.key[pos - 1] = nb.key[nb.n - 1];
                    Node child = nb.getChild()[nb.getN()];
                    nb.setN(nb.getN()-1);
//                    nb.n--;

                    for (int i = tmp.getN(); i > 0; i--) {
                        tmp.setKeyOnIndex(i, tmp.getKey()[i-1]);
//                        tmp.key[i] = tmp.key[i - 1];
                    }
                    tmp.setKeyOnIndex(0, devider);
//                    tmp.key[0] = devider;
                    for (int i = tmp.getN() + 1; i > 0; i--) {
                        tmp.setChildOnIndex(i, tmp.getChild()[i-1]);
//                        tmp.child[i] = tmp.child[i - 1];
                    }
                    tmp.setChildOnIndex(0, child);
                    tmp.setN(tmp.getN()+1);
//                    tmp.child[0] = child;
//                    tmp.n++;
                    Remove(tmp, key);
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
//                        x.key[i] = x.key[i + 1];
                    }
                    for (int i = pos + 1; i < x.getN(); i++) {
                        x.setChildOnIndex(i, x.getChild()[i+1]);
//                        x.child[i] = x.child[i + 1];
                    }
                    x.setN(x.getN()-1);
                    lt.setKeyOnIndex(lt.getN()+1, devider);
                    lt.setN(lt.getN()+1);
//                    x.n--;
//                    lt.key[lt.n++] = devider;

                    for (int i = 0, j = lt.getN(); i < rt.getN() + 1; i++, j++) {
                        if (i < rt.getN()) {
                            lt.setKeyOnIndex(j, rt.getKey()[i]);
//                            lt.key[j] = rt.key[i];
                        }
                        lt.setChildOnIndex(j, rt.getChild()[i]);
//                        lt.child[j] = rt.child[i];
                    }
                    lt.setN(lt.getN()+rt.getN());
//                    lt.n += rt.n;
                    if (x.getN()== 0) {
                        if (x == root) {
                            root = x.getChild()[0];
                        }
                        x = x.getChild()[0];
                    }
                    Remove(lt, key);
                    return;
                }
            }
        }
    }
    private void Split(Node x, int pos, Node y) {
        Node z = new Node(this.T);
        z.setLeaf(y.getLeaf());
        z.setN(T-1);
//        z.leaf = y.leaf;
//        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.setKeyOnIndex(j, y.getKey()[j+T]);
//            z.key[j] = y.key[j + T];
        }
        if (!y.getLeaf()) {
            for (int j = 0; j < T; j++) {
                z.setChildOnIndex(j, y.getChild()[j+T]);
//                z.child[j] = y.child[j + T];
            }
        }
        y.setN(T-1);
//        y.n = T - 1;
        for (int j = x.getN(); j >= pos + 1; j--) {
            x.setChildOnIndex(j+1, x.getChild()[j]);
//            x.child[j + 1] = x.child[j];
        }
        x.setChildOnIndex(pos+1, z);
//        x.child[pos + 1] = z;

        for (int j = x.getN() - 1; j >= pos; j--) {
            x.setKeyOnIndex(j+1, x.getKey()[j]);
//            x.key[j + 1] = x.key[j];
        }
        x.setKeyOnIndex(pos, y.getKey()[T-1]);
        x.setN(x.getN()+1);
//        x.key[pos] = y.key[T - 1];
//        x.n = x.n + 1;
    }
    public void Insert(final int key) {
        Node r = root;
        if (r.getN() == 2 * T - 1) {
            Node s = new Node(this.T);
            root = s;
            s.setLeaf(false);
            s.setN(0);
            s.setChildOnIndex(0, r);
//            s.leaf = false;
//            s.n = 0;
//            s.child[0] = r;
            Split(s, 0, r);
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
//                x.key[i + 1] = x.key[i];
            }
            x.setKeyOnIndex(i+1, k);
            x.setN(x.getN()+1);
//            x.key[i + 1] = k;
//            x.n = x.n + 1;
        } else {
            int i = 0;
            for (i = x.getN() - 1; i >= 0 && k <= x.getKey()[i]; i--) {
            }
            i++;
            Node tmp = x.getChild()[i];
            if (tmp.getN() == 2 * T - 1) {
                Split(x, i, tmp);
                if (k > x.getKey()[i]) {
                    i++;
                }
            }
            insertValue(x.getChild()[i], k);
        }

    }
    public void Show() {
        int j = 0;
        Show(root, j);
    }
    private void Show(Node x, int j) {
        assert (x == null);
        System.out.print("This is root - "+j+".||| ");
        for (int i = 0; i < x.getN(); i++) {
            System.out.print(x.getKey()[i] + " ");
        }
        System.out.println("");
        j++;
        if (!x.getLeaf()) {
            for (int i = 0; i < x.getN() + 1; i++) {
                Show(x.getChild()[i], j);
            }
        }
    }
    public static void main(String[] args) {
        Btree b = new Btree(2);
        b.Insert(2);
        b.Insert(1);
        b.Insert(3);
        b.Insert(4);
        b.Insert(4);
        b.Insert(4);
        b.Insert(4);
        b.Insert(2);
        b.Insert(2);
        b.Insert(1);
        b.Remove(4);
        b.Remove(4);
        b.Show();
        if (b.Contain(12)) {
            System.out.println("\nfound");
        } else {
            System.out.println("\nnot found");
        }
    }
}
