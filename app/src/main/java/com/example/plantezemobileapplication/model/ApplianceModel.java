package com.example.plantezemobileapplication.model;

public class ApplianceModel {
    private String product_price;
    private String product_title;
    private String product_photo;

    private String product_url;

    public ApplianceModel(String product_price, String product_title, String product_photo, String product_url) {
        this.product_price = product_price;
        this.product_title = product_title;
        this.product_photo = product_photo;
        this.product_url = product_url;

    }

    public String getPrice() {
        return product_price;
    }

    public String getTitle() {
        return product_title;
    }

    public String getImageUrl() {
        return product_photo;
    }

    public String getUrl() {
        return product_url;
    }
}
