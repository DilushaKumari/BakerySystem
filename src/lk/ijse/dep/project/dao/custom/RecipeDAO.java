package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.Recipe;

public interface RecipeDAO extends CrudDAO<Recipe,String> {
 String   getLastRecipeId() throws Exception;
}
