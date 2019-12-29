package lk.ijse.dep.project.dto;

import java.sql.Time;
import java.util.Date;

public class EmpAssignDTO {
    private String empId;
    private String taskId;
    private Date date;
    private Time startTime;
    private Time endTime;

    public EmpAssignDTO() {
    }

    public EmpAssignDTO(String empId, String taskId, Date date, Time startTime, Time endTime) {
        this.setEmpId(empId);
        this.setTaskId(taskId);
        this.setDate(date);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "EmpAssignDTO{" +
                "empId='" + empId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
