package lk.ijse.dep.project.dto;

import java.util.Date;

public class TaskDTO3 {
    private String taskId;
    private String des;
    private String productId;
    private String taskStatus;
    private Date date;
    private Date  startTime;
    private Date endTime;

    public TaskDTO3() {
    }

    public TaskDTO3(String taskId, String des, String productId, String taskStatus, Date date, Date startTime, Date endTime) {
        this.setTaskId(taskId);
        this.setDes(des);
        this.setProductId(productId);
        this.setTaskStatus(taskStatus);
        this.setDate(date);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TaskDTO3{" +
                "taskId='" + taskId + '\'' +
                ", des='" + des + '\'' +
                ", productId='" + productId + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
