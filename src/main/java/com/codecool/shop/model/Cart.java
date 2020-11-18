package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends BaseModel{



    private List<LineItem> lineItems;

    private float totalPrice = 0;

    private static Cart instance;
    public Cart(String name) {
        super(name);
        this.lineItems = new ArrayList<>();
    }

    public void setProducts(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public static Cart getInstance() {
        if (instance == null) {
            String name = "actual";
            instance = new Cart(name);
        }
        return instance;
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
            removeLineItem(lineItem);
        }
    }

    public void removeLineItem(LineItem lineItem) {
        lineItems.remove(lineItem);
    }

/*    public void changeQuantityInLineItem(LineItem lineItem, int quantity) {
        int prevQuantity = lineItem.getQuantity();
        lineItem.setQuantity(quantity);
        if (lineItem.isClearable()) {
            lineItems.remove(lineItem);
        } else {
            int actualQuantity = lineItem.getQuantity();
            int difference = actualQuantity-prevQuantity;
            totalPrice += difference * lineItem.getDefaultPrice(); //downcast
        }

    }*/

//optional?
    private LineItem getLineItemByProductId(int productId) {
        return lineItems.stream().filter(item -> item.getProductId() == productId).findFirst().orElse(null);
    }

    public LineItem getLineItemByLineItemId(int itemId) {
        return lineItems.stream().filter(item -> item.getId() == itemId).findFirst().orElse(null);
    }


    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, ",
                this.id,
                this.name
        );
    }

    public int getAddedProductsQuantity() {
        return lineItems.stream().mapToInt(LineItem::getQuantity).sum();
    }
}
