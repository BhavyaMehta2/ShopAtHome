package com.project.shopathome;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Order {

    @SerializedName("id")
    private int superID;

    @SerializedName("date")
    private String superDate;

    @SerializedName("products")
    private products superProduct= new products();

    public class products
    {
        @SerializedName("productId")
        int pid;
        @SerializedName("quantity")
        int quantity;
    }

    public Order(int ID, String date, products Product) {
        this.superID = ID;
        this.superDate = date;
        this.superProduct.pid = Product.pid;
        this.superProduct.quantity = Product.quantity;
    }
    public int getID() {
        return superID;
    }

    public String getDate() {
        return superDate;
    }

    public products getProducts() {
        return superProduct;
    }
}

