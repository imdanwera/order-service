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
        RestTemplate restTemplate = new RestTemplate();
        ProductSearchResultDTO productSearchResult = new ProductSearchResultDTO();

        // Get Products from Inventory Service
        List<ServiceInstance> invInstances = discoveryClient.getInstances("INVENTORY-SERVICE");
        ServiceInstance invServiceInstance = invInstances.get(0);

        String invServiceURL = getServiceURL(
                invServiceInstance.getUri().toString(),
                "/api/inventory",
                "QueryParam",
                "prodtype",
                type);

        ResponseEntity<List<ProductDTO>>  productList =
                restTemplate.exchange(invServiceURL, HttpMethod.GET, getHeader(), new ParameterizedTypeReference<List<ProductDTO>>() {});

        productSearchResult.setProducts(productList.getBody());

        // Get Accessories from Recommendation Service
        List<ServiceInstance> recInstances = discoveryClient.getInstances("RECOMMEND-SERVICE");
        ServiceInstance recServiceInstance = recInstances.get(0);

        String recServiceURL = getServiceURL(
                recServiceInstance.getUri().toString(),
                "api/recommand/accessories/",
                "PathParam",
                null,
                type);

        ResponseEntity<List<ProductDTO>> accessoryList =
                restTemplate.exchange(recServiceURL, HttpMethod.GET, getHeader(), new ParameterizedTypeReference<List<ProductDTO>>() {});

        productSearchResult.setAccessories(accessoryList.getBody());

        return productSearchResult;
    }

    private HttpEntity<?> getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


    private String getServiceURL(String baseURL, String contextPath, String paramType, String paramName, String paramValue ) {
        UriComponentsBuilder builder;
        if("QueryParam".equals(paramType))
            builder = UriComponentsBuilder.fromHttpUrl(baseURL+contextPath).queryParam(paramName, paramValue);
        else
            builder = UriComponentsBuilder.fromHttpUrl(baseURL+contextPath).path(paramValue);

        return builder.toUriString();
    }
}
