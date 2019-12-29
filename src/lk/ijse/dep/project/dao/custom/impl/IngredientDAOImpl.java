package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.IngredientDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.EmployeeRegister;
import lk.ijse.dep.project.entity.Ingredient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO {
    @Override
    public List<Ingredient> findAll() throws Exception {
        List<Ingredient> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM ingredient");
        while (rst.next()) {
            list.add(new Ingredient(rst.getString(1), rst.getString(2), rst.getDouble(3)));
        }
        return list;
    }

    @Override
    public Ingredient find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM ingredient WHERE ingId=?", s);
        if (rst.next()) {
            return new Ingredient(rst.getString(1), rst.getString(2), rst.getDouble(3));
        }
        return null;
    }

    @Override
    public boolean save(Ingredient entity) throws Exception {
        return CrudUtil.execute("INSERT INTO ingredient VALUES (?,?,?)", entity.getIngId(), entity.getIngName(), entity.getIngQty());

    }

    @Override
    public boolean update(Ingredient entity) throws Exception {
        return CrudUtil.execute("UPDATE  ingredient SET ingName=?,ingQty=? WHERE ingId=?", entity.getIngName(), entity.getIngQty(), entity.getIngId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM ingredient WHERE ingId=? ", s);
    }

    @Override
    public String getLastIngId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT ingId FROM ingredient ORDER BY ingId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Ingredient> getIngredientsBySearch(String text) throws Exception {
        List<Ingredient> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM ingredient WHERE ingId LIKE ? OR ingName LIKE ? OR ingQty LIKE ? ",text,text,text);
        while (rst.next()){
            list.add(new Ingredient(rst.getString(1),rst.getString(2),rst.getDouble(3)));
        }
        return list;
    }
}
