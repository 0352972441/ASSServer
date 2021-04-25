package com.example.androidserver.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("_id")
    private String _id;
    @SerializedName("image")
    private String image;
    @SerializedName("price")
    private double price;
    @SerializedName("name")
    private String name;
    @SerializedName("desc")
    private String desc;
    @SerializedName("contentType")
    private String contentType;

    public Product() {
    }

    public Product(String _id, String image, double price, String name, String desc, String contentType) {
        this._id = _id;
        this.image = image;
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.contentType = contentType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
