package com.example.store.models;

import org.json.JSONException;
import org.json.JSONObject;

public class CartItem {
    private String customer_id;
    private Integer product_id;
    private String product_name;
    private Integer quantity;
    private Double cost;

    public CartItem(String customer_id, Integer product_id, String product_name, Integer quantity, Double cost) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public Integer getProductId() {
        return product_id;
    }

    public String getProductName() {
        return product_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getCost() {
        return cost;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("customer_id", customer_id);
            obj.put("product_id", product_id);
            obj.put("quantity", quantity);
            obj.put("cost", cost);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
