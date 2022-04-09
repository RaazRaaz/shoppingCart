package com.project.shoppingcart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.shoppingcart.databinding.ItemProductCartBinding;
import com.project.shoppingcart.databinding.ItemProductListBinding;
import com.project.shoppingcart.roomdb.ProductListDB;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ProductListDB> productItem;
    private final ProductRemoveListener listener;



    public CartAdapter(List<ProductListDB> productItem,  ProductRemoveListener listener) {
        this.productItem = productItem;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemProductCartBinding binding = DataBindingUtil.inflate(Objects.requireNonNull(inflater), R.layout.item_product_cart, parent, false);
        return new CartAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof CartAdapter.ViewHolder) {
            ProductListDB list = productItem.get(position);

            String ext = list.getProductAmount().replaceAll("[^a-zA-Z0-9]", " ");

            String amount = ext.replaceAll("\\s", "");

            Integer amt = Integer.parseInt(list.getProductQuantity())*Integer.parseInt(amount);
            Picasso.get().load(list.getProductImage()).placeholder(R.drawable.shopping_cart).into(((ViewHolder) holder).binding.productImage);

            ((ViewHolder) holder).binding.productName.setText(list.getProductName());
            ((ViewHolder) holder).binding.productAmount.setText("₹"+amt);
            ((ViewHolder) holder).binding.productQuantity.setText(list.getProductQuantity());


        }


    }

    @Override
    public int getItemCount() {
        return productItem.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder{


        ItemProductCartBinding binding;
        int i = 0;
        int j = 0;

        @SuppressLint("SetTextI18n")
        private ViewHolder(ItemProductCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.imgDelete.setOnClickListener(view -> {
                listener.deleteProduct(productItem.get(getAdapterPosition()));
            });

        }

    }


    public interface ProductRemoveListener {
        void deleteProduct(ProductListDB productsItem);
    }

}
