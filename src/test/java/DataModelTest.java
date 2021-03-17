import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stage3.*;

public class DataModelTest {
    @Test
    public void testOverwhelmed(){
        Human Arthur = new Human("Артур");
        Human human = new Human("Человек");
        human.addAction(new Action("Ошеломить", Statuses.Overwhelmed, Arthur));
        human.activateAction("Ошеломить");
        Assertions.assertTrue(Arthur.getStatuses().contains(Statuses.Overwhelmed));
    }
    @Test
    public void testHeadActivateAction(){
        Human human = new Human("Человек");
        Head headLeft = new Head("Правая голова");
        Head headRight = new Head("Левая голова");
        headRight.addAction(new Action("Смотрю на Артура", Statuses.Busy, headRight));
        headLeft.addAction(new Action("Улыбаюсь широко", Statuses.Glad, headLeft));
        human.setHead(headLeft);
        human.setHead(headRight);
        headRight.activateAction("Смотрю на Артура");
        headLeft.activateAction("Улыбаюсь широко");
        Assertions.assertTrue(headLeft.getStatuses().contains(Statuses.Glad));
        Assertions.assertTrue(headRight.getStatuses().contains(Statuses.Busy));
    }
    @Test
    public void testNervousArthur(){
        Human Arthur = new Human("Артур");
        Human human = new Human("Человек");
        Head headLeft = new Head("Правая голова");
        Head headRight = new Head("Левая голова");
        human.setHead(headLeft);
        human.setHead(headRight);
        human.addAction(new Action("Сделать Антона нервным", Statuses.Nervous, Arthur, new Condition() {
            @Override
            public boolean checkCondition() {
                return human.getHeads().size() > 1;
            }
        }));
        human.addAction(new Action("Челюсть Антона отвиснет", Statuses.Jagged, Arthur.getJaw(), new Condition() {
            @Override
            public boolean checkCondition() {
                return Arthur.getStatuses().contains(Statuses.Nervous);
            }
        }));
        human.activateAction("Сделать Антона нервным");
        human.activateAction("Челюсть Антона отвиснет");
        Assertions.assertTrue(Arthur.getStatuses().contains(Statuses.Nervous));
        Assertions.assertTrue(Arthur.getJaw().getStatuses().contains(Statuses.Jagged));

    }
    @Test
    public void jawNotSagged(){
        Human Arthur = new Human("Артур");
        Assertions.assertFalse(Arthur.getStatuses().contains(Statuses.Nervous));
        Assertions.assertFalse(Arthur.getStatuses().contains(Statuses.Overwhelmed));
        Assertions.assertFalse(Arthur.getJaw().getStatuses().contains(Statuses.Jagged));
    }
}
