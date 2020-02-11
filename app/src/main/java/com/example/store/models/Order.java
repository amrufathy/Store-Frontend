package com.example.store.models;

public class Order {
    private Integer id;
    private Integer order_id;
    private Double cost;
    private String created_at;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer order_id) {
        this.order_id = order_id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
}
