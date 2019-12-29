package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.dto.EmployeeDTO;
import lk.ijse.dep.project.entity.Employee;

import java.util.List;

public interface EmployeeDAO  extends CrudDAO<Employee,String> {
    String getLastEmpId() throws Exception;
    List<Employee>  getCustomersBySearch(String text) throws Exception;
    boolean checkEmpIn(String empId) throws Exception;
}
