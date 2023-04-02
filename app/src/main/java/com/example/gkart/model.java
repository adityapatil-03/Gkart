package com.example.gkart;

import java.util.HashMap;

public class model {
    String name,image;
    String price;

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
