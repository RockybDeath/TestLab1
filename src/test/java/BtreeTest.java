import org.junit.jupiter.api.*;
import stage2.Btree;
import stage2.Node;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BtreeTest {
    private Btree btree;
    public boolean compareBtree(Btree a, Btree b){
        if(a.getRoot().getKey().length != b.getRoot().getKey().length) return false;
        return compareNodes(a.getRoot(), b.getRoot());
    }
    public boolean compareNodes(Node a, Node b){
        for (int i = 0; i < a.getN(); i++) {
            if(a.getKey()[i] != b.getKey()[i]) return false;
        }
        if (!a.getLeaf() && !b.getLeaf()) {
            for (int i = 0; i < a.getN() + 1; i++) {
                compareNodes(a.getChild()[i], b.getChild()[i]);
            }
        } else return false;
        return true;
    }
    @BeforeEach
    public void init(){
        btree = new Btree(2);
    }
    @Test
    public void testEmpty(){
        Assertions.assertFalse(btree.show());
        btree.insert(1);
        Assertions.assertTrue(btree.show());
        btree.clear();
        Assertions.assertFalse(btree.show());
    }
    @Test
    public void testSearch(){
        Assertions.assertFalse(btree.contain(0));
        btree.insert(1);
        Assertions.assertTrue(btree.contain(1));
        btree.remove(1);
        Assertions.assertFalse(btree.contain(1));
    }
    @Test
    public void testinsert(){
        btree.insert(2);
        btree.insert(1);
        btree.insert(3);
        btree.insert(4);
        btree.insert(4);
        btree.insert(4);
        btree.insert(4);
        btree.insert(2);
        btree.insert(2);
        btree.insert(1);
        Btree btree1 = new Btree(2);
        btree1.getRoot().setKey(new int[]{2,2,4});
        btree1.getRoot().setLeaf(false);
        Node[] nodes = btree1.getRoot().getChild();
        nodes[0] = new Node(2);
        nodes[1] = new Node(2);
        nodes[2] = new Node(2);
        nodes[3] = new Node(2);
        nodes[0].setKey(new int[]{1,1});
        nodes[0].setN(2);
        nodes[0].setLeaf(true);
        nodes[1].setKey(new int[]{2});
        nodes[1].setN(1);
        nodes[1].setLeaf(true);
        nodes[2].setKey(new int[]{3,4,4});
        nodes[2].setN(3);
        nodes[2].setLeaf(true);
        nodes[3].setKey(new int[]{4});
        nodes[3].setN(1);
        nodes[3].setLeaf(true);
        Assertions.assertTrue(compareBtree(btree, btree1));
    }
    @Test
    public void testRemove(){
        btree.insert(2);
        btree.insert(1);
        btree.insert(3);
        btree.insert(4);
        btree.insert(4);
        btree.insert(4);
        btree.insert(4);
        btree.insert(2);
        btree.insert(2);
        btree.insert(1);
        btree.remove(4);
        btree.remove(4);
        btree.remove(2);
        btree.remove(2);
        btree.remove(1);
        btree.remove(1);
        Btree btree1 = new Btree(2);
        btree1.getRoot().setKey(new int[]{4,0,0});
        btree1.getRoot().setLeaf(false);
        Node[] nodes = btree1.getRoot().getChild();
        nodes[0] = new Node(2);
        nodes[1] = new Node(2);
        nodes[0].setKey(new int[]{2,3});
        nodes[0].setN(2);
        nodes[0].setLeaf(true);
        nodes[1].setKey(new int[]{4});
        nodes[1].setN(1);
        nodes[1].setLeaf(true);
        Assertions.assertTrue(compareBtree(btree, btree1));
    }
    @Test
    public void testRemove1(){
        btree.insert(2);
        btree.insert(1);
        btree.insert(3);
        btree.insert(4);
        btree.insert(4);
        btree.insert(4);
        btree.insert(4);
        btree.insert(2);
        btree.insert(2);
        btree.insert(1);
        btree.insert(5);
        btree.insert(0);
        btree.insert(0);
        btree.remove(1);
        btree.remove(0);
        btree.remove(0);
        btree.remove(2);
        Btree btree1 = new Btree(2);
        btree1.getRoot().setKey(new int[]{2,4,0});
        btree1.getRoot().setLeaf(false);
        Node[] nodes = btree1.getRoot().getChild();
        nodes[0] = new Node(2);
        nodes[1] = new Node(2);
        nodes[2] = new Node(2);
        nodes[0].setKey(new int[]{1,2});
        nodes[0].setN(2);
        nodes[0].setLeaf(true);
        nodes[1].setKey(new int[]{3,4,4});
        nodes[1].setN(3);
        nodes[1].setLeaf(true);
        nodes[2].setKey(new int[]{4,5});
        nodes[2].setN(2);
        nodes[2].setLeaf(true);
        Assertions.assertTrue(compareBtree(btree, btree1));
    }
    @Test
    public void testRemove2(){
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        btree.insert(4);
        btree.insert(5);
        btree.insert(6);
        btree.insert(7);
        btree.insert(8);
        btree.insert(9);
        btree.insert(10);
        btree.remove(2);
        Btree btree1 = new Btree(2);
        btree1.getRoot().setKey(new int[]{6,0,0});
        btree1.getRoot().setLeaf(false);
        Node[] nodes = btree1.getRoot().getChild();
        nodes[0] = new Node(2);
        nodes[0].setKey(new int[]{4});
        nodes[0].setLeaf(true);
        nodes[1] = new Node(2);
        nodes[1].setKey(new int[]{8});
        nodes[1].setLeaf(true);
        Node[] subNodes1 = nodes[0].getChild();
        Node[] subNodes2 = nodes[1].getChild();
        subNodes1[0] = new Node(2);
        subNodes1[0].setKey(new int[]{1,3});
        subNodes1[0].setLeaf(true);
        subNodes1[1] = new Node(2);
        subNodes1[1].setKey(new int[]{5});
        subNodes1[1].setLeaf(true);
        subNodes2[0] = new Node(2);
        subNodes2[0].setKey(new int[]{7});
        subNodes2[0].setLeaf(true);
        subNodes2[1] = new Node(2);
        subNodes2[1].setKey(new int[]{9,10});
        subNodes2[1].setLeaf(true);
        Assertions.assertTrue(compareBtree(btree, btree1));
    }
}
