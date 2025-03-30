package com.musinsa.assignment.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceByBrandDTO {
    private String brand;
    private int price;

    public PriceByBrandDTO(String brand, int price) {
        this.brand = brand;
        this.price = price;
    }
}
