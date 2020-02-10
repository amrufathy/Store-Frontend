package com.example.store.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.store.R;
import com.example.store.database.SQLiteDriver;

public class ProductActivity extends HomeActivity {

    @Nullable
    private SQLiteDriver db;
    private ElegantNumberButton btn_productQuantity;
    private TextView tv_totalPrice;
    private Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_product);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_product, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Product");

        // display product
        TextView tv_productName = findViewById(R.id.tv_product_name);
        TextView tv_productPrice = findViewById(R.id.tv_product_price);
        TextView tv_productDescription = findViewById(R.id.tv_product_description);
        TextView tv_productStock = findViewById(R.id.tv_product_stock);

        final Intent intent = getIntent();
        tv_productName.setText(intent.getStringExtra("product_name"));
        price = intent.getDoubleExtra("product_price", 0);
        tv_productPrice.setText(String.format("Item price: %.2f $", price));
        tv_productDescription.setText(intent.getStringExtra("product_description"));
        Integer stock = intent.getIntExtra("product_stock", 0);
        tv_productStock.setText(String.format("%d items remaining", stock));

        tv_totalPrice = findViewById(R.id.tv_total_product_price);
        tv_totalPrice.setText(String.format("Total price: %.2f $", price));

        btn_productQuantity = findViewById(R.id.btn_product_quantity);
        btn_productQuantity.setRange(1, stock);

        btn_productQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                tv_totalPrice.setText(String.format("Total price: %.2f $", newValue * price));
            }
        });


        // add to cart
        db = SQLiteDriver.getInstance(getApplicationContext());
        Button btn_addProductToCart = findViewById(R.id.btn_add_product_to_cart);
        btn_addProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int customer_id = 0; // TODO: get customer id
                int product_id = intent.getIntExtra("product_id", 0);
                String product_name = intent.getStringExtra("product_name");
                int quantity = Integer.parseInt(btn_productQuantity.getNumber());
                double cost = quantity * price;

                if (db.save(customer_id, product_id, product_name, quantity, cost)) {
                    Toast.makeText(getApplicationContext(), "Item added to cart successfully", Toast.LENGTH_LONG).show();
                    finish();
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
