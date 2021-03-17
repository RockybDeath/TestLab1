package stage3;

import lombok.Data;

@Data
public class Action {
    private String name;
    private Statuses targetStatus;
    private Entity target;
    private Condition condition = null;
    public Action(String name, Statuses status, Entity target){
        this.name = name;
        this.targetStatus = status;
        this.target = target;
    }

    public Condition getCondition() {
        return condition;
    }

    public Action(String name, Statuses status, Entity target, Condition condition){
        this.name = name;
        this.targetStatus = status;
        this.target = target;
        this.condition = condition;
    }
    public Statuses getTargetStatus() {
        return targetStatus;
    }

    public Entity getTarget() {
        return target;
    }

    public String getName() {
        return name;
    }
}
