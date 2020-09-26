package com.store.order.model;

import java.util.List;

public class ProductSearchResultDTO {

    List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
