package com.example.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.store.R;

public class ProductActivity extends AppCompatActivity {

    private TextView tv_productName, tv_productPrice, tv_productDescription, tv_productStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tv_productName = findViewById(R.id.product_name);
        tv_productPrice = findViewById(R.id.product_price);
        tv_productDescription = findViewById(R.id.product_description);
        tv_productStock = findViewById(R.id.product_stock);

        Intent intent = getIntent();
        tv_productName.setText(intent.getStringExtra("product_name"));
        Double price = intent.getDoubleExtra("product_price", 0);
        tv_productPrice.setText(price.toString());
        tv_productDescription.setText(intent.getStringExtra("product_description"));
        Integer stock = intent.getIntExtra("product_stock", 0);
        tv_productStock.setText(stock.toString());
    }
}
