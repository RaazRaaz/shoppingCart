package com.project.shoppingcart.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductListDAO {
    @Query("SELECT * FROM productList")
    List<ProductListDB> getAll();

    @Insert
    void insert(ProductListDB dbPojo);

    @Delete
    void delete(ProductListDB dbPojo);

    @Update
    void update(ProductListDB dbPojo);



    @Query("UPDATE productList SET product_quantity = :quantity  WHERE product_id = :pid")
    void updateQuantity(String quantity, String pid );


    @Query("DELETE FROM productList")
    void nukeTable();

    @Query("DELETE FROM productList WHERE id = :id")
    abstract void deleteByProductId(Integer id);


    @Query("SELECT COUNT(id) FROM productList")
    int getCount();




}
