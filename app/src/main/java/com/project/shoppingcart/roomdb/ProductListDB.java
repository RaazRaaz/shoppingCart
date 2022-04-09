package com.project.shoppingcart.roomdb;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity (tableName = "productList")
public class ProductListDB implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;



    @ColumnInfo(name = "product_id")
    public String productID;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "productAmount")
    private String productAmount;

    @ColumnInfo(name = "product_description")
    private String productDescription;

    @ColumnInfo(name = "product_image")
    private String productImage;

    @ColumnInfo(name = "product_quantity")
    private String productQuantity;






    public ProductListDB(Parcel in) {

     productQuantity = in.readString();
     productAmount = in.readString();
     productDescription = in.readString();
     productImage = in.readString();
     productName = in.readString();
     productID = in.readString();

    }

    public ProductListDB() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productQuantity);
        dest.writeString(productAmount);
        dest.writeString(productDescription);
        dest.writeString(productImage);
        dest.writeString(productName);
        dest.writeString(productID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductListDB> CREATOR = new Creator<ProductListDB>() {
        @Override
        public ProductListDB createFromParcel(Parcel in) {
            return new ProductListDB(in);
        }

        @Override
        public ProductListDB[] newArray(int size) {
            return new ProductListDB[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

}
