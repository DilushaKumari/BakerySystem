package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.EmployeeRegisterDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.EmployeeRegister;
import lk.ijse.dep.project.entity.EmployeeRegisterPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeRegisterDAOImpl implements EmployeeRegisterDAO {
    @Override
    public List<EmployeeRegister> findAll() throws Exception {
        List<EmployeeRegister> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeregister");
        while (rst.next()){
            list.add(new EmployeeRegister(rst.getString(1),rst.getDate(2),rst.getDate(3),rst.getDate(4)));
        }
        return list;
    }

    @Override
    public EmployeeRegister find(EmployeeRegisterPK employeeRegisterPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeregister WHERE empId=? AND date=? AND inTime=?",employeeRegisterPK.getEmpId(),employeeRegisterPK.getDate(),employeeRegisterPK.getInTime());
        if (rst.next()){
          return   new EmployeeRegister(rst.getString(1),rst.getDate(2),rst.getDate(3),rst.getDate(4));
        }
        return null;
    }

    @Override
    public boolean save(EmployeeRegister entity) throws Exception {
        return CrudUtil.execute("INSERT INTO employeeregister VALUES (?,?,?,?)",entity.getEmployeeRegisterPK().getEmpId(),entity.getEmployeeRegisterPK().getDate(),entity.getEmployeeRegisterPK().getInTime(),entity.getOutTime());

    }

    @Override
    public boolean update(EmployeeRegister entity) throws Exception {
        return CrudUtil.execute("UPDATE  employeeregister SET outTime=? WHERE empId=? AND date=? AND inTime=? ",entity.getOutTime(),entity.getEmployeeRegisterPK().getEmpId(),entity.getEmployeeRegisterPK().getDate(),entity.getEmployeeRegisterPK().getInTime());

    }

    @Override
    public boolean delete(EmployeeRegisterPK employeeRegisterPK) throws Exception {
        return CrudUtil.execute("DELETE FROM employeeregister WHERE empId=? AND date=? AND inTime ",employeeRegisterPK.getEmpId(),employeeRegisterPK.getDate(),employeeRegisterPK
        .getInTime());
    }

    @Override
    public boolean existsByEmpId(String empId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeregister WHERE empId=?",empId);
        return rst.next();
    }

    @Override
    public boolean checkEmpIn(String empId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeregister WHERE empId=? AND outTime IS NULL ",empId);
        return rst.next();
    }

    @Override
    public boolean updateEmployeeOut(String empId) throws Exception {
        return CrudUtil.execute("UPDATE employeeregister SET outTime=? WHERE empId=? AND outTime is null ",new Date(),empId);
    }
}
