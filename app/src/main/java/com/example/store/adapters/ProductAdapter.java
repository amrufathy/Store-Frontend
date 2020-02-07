package com.example.store.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.activities.ProductActivity;
import com.example.store.models.Product;
import com.example.store.view_holders.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(ArrayList<Product> products) {
        productList = products;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Product product = productList.get(position);

        holder.setName(product.getName());
        holder.setPrice(product.getPrice());
        holder.setDescription(product.getDescription());

        holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductActivity.class);
                intent.putExtra("product_id", product.getId());
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_description", product.getDescription());
                intent.putExtra("product_stock", product.getStock());
                intent.putExtra("product_price", product.getPrice());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
