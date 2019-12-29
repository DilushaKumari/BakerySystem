package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.EmployeeAssign;
import lk.ijse.dep.project.entity.EmployeeAssignPK;

import java.sql.Time;
import java.util.Date;

public interface EmployeeAssignDAO extends CrudDAO<EmployeeAssign, EmployeeAssignPK> {
    boolean checkEmpAssign(String empId, String taskId) throws Exception;

    boolean checkEmpAssigningToOut(String empId) throws Exception;

    boolean updateEmployeeRelease(String empId, String taskId, Time finishTime) throws Exception;

    boolean empReleasingWhenProcessFinished(String taskId, Date finishTime) throws Exception;
}
