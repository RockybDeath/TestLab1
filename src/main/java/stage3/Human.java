package stage3;

import java.util.ArrayList;

public class Human extends Entity{
    private ArrayList<Head> heads = new ArrayList<>();
    private Jaw jaw = new Jaw("Челюсть");
    public Human(String name) {
        super(name);
    }

    public Jaw getJaw() {
        return jaw;
    }

    public ArrayList<Head> getHeads() {
        return heads;
    }

    public void setHead(Head head) {
        this.heads.add(head);
    }
}
