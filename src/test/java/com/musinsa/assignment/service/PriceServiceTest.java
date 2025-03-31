package com.musinsa.assignment.service;

import com.musinsa.assignment.controller.dto.*;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import com.musinsa.assignment.domain.product.Product;
import com.musinsa.assignment.repository.BrandRepository;
import com.musinsa.assignment.repository.CategoryRepository;
import com.musinsa.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PriceServiceTest {

    @Autowired
    private PriceService priceService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {}

    @Test
    @DisplayName("Check that result equals expected each min price of products by all of categories")
    public void checkGetMinPriceProductByAllCategories() {
        PriceByCategoryMinResponse response = priceService.getMinPriceProductByAllCategories();

        // verify individual categories
        PriceByBrandDTO top = response.getMinPriceByCategory().get("상의");
        assertEquals("C", top.getBrand());
        assertEquals(10000, top.getPrice());

        PriceByBrandDTO outer = response.getMinPriceByCategory().get("아우터");
        assertEquals("E", outer.getBrand());
        assertEquals(5000, outer.getPrice());

        PriceByBrandDTO sneakers = response.getMinPriceByCategory().get("스니커즈");
        assertEquals("G", sneakers.getBrand());
        assertEquals(9000, sneakers.getPrice());

        PriceByBrandDTO bag = response.getMinPriceByCategory().get("가방");
        assertEquals("A", bag.getBrand());
        assertEquals(2000, bag.getPrice());

        PriceByBrandDTO hat = response.getMinPriceByCategory().get("모자");
        assertEquals("D", hat.getBrand());
        assertEquals(1500, hat.getPrice());

        PriceByBrandDTO socks = response.getMinPriceByCategory().get("양말");
        assertEquals("I", socks.getBrand());
        assertEquals(1700, socks.getPrice());

        PriceByBrandDTO acc = response.getMinPriceByCategory().get("액세서리");
        assertEquals("F", acc.getBrand());
        assertEquals(1900, acc.getPrice());

        assertEquals(response.getTotalPrice(), 43100);
    }

    @Test
    @DisplayName("Check that result equals expected min total prices of a brand by all of categories")
    public void checkGetMinPriceBrandByAllCategories() {
        PriceByBrandMinResponse response = priceService.getMinPriceBrandByAllCategories();
        PriceByBrandMin result = response.getResult();
        assertEquals(result.getBrand(), "D");
        assertEquals(result.getTotalPrice(), 36100);

        for (PriceByCategoryDTO categoryDTO: result.getCategories()) {
            if (Objects.equals(categoryDTO.getCategory(), "상의")) {
                assertEquals(categoryDTO.getPrice(), 10100);
            } else if (Objects.equals(categoryDTO.getCategory(), "아우터")) {
                assertEquals(categoryDTO.getPrice(), 5100);
            } else if (Objects.equals(categoryDTO.getCategory(), "바지")) {
                assertEquals(categoryDTO.getPrice(), 3000);
            } else if (Objects.equals(categoryDTO.getCategory(), "스니커즈")) {
                assertEquals(categoryDTO.getPrice(), 9500);
            } else if (Objects.equals(categoryDTO.getCategory(), "가방")) {
                assertEquals(categoryDTO.getPrice(), 2500);
            } else if (Objects.equals(categoryDTO.getCategory(), "모자")) {
                assertEquals(categoryDTO.getPrice(), 1500);
            } else if (Objects.equals(categoryDTO.getCategory(), "양말")) {
                assertEquals(categoryDTO.getPrice(), 2400);
            } else if (Objects.equals(categoryDTO.getCategory(), "액세서리")) {
                assertEquals(categoryDTO.getPrice(), 2000);
            }
        }
    }

    @Test
    @DisplayName("")
    public void checkGetCategoryPriceRange() {
        String categoryName = "상의";
        PriceByCategoryRangeResponse response = priceService.getCategoryPriceRange(categoryName);
        List<PriceByBrandDTO> minPrices = response.getMinPrice();
        List<PriceByBrandDTO> maxPrices = response.getMaxPrice();

        assertEquals(response.getCategory(), categoryName);
        assertEquals(minPrices.get(0).getBrand(), "C");
        assertEquals(minPrices.get(0).getPrice(), 10000);
        assertEquals(maxPrices.get(0).getBrand(), "I");
        assertEquals(maxPrices.get(0).getPrice(), 11400);
    }
}
