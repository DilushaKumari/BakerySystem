package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.Employee;
import lk.ijse.dep.project.entity.Ingredient;

import java.util.List;

public interface IngredientDAO  extends CrudDAO<Ingredient,String> {
    String getLastIngId() throws Exception;
    List<Ingredient> getIngredientsBySearch(String text) throws Exception;
}
