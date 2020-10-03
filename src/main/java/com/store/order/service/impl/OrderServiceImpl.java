package com.store.order.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static List<ProductDTO> inventoryList = Arrays.asList(
            new ProductDTO("Apple iPhone 7 plus", "Apple iPhone 7 plus", "MOBILE", null),
            new ProductDTO("Apple iPhone XR", "Apple iPhone XR", "MOBILE", null),
            new ProductDTO("SanDisk MicroSD", "SanDisk MicroSD", "MEMORY_CARD", "MOBILE"),
            new ProductDTO("Anker Power Bank", "Anker Power Bank", "CHARGER", "MOBILE"));

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


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
    @HystrixCommand(fallbackMethod = "getFallbackProducts", threadPoolKey = "productThreadPool",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public List<ProductDTO> getProducts(String type) {
        List<ServiceInstance> invInstances = discoveryClient.getInstances("INVENTORY-SERVICE");
        ServiceInstance invServiceInstance = invInstances.get(0);

        String invServiceURL = getServiceURL(
                invServiceInstance.getUri().toString(),
                "/api/inventory",
                "QueryParam",
                "prodtype",
                type);

        ResponseEntity<List<ProductDTO>> productList =
                restTemplate.exchange(invServiceURL, HttpMethod.GET, getHeader(), new ParameterizedTypeReference<List<ProductDTO>>() {
                });
        return productList.getBody();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackAccessories" )
    public List<ProductDTO> getAccessories(String type) {
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

        return accessoryList.getBody();
    }

    public List<ProductDTO> getFallbackAccessories(String type) {
        return inventoryList.stream()
                .filter(prd -> type.equals(prd.getProductAccessoryType()))
                .collect(Collectors.toList());

    }
    public List<ProductDTO> getFallbackProducts(String type) {
        return inventoryList.stream()
                .filter(prd -> type.equals(prd.getProductType()))
                .collect(Collectors.toList());

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
