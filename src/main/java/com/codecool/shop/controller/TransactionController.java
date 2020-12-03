package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/transaction"})
public class TransactionController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isCompleted = req.getParameter("transaction");

        OrderDao orderDataStore = OrderDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if (isCompleted.equals("error")) {
            logger.error("Transaction failed.");
            engine.process("transaction_error.html", context, resp.getWriter());
        }
        else {
            Cart cart = Cart.getInstance();
            Order lastOrder = orderDataStore.findLast();
            lastOrder.setCart(cart);
            context.setVariable("order", lastOrder);

            logger.error("Transaction completed.");
            engine.process("transaction_completed.html", context, resp.getWriter());
            cart.setProducts(new ArrayList<>());
        }
    }
}
