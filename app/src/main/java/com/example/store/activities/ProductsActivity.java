package com.example.store.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapters.ProductsAdapter;
import com.example.store.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsActivity extends HomeActivity {

    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_products);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_products, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Products");

        String url = "http://10.0.2.2:3000/products.json";

        productArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_products_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(@NonNull JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);

                                Product product = new Product();
                                product.setId(jo.getInt("id"));
                                product.setName(jo.getString("name"));
                                product.setPrice(jo.getDouble("price"));
                                product.setDescription(jo.getString("description"));
                                product.setStock(jo.getInt("stock"));

                                productArrayList.add(product);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        productsAdapter = new ProductsAdapter(productArrayList);
                        recyclerView.setAdapter(productsAdapter);
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        // TODO: Handle error
                        Log.d("Error fetching products", error.toString());
                    }
                });

        queue.add(jsonObjectRequest);


    }

}
