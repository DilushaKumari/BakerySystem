package lk.ijse.dep.project.entity;

import java.sql.Time;

public class EmployeeAssignPK {
    private String empId;
    private String taskId;
    private Time startTime;

    public EmployeeAssignPK() {
    }

    public EmployeeAssignPK(String empId, String taskId, Time startTime) {
        this.setEmpId(empId);
        this.setTaskId(taskId);
        this.setStartTime(startTime);
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "EmployeeAssignPK{" +
                "empId='" + empId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
