package lk.ijse.dep.project.business.custom.impl;

import lk.ijse.dep.project.business.custom.ProductBO;
import lk.ijse.dep.project.business.custom.RecipeBO;
import lk.ijse.dep.project.business.exception.AllReadyExisitsRcpIdInProductException;
import lk.ijse.dep.project.business.exception.AllReadyExistsProductIdInTaskException;
import lk.ijse.dep.project.dao.DAOFactory;
import lk.ijse.dep.project.dao.DAOTypes;
import lk.ijse.dep.project.dao.custom.ProductDAO;
import lk.ijse.dep.project.dao.custom.RecipeDAO;
import lk.ijse.dep.project.dao.custom.RecipeIngredientDAO;
import lk.ijse.dep.project.db.DBConnection;
import lk.ijse.dep.project.dto.RecipeDTO;
import lk.ijse.dep.project.dto.RecipeDTO2;
import lk.ijse.dep.project.dto.RecipeIngDTO;
import lk.ijse.dep.project.entity.Recipe;
import lk.ijse.dep.project.entity.RecipeIngredient;
import org.springframework.core.annotation.Order;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecipeBOImpl implements RecipeBO {

    RecipeDAO recipeDAO = DAOFactory.getInstance().getDAO(DAOTypes.RECIPE);
    RecipeIngredientDAO recipeIngredientDAO = DAOFactory.getInstance().getDAO(DAOTypes.RECIPE_INGREDIENT);
    ProductDAO productDAO = DAOFactory.getInstance().getDAO(DAOTypes.PRODUCT);

    @Override
    public boolean saveRecipe(RecipeDTO recipe) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            connection.setAutoCommit(false);

            String rcpId = recipe.getRcpId();
            boolean result = recipeDAO.save(new Recipe(rcpId, recipe.getRcpDes(), recipe.getRcpDate()));

            if (!result) {
                connection.rollback();
                throw new RuntimeException("Something happen when save recipe");
            }


            for (RecipeIngDTO recipeIngredient : recipe.getRcpIngDetails()) {
                result = recipeIngredientDAO.save(new RecipeIngredient(rcpId, recipeIngredient.getIngId(),
                        recipeIngredient.getIngQty()));

                if (!result) {
                    connection.rollback();
                    throw new RuntimeException("Something happen when save recipe ingredient details");
                }
            }

            connection.commit();
            return true;

        } catch (Throwable e) {

            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateRecipe(RecipeDTO2 recipe) throws Exception {
        return recipeDAO.update(new Recipe(recipe.getRcpId(), recipe.getRcpDes(), recipe.getRcpDate()));
    }


    @Override
    public boolean deleteRecipe(String recipeId) throws Exception {
        if (productDAO.existsByRcpId(recipeId)) {
            throw new AllReadyExisitsRcpIdInProductException("This recipe already exists in a product, hence unable to delete");
        }
        return recipeDAO.delete(recipeId);
    }

    @Override
    public RecipeDTO2 findRecipe(String rcpId) throws Exception {
        Recipe recipe = recipeDAO.find(rcpId);

        return new RecipeDTO2(recipe.getRcpId(), recipe.getRcpDes(), recipe.getRcpDate());
    }

    @Override
    public List<RecipeDTO2> findAllRecipes() throws Exception {
        List<Recipe> recipesFromDAO = recipeDAO.findAll();
        List<RecipeDTO2> recipesToBO = new ArrayList<>();

        for (Recipe recipe : recipesFromDAO) {
            recipesToBO.add(new RecipeDTO2(recipe.getRcpId(), recipe.getRcpDes(), recipe.getRcpDate()));
        }
        return recipesToBO;
    }


    @Override
    public String getLastRecipeId() throws Exception {
        return recipeDAO.getLastRecipeId();
    }

    @Override
    public List<String> findAllRecipeIds() throws Exception {
        List<Recipe> recipesFromDAO = recipeDAO.findAll();
        List<String> recipeIdsToBO = new ArrayList<>();

        for (Recipe recipe : recipesFromDAO) {
            recipeIdsToBO.add(recipe.getRcpId());
        }
        return recipeIdsToBO;
    }
}
