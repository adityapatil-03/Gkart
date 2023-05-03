package com.example.gkart;

import java.util.HashMap;

public class model {
    String name,image,c_id,category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public model(String id, String name, String image, String price, int quantity) {
        this.c_id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;

    }

    public model(String id, String name, String image, String price, int quantity,String category) {
        this.c_id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    model(){

    }

    public model(HashMap hashMap) {
        this.name = hashMap.get("name").toString();
        this.image = hashMap.get("image").toString();
        this.price = hashMap.get("price").toString();
    }

    public model(HashMap hashMap,int a){
        this.name = hashMap.get("name").toString();
        this.image = hashMap.get("image").toString();
        this.price = hashMap.get("price").toString();
        this.quantity = Integer.parseInt(hashMap.get("stock").toString());
    }

    public model(HashMap hashMap,String c)
        {
            this.name = hashMap.get("name").toString();
            this.image = hashMap.get("image").toString();
            this.price = hashMap.get("price").toString();
            this.category = hashMap.get("category").toString();
            this.quantity = Integer.parseInt(hashMap.get("quantity").toString());
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
