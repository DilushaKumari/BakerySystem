package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.RecipeIngredient;
import lk.ijse.dep.project.entity.RecipeIngredientPK;

public interface RecipeIngredientDAO extends CrudDAO<RecipeIngredient, RecipeIngredientPK> {
    boolean existsByIngId(String ingId) throws Exception;
}
