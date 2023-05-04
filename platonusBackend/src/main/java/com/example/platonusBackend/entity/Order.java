package com.example.platonusBackend.entity;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

public class Order {
    private Long id;

    @NotBlank
    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Positive
    private int quantity;
    @NotNull
    @Positive
    private double price;

    public Order() {
    }

    public Order(Long id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
