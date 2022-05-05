package com.ecommerceapp.orderservice.controller;

import com.ecommerceapp.orderservice.dto.OrderRequestDto;
import com.ecommerceapp.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.submitOrder(orderRequestDto);
    }
}
