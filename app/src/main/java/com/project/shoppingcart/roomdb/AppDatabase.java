package com.project.shoppingcart.roomdb;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductListDB.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductListDAO ProductListDAO();
}
