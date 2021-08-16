package com.example.androidserver.models;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("num")
    private int num;
    @SerializedName("image")
    private String image;
    @SerializedName("price")
    private double price;
    @SerializedName("product")
    private String product;

    public Cart(String name, int num, String image, double price, String product) {
        this.name = name;
        this.num = num;
        this.image = image;
        this.price = price;
        this.product = product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
