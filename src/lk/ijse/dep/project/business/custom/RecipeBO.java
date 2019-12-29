package lk.ijse.dep.project.business.custom;

import lk.ijse.dep.project.business.SuperBO;
import lk.ijse.dep.project.dto.RecipeDTO;
import lk.ijse.dep.project.dto.RecipeDTO2;
import lk.ijse.dep.project.entity.Recipe;

import java.util.List;

public interface RecipeBO extends SuperBO {

    boolean saveRecipe(RecipeDTO recipe) throws Exception;

    boolean updateRecipe(RecipeDTO2 recipe) throws Exception;

    boolean deleteRecipe(String recipeId) throws Exception;

    RecipeDTO2 findRecipe(String recipeId) throws Exception;

    String getLastRecipeId() throws Exception;

    List<String> findAllRecipeIds() throws Exception;

    List<RecipeDTO2> findAllRecipes() throws Exception;

}
