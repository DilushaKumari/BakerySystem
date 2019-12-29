package lk.ijse.dep.project.dao.custom.impl;

import lk.ijse.dep.project.dao.CrudUtil;
import lk.ijse.dep.project.dao.custom.ProductDAO;
import lk.ijse.dep.project.entity.Ingredient;
import lk.ijse.dep.project.entity.Product;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM product");
        while (rst.next()){
            list.add(new Product(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4)));        }
        return list;
    }

    @Override
    public Product find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM product WHERE productId=?",s);
        if (rst.next()){
            return new Product(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));        }
        return null;
    }

    @Override
    public boolean save(Product entity) throws Exception {
        return CrudUtil.execute("INSERT INTO product VALUES (?,?,?,?)", entity.getProductId(), entity.getRcpId(), entity.getProductName(),entity.getProductQty());

    }

    @Override
    public boolean update(Product entity) throws Exception {
        return CrudUtil.execute("UPDATE  product SET rcpId=?,productName=? ,productQty=? WHERE productId=?", entity.getRcpId(), entity.getProductName(), entity.getProductQty(),entity.getProductId());

    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM product WHERE productId =? ", s);

    }

    @Override
    public String getLastProductId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT productId FROM product ORDER BY productId DESC LIMIT 1");
        if(rst.next())
            return rst.getString(1);
        return null;
    }

    @Override
    public List<Product> getAllProductsBySearch(String text) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM product WHERE productId LIKE ? OR productName LIKE ? OR rcpId LIKE ?",text,text,text);
       List<Product> productToBO = new ArrayList<>();
        while (rst.next()){
            productToBO.add(new Product(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4)));        }
        return productToBO;
    }

    @Override
    public boolean existsByRcpId(String recipeId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM product WHERE rcpId=?", recipeId);
        return rst.next();
    }
}
