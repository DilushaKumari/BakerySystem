package lk.ijse.dep.project.entity;

import java.sql.Date;

public class EmployeeRegister implements SuperEntity {
    private EmployeeRegisterPK employeeRegisterPK ;
    private java.util.Date outTime;

    public EmployeeRegister() {
    }

    public EmployeeRegister(EmployeeRegisterPK employeeRegisterPK, Date outTime) {
        this.setEmployeeRegisterPK(employeeRegisterPK);
        this.setOutTime(outTime);
    }
    public EmployeeRegister(String empId, Date date, java.util.Date inTime, Date outTime) {
        this.setEmployeeRegisterPK(new EmployeeRegisterPK(empId,date,inTime));
        this.setOutTime(outTime);
    }




    public EmployeeRegisterPK getEmployeeRegisterPK() {
        return employeeRegisterPK;
    }

    public void setEmployeeRegisterPK(EmployeeRegisterPK employeeRegisterPK) {
        this.employeeRegisterPK = employeeRegisterPK;
    }

    public java.util.Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return "EmployeeRegister{" +
                "employeeRegisterPK=" + getEmployeeRegisterPK() +
                ", outTime=" + getOutTime() +
                '}';
    }
}
