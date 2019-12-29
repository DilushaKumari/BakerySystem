package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.RecipeDAO;
import lk.ijse.dep.project.dao.custom.RecipeIngredientDAO;
import lk.ijse.dep.project.entity.EmployeeRegister;
import lk.ijse.dep.project.entity.RecipeIngredient;
import lk.ijse.dep.project.entity.RecipeIngredientPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientDAOImpl implements RecipeIngredientDAO{
    @Override
    public List<RecipeIngredient> findAll() throws Exception {
        List<RecipeIngredient> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM recipeingredient");
        while (rst.next()){
            list.add(new RecipeIngredient(rst.getString(1),rst.getString(2),rst.getDouble(3)));
        }
        return list;
    }

    @Override
    public RecipeIngredient find(RecipeIngredientPK recipeIngredientPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM recipeingredient WHERE rcpId=? AND ingId=? ",recipeIngredientPK.getRcpId(),recipeIngredientPK.getIngId());
        if (rst.next()){
          new RecipeIngredient(rst.getString(1),rst.getString(2),rst.getDouble(3));
        }
        return null;
    }

    @Override
    public boolean save(RecipeIngredient entity) throws Exception {
        return CrudUtil.execute("INSERT INTO recipeingredient VALUES (?,?,?)",entity.getRecipeIngredientPK().getRcpId(),entity.getRecipeIngredientPK().getIngId(),entity.getIngQty());

    }

    @Override
    public boolean update(RecipeIngredient entity) throws Exception {
        return CrudUtil.execute("UPDATE  recipeingredient SET ingQty=? WHERE rcpId=? AND ingId=?  ",entity.getIngQty(),entity.getRecipeIngredientPK().getRcpId(),entity.getRecipeIngredientPK().getIngId());

    }

    @Override
    public boolean delete(RecipeIngredientPK recipeIngredientPK) throws Exception {
        return CrudUtil.execute("DELETE FROM recipeingredient WHERE rcpId=? AND ingId=? ",recipeIngredientPK. getRcpId(),recipeIngredientPK.getIngId());

    }

    @Override
    public boolean existsByIngId(String ingId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM recipeingredient WHERE ingId=?", ingId);
        return rst.next();
    }

}
