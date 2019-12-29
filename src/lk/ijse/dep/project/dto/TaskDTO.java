package lk.ijse.dep.project.dto;

import java.sql.Date;

public class TaskDTO {
    private String taskId;
    private String taskDes;
    private Date taskDate;
    private String productId;
    private double expectQty;

    public TaskDTO() {
    }

    public TaskDTO(String taskId, String taskDes, Date taskDate, String productId, double expectQty) {
        this.setTaskId(taskId);
        this.setTaskDes(taskDes);
        this.setTaskDate(taskDate);
        this.setProductId(productId);
        this.setExpectQty(expectQty);
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

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getExpectQty() {
        return expectQty;
    }

    public void setExpectQty(double expectQty) {
        this.expectQty = expectQty;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "taskId='" + taskId + '\'' +
                ", taskDes='" + taskDes + '\'' +
                ", taskDate=" + taskDate +
                ", productId='" + productId + '\'' +
                ", expectQty=" + expectQty +
                '}';
    }
}
