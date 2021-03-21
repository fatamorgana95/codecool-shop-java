package com.codecool.shop.model;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Crunchify.com
 * How to write JSON object to File in Java?
 */

public class OrderWriter {
    private static FileWriter file;
    public Order order;

    public OrderWriter(Order order) {
        this.order = order;
    }


    @SuppressWarnings("unchecked")
    public void write() {

        // JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONArray personalInfoContainer = new JSONArray();
        JSONObject personalInfo = new JSONObject();
        personalInfo.put("First Name", order.getFirstName());
        personalInfo.put("Last Name", order.getLastName());
        personalInfo.put("Email", order.geteMail());
        personalInfo.put("Phone number", order.getPhoneNumber());
        personalInfoContainer.add(personalInfo);

        JSONArray billingAddressContainer = new JSONArray();
        JSONObject billingAddress = new JSONObject();
        billingAddress.put("Billing city", order.getCity());
        billingAddress.put("Billing country", order.getCountry());
        billingAddress.put("Billing zipcode", order.getZipCode());
        billingAddress.put("Billing address", order.getAddress());
        billingAddressContainer.add(billingAddress);

        JSONArray shippingAddressContainer = new JSONArray();
        JSONObject shippingAddress = new JSONObject();
        shippingAddress.put("Shipping city", order.getShippingCity());
        shippingAddress.put("Shipping country", order.getShippingCountry());
        shippingAddress.put("Shipping zipcode", order.getShippingZipCode());
        shippingAddress.put("Shipping address", order.getShippingAddress());
        shippingAddressContainer.add(shippingAddress);

        JSONArray orderedItems = new JSONArray();
        for (LineItem item: order.getCart().getLineItems()) {
            orderedItems.add(String.format("Item : %s", item.getName()));
        }

        JSONObject obj = new JSONObject();
        obj.put("Personal info", personalInfoContainer);
        obj.put("Billing address", billingAddressContainer);
        obj.put("Shipping address", shippingAddressContainer);
        obj.put("Ordered items", orderedItems);


        try {

            // Constructs a FileWriter given a file name, using the platform's default charset
            OrderDao orderDataStore = OrderDaoMem.getInstance();
            file = new FileWriter("/static/text/order" + orderDataStore.getAll().size() + ".txt");
            file.write(obj.toJSONString());
            CrunchifyLog("Successfully Copied JSON Object to File...");
            CrunchifyLog("\nJSON Object: " + obj);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    static public void CrunchifyLog(String str) {
        System.out.println("str");
    }

}