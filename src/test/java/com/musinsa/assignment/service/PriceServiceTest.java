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
    public void before() {
        setCategories();
        setBrands();
        setProducts();
        System.out.println("Test before ");
    }

    @Test
    @DisplayName("Check that result equals expected each min price of products by all of categories")
    public void checkGetMinPriceProductByAllCategories() {
        PriceByCategoryMinResponse response = priceService.getMinPriceProductByAllCategories();

        // verify individual categories
        PriceByBrandDTO top = response.getMinPriceByCategory().get("상의");
        assertEquals("A", top.getBrand());
        assertEquals(1000, top.getPrice());

        PriceByBrandDTO outer = response.getMinPriceByCategory().get("아우터");
        assertEquals("C", outer.getBrand());
        assertEquals(2000, outer.getPrice());

        PriceByBrandDTO pants = response.getMinPriceByCategory().get("바지");
        assertEquals("B", pants.getBrand());
        assertEquals(300, pants.getPrice());

        assertEquals(response.getTotalPrice(), 3300);
    }

    @Test
    @DisplayName("Check that result equals expected min total prices of a brand by all of categories")
    public void checkGetMinPriceBrandByAllCategories() {
        PriceByBrandMinResponse response = priceService.getMinPriceBrandByAllCategories();
        PriceByBrandMin result = response.getResult();
        assertEquals(result.getBrand(), "B");
        assertEquals(result.getTotalPrice(), 3700);

        for (PriceByCategoryDTO categoryDTO: result.getCategories()) {
            if (Objects.equals(categoryDTO.getCategory(), "상의")) {
                assertEquals(categoryDTO.getPrice(), 1100);
            } else if (Objects.equals(categoryDTO.getCategory(), "아우터")) {
                assertEquals(categoryDTO.getPrice(), 2300);
            } else if (Objects.equals(categoryDTO.getCategory(), "바지")) {
                assertEquals(categoryDTO.getPrice(), 300);
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
        assertEquals(minPrices.get(0).getBrand(), "A");
        assertEquals(minPrices.get(0).getPrice(), 1000);
        assertEquals(maxPrices.get(0).getBrand(), "C");
        assertEquals(maxPrices.get(0).getPrice(), 1200);
    }

    private void setCategories() {
        // set categories
        categoryRepository.save(new Category("상의"));
        categoryRepository.save(new Category("아우터"));
        categoryRepository.save(new Category("바지"));
    }

    private void setBrands() {
        // set brands
        brandRepository.save(new Brand("A"));
        brandRepository.save(new Brand("B"));
        brandRepository.save(new Brand("C"));
    }

    private void setProducts() {
        Category top = categoryRepository.findByName("상의").orElseThrow();
        Category outer = categoryRepository.findByName("아우터").orElseThrow();
        Category pants = categoryRepository.findByName("바지").orElseThrow();

        Brand brandA = brandRepository.findByName("A").orElseThrow();
        Brand brandB = brandRepository.findByName("B").orElseThrow();
        Brand brandC = brandRepository.findByName("C").orElseThrow();

        // set products
        productRepository.save(new Product("prd1", top, brandA, 1000));
        productRepository.save(new Product("prd2", top, brandB, 1100));
        productRepository.save(new Product("prd3", top, brandC, 1200));
        productRepository.save(new Product("prd4", outer, brandA, 2500));
        productRepository.save(new Product("prd5", outer, brandB, 2300));
        productRepository.save(new Product("prd6", outer, brandC, 2000));
        productRepository.save(new Product("prd7", pants, brandA, 500));
        productRepository.save(new Product("prd8", pants, brandB, 300));
        productRepository.save(new Product("prd9", pants, brandC, 850));
    }
}
