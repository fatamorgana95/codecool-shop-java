package com.codecool.shop.model;

public class LineItem extends BaseModel{

    private int quantity = 0;
    private int subTotalPrice = 0;

    private final Product product;

    public LineItem(Product product, String name) {
        super(name);
        this.product = product;
        increaseQuantity();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity ++;
        this.subTotalPrice += product.getDefaultPrice();
    }

    public void decreaseQuantity() {
        this.quantity --;
        this.subTotalPrice -= product.getDefaultPrice();
    }

    public boolean isClearable() {
        return (quantity < 1);
    }
}
