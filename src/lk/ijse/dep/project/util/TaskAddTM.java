package lk.ijse.dep.project.util;

public class TaskAddTM {
    private String taskId;
    private String taskDes;
    private String productId;
    private double expectedQty;

    public TaskAddTM() {
    }

    public TaskAddTM(String taskId, String taskDes, String productId, double expectedQty) {
        this.setTaskId(taskId);
        this.setTaskDes(taskDes);
        this.setProductId(productId);
        this.setExpectedQty(expectedQty);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDes() {
        return taskDes;
    }

    public void setTaskDes(String taskDes) {
        this.taskDes = taskDes;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(double expectedQty) {
        this.expectedQty = expectedQty;
    }

    @Override
    public String toString() {
        return "TaskAddTM{" +
                "taskId='" + taskId + '\'' +
                ", taskDes='" + taskDes + '\'' +
                ", productId='" + productId + '\'' +
                ", expectedQty=" + expectedQty +
                '}';
    }
}
