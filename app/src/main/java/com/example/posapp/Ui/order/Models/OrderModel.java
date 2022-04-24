package com.example.posapp.Ui.order.Models;

public class OrderModel {
    private String orderNo;
    private String orderData;
    private String orderStatus;
    private String orderCustomer;
    private String orderUser;
    private int totalPrice;

    public OrderModel(String orderNo, String orderData, String orderStatus, String orderCustomer, String orderUser, int totalPrice) {
        this.orderNo = orderNo;
        this.orderData = orderData;
        this.orderStatus = orderStatus;
        this.orderCustomer = orderCustomer;
        this.orderUser = orderUser;
        this.totalPrice = totalPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getOrderData() {
        return orderData;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderCustomer() {
        return orderCustomer;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
