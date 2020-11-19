package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart/change"})
public class CartChangeController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int itemId =  Integer.parseInt(req.getParameter("itemId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        quantity = Math.max(quantity, 1);

        Cart cart = Cart.getInstance();

        LineItem item = cart.getLineItemByLineItemId(itemId);
        cart.changeQuantityInLineItem(item, quantity);

//        if (cart.getLineItems().size() == 0){
//            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
//            WebContext context = new WebContext(req, resp, req.getServletContext());
//            engine.process("empty_cart.html", context, resp.getWriter());
//        }
//        else {

            String subtotalPrice = String.format("%s %s", item.getSubTotalPrice(), item.getDefaultCurrency());
            String totalPrice = String.format("%s %s", cart.getTotalPrice(), item.getDefaultCurrency());
            resp.getWriter().write(subtotalPrice + "," + totalPrice);
    }




}

