package com.ecommerceapp.orderservice.controller;

import com.ecommerceapp.orderservice.dto.OrderRequestDto;
import com.ecommerceapp.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> submitOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return CompletableFuture.supplyAsync(() -> orderService.submitOrder(orderRequestDto));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequestDto orderRequestDto, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "An error occurred! Please try again later");
    }
}
