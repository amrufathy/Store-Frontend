package com.example.store.models;

public class CartItem {
    private Integer customer_id;
    private Integer product_id;
    private String product_name;
    private Integer quantity;
    private Double cost;

    public CartItem() {

    }

    public CartItem(Integer customer_id, Integer product_id, String product_name, Integer quantity, Double cost) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Integer getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
