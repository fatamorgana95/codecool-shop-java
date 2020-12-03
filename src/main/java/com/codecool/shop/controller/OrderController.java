package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("order.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String eMail = req.getParameter("eMail");
        String phoneNumber = req.getParameter("phoneNumber");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String zipCode = req.getParameter("zipCode");
        String address = req.getParameter("address");
        String shippingCountry = req.getParameter("shippingCountry");
        String shippingCity = req.getParameter("shippingCity");
        String shippingZipCode = req.getParameter("shippingZipCode");
        String shippingAddress = req.getParameter("shippingAddress");

        Order order = new Order(firstName, lastName, eMail, phoneNumber, country, city, zipCode, address, shippingCountry, shippingCity, shippingZipCode, shippingAddress);

        logger.info("Created new order.");

        OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();
        orderDaoMem.add(order);
    }
}
