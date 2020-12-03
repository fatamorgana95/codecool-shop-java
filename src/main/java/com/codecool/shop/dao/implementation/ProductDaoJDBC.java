package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionSetter;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private static ProductDaoJDBC instance = null;
    private final DataSource dataSource;

    public ProductDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC(ConnectionSetter.connect());
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, product.getName());
            st.setFloat(2, product.getDefaultPrice());
            //st.setString(3, product.getDefaultCurrency().getCurrencyCode());
            st.setObject(3, product.getDefaultCurrency());
            st.setString(4, product.getDescription());
            st.setInt(5, product.getProductCategory().getId());
            st.setString(6, product.getSupplier().getName());
            st.setString(7, product.getImage());
            st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while reading product with id: ", throwable);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT name, default_price, default_currency, description, product_category_id, supplier, image FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            ProductCategory productCategory = ProductCategoryDaoJDBC.getInstance().find(resultSet.getInt(5));
            SupplierDao supplierDao = SupplierDaoMem.getInstance();
            Supplier supplier = null;
            for (Supplier suppy : supplierDao.getAll()) {
                if (suppy.getName().equals(resultSet.getString(6))) {
                    supplier = suppy;
                }
            }
            Product product = new Product(resultSet.getString(1), resultSet.getFloat(2), resultSet.getString(3), resultSet.getString(4), productCategory, supplier, resultSet.getString(7));
            product.setId(id);
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product category with id: " + id, e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection connection = dataSource.getConnection()) {
            int productCategoryId = productCategory.getId();
            String sql = "SELECT id, name, default_price, default_currency, description, product_category_id, supplier, image FROM product WHERE product_category_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, productCategoryId);
            ResultSet rs = st.executeQuery();
            List<Product> result = new ArrayList<>();
            SupplierDao supplierDao = SupplierDaoMem.getInstance();
            Supplier supplier = null;
            for (Supplier suppy : supplierDao.getAll()) {
                if (suppy.getName().equals(rs.getString(7))) {
                    supplier = suppy;
                }
            }
            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5), productCategory, supplier, rs.getString(8));
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all products", e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing product with id: " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, default_price, default_currency, description, product_category_id, supplier, image FROM product";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                ProductCategory productCategory = ProductCategoryDaoJDBC.getInstance().find(rs.getInt(6));
                SupplierDao supplierDao = SupplierDaoMem.getInstance();
                Supplier supplier = new Supplier("mama", "help me");
                for (Supplier suppy : supplierDao.getAll()) {
                    if (suppy.getName().equals(rs.getString(7))) {
                        supplier = suppy;
                    }
                }
                Product product = new Product(rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5), productCategory, supplier, rs.getString(8));
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all product categories", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, default_price, default_currency, description, product_category_id, image FROM product WHERE supplier = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, supplier.getName());
            ResultSet rs = st.executeQuery();
            List<Product> result = new ArrayList<>();
            ProductCategory productCategory = ProductCategoryDaoJDBC.getInstance().find(rs.getInt(6));
            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5), productCategory, supplier, rs.getString(7));
                product.setId(rs.getInt(1));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all products", e);
        }
    }
}