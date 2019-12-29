package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.EmployeeDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.EmployeeAssign;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> findAll() throws Exception {
        List<Employee> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee ");
        while (rst.next()){
            list.add(new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5)));
        }
        return list;
    }

    @Override
    public Employee find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee WHERE empId=? ", s);
        if (rst.next()){
            return new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));
        }
        return null;
    }

    @Override
    public boolean save(Employee entity) throws Exception {
        return CrudUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?)",entity.getEmpId(),entity.getEmpName(),entity.getEmpAddress(),entity.getEmpContact(),entity.getEmpAvailability());
    }

    @Override
    public boolean update(Employee entity) throws Exception {
        return CrudUtil.execute("UPDATE employee SET empName=?,empAddress=?,empContact=?,empAvailability=? WHERE empId=? ",entity.getEmpName(),entity.getEmpAddress(),entity.getEmpContact(),entity.getEmpAvailability(),entity.getEmpId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM employee WHERE empId=? ",s);
    }

    @Override
    public String getLastEmpId() throws Exception {

        ResultSet rst = CrudUtil.execute("SELECT empId FROM employee ORDER BY empId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Employee> getCustomersBySearch( String text) throws Exception {
        List<Employee> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee WHERE empId LIKE ? OR empName LIKE ? OR empAddress LIKE ? OR empContact LIKE ? OR empAvailability LIKE ?",text,text,text,text,text);
        while (rst.next()){
            list.add(new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5)));
        }
        return list;
    }
    @Override
    public boolean checkEmpIn(String empId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee WHERE empId=? AND empAvailability=? ",empId,"YES");
        return rst.next();
    }
}
