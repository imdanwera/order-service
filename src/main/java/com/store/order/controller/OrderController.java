package com.store.order.controller;

import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.store.order.model.ProductSearchResultDTO;
import com.store.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Value("${store.order.version}")
    private String version;

    @RequestMapping(value = "/{ordId}", method = RequestMethod.GET)
    public String getOrder(@PathVariable("ordId") String id) {
        return "ID is " + id;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<ProductSearchResultDTO> searchProduct(@RequestParam("type") String type) {
        ProductSearchResultDTO productSearchResult = new ProductSearchResultDTO();

        productSearchResult.setProducts(orderService.getProducts(type));
        productSearchResult.setAccessories(orderService.getAccessories(type));
        productSearchResult.setVersion(version);
        return new ResponseEntity<ProductSearchResultDTO>(productSearchResult, HttpStatus.OK);
    }


}
