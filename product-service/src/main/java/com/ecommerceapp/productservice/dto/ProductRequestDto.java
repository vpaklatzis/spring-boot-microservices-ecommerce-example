package com.ecommerceapp.productservice.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
}
