package com.musinsa.assignment.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PriceByCategoryMinResponse {
    private Map<String, PriceByBrandDTO> minPriceByCategory;
    private int totalPrice;

    public PriceByCategoryMinResponse(Map<String, PriceByBrandDTO> minPriceByCategory,
                                      int totalPrice) {
        this.minPriceByCategory = minPriceByCategory;
        this.totalPrice = totalPrice;
    }
}
