package com.musinsa.assignment.controller;

import com.musinsa.assignment.controller.dto.PriceByCategoryRangeResponse;
import com.musinsa.assignment.controller.dto.PriceByCategoryMinResponse;
import com.musinsa.assignment.controller.dto.PriceByBrandMinResponse;
import com.musinsa.assignment.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/category/all/min")
    public PriceByCategoryMinResponse getMinPrice() {
        return priceService.getMinPriceProductByAllCategories();
    }

    @GetMapping("/category/all/brand/min")
    public PriceByBrandMinResponse getMinBrandPrice() {
        return priceService.getMinPriceBrandByAllCategories();
    }

    @GetMapping("/category/range")
    public PriceByCategoryRangeResponse getCategoryPriceRange(@RequestParam String category) {
        return priceService.getCategoryPriceRange(category);
    }

}
