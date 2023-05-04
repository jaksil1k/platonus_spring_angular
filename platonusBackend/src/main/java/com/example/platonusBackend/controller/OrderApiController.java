package com.example.platonusBackend.controller;

import com.example.platonusBackend.entity.Order;
import com.example.platonusBackend.exception.OrderNotFoundException;
import com.example.platonusBackend.serivce.OrderService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderApiController {
    private final OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Order order) {
        try {
            return ResponseEntity.ok(orderService.updateOrSave(order));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.getById(id);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new OrderNotFoundException("no order with id = " + id));
        }
        return ResponseEntity.ok(optionalOrder.get());
    }
}
