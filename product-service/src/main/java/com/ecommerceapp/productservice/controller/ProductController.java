package com.ecommerceapp.productservice.controller;

import com.ecommerceapp.productservice.dto.ProductRequestDto;
import com.ecommerceapp.productservice.dto.ProductResponseDto;
import com.ecommerceapp.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDto productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }
}
