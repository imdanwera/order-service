package com.store.order.model;

public class ProductDTO {
    private String productName;
    private String productDescription;
    private String productType;
    private String productAccessoryType;

    public ProductDTO(){}

    public ProductDTO(String name, String desc, String type, String accsType) {
        this.productName = name;
        this.productDescription = desc;
        this.productType = type;
        this.productAccessoryType = accsType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductAccessoryType() {
        return productAccessoryType;
    }

    public void setProductAccessoryType(String productAccessoryType) {
        this.productAccessoryType = productAccessoryType;
    }
}
