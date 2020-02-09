package com.example.store.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.database.SQLiteDriver;

public class ProductActivity extends HomeActivity {

    private TextView tv_productQuantity;

    private Double price;
    private Integer stock;

    SQLiteDriver db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_product);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_product, null, false);
        mDrawer.addView(contentView, 0);

        // display product
        TextView tv_productName = findViewById(R.id.product_name);
        TextView tv_productPrice = findViewById(R.id.product_price);
        TextView tv_productDescription = findViewById(R.id.product_description);
        TextView tv_productStock = findViewById(R.id.product_stock);

        final Intent intent = getIntent();
        tv_productName.setText(intent.getStringExtra("product_name"));
        price = intent.getDoubleExtra("product_price", 0);
        tv_productPrice.setText(price.toString());
        tv_productDescription.setText(intent.getStringExtra("product_description"));
        stock = intent.getIntExtra("product_stock", 0);
        tv_productStock.setText(stock.toString());


        // onclick quantity buttons
        Button btn_increaseQuantity = findViewById(R.id.btn_product_increase_quantity);
        Button btn_decreaseQuantity = findViewById(R.id.btn_product_decrease_quantity);
        tv_productQuantity = findViewById(R.id.product_quantity_amount);

        btn_increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.parseInt(tv_productQuantity.getText().toString());
                quantity++;
                if (quantity > stock) quantity = stock;
                tv_productQuantity.setText(quantity.toString());
            }
        });

        btn_decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.parseInt(tv_productQuantity.getText().toString());
                quantity--;
                if (quantity < 0) quantity = 0;
                tv_productQuantity.setText(quantity.toString());
            }
        });


        // add to cart
        db = new SQLiteDriver(getApplicationContext());
        Button btn_addProductToCart = findViewById(R.id.btn_add_product_to_cart);
        btn_addProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int product_id = intent.getIntExtra("product_id", 0);
                int quantity = Integer.parseInt(tv_productQuantity.getText().toString());
                double cost = quantity * price;
                int customer_id = 0; // TODO: get current customer id

                if (db.save(customer_id, product_id, quantity, cost)) {
                    Toast.makeText(getApplicationContext(), "Item added to cart successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't add item to cart", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
