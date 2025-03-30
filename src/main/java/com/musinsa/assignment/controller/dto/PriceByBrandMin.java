package com.musinsa.assignment.controller.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriceByBrandMin {
    private String brand;
    private List<PriceByCategoryDTO> categories;
    private int totalPrice;

    public PriceByBrandMin(String brand, List<PriceByCategoryDTO> categories, int totalPrice) {
        this.brand = brand;
        this.categories = categories;
        this.totalPrice = totalPrice;
    }
}
