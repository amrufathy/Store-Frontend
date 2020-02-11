package com.example.store.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.store.R;
import com.example.store.activities.OrderActivity;
import com.example.store.models.Order;

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<Order> {
    public OrdersAdapter(@NonNull Context context, @NonNull ArrayList<Order> itemsList) {
        super(context, 0, itemsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        final Order order = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order, parent, false);
        }

        TextView tv_orderId = convertView.findViewById(R.id.tv_order_id);
        TextView tv_orderPrice = convertView.findViewById(R.id.tv_order_price);
        TextView tv_orderDate = convertView.findViewById(R.id.tv_order_created_date);
        RelativeLayout rv_orderItem = convertView.findViewById(R.id.rl_item_order_layout);

        tv_orderId.setText(String.format("Order #%d", order.getOrderId()));
        tv_orderPrice.setText(String.format("%.2f $", order.getCost()));
        tv_orderDate.setText(String.format("%s", order.getCreatedAt()));
        rv_orderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), OrderActivity.class)
                        .putExtra("order_id", order.getId())
                        .putExtra("order_cost", order.getCost()));
            }
        });

        return convertView;
    }
}
