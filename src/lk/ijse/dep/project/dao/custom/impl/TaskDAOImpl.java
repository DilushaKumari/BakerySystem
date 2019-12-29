package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.TaskDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.Task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public List<Task> findAll() throws Exception {
        List<Task> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM task");
        while (rst.next()){
            list.add(new Task(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDate(4),rst.getDate(5),
                    rst.getDate(6),rst.getString(7),rst.getDouble(8),rst.getDouble(9)));

        }
        return list;
    }

    @Override
    public Task find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM task WHERE taskId=?",s);
        if (rst.next()){
         return   new Task(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDate(4),rst.getDate(5),
                    rst.getDate(6),rst.getString(7),rst.getDouble(8),rst.getDouble(9));
        }
        return null;
    }

    @Override
    public boolean save(Task entity) throws Exception {
        return CrudUtil.execute("INSERT INTO task VALUES (?,?,?,?,?,?,?,?,?)",
                entity.getTaskId(),entity.getTaskDescription(),entity.getTaskStatus(),entity.getTaskDate(),entity.getTaskStartTime(),
                entity.getTaskEndTime(),entity.getProductId(),entity.getExpectQty(),entity.getActualQty());

    }

    @Override
    public boolean update(Task entity) throws Exception {
        return CrudUtil.execute("UPDATE  task SET taskDescription=?,taskStatus=?,taskDate=?,taskStartTime=?," +
                "taskEndTime=?,productId=?,expectQty=?,actualQty=? WHERE taskId=? ",
                entity.getTaskDescription(),entity.getTaskStatus(),entity.getTaskDate(),entity.getTaskStartTime(),entity.getTaskEndTime(),
                entity.getProductId(),entity.getExpectQty(),entity.getActualQty(),entity.getTaskId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM task WHERE taskId=? ",s);
    }

    @Override
    public boolean existsByProductId(String productId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM task WHERE productId=?", productId);
        return rst.next();
    }

    @Override
    public String getLastTaskId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT taskId FROM task ORDER BY taskId DESC LIMIT 1");
        if(rst.next())
            return rst.getString(1);
        return null;
    }

    @Override
    public List<Task> getTaskBySearch(String text, String taskCategory) throws Exception {
        List<Task> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM task WHERE taskStatus=? AND ( taskId LIKE ? OR taskDescription LIKE ? OR productId LIKE ?)",taskCategory,text,text,text);
        while (rst.next()){
            list.add(new Task(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDate(4),rst.getDate(5),
                    rst.getDate(6),rst.getString(7),rst.getDouble(8),rst.getDouble(9)));

        }
        return list;
    }
    @Override
    public List<Task> getAllTaskBySearch(String text) throws Exception {
        List<Task> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM task WHERE  taskId LIKE ? OR taskDescription LIKE ? OR productId LIKE ? OR taskStatus LIKE ? OR taskDate LIKE ?",text,text,text,text,text);
        while (rst.next()){

            list.add(new Task(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDate(4),rst.getTimestamp(5),
                    rst.getTimestamp(6),rst.getString(7),rst.getDouble(8),rst.getDouble(9)));

        }
        return list;
    }

    @Override
    public boolean startTask(String taskId) throws Exception {
        return CrudUtil.execute("UPDATE  task SET taskStartTime=?,taskStatus=? WHERE taskId=? ",new Date(),"PR",taskId);

    }
    @Override
    public boolean finishedTask(String taskId, double proQty,Date finishTime) throws Exception {
       return CrudUtil.execute("UPDATE  task SET taskEndTime=?,actualQty=?,taskStatus=? WHERE taskId=? ",finishTime,proQty,"FI",taskId);

    }


}
