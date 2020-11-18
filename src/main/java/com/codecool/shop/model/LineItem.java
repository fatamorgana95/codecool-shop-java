package com.codecool.shop.model;

public class LineItem extends BaseModel{

    private final float UNIT_PRICE;
    private int quantity;
    private float subTotalPrice = 0;
    private final int productId;

    private final Product product;

    public LineItem(Product product, String name) {
        super(name);
        this.product = product;
        this.productId = product.id;
        this.quantity = 1;
        this.UNIT_PRICE = product.getDefaultPrice();
        this.subTotalPrice = UNIT_PRICE;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return UNIT_PRICE;
    }

    public float getSubTotalPrice() {
        return subTotalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subTotalPrice = quantity * product.getDefaultPrice();
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
