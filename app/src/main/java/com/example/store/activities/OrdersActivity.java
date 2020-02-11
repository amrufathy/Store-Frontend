package com.example.store.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapters.OrdersAdapter;
import com.example.store.models.Order;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersActivity extends HomeActivity {

    private ArrayList<Order> ordersList;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_orders);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_orders, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Orders");

        fb_cartCheckout.setVisibility(View.INVISIBLE);

        final RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl = getString(R.string.backend_base_url) + "customer/orders";
        String url = String.format("%s?token=%s", baseUrl, AccessToken.getCurrentAccessToken().getToken());

        ordersList = new ArrayList<>();
        final ListView listView = findViewById(R.id.lv_orders_list);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);

                                Order order = new Order();
                                order.setId(jo.getInt("id"));
                                order.setOrderId(response.length() - i);
                                order.setCost(jo.getDouble("cost"));
                                order.setCreatedAt(jo.getString("created_at"));

                                ordersList.add(order);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ordersAdapter = new OrdersAdapter(OrdersActivity.this, ordersList);
                        listView.setAdapter(ordersAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(jsonArrayRequest);
    }
}
