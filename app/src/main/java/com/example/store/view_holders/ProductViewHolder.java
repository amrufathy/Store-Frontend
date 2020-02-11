package com.example.store.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private TextView hName, hPrice, hDescription;
    private ImageView hImage;
    private LinearLayout hLinearLayout;
    private Random r;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        r = new Random();

        hName = itemView.findViewById(R.id.tv_item_product_name);
        hPrice = itemView.findViewById(R.id.tv_item_product_price);
        hDescription = itemView.findViewById(R.id.tv_item_product_description);
        hImage = itemView.findViewById(R.id.iv_item_product_image);
        hLinearLayout = itemView.findViewById(R.id.item_product_card_layout);
    }

    public void setName(String name) {
        hName.setText(name);
    }

    public void setPrice(@NonNull Double price) {
        String price_str = price.toString() + " $";
        hPrice.setText(price_str);
    }

    public void setDescription(String description) {
        hDescription.setText(description);
    }

    public void setImage() {
        // set with random image
        // https://www.programmableweb.com/api/lorem-picsum-rest-api-v2
        Picasso.get().load("https://picsum.photos/128/128?random=" + r.nextInt()).into(hImage);
    }

    public LinearLayout getLinearLayout() {
        return hLinearLayout;
    }
}
