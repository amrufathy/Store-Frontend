package com.example.store.activities;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapters.CartItemsAdapter;
import com.example.store.database.SQLiteDriver;
import com.example.store.models.CartItem;
import com.facebook.AccessToken;

import org.json.JSONArray;

import java.util.ArrayList;

public class CartActivity extends HomeActivity {

    private TextView tv_cartTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_cart);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_cart, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Cart");

        final RequestQueue queue = Volley.newRequestQueue(this);

        fb_cartCheckout.setVisibility(View.INVISIBLE);
        tv_cartTotalPrice = findViewById(R.id.tv_cart_total_price);

        // load data from sqlite
        SQLiteDriver db = SQLiteDriver.getInstance(getApplicationContext());
        final ArrayList<CartItem> itemsList = db.readAllProducts();

        CartItemsAdapter itemsAdapter = new CartItemsAdapter(this, itemsList);
        ListView listView = findViewById(R.id.lv_cart_list);
        listView.setAdapter(itemsAdapter);

        updateTotalPriceTextView(itemsList);

        itemsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateTotalPriceTextView(itemsList);
            }
        });

        Button btn_cartCheckout = findViewById(R.id.btn_checkout_cart);
        btn_cartCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: checkout cart to backend
                if (itemsList.size() > 0) {
                    String text = String.format("Checking out %d items", itemsList.size());
                    Toast.makeText(CartActivity.this, text, Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray = new JSONArray();
                    for (CartItem item : itemsList) {
                        jsonArray.put(item.toJSON());
                    }

                    Log.d("JSON", jsonArray.toString());

                    String baseUrl = getString(R.string.backend_base_url) + "orders";
                    String url = String.format("%s?token=%s", baseUrl, AccessToken.getCurrentAccessToken().getToken());

                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("response", response.toString());
                                    Toast.makeText(CartActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(CartActivity.this, "Error checking out cart", Toast.LENGTH_SHORT).show();
                                }
                            });

                    queue.add(jsonArrayRequest);

                } else {
                    Toast.makeText(CartActivity.this, "No items to checkout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTotalPriceTextView(@NonNull ArrayList<CartItem> items) {
        Double totalCartPrice = 0.0;
        for (CartItem item : items) {
            totalCartPrice += item.getCost();
        }

        tv_cartTotalPrice.setText(String.format("Total Price: %.2f $", totalCartPrice));
    }
}
