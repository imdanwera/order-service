package com.store.order.service;

import com.store.order.model.OrderDTO;
import com.store.order.model.ProductSearchResultDTO;
import org.springframework.stereotype.Service;

public interface OrderService {

    OrderDTO getOrder(String id);
    void createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(OrderDTO orderDTO);
    void deleteOrder(String Id);

    ProductSearchResultDTO searchProduct(String type);



}
