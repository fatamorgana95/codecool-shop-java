package com.codecool.shop.model;

public class LineItem extends BaseModel{

    private final float unitPrice;
    private int quantity;
    private float subTotalPrice = 0;

    private final int productId;

    private final Product product;

    public LineItem(Product product, String name) {
        super(name);
        this.product = product;
        this.productId = product.id;
        this.quantity = 1;
        this.unitPrice = product.getDefaultPrice();
        this.subTotalPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
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
