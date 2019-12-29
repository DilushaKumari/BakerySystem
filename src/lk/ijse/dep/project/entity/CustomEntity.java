package lk.ijse.dep.project.entity;

import java.sql.Date;
import java.sql.Time;

public class CustomEntity implements SuperEntity {
    private String ingId;
    private String ingDes;
    private double ingQty;
    private String empId;
    private  String taskId;
    private Date taskDate;
    private Time startTime;
    private Time endTime;

    public CustomEntity() {
    }

    public CustomEntity(String ingId, String ingDes, double ingQty) {
        this.setIngId(ingId);
        this.setIngDes(ingDes);
        this.setIngQty(ingQty);
    }

    public CustomEntity(String empId, String taskId, Date taskDate, Time startTime, Time endTime) {
        this.setEmpId(empId);
        this.setTaskId(taskId);
        this.setTaskDate(taskDate);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    public String getIngDes() {
        return ingDes;
    }

    public void setIngDes(String ingDes) {
        this.ingDes = ingDes;
    }

    public double getIngQty() {
        return ingQty;
    }

    public void setIngQty(double ingQty) {
        this.ingQty = ingQty;
    }


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
