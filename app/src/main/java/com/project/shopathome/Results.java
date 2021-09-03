package com.project.shopathome;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Results {

    @SerializedName("id")
    private int superID;

    @SerializedName("title")
    private String superName;

    @SerializedName("image")
    private URL superImage;

    @SerializedName("price")
    private String superPrice;

    @SerializedName("category")
    private String superCategory;

    @SerializedName("description")
    private String superDesc;

    @SerializedName("rating")
    private rating superRate= new rating();

    public class rating
    {
        @SerializedName("rate")
        double rate;
        @SerializedName("count")
        int count;
    }

    public Results(int ID, String name, URL image, String price, String category, String desc, rating Rate) {
        this.superID = ID;
        this.superName = name;
        this.superImage = image;
        this.superPrice = price;
        this.superCategory = category;
        this.superDesc = desc;
        this.superRate.rate = Rate.rate;
        this.superRate.count = Rate.count;
    }
    public int getID() {
        return superID;
    }

    public String getName() {
        return superName;
    }

    public URL getImage() {
        return superImage;
    }

    public String getPrice() {
        return superPrice;
    }

    public String getCategory() {
        return superCategory;
    }

    public String getDesc() {
        return superDesc;
    }

    public rating getRate() {
        return superRate;
    }
}
