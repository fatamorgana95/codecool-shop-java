package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
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

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDataStore = CartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        try {
            Cart lastCart = ((CartDaoMem) cartDataStore).findLast();
            if (lastCart.getLineItems().size() == 0){
                engine.process("empty_cart.html", context, resp.getWriter());
            }
            else {
                context.setVariable("cart", lastCart);
                engine.process("cart.html", context, resp.getWriter());
            }
        }
        catch (IndexOutOfBoundsException e) {
            engine.process("empty_cart.html", context, resp.getWriter());
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int itemId =  Integer.parseInt(req.getParameter("itemId"));
        Cart cart = Cart.getInstance();

        LineItem item = cart.getLineItemByLineItemId(itemId);
        cart.changeQuantityInLineItem(item, 0);


        String totalPrice = String.format("%s %s",cart.getTotalPrice(), cart.getDefaultCurrency());

        resp.getWriter().write(totalPrice);

    }




}
