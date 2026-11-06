package models;

import exceptions.TestDataException;

public class Product {

    private final String productName;
    private final double productPrice;

    public Product (String productName, double productPrice) {
        if (productName == null) {
            throw new TestDataException("productName не может быть null");
        }

        if (productPrice < 0) {
            throw new TestDataException("productPrice не может быть отрицательным числом");
        }
        this.productName = productName;
        this.productPrice = productPrice;
    }


    public String getProductName(){
        return productName;
    }

    public double getProductPrice(){
        return productPrice;
    }

}
