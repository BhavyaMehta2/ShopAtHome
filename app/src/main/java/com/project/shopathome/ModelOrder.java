package com.project.shopathome;

import java.net.URL;

public class ModelOrder {
    private String date;
    private int ID;
    private int pid;
    private int quantity;

    public ModelOrder(int ID, String date, Order.products Product) {
        this.date = date;
        this.ID = ID;
        this.pid = Product.pid;
        this.quantity = Product.quantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
