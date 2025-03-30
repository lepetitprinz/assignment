package com.musinsa.assignment.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriceByCategoryRangeResponse {
    private String category;
    private List<PriceByBrandDTO> minPrice;
    private List<PriceByBrandDTO> maxPrice;

    public PriceByCategoryRangeResponse(String category, List<PriceByBrandDTO> minPrice, List<PriceByBrandDTO> maxPrice) {
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
