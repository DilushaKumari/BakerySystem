package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.EmployeeRegister;
import lk.ijse.dep.project.entity.EmployeeRegisterPK;

public interface EmployeeRegisterDAO extends CrudDAO<EmployeeRegister, EmployeeRegisterPK> {
    boolean existsByEmpId(String empId) throws Exception;
    boolean checkEmpIn(String empId) throws Exception;
    boolean updateEmployeeOut(String empId) throws  Exception;
}
