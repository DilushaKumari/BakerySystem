package lk.ijse.dep.project.entity;

public class TaskIngredientPK {
    private String taskId;
    private String ingId;

    public TaskIngredientPK() {
    }

    public TaskIngredientPK(String taskId, String ingId) {
        this.setTaskId(taskId);
        this.setIngId(ingId);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    @Override
    public String toString() {
        return "TaskIngredientPK{" +
                "taskId='" + taskId + '\'' +
                ", ingId='" + ingId + '\'' +
                '}';
    }
}
