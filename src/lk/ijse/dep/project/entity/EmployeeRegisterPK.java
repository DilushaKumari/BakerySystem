package lk.ijse.dep.project.entity;

import java.sql.Date;

public class EmployeeRegisterPK {
    private String empId;
    private Date date;
    private java.util.Date inTime;

    public EmployeeRegisterPK() {
    }

    public EmployeeRegisterPK(String empId, Date date, java.util.Date inTime) {
        this.setEmpId(empId);
        this.setDate(date);
        this.setInTime(inTime);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public java.util.Date getInTime() {
        return inTime;
    }

    public void setInTime(java.util.Date inTime) {
        this.inTime = inTime;
    }

    @Override
    public String toString() {
        return "EmployeeRegisterPK{" +
                "empId='" + empId + '\'' +
                ", date=" + date +
                ", inTime=" + inTime +
                '}';
    }
}

