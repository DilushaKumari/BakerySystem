package lk.ijse.dep.project.business.custom.impl;

import lk.ijse.dep.project.business.custom.ProductBO;
import lk.ijse.dep.project.business.exception.AllReadyExistsProductIdInTaskException;
import lk.ijse.dep.project.dao.DAOFactory;
import lk.ijse.dep.project.dao.DAOTypes;
import lk.ijse.dep.project.dao.custom.ProductDAO;
import lk.ijse.dep.project.dao.custom.TaskDAO;
import lk.ijse.dep.project.dto.ProductDTO;
import lk.ijse.dep.project.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = DAOFactory.getInstance().getDAO(DAOTypes.PRODUCT);
    TaskDAO taskDAO = DAOFactory.getInstance().getDAO(DAOTypes.TASK);

    @Override
    public boolean saveProduct(ProductDTO product) throws Exception {
        return productDAO.save(new Product(product.getProductId(), product.getRcpId(), product.getProductName(), product.getProductQty()));

    }

    @Override
    public boolean updateProduct(ProductDTO product) throws Exception {
        return productDAO.update(new Product(product.getProductId(), product.getRcpId(), product.getProductName(), product.getProductQty()));
    }

    @Override
    public boolean deleteProduct(String productId) throws Exception {
        if (taskDAO.existsByProductId(productId)) {
            throw new AllReadyExistsProductIdInTaskException("This product already exists in a task, hence unable to delete");
        }
        return taskDAO.delete(productId);
    }

    @Override
    public List<ProductDTO> findAllProducts() throws Exception {
        List<ProductDTO> productsToController = new ArrayList<>();
        List<Product> productsFromDAO = productDAO.findAll();
        for (Product product : productsFromDAO) {
            productsToController.add(new ProductDTO(product.getProductId(), product.getRcpId(), product.getProductName(), product.getProductQty()));
        }
        return productsToController;
    }

    @Override
    public List<ProductDTO> findAllProductsBySearch(String text) throws Exception {
        List<ProductDTO> productsToController = new ArrayList<>();
        List<Product> productsFromDAO = productDAO.getAllProductsBySearch(text);
        for (Product product : productsFromDAO) {
            productsToController.add(new ProductDTO(product.getProductId(), product.getRcpId(), product.getProductName(), product.getProductQty()));
        }
        return productsToController;
    }

    @Override
    public String getLastProductId() throws Exception {
        return productDAO.getLastProductId();
    }

    @Override
    public List<String> getProductId() throws Exception {
        List<Product> all = productDAO.findAll();
        List<String> list = new ArrayList<>();
        for (Product product : all) {
            list.add(product.getProductId());
        }
        return list;
    }

    @Override
    public ProductDTO getProductDetails(String productId) throws Exception {
        Product product = productDAO.find(productId);
        return new ProductDTO(product.getProductId(), product.getRcpId(), product.getProductName(), product.getProductQty());
    }
}
