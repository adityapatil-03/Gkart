package com.example.gkart;

import java.util.HashMap;

public class model {
    String name,image,c_id;
    String price;
    int quantity;

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public model(String id, String name, String image, String price, int quantity) {
        this.c_id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    model(){

    }

    public model(HashMap hashMap) {
        this.name = hashMap.get("name").toString();
        this.image = hashMap.get("image").toString();
        this.price = hashMap.get("price").toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
