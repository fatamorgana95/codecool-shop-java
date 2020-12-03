package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart/change"})
public class CartChangeController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartChangeController.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int itemId =  Integer.parseInt(req.getParameter("itemId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        quantity = Math.max(quantity, 1);

        Cart cart = Cart.getInstance();

        LineItem item = cart.getLineItemByLineItemId(itemId);
        cart.changeQuantityInLineItem(item, quantity);

        logger.info(String.format("Changed %s's quantity to %s.", item.getName(), quantity));

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

