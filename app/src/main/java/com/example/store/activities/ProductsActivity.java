package com.example.store.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.store.R;
import com.example.store.adapters.ProductAdapter;
import com.example.store.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        String url = "http://10.0.2.2:3000/products.json";

        productArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            Log.d("bibo", response.getJSONObject(0).toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);

                                Product product = new Product();
                                product.setId(jo.getInt("id"));
                                product.setName(jo.getString("name"));
                                product.setPrice((float) jo.getDouble("price"));
                                product.setDescription(jo.getString("description"));
                                product.setStock(jo.getInt("stock"));

                                productArrayList.add(product);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        productAdapter = new ProductAdapter(productArrayList);
                        recyclerView.setAdapter(productAdapter);
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("Error fetching products", error.toString());
                    }
                });

        queue.add(jsonObjectRequest);


    }

}
