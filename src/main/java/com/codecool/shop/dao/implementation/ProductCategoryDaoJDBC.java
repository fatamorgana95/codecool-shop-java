package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionSetter;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private static ProductCategoryDaoJDBC instance = null;
    private final DataSource dataSource;

    public ProductCategoryDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC(ConnectionSetter.connect());
        }
        return instance;
    }


    @Override
    public void add(ProductCategory category) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product_category (name, description, department) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, category.getName());
            st.setString(2, category.getDescription());
            st.setString(3, category.getDepartment());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next(); // Read next returned value - in this case the first one. See ResultSet docs for more explaination
            category.setId(rs.getInt(1));

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product Category.", throwable);
        }
    }


    @Override
    public ProductCategory find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description, department FROM product_category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ProductCategory category = new ProductCategory(rs.getString(1), rs.getString(2), rs.getString(3));
            category.setId(id);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product category with id: " + id, e);
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, name, description, department FROM product_category";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ProductCategory> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                ProductCategory category = new ProductCategory(rs.getString(2), rs.getString(4), rs.getString(3));
                category.setId(rs.getInt(1));
                result.add(category);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all product categories", e);
        }
    }


    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM product_category WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing product category with id: " + id, e);
        }
    }

    @Override
    public void removeAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM product_category";
            PreparedStatement st = conn.prepareStatement(sql);
            st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing all product categories.");
        }
    }


}
