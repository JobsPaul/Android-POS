package com.example.posapp.Ui.sale.Models;

public class CustomerModel {
    public String customerName;
    public String customerAddress;

    public CustomerModel(String customerName, String customerAddress) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

}
