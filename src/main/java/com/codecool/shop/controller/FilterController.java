package com.codecool.shop.controller;

import com.codecool.shop.config.DaoSelector;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/filter/"})
public class FilterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("category");
        String supplier = req.getParameter("supplier");

        List<Product> filteredProducts = new ArrayList<>();
        //String daoType = DaoSelector.select();
        ProductDao productDataStore = ProductDaoMem.getInstance();

        if (category.equals("All Categories") && supplier.equals("All Suppliers")) {
            filteredProducts = productDataStore.getAll();
        }
        else if (!category.equals("All Categories") && supplier.equals("All Suppliers")) {
            for (Product product: productDataStore.getAll()) {
                if (product.getProductCategory().getName().equals(category)) {
                    filteredProducts.add(product);
                }
            }
        }
        else if (category.equals("All Categories") && !supplier.equals("All Suppliers")) {
            for (Product product : productDataStore.getAll()) {
                if (product.getSupplier().getName().equals(supplier)) {
                    filteredProducts.add(product);
                }
            }
        }
        else {
            for (Product product : productDataStore.getAll()) {
                if (product.getProductCategory().getName().equals(category) && product.getSupplier().getName().equals(supplier)) {
                    filteredProducts.add(product);
                }
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(filteredProducts);
        PrintWriter out = resp.getWriter();
        out.println(json);
    }

}


