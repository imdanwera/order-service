package com.store.order.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

public class ProductSearchResultDTO {

    private String version;

    List<ProductDTO> products;
    List<ProductDTO> accessories;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<ProductDTO> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<ProductDTO> accessories) {
        this.accessories = accessories;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
