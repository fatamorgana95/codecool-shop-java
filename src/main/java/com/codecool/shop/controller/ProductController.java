package com.codecool.shop.controller;

import com.codecool.shop.config.DaoSelector;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String daoType = DaoSelector.select();
        ProductDao productDataStore = daoType.equals("memory") ? ProductDaoMem.getInstance() : ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDataStore = daoType.equals("memory") ? ProductCategoryDaoMem.getInstance() : ProductCategoryDaoJDBC.getInstance();
 
        SupplierDao supplierDao = SupplierDaoMem.getInstance();

        Cart cart = Cart.getInstance();


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("supplier", supplierDao.getAll());
        context.setVariable("addedProductsQuantity", cart.getAddedProductsQuantity());


        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId =  Integer.parseInt(req.getParameter("productId"));
        String daoType = DaoSelector.select();
        ProductDao productDataStore = daoType.equals("memory") ? ProductDaoMem.getInstance() : ProductDaoJDBC.getInstance();
        Product product = productDataStore.find(productId);
        Cart cart = Cart.getInstance();
        cart.addProduct(product);

        CartDao cartDataStore = CartDaoMem.getInstance();
        if (cartDataStore.find(cart.getId()) == null) {
            cartDataStore.add(cart);
        }

        Gson gson = new Gson();

        String productsQuantity = gson.toJson(cart.getAddedProductsQuantity());

        resp.getWriter().write(productsQuantity);
    }

}
