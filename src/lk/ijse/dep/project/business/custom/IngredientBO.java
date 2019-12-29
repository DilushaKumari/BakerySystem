package lk.ijse.dep.project.business.custom;

import lk.ijse.dep.project.business.SuperBO;
import lk.ijse.dep.project.dto.IngredientDTO;
import lk.ijse.dep.project.dto.IngredientDTO;

import java.util.List;

public interface IngredientBO extends SuperBO {

    boolean saveIngredient(IngredientDTO ingredient) throws Exception;

    boolean updateIngredient(IngredientDTO ingredient) throws Exception;

    boolean deleteIngredient(String ingredientId) throws Exception;

    List<IngredientDTO> getAllIngredients() throws Exception;

    List<IngredientDTO> findAllIngredientsBySearch(String text) throws Exception;

    String getLastIngredientId() throws Exception;

    List<String> getAllIngredientsIds() throws Exception;

    List<String> getProductIngIds(String productId) throws Exception;

    IngredientDTO findIngrediet(String ingId) throws Exception;

    List<IngredientDTO> getAllIngForRcpId(String rcpId) throws Exception;


}
