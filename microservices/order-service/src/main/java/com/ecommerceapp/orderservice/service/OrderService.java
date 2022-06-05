package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.orderservice.dto.InventoryResponseDto;
import com.ecommerceapp.orderservice.dto.OrderLineItemsDto;
import com.ecommerceapp.orderservice.dto.OrderRequestDto;
import com.ecommerceapp.orderservice.model.Order;
import com.ecommerceapp.orderservice.model.OrderLineItems;
import com.ecommerceapp.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String submitOrder(OrderRequestDto orderRequest) {
        Order order = new Order();
        order.setOrderCode(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        // Get the skuCodes of the products
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Gets an array of inventoryResponseDto objects
        // If the products are in stock in the inventory-service, then submits the order.
        InventoryResponseDto[] inventoryResponseDtos = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponseDto[].class)
                .block();

        // Introduced bug. Inventory quantity must be higher than order's product quantity
        boolean productsAreInStock = inventoryResponseDtos != null && Arrays.stream(inventoryResponseDtos).allMatch(InventoryResponseDto::isInStock);

        if (productsAreInStock) {
            orderRepository.save(order);
            log.info("Order {} is saved successfully", order.getOrderCode());
            return "Order submitted successfully";
        } else
            throw new IllegalArgumentException("The product is out of stock! Cannot complete the order.");
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuality(orderLineItemsDto.getQuality());

        return orderLineItems;
    }
}
