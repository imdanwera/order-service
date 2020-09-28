package com.store.order.model;

import java.util.List;

public class ProductSearchResultDTO {

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
}
