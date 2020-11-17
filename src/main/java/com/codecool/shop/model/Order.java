package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel{

    private List<LineItem> lineItems;
    private int totalPrice = 0;

    public Order(String name, Product product) {
        super(name);
        this.lineItems = new ArrayList<>();
        addProduct(product);
    }

    public void setProducts(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }


    public void addProduct(Product product) {
        LineItem lineItem = getLineItemByProductId(product.getId());
        if (lineItem == null) {
            String name = product.getName();
            LineItem newLineItem = new LineItem(product, name);
            lineItems.add(newLineItem);
        } else {
            lineItem.increaseQuantity();
        }
        totalPrice += product.getDefaultPrice();
    }


    public void removeProduct(Product product) {
        int productId = product.getId();
        LineItem lineItem = getLineItemByProductId(productId);
        lineItem.decreaseQuantity();
        totalPrice -= product.getDefaultPrice();
        if (lineItem.isClearable()) {
            lineItems.remove(lineItem);
        }
    }

/*    public void changeQuantityInLineItem(LineItem lineItem, int quantity) {
        int prevQuantity = lineItem.getQuantity();
        lineItem.setQuantity(quantity);
        if (lineItem.isClearable()) {
            lineItems.remove(lineItem);
        } else {
            int actualQuantity = lineItem.getQuantity();
            int difference = actualQuantity-prevQuantity;
            totalPrice += difference * lineItem.getDefaultPrice();
        }

    }*/

//optional?
    private LineItem getLineItemByProductId(int productId) {
        for (LineItem item : lineItems){
            if (item.id == productId){
                return item;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, ",
                this.id,
                this.name
        );
    }
}
