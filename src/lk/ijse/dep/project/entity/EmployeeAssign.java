package lk.ijse.dep.project.entity;

import java.sql.Time;

public class EmployeeAssign implements SuperEntity {
    private EmployeeAssignPK employeeAssignPK;
    private Time finishTime;

    public EmployeeAssign() {
    }

    public EmployeeAssign(EmployeeAssignPK employeeAssignPK, Time finishTime) {
        this.setEmployeeAssignPK(employeeAssignPK);
        this.setFinishTime(finishTime);
    }
    public EmployeeAssign(String empId,String taskId,Time startTime, Time finishTime) {
        this.setEmployeeAssignPK(new EmployeeAssignPK(empId,taskId,startTime));
        this.setFinishTime(finishTime);
    }

    public EmployeeAssignPK getEmployeeAssignPK() {
        return employeeAssignPK;
    }

    public void setEmployeeAssignPK(EmployeeAssignPK employeeAssignPK) {
        this.employeeAssignPK = employeeAssignPK;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "EmployeeAssign{" +
                "employeeAssignPK=" + employeeAssignPK +
                ", finishTime=" + finishTime +
                '}';
    }
}
