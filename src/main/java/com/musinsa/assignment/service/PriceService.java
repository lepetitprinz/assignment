package com.musinsa.assignment.service;

import com.musinsa.assignment.controller.dto.*;
import com.musinsa.assignment.exception.BrandErrorCode;
import com.musinsa.assignment.exception.CategoryErrorCode;
import com.musinsa.assignment.exception.ProductErrorCode;
import com.musinsa.assignment.repository.CategoryRepository;
import com.musinsa.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final CacheService cacheService;

    @Transactional
    public PriceByCategoryMinResponse getMinPriceProductByAllCategories() {
        Map<Object, Object> cache = cacheService.getMinPriceByAllCategory();
        if (cache != null) {
            return toPriceByCategoryMinResponse(cache);
        }

        List<Object[]> rawResults = productRepository.findMinPriceProductsByCategory();

        Map<String, PriceByBrandDTO> minPriceByCategory = new HashMap<>();

        int totalPrice = 0;
        for (Object[] row : rawResults) {
            String category = (String) row[0];
            String brand = (String) row[1];
            int price = (int) row[2];

            minPriceByCategory.put(category, new PriceByBrandDTO(brand, price));
            totalPrice += price;
        }

        return new PriceByCategoryMinResponse(minPriceByCategory, totalPrice);
    }

    private PriceByCategoryMinResponse toPriceByCategoryMinResponse(Map<Object, Object> cache) {
        int totalPrice = 0;
        Map<String, PriceByBrandDTO> result = new HashMap<>();

        for (Map.Entry<Object, Object> entry : cache.entrySet()) {
            String category = entry.getKey().toString();
            String value = entry.getValue().toString();

            String[] parts = value.split(":");
            if (parts.length == 2) {
                String brand = parts[0];
                int price = Integer.parseInt(parts[1]);
                result.put(category, new PriceByBrandDTO(brand, price));

                totalPrice += price;
            }
        }

        return new PriceByCategoryMinResponse(result, totalPrice);
    }

    @Transactional
    public PriceByBrandMinResponse getMinPriceBrandByAllCategories() {
        List<Object[]> result = productRepository.findMinTotalPriceByBrandForAllCategories();

        if (result.isEmpty()) {
            throw BrandErrorCode.MIN_PRICE_BRAND_NO_CONTENT.exception();
        }

        String brand = (String) result.get(0)[0];
        int totalPrice = ((Number) result.get(0)[3]).intValue();

        List<PriceByCategoryDTO> categoryPrices = result.stream()
            .map(row -> new PriceByCategoryDTO(
                (String) row[1],
                ((Number) row[2]).intValue()
            ))
            .toList();

        PriceByBrandMin priceByBrandMin = new PriceByBrandMin(brand, categoryPrices, totalPrice);
        return new PriceByBrandMinResponse(priceByBrandMin);
    }

    public PriceByCategoryRangeResponse getCategoryPriceRange(String categoryName) {
        // validate that category exist
        categoryRepository.findByName(categoryName)
            .orElseThrow(CategoryErrorCode.CATEGORY_NOT_FOUND::exception);

        List<Object[]> result = productRepository.findMinAndMaxPricesByCategory(categoryName);

        if (result.isEmpty()) {
            throw ProductErrorCode.PRODUCT_NOT_FOUND.exception();
        }

        List<PriceByBrandDTO> minList = new ArrayList<>();
        List<PriceByBrandDTO> maxList = new ArrayList<>();

        for (Object[] row : result) {
            String type = (String) row[0];
            String brand = (String) row[1];
            int price = ((Number) row[2]).intValue();

            if ("min".equals(type)) {
                minList.add(new PriceByBrandDTO(brand, price));
            } else if ("max".equals(type)) {
                maxList.add(new PriceByBrandDTO(brand, price));
            }
        }

        return new PriceByCategoryRangeResponse(categoryName, minList, maxList);
    }
}
