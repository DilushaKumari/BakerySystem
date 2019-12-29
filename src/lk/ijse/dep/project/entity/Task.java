package lk.ijse.dep.project.entity;

import java.sql.Date;

public class Task implements SuperEntity {
    private String taskId;
    private String taskDescription;
    private String taskStatus;
    private Date taskDate;
    private java.util.Date taskStartTime;
    private java.util.Date taskEndTime;
    private String productId;
    private double expectQty;
    private double actualQty;

    public Task() {
    }

    public Task(String taskId, String taskDescription, String taskStatus, Date taskDate, java.util.Date taskStartTime, java.util.Date taskEndTime, String productId, double expectQty, double actualQty) {
        this.setTaskId(taskId);
        this.setTaskDescription(taskDescription);
        this.setTaskStatus(taskStatus);
        this.setTaskDate(taskDate);
        this.setTaskStartTime(taskStartTime);
        this.setTaskEndTime(taskEndTime);
        this.setProductId(productId);
        this.setExpectQty(expectQty);
        this.setActualQty(actualQty);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public java.util.Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(java.util.Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public java.util.Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(java.util.Date taskEndTime) {
        this.taskEndTime = taskEndTime;
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

    public double getActualQty() {
        return actualQty;
    }

    public void setActualQty(double actualQty) {
        this.actualQty = actualQty;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskDate=" + taskDate +
                ", taskStartTime=" + taskStartTime +
                ", taskEndTime=" + taskEndTime +
                ", productId='" + productId + '\'' +
                ", expectQty=" + expectQty +
                ", actualQty=" + actualQty +
                '}';
    }
}

