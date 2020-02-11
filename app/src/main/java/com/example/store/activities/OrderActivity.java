package com.example.store.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapters.CartItemsAdapter;
import com.example.store.models.CartItem;
import com.facebook.AccessToken;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends HomeActivity {

    private TextView tv_cartTotalPrice;
    private ArrayList<CartItem> orderItemsList;
    private CartItemsAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_cart);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_cart, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Order");

        Button btn_cartCheckout = findViewById(R.id.btn_checkout_cart);
        btn_cartCheckout.setVisibility(View.INVISIBLE);

        final RequestQueue queue = Volley.newRequestQueue(this);

        fb_cartCheckout.setVisibility(View.INVISIBLE);
        tv_cartTotalPrice = findViewById(R.id.tv_cart_total_price);
        tv_cartTotalPrice.setText(String.format("Total Price: %.2f $",
                getIntent().getDoubleExtra("order_cost", 0.0)));

        final ListView listView = findViewById(R.id.lv_cart_list);

        orderItemsList = new ArrayList<>();

        String baseUrl = getString(R.string.backend_base_url) +
                String.format("orders/%d/items", getIntent().getIntExtra("order_id", 0));
        String url = String.format("%s?token=%s", baseUrl, AccessToken.getCurrentAccessToken().getToken());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);

                                CartItem item = new CartItem(
                                        Profile.getCurrentProfile().getId(),
                                        jo.getInt("product_id"),
                                        jo.getString("name"),
                                        jo.getInt("quantity"),
                                        jo.getDouble("cost"));

                                orderItemsList.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ordersAdapter = new CartItemsAdapter(getApplicationContext(), orderItemsList, false);
                        listView.setAdapter(ordersAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error retrieving order items", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonArrayRequest);
    }
}
