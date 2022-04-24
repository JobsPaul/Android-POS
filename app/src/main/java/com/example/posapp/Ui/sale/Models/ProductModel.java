package com.example.posapp.Ui.sale.Models;

public class ProductModel {
    private String productNo;
    private String productName;
    private int price;
    private int count;
    private int pack;
    private int totalPrice;

    public ProductModel(String productNo, String productName,int price, int count, int pack, int totalPrice) {
        this.productNo = productNo;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.pack = pack;
        this.totalPrice = totalPrice;
    }

    public String getProductNo() {
        return productNo;
    }

    public String getProductName() {
        return  productName;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public int getPack() {
        return pack;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
