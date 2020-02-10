package com.example.store.activities;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.store.R;
import com.example.store.adapters.CartItemsAdapter;
import com.example.store.database.SQLiteDriver;
import com.example.store.models.CartItem;

import java.util.ArrayList;

public class CartActivity extends HomeActivity {

    private TextView tv_cartTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_cart);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_cart, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Cart");

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
