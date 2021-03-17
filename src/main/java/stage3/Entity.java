package stage3;

import lombok.Data;

import java.util.ArrayList;

@Data
public abstract class Entity {
    private ArrayList<Action> actions = new ArrayList<>();
    private String name;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    public Entity(String name){
        this.name = name;
    }

    public ArrayList<Statuses> getStatuses() {
        return statuses;
    }

    public void setStatus(Statuses status) {
        statuses.add(status);
    }

    public boolean addAction(Action action) {
        return actions.add(action);
    }

    public void activateAction(String actionName) {
        actions.stream().filter(e -> e.getName().equals(actionName)).forEach(e -> {
            if(e.getCondition() == null) e.getTarget().setStatus(e.getTargetStatus());
            else {
                if(e.getCondition().checkCondition()) e.getTarget().setStatus(e.getTargetStatus());
            }
        });
    }
}
