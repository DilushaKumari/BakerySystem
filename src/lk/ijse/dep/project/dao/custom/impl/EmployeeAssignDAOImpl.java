package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.EmployeeAssignDAO;
import lk.ijse.dep.project.entity.EmployeeAssign;
import lk.ijse.dep.project.entity.EmployeeAssignPK;

import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeAssignDAOImpl implements EmployeeAssignDAO {
    @Override
    public List<EmployeeAssign> findAll() throws Exception {

        List<EmployeeAssign> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeassign");
        while (rst.next()){
            list.add(new EmployeeAssign(rst.getString(1),rst.getString(2),rst.getTime(3),rst.getTime(4)));
        }
        return list;
    }

    @Override
    public EmployeeAssign find(EmployeeAssignPK employeeAssignPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeassign WHERE empId=? AND taskId=?", employeeAssignPK.getEmpId(), employeeAssignPK.getTaskId());
        if (rst.next()){
            return new EmployeeAssign(rst.getString(1),rst.getString(2),rst.getTime(3),rst.getTime(4));
        }
        return null;
    }

    @Override
    public boolean save(EmployeeAssign entity) throws Exception {
    return CrudUtil.execute("INSERT INTO employeeassign VALUES (?,?,?,?)",entity.getEmployeeAssignPK().getEmpId(),entity.getEmployeeAssignPK().getTaskId(),entity.getEmployeeAssignPK().getStartTime(),entity.getFinishTime());
    }

    @Override
    public boolean update(EmployeeAssign entity) throws Exception {
        return CrudUtil.execute("UPDATE employeeassign SET finishTime=? WHERE taskId=? AND empId=? AND startTime=?",entity.getFinishTime(),entity.getEmployeeAssignPK().getTaskId(),entity.getEmployeeAssignPK().getEmpId(),entity.getEmployeeAssignPK().getStartTime());
    }

    @Override
    public boolean delete(EmployeeAssignPK employeeAssignPK) throws Exception {
        return CrudUtil.execute("DELETE FROM employeeassign WHERE empId=? AND taskId=?",employeeAssignPK.getEmpId(),employeeAssignPK.getTaskId());
    }


    @Override
    public boolean checkEmpAssign(String empId,String taskId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeassign WHERE empId=? AND taskId=? AND finishTime IS NULL ",empId,taskId);
        return rst.next();
    }

    @Override
    public boolean checkEmpAssigningToOut(String empId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employeeassign WHERE empId=? AND finishTime IS NULL ",empId);
        return rst.next();
    }

    @Override
    public boolean updateEmployeeRelease(String empId, String taskId, Time finishTime) throws Exception {
        return CrudUtil.execute("UPDATE employeeassign SET finishTime=? WHERE empId=? AND taskId =? AND finishTime IS NULL  ",finishTime,empId,taskId);
    }

    @Override
    public boolean empReleasingWhenProcessFinished(String taskId, Date finishTime) throws Exception {
        return CrudUtil.execute("UPDATE employeeassign SET finishTime=? WHERE taskId =? AND finishTime IS NULL  ",finishTime,taskId);
    }

}
