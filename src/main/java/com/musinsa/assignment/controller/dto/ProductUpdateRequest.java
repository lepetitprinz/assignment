package com.musinsa.assignment.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductUpdateRequest {
    private UUID id;
    private String name;
    private Category category;
    private Brand brand;
    private Integer price;

    public ProductUpdateRequest(UUID id, Category category) {
        this.id = id;
        this.category = category;
    }

    public ProductUpdateRequest(UUID id, Brand brand) {
        this.id = id;
        this.brand = brand;
    }

    public ProductUpdateRequest(UUID id, Integer price) {
        this.id = id;
        this.price = price;
    }

    public ProductUpdateRequest(UUID id, Category category, Brand brand) {
        this.id = id;
        this.category = category;
        this.brand = brand;
    }

    public ProductUpdateRequest(UUID id, Category category, Integer price) {
        this.id = id;
        this.category = category;
        this.price = price;
    }

    public ProductUpdateRequest(UUID id, Brand brand, Integer price) {
        this.id = id;
        this.brand = brand;
        this.price = price;
    }

    public ProductUpdateRequest(
            @JsonProperty("id") UUID id,
            @JsonProperty("category") Category category,
            @JsonProperty("brand") Brand brand,
            @JsonProperty("price") Integer price
    ) {
        this.id = id;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }
}
