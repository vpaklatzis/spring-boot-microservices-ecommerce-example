package com.ecommerceapp.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryResponseDto {
    private String skuCode;
    private boolean isInStock;
    private Integer quantity;
}
