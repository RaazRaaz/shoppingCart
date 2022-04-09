package com.project.shoppingcart;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.project.shoppingcart.databinding.ItemProductListBinding;
import com.project.shoppingcart.roomdb.ProductListDB;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ProductsItem> productItem;
    private final ProductCountListener countListener;
    private List<ProductListDB> productListDB;



    public ProductListAdapter(List<ProductsItem> productItem, List<ProductListDB> productListDBS,ProductCountListener productCountListener) {
        this.productItem = productItem;
        this.countListener = productCountListener;
        this.productListDB = productListDBS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemProductListBinding binding = DataBindingUtil.inflate(Objects.requireNonNull(inflater), R.layout.item_product_list, parent, false);
        return new ProductListAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof ProductListAdapter.ViewHolder) {
            ProductsItem list = productItem.get(position);

            Picasso.get().load(list.getThumb()).placeholder(R.drawable.shopping_cart).into(((ViewHolder) holder).binding.productImage);

            ((ViewHolder) holder).binding.productName.setText(list.getName());
            ((ViewHolder) holder).binding.productDescription.setText(list.getDescription());


            ((ViewHolder) holder).binding.productPrice.setText(list.getPrice());



            for(ProductListDB db:productListDB){
                if(db.getProductID().equals(productItem.get(position).getProductId())){
                    ((ViewHolder) holder).binding.productCount.setText(db.getProductQuantity());

                }
            }
        }






    }

    @Override
    public int getItemCount() {
        return productItem.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder{

        int i = 0;
        int j = 0;

        ItemProductListBinding binding;



        @SuppressLint("SetTextI18n")
        private ViewHolder(ItemProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.productIncrement.setOnClickListener(view -> {
                i = Integer.parseInt(binding.productCount.getText().toString());

                if(i>=0){
                    i++;
                    binding.productCount.setText(""+i);
                    countListener.addProduct(productItem.get(getAdapterPosition()), i);
                }
            });

            binding.productDecrement.setOnClickListener(view -> {
                i = Integer.parseInt(binding.productCount.getText().toString());

                if(i>0){
                    if(j<i){
                        i--;
                        binding.productCount.setText(""+i);
                        countListener.deleteProduct(productItem.get(getAdapterPosition()),i);
                    }

                }
            });
        }

    }


    public interface ProductCountListener {
        void addProduct(ProductsItem productsItem,int count);
        void deleteProduct(ProductsItem productsItem,int count);

    }

}
