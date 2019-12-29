package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.TaskIngredientDAO;
import lk.ijse.dep.project.entity.EmployeeAssign;
import lk.ijse.dep.project.entity.TaskIngredient;
import lk.ijse.dep.project.entity.TaskIngredientPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskIngredientDAOImpl implements TaskIngredientDAO {
    @Override
    public List<TaskIngredient> findAll() throws Exception {

        List<TaskIngredient> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM taskingredient");
        while (rst.next()) {
            list.add(new TaskIngredient(rst.getString(1), rst.getString(2), rst.getDouble(3)));
        }
        return list;
    }

    @Override
    public TaskIngredient find(TaskIngredientPK taskIngredientPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM taskingredient WHERE taskId=? AND ingId=?", taskIngredientPK.getTaskId(), taskIngredientPK.getIngId());
        if (rst.next()) {
            return new TaskIngredient(rst.getString(1), rst.getString(2), rst.getDouble(3));
        }
        return null;
    }

    @Override
    public boolean save(TaskIngredient entity) throws Exception {
        return CrudUtil.execute("INSERT INTO taskingredient VALUES (?,?,?)",entity.getTaskIngredientPK().getTaskId(),entity.getTaskIngredientPK().getIngId(),entity.getIngQty());

    }

    @Override
    public boolean update(TaskIngredient entity) throws Exception {
        return CrudUtil.execute("UPDATE taskingredient SET ingQty=? WHERE taskId=? AND ingId=?",entity.getIngQty(),entity.getTaskIngredientPK().getTaskId(),entity.getTaskIngredientPK().getIngId());

    }

    @Override
    public boolean delete(TaskIngredientPK taskIngredientPK) throws Exception {
        return CrudUtil.execute("DELETE FROM taskingredient WHERE taskId=? AND ingId=?",taskIngredientPK.getTaskId(),taskIngredientPK.getIngId());
    }
}
