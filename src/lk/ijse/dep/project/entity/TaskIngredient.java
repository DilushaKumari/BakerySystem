package lk.ijse.dep.project.entity;

public class TaskIngredient implements SuperEntity {
    private TaskIngredientPK taskIngredientPK;
    private double ingQty;

    public TaskIngredient() {
    }

    public TaskIngredient(TaskIngredientPK taskIngredientPK, double ingQty) {
        this.setTaskIngredientPK(taskIngredientPK);
        this.setIngQty(ingQty);
    }
    public TaskIngredient(String taskId,String ingId, double ingQty) {
        this.setTaskIngredientPK(new TaskIngredientPK(taskId, ingId));
        this.setIngQty(ingQty);
    }


    public TaskIngredientPK getTaskIngredientPK() {
        return taskIngredientPK;
    }

    public void setTaskIngredientPK(TaskIngredientPK taskIngredientPK) {
        this.taskIngredientPK = taskIngredientPK;
    }

    public double getIngQty() {
        return ingQty;
    }

    public void setIngQty(double ingQty) {
        this.ingQty = ingQty;
    }

    @Override
    public String toString() {
        return "TaskIngredient{" +
                "taskIngredientPK=" + taskIngredientPK +
                ", ingQty=" + ingQty +
                '}';
    }
}
