package lk.ijse.dep.project.business.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.dep.project.business.custom.EmployeeBO;
import lk.ijse.dep.project.business.exception.AllReadyExistsEmpIdInEmployeeRegisterException;
import lk.ijse.dep.project.dao.DAOFactory;
import lk.ijse.dep.project.dao.DAOTypes;
import lk.ijse.dep.project.dao.custom.EmployeeAssignDAO;
import lk.ijse.dep.project.dao.custom.EmployeeDAO;
import lk.ijse.dep.project.dao.custom.EmployeeRegisterDAO;
import lk.ijse.dep.project.dao.custom.QueryDAO;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.EmpAssignDTO;
import lk.ijse.dep.project.dto.EmployeeDTO;
import lk.ijse.dep.project.entity.CustomEntity;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.EmployeeAssign;
import lk.ijse.dep.project.entity.EmployeeRegister;

import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = DAOFactory.getInstance().getDAO(DAOTypes.EMPLOYEE);
    EmployeeRegisterDAO employeeRegisterDAO = DAOFactory.getInstance().getDAO(DAOTypes.EMPLOYEE_REGISTER);
    EmployeeAssignDAO employeeAssignDAO = DAOFactory.getInstance().getDAO(DAOTypes.EMPLOYEE_ASSIGN);
    QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);

    @Override
    public boolean saveEmployee(EmployeeDTO employee) throws Exception {
        return employeeDAO.save(new Employee(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employee) throws Exception {
        return employeeDAO.update(new Employee(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));

    }

    @Override
    public boolean deleteEmployee(String employeeId) throws Exception {
        if (employeeRegisterDAO.existsByEmpId(employeeId)) {
            throw new AllReadyExistsEmpIdInEmployeeRegisterException("Employee already exists in employee register, hence unable to delete");
        }
        return employeeDAO.delete(employeeId);

    }

    @Override
    public boolean checkEmpIn(String empId) throws Exception {
        return employeeRegisterDAO.checkEmpIn(empId);
    }

    @Override
    public boolean checkEmpAvailability(String empId) throws Exception {
        return employeeDAO.checkEmpIn(empId);
    }

    @Override
    public boolean markingEmpAttendanceIN(String empId) throws Exception {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean result = employeeRegisterDAO.save(new EmployeeRegister(empId, new java.sql.Date(new Date().getTime()), new Date(), null));


            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when marking In");
            }

            Employee employee = employeeDAO.find(empId);
            employee.setEmpAvailability("YES");
            result = employeeDAO.update(employee);

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when update employee availability");
            }

            connection.commit();
            return true;
        } catch (Throwable e) {

            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean markingEmpAttendanceOUT(String empId) throws Exception {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean result = employeeRegisterDAO.updateEmployeeOut(empId);


            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when marking Out");
            }

            Employee employee = employeeDAO.find(empId);
            employee.setEmpAvailability("NO");
            result = employeeDAO.update(employee);

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when update employee availability");
            }

            connection.commit();
            return true;
        } catch (Throwable e) {

            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean empAssigning(String empId, String taskId, Time startTime) throws Exception {
        employeeAssignDAO.save(new EmployeeAssign(empId, taskId, startTime, null));
        return false;
    }


    @Override
    public boolean checkEmpAssigningToOut(String empId) throws Exception {
        return employeeAssignDAO.checkEmpAssigningToOut(empId);
    }

    @Override
    public boolean empReleasing(String empId, String taskId, Time finishTime) throws Exception {
        return employeeAssignDAO.updateEmployeeRelease(empId, taskId, finishTime);
    }


    @Override
    public EmployeeDTO findEmployee(String empId) throws Exception {
        Employee employee = employeeDAO.find(empId);
        return new EmployeeDTO(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability());
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() throws Exception {
        List<Employee> allEmployees = employeeDAO.findAll();
        List<EmployeeDTO> employees = new ArrayList<>();

        for (Employee employee : allEmployees) {
            employees.add(new EmployeeDTO(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));
        }
        return employees;
    }

    @Override
    public List<EmpAssignDTO> getAllEmpAssignWorks(String text) throws Exception {
        List<CustomEntity> allAssigns = queryDAO.gettingAssignWork(text);
        List<EmpAssignDTO> list = new ArrayList<>();
        for (CustomEntity allAssign : allAssigns) {
            list.add(new EmpAssignDTO(allAssign.getEmpId(),
                    allAssign.getTaskId(),
                    allAssign.getTaskDate(),
                    allAssign.getStartTime(),
                    allAssign.getEndTime()));

        }

        return list;
    }

    @Override
    public List<EmployeeDTO> findAllEmployeesBySearch(String text) throws Exception {
        List<Employee> allEmployees = employeeDAO.getCustomersBySearch(text);
        List<EmployeeDTO> employees = new ArrayList<>();

        for (Employee employee : allEmployees) {
            employees.add(new EmployeeDTO(employee.getEmpId(), employee.getEmpName(), employee.getEmpAddress(), employee.getEmpContact(), employee.getEmpAvailability()));
        }
        return employees;
    }

    @Override
    public String getLastEmployeeId() throws Exception {
        return employeeDAO.getLastEmpId();
    }

    @Override
    public List<String> getAllEmployeeId() throws Exception {
        List<Employee> all = employeeDAO.findAll();
        List<String> list = new ArrayList<>();
        for (Employee employee : all) {
            list.add(employee.getEmpId());
        }

        return list;

    }
}
