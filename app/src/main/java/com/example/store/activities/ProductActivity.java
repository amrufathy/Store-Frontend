package com.example.store.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.store.R;

public class ProductActivity extends AppCompatActivity {

    private TextView tv_productName, tv_productPrice, tv_productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tv_productName = findViewById(R.id.product_name);
        tv_productPrice = findViewById(R.id.product_price);
        tv_productDescription = findViewById(R.id.product_description);

        Intent intent = getIntent();



    }
}
