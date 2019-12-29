package lk.ijse.dep.project.business.custom.impl;

import lk.ijse.dep.project.business.custom.IngredientBO;
import lk.ijse.dep.project.business.exception.AllReadyExistsIngIdInRecipeException;
import lk.ijse.dep.project.dao.DAOFactory;
import lk.ijse.dep.project.dao.DAOTypes;
import lk.ijse.dep.project.dao.custom.IngredientDAO;
import lk.ijse.dep.project.dao.custom.QueryDAO;
import lk.ijse.dep.project.dao.custom.RecipeIngredientDAO;
import lk.ijse.dep.project.dto.IngredientDTO;
import lk.ijse.dep.project.entity.CustomEntity;
import lk.ijse.dep.project.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientBOImpl implements IngredientBO {
    IngredientDAO ingredientDAO = DAOFactory.getInstance().getDAO(DAOTypes.INGREDIENT);
    RecipeIngredientDAO recipeIngredientDAO = DAOFactory.getInstance().getDAO(DAOTypes.RECIPE_INGREDIENT);
    QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);

    @Override
    public boolean saveIngredient(IngredientDTO ingredient) throws Exception {
        return ingredientDAO.save(new Ingredient(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty()));
    }

    @Override
    public boolean updateIngredient(IngredientDTO ingredient) throws Exception {
        return ingredientDAO.update(new Ingredient(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty()));

    }

    @Override
    public boolean deleteIngredient(String ingredientId) throws Exception {
        if (recipeIngredientDAO.existsByIngId(ingredientId)) {
            throw new AllReadyExistsIngIdInRecipeException("This Ingredient already exists in a Recipe, hence unable to delete");
        }
        return ingredientDAO.delete(ingredientId);
    }

    @Override
    public List<IngredientDTO> getAllIngredients() throws Exception {
        List<IngredientDTO> list = new ArrayList<>();
        List<Ingredient> all = ingredientDAO.findAll();
        for (Ingredient ingredient : all) {
            list.add(new IngredientDTO(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty()));

        }

        return list;
    }

    @Override
    public List<IngredientDTO> findAllIngredientsBySearch(String text) throws Exception {
        List<Ingredient> allIngredients = ingredientDAO.getIngredientsBySearch(text);
        List<IngredientDTO> ingredients = new ArrayList<>();

        for (Ingredient ingredient : allIngredients) {
            ingredients.add(new IngredientDTO(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty()));
        }
        return ingredients;

    }


    @Override
    public String getLastIngredientId() throws Exception {
        return ingredientDAO.getLastIngId();
    }

    @Override
    public List<String> getAllIngredientsIds() throws Exception {
        List<String> allIngId = new ArrayList<>();
        List<Ingredient> all = ingredientDAO.findAll();
        for (Ingredient ingredient : all) {
            allIngId.add(ingredient.getIngId());
        }
        return allIngId;
    }

    @Override
    public List<String> getProductIngIds(String productId) throws Exception {
        List<String> allIngId = new ArrayList<>();
        List<String> all = queryDAO.gettingProductIngIdsList(productId);
        for (String ingredient : all) {
            allIngId.add(ingredient);
        }
        return allIngId;
    }

    @Override
    public IngredientDTO findIngrediet(String ingId) throws Exception {
        Ingredient ingredient = ingredientDAO.find(ingId);
        return new IngredientDTO(ingredient.getIngId(), ingredient.getIngName(), ingredient.getIngQty());
    }

    @Override
    public List<IngredientDTO> getAllIngForRcpId(String rcpId) throws Exception {
        List<CustomEntity> allIngredients = queryDAO.gettingRcpIngList(rcpId);
        List<IngredientDTO> ingredients = new ArrayList<>();

        for (CustomEntity ingredient : allIngredients) {
            ingredients.add(new IngredientDTO(ingredient.getIngId(), ingredient.getIngDes(), ingredient.getIngQty()));
        }
        return ingredients;
    }

}
