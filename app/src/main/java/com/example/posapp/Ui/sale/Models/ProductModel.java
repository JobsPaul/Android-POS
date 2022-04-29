package com.example.posapp.Ui.sale.Models;

public class ProductModel {
    private String productId;
    private String productNo;
    private String productName;
    private double price;
    private double count;
    private double pack;
    private double totalPrice;
    private double maxPrice;
    private double minPrice;
    private String picture;

    //public ProductModel() {}
    public ProductModel(String productId, String productNo, String productName, double price, double count, double pack, double totalPrice, double maxPrice, double minPrice,  String picture) {
        this.productId = productId;
        this.productNo = productNo;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.pack = pack;
        this.totalPrice = totalPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.picture = picture;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public String getProductName() {
        return  productName;
    }

    public double getPrice() {
        return price;
    }

    public double getCount() {
        return count;
    }

    public double getPack() {
        return pack;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public String getPicture() {
        return picture;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void setPack(double pack) {
        this.pack = pack;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
