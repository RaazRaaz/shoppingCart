package com.project.shoppingcart;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.project.shoppingcart.databinding.ActivtyProductBinding;
import com.project.shoppingcart.roomdb.DatabaseClient;
import com.project.shoppingcart.roomdb.ProductListDB;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.ProductRemoveListener {

    ActivtyProductBinding activtyProductBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activtyProductBinding = DataBindingUtil.setContentView(CartActivity.this, R.layout.activty_product);
        activtyProductBinding.appBarTitle.setText("My Cart");
        activtyProductBinding.cart.setVisibility(View.GONE);
        getSavedProductData();
    }

    private void getSavedProductData() {
        class GetTasks extends AsyncTask<Void, Void, List<ProductListDB>> {

            @Override
            protected List<ProductListDB> doInBackground(Void... voids) {
                List<ProductListDB> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .ProductListDAO()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<ProductListDB> data) {
                super.onPostExecute(data);
                activtyProductBinding.recyclerView.setAdapter(new CartAdapter(data, CartActivity.this));
                if(data.size()==0){
                    activtyProductBinding.cartEmpty.setVisibility(View.VISIBLE);
                }else {
                    activtyProductBinding.cartEmpty.setVisibility(View.GONE);

                }

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ProductActivity.class));
    }

    @Override
    public void deleteProduct(ProductListDB productsItem) {
        deleteSavedProductData(productsItem);
    }

    private void deleteSavedProductData(ProductListDB productListDB) {
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .ProductListDAO()
                        .deleteByProductId(productListDB.getId());
                return null;
            }
            @SuppressLint("UseRequireInsteadOfGet")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getSavedProductData();

            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }

}