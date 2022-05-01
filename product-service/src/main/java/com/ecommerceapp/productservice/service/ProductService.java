package com.ecommerceapp.productservice.service;

import com.ecommerceapp.productservice.dto.ProductRequestDto;
import com.ecommerceapp.productservice.dto.ProductResponseDto;
import com.ecommerceapp.productservice.model.Product;
import com.ecommerceapp.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDto productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved successfully", product.getId());
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponseDto mapToProductResponse(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
