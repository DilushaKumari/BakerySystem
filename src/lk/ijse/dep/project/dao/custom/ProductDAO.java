package lk.ijse.dep.project.dao.custom;

import lk.ijse.dep.project.dao.CrudDAO;
import lk.ijse.dep.project.entity.Product;

import java.util.List;

public interface ProductDAO extends CrudDAO<Product,String> {
  String   getLastProductId() throws Exception;
  List<Product> getAllProductsBySearch(String text) throws Exception;
  boolean existsByRcpId(String recipeId) throws Exception;

}
