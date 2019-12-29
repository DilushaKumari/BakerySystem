package lk.ijse.dep.project.business.custom;

import lk.ijse.dep.project.business.SuperBO;
import lk.ijse.dep.project.dto.ProductDTO;

import java.util.List;

public interface ProductBO extends SuperBO {
    boolean saveProduct(ProductDTO employee) throws Exception;

    boolean updateProduct(ProductDTO employee) throws Exception;

    boolean deleteProduct(String employeeId) throws Exception;

    List<ProductDTO> findAllProducts() throws Exception;

    List<ProductDTO> findAllProductsBySearch(String text) throws Exception;

    String getLastProductId() throws Exception;

    List<String> getProductId() throws Exception;

    ProductDTO getProductDetails(String productId) throws Exception;

}
