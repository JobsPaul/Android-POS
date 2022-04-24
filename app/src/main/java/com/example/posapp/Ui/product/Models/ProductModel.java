package com.example.posapp.Ui.product.Models;

public class ProductModel {
    private String productBarcode;
    private String productName;
    private String categoryProduct;
    private String typeProduct;
    private String groupProduct;
    private String unitOfMeasure;
    private int maxPrice;
    private int minPrice;
    private int priceProduct;
    private String filePath;


    public ProductModel(String productBarcode, String productName, String categoryProduct, String typeProduct, String groupProduct, String unitOfMeasure, int maxPrice, int minPrice, int priceProduct, String filePath) {
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.categoryProduct = categoryProduct;
        this.typeProduct = typeProduct;
        this.groupProduct = groupProduct;
        this.unitOfMeasure = unitOfMeasure;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.priceProduct = priceProduct;
        this.filePath = filePath;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public String getGroupProduct() {
        return groupProduct;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public String getFilePath() {
        return filePath;
    }

}
