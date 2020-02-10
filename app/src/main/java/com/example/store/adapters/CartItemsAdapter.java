package com.example.store.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.store.R;
import com.example.store.database.SQLiteDriver;
import com.example.store.models.CartItem;

import java.util.ArrayList;

public class CartItemsAdapter extends ArrayAdapter<CartItem> {
    public CartItemsAdapter(@NonNull Context context, @NonNull ArrayList<CartItem> itemsList) {
        super(context, 0, itemsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        final CartItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
        }

        TextView tv_itemNameQuantity = convertView.findViewById(R.id.tv_cart_product_name_and_quantity);
        TextView tv_itemPrice = convertView.findViewById(R.id.tv_cart_product_price);

        tv_itemNameQuantity.setText(String.format("%d x %s", item.getQuantity(), item.getProductName()));
        tv_itemPrice.setText(String.format("%.2f $", item.getCost()));

        final SQLiteDriver db = SQLiteDriver.getInstance(getContext());
        ImageButton ib_deleteItem = convertView.findViewById(R.id.ibtn_cart_item_delete);
        ib_deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRecord(item.getCustomerId(), item.getProductId(), item.getQuantity());
                remove(item);
            }
        });

        return convertView;
    }
}
