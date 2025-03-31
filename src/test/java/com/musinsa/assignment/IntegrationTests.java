package com.musinsa.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.assignment.controller.dto.BrandAddRequest;
import com.musinsa.assignment.controller.dto.ProductAddRequest;
import com.musinsa.assignment.controller.dto.ProductDeleteRequest;
import com.musinsa.assignment.controller.dto.ProductUpdateRequest;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;

import com.musinsa.assignment.service.BrandService;
import com.musinsa.assignment.service.CategoryService;
import com.musinsa.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IntegrationTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired private ProductService productService;
    @Autowired private BrandService brandService;
    @Autowired private CategoryService categoryService;

    @BeforeEach
    void setup() {}

    @Test
    @DisplayName("IntegrationTest: Add the brand")
    @Transactional
    void testAddBrand() throws Exception {
        BrandAddRequest request = BrandAddRequest.builder()
            .name("New-Brand")
            .build();

        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/brand/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Integration Test: Add the product")
    @Transactional
    void testAddProduct() throws Exception {
        ProductAddRequest request = ProductAddRequest.builder()
            .name("new-product")
            .price(5000)
            .brand(new Brand("D"))
            .category(new Category("스니커즈"))
            .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/product/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(request.getName()))
            .andExpect(jsonPath("$.category.name").value(request.getCategory().getName()))
            .andExpect(jsonPath("$.brand.name").value(request.getBrand().getName()))
            .andExpect(jsonPath("$.price").value(request.getPrice()));
    }

    @Test
    @DisplayName("Integration Test: Update the product")
    @Transactional
    void testUpdateProduct() throws Exception {
        UUID id = productService.findByName("BRD-A-CTGR-4-1").getId();

        // create a new category of target category to update
        Category category = categoryService.findByName("스니커즈");
        Brand brand = brandService.findByName("F");

        Integer newPrice = 999;

        ProductUpdateRequest request = new ProductUpdateRequest(id, category, brand, newPrice);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Integration test: Delete the product")
    @Transactional
    void testDeleteProduct() throws Exception {
        UUID id = productService.findByName("BRD-A-CTGR-1-1").getId();

        ProductDeleteRequest request1 = new ProductDeleteRequest(id);
        String json1 = objectMapper.writeValueAsString(request1);
        mockMvc.perform(delete("/api/v1/product/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json1))
            .andExpect(status().isNotFound());

        String name = productService.findByName("BRD-B-CTGR-1-1").getName();

        ProductDeleteRequest request2 = new ProductDeleteRequest(name);
        String json2 = objectMapper.writeValueAsString(request2);
        mockMvc.perform(delete("/api/v1/product/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json2))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Integration test: total minimum prices for each brand of all categories")
    @Transactional
    void testGetMinPriceProductByAllCategories() throws Exception {
        mockMvc.perform(get("/api/v1/price/category/all/min"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPrice").value(43100))
            .andExpect(jsonPath("$.minPriceByCategory.상의.brand").value("C"))
            .andExpect(jsonPath("$.minPriceByCategory.상의.price").value(10000))
            .andExpect(jsonPath("$.minPriceByCategory.아우터.brand").value("E"))
            .andExpect(jsonPath("$.minPriceByCategory.아우터.price").value(5000))
            .andExpect(jsonPath("$.minPriceByCategory.바지.brand").value("D"))
            .andExpect(jsonPath("$.minPriceByCategory.바지.price").value(3000))
            .andExpect(jsonPath("$.minPriceByCategory.스니커즈.brand").value("G"))
            .andExpect(jsonPath("$.minPriceByCategory.스니커즈.price").value(9000))
            .andExpect(jsonPath("$.minPriceByCategory.가방.brand").value("A"))
            .andExpect(jsonPath("$.minPriceByCategory.가방.price").value(2000))
            .andExpect(jsonPath("$.minPriceByCategory.모자.brand").value("D"))
            .andExpect(jsonPath("$.minPriceByCategory.모자.price").value(1500))
            .andExpect(jsonPath("$.minPriceByCategory.양말.brand").value("I"))
            .andExpect(jsonPath("$.minPriceByCategory.양말.price").value(1700))
            .andExpect(jsonPath("$.minPriceByCategory.액세서리.brand").value("F"))
            .andExpect(jsonPath("$.minPriceByCategory.액세서리.price").value(1900))
        ;
    }

    @Test
    @DisplayName("Integration test: total minimum prices for each brand of all categories")
    @Transactional
    void testGetMinPriceBrandByAllCategories() throws Exception {
        mockMvc.perform(get("/api/v1/price/category/all/brand/min"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.totalPrice").value(36100))
            .andExpect(jsonPath("$.result.brand").value("D"))
            .andExpect(jsonPath("$.result.categories[*].category",
                containsInAnyOrder("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")))
            .andExpect(jsonPath("$.result.categories[*].price",
                containsInAnyOrder(10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000)));
    }

    @Test
    @DisplayName("Integration tes: Min-Max prices by brand of one category")
    @Transactional
    void testGetCategoryPriceRange() throws Exception {
        mockMvc.perform(get("/api/v1/price/category/range")
                .param("category", "상의"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.category").value("상의"))
            .andExpect(jsonPath("$.minPrice[0].brand").value("C"))
            .andExpect(jsonPath("$.minPrice[0].price").value(10000))
            .andExpect(jsonPath("$.maxPrice[0].brand").value("I"))
            .andExpect(jsonPath("$.maxPrice[0].price").value(11400))
        ;
    }
}
