package com.example.posapp.Ui.sale.Models;

public interface ICallBackProducts {
    void onGetProduct(String productId, String productNo, String productName, double price, double count, double pack, double totalPrice, double maxPrice, double minPrice,  String picture);
}
