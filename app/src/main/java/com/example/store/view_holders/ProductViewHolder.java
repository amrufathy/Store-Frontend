package com.example.store.view_holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private TextView hName, hPrice, hDescription;
    private LinearLayout hLinearLayout;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        this.hName = itemView.findViewById(R.id.item_product_name);
        this.hPrice = itemView.findViewById(R.id.item_product_price);
        this.hDescription = itemView.findViewById(R.id.item_product_description);
        this.hLinearLayout = itemView.findViewById(R.id.item_product_card_layout);
    }

    public void setName(String name) {
        this.hName.setText(name);
    }

    public void setPrice(Float price) {
        String price_str = price.toString() + " $";
        this.hPrice.setText(price_str);
    }

    public void setDescription(String description) {
        this.hDescription.setText(description);
    }

    public LinearLayout getLinearLayout() {
        return this.hLinearLayout;
    }
}
