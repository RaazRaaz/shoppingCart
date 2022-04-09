package com.project.shoppingcart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.project.shoppingcart.databinding.ActivtyProductBinding;
import com.project.shoppingcart.roomdb.DatabaseClient;
import com.project.shoppingcart.roomdb.ProductListDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements ProductListAdapter.ProductCountListener {

    ActivtyProductBinding activtyProductBinding;
    ProductListAdapter adapter;


    List<ProductsItem> pItem = new ArrayList<>();
    List<ProductListDB> productListDBS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activtyProductBinding = DataBindingUtil.setContentView(ProductActivity.this, R.layout.activty_product);
        callProductList();

        getSavedProductData(1, new ProductsItem(), 0);

        activtyProductBinding.cart.setOnClickListener(view -> {
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });
    }

    private void getSavedProductData(int action, ProductsItem productsItem, int quantity) {
        @SuppressLint("StaticFieldLeak")
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

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(List<ProductListDB> data) {
                super.onPostExecute(data);
                Boolean newData = true;

                if (action == 1) {
                    if (data.size() != 0) {
                        activtyProductBinding.cartCountBadgeTxt.setVisibility(View.VISIBLE);
                        activtyProductBinding.cartCountBadgeTxt.setText("" + data.size());
                        productListDBS.clear();
                        productListDBS = data;
                    }else {
                        activtyProductBinding.cartCountBadgeTxt.setVisibility(View.GONE);

                    }
                } else if (action == 2) {

                    if (data.size() == 0) {
                        saveTask(productsItem, quantity);
                    } else {

                        for (ProductListDB productListDB : data) {
                            if (productListDB.getProductID().equals(productsItem.getProductId())) {
                                newData = false;

                                if (quantity == 0) {
                                    deleteSavedProductData(productListDB);
                                } else {
                                    updateTask(productsItem, quantity);

                                }

                            }
                        }

                        if (newData) {
                            saveTask(productsItem, quantity);
                        }

                    }
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void saveTask(ProductsItem productsItem, int quantity) {
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {

                ProductListDB db = new ProductListDB();


                db.setProductAmount(productsItem.getPrice());
                db.setProductID(productsItem.getProductId());
                db.setProductDescription(productsItem.getDescription());
                db.setProductName(productsItem.getName());
                db.setProductQuantity("" + quantity);
                db.setProductImage(productsItem.getImage());

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .ProductListDAO()
                        .insert(db);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getSavedProductData(1, new ProductsItem(), 0);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    private void updateTask(ProductsItem productsItem, int quantity) {
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .ProductListDAO()
                        .updateQuantity("" + quantity, productsItem.getProductId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getSavedProductData(1, new ProductsItem(), 0);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }


    private void callProductList() {
        showProgress(activtyProductBinding.progressBar, true);
        ApiInterface apiInterface = ApiHandler.getRetrofit().create(ApiInterface.class);
        Call<ProductListData> call = null;

        call = apiInterface.getProductList();
        call.enqueue(new Callback<ProductListData>() {
            @Override
            public void onResponse(Call<ProductListData> call, Response<ProductListData> response) {
                showProgress(activtyProductBinding.progressBar, false);


                pItem = response.body().getProducts();
                adapter = new ProductListAdapter(pItem, productListDBS, ProductActivity.this);
                activtyProductBinding.recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ProductListData> call, Throwable t) {
                showProgress(activtyProductBinding.progressBar, false);
                Toast.makeText(ProductActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
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
                getSavedProductData(1, new ProductsItem(), 0);

            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }


    public static void showProgress(ProgressBar progressBar, boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void addProduct(ProductsItem productsItem, int count) {
        getSavedProductData(2, productsItem, count);

    }

    @Override
    public void deleteProduct(ProductsItem productsItem, int count) {
        getSavedProductData(2, productsItem, count);
    }
}