package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.orderservice.dto.OrderLineItemsDto;
import com.ecommerceapp.orderservice.dto.OrderRequestDto;
import com.ecommerceapp.orderservice.model.Order;
import com.ecommerceapp.orderservice.model.OrderLineItems;
import com.ecommerceapp.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public void submitOrder(OrderRequestDto orderRequest) {
        Order order = new Order();
        order.setOrderCode(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
        log.info("Product {} is saved successfully", order.getId());
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuality(orderLineItemsDto.getQuality());

        return orderLineItems;
    }
}
