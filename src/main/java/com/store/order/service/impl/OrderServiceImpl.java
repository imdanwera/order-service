package com.store.order.service.impl;

import com.store.order.model.OrderDTO;
import com.store.order.model.ProductDTO;
import com.store.order.model.ProductSearchResultDTO;
import com.store.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private DiscoveryClient discoveryClient;


    @Override
    public OrderDTO getOrder(String id) {
        return null;
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {

    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(String Id) {

    }

    @Override
    public ProductSearchResultDTO searchProduct(String type) {

        List<ServiceInstance> instances = discoveryClient.getInstances("INVENTORY-SERVICE");
        ServiceInstance serviceInstance = instances.get(0);
        //String baseURL = "http://localhost:8081";
        String baseURL = serviceInstance.getUri().toString();

        String contextPath = "/api/inventory";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL+contextPath).queryParam("prodtype", type);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ProductDTO>>  productList =
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHeader(), new ParameterizedTypeReference<List<ProductDTO>>() {});

        productList.getBody().stream().forEach(productDTO -> System.out.println(productDTO.getProductName()));
        ProductSearchResultDTO productSearchResult = new ProductSearchResultDTO();
        productSearchResult.setProducts(productList.getBody());
        return productSearchResult;
    }

    private HttpEntity<?> getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
