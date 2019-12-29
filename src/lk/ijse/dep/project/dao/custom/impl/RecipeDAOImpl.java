package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.RecipeDAO;
import lk.ijse.dep.project.entity.Product;
import lk.ijse.dep.project.entity.Recipe;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAOImpl implements RecipeDAO {
    @Override
    public List<Recipe> findAll() throws Exception {
        List<Recipe> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM recipe");
        while (rst.next()) {
            list.add(new Recipe(rst.getString(1), rst.getString(2), rst.getDate(3)));
        }
        return list;
    }

    @Override
    public Recipe find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM recipe WHERE rcpId=?", s);
        if (rst.next()) {
            return new Recipe(rst.getString(1), rst.getString(2), rst.getDate(3));
        }
        return null;
    }

    @Override
    public boolean save(Recipe entity) throws Exception {
        return CrudUtil.execute("INSERT INTO recipe VALUES (?,?,?)", entity.getRcpId(), entity.getRcpDes(), entity.getRcpDate());

    }

    @Override
    public boolean update(Recipe entity) throws Exception {
        return CrudUtil.execute("UPDATE  recipe SET rcpDes=?,rcpDate=?  WHERE rcpId=?", entity.getRcpDes(), entity.getRcpDate(), entity.getRcpId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM recipe WHERE rcpId =? ", s);

    }

    @Override
    public String getLastRecipeId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT rcpId FROM recipe ORDER BY rcpId DESC LIMIT 1 ");
        if (rst.next()){
           return rst.getString(1);
        }
        return null;
    }
}
