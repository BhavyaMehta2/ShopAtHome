package com.project.shopathome;

import java.net.URL;

public class Model {
    private String name;
    private URL image;
    private String price;
    private String category;
    private String desc;
    private int ID;
    private double rate;
    private int count;
    // Constructor
    public Model(int ID, String name, URL image, String price, String category, String desc, Results.rating Rate) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.category = category;
        this.desc = desc;
        this.ID = ID;
        this.rate = Rate.rate;
        this.count = Rate.count;
    }

    // Getter and Setter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String name) {
        this.desc = desc;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRating() {
        return rate;
    }

    public void setRating(double rate) {
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
