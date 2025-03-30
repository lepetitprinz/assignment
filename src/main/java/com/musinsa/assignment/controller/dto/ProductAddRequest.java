package com.musinsa.assignment.controller.dto;

import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductAddRequest {
    @Hidden
    private UUID id;
    private String name;
    private Category category;
    private Brand brand;
    private Integer price;

    @Builder
    public ProductAddRequest(String name, Category category, Brand brand, Integer price) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }
}
