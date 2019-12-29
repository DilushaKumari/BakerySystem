package lk.ijse.dep.project.business.custom;

import lk.ijse.dep.project.business.SuperBO;
import lk.ijse.dep.project.dto.EmpAssignDTO;
import lk.ijse.dep.project.dto.EmployeeDTO;

import java.sql.Time;
import java.util.List;

public interface EmployeeBO extends SuperBO {

    boolean saveEmployee(EmployeeDTO employee) throws Exception;

    boolean updateEmployee(EmployeeDTO employee) throws Exception;

    boolean deleteEmployee(String employeeId) throws Exception;

    boolean checkEmpIn(String empId) throws Exception;

    boolean checkEmpAvailability(String empId) throws Exception;

    boolean markingEmpAttendanceIN(String empId) throws Exception;

    boolean markingEmpAttendanceOUT(String empId) throws Exception;

    boolean empAssigning(String empId, String taskId, Time startTime) throws Exception;


    boolean checkEmpAssigningToOut(String empId) throws Exception;

    boolean empReleasing(String empId, String taskId, Time finishTime) throws Exception;

    EmployeeDTO findEmployee(String empId) throws Exception;


    List<EmployeeDTO> findAllEmployees() throws Exception;


    List<EmpAssignDTO> getAllEmpAssignWorks(String text) throws Exception;

    List<EmployeeDTO> findAllEmployeesBySearch(String text) throws Exception;

    String getLastEmployeeId() throws Exception;

    List<String> getAllEmployeeId() throws Exception;


}
