package com.musinsa.assignment.service;

import com.musinsa.assignment.controller.dto.ProductAddRequest;
import com.musinsa.assignment.controller.dto.ProductUpdateRequest;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import com.musinsa.assignment.domain.product.Product;
import com.musinsa.assignment.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    private final String productName = "test-";
    private final String newCategoryName = "스니커즈";
    private final String newBrandName = "D";

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    public void before() {
    }

    @Test
    @DisplayName("product add success test")
    public void successProductAdd() {
        ProductAddRequest request = createTestAddProductRequest();
        Product createdProduct = productService.add(request);

        Product product = productService.findById(createdProduct.getId());
        assertThat(product).isNotNull();
        assertEquals(product.getName(), request.getName());
        assertEquals(product.getCategory().getName(), createdProduct.getCategory().getName());
        assertEquals(product.getBrand().getName(), createdProduct.getBrand().getName());
        assertEquals(product.getPrice(), createdProduct.getPrice());
    }

    @Test
    @DisplayName("product update success test 1")
    public void successProductUpdate1() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();
        
        UUID newCategoryId = categoryService.findByName(newCategoryName).getId();
        Brand newBrand = brandService.findByName(newBrandName);
        UUID newBrandId = newBrand.getId();

        Category category = Category.builder().name(newCategoryName).build();
        Brand brand = Brand.builder().name(newBrandName).build();
        Integer price = 100000;

        ProductUpdateRequest request = new ProductUpdateRequest(id, category);
        Product updatedProduct = productService.update(request);
        assertEquals(newCategoryId, updatedProduct.getCategory().getId());

        ProductUpdateRequest request7 = new ProductUpdateRequest(id, category, brand, price);
        Product updatedProduct7 = productService.update(request7);
        assertEquals(newCategoryId, updatedProduct7.getCategory().getId());
        assertEquals(newBrandId, updatedProduct7.getBrand().getId());
        assertEquals(price, updatedProduct7.getPrice());

        productService.deleteById(id);
    }

    @Test
    @DisplayName("product update success test 2")
    public void successProductUpdate2() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();

        Brand newBrand = brandService.findByName(newBrandName);
        UUID newBrandId = newBrand.getId();

        Brand brand = Brand.builder().name(newBrandName).build();

        ProductUpdateRequest request = new ProductUpdateRequest(id, brand);
        Product updatedProduct = productService.update(request);
        assertEquals(newBrandId, updatedProduct.getBrand().getId());
    }

    @Test
    @DisplayName("product update success test 3")
    public void successProductUpdate3() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();

        Integer newPrice = 100000;

        ProductUpdateRequest request = new ProductUpdateRequest(id, newPrice);
        Product updatedProduct = productService.update(request);
        assertEquals(newPrice, updatedProduct.getPrice());
    }

    @Test
    @DisplayName("product update success test 4")
    public void successProductUpdate4() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();

        UUID newCategoryId = categoryService.findByName(newCategoryName).getId();
        Brand newBrand = brandService.findByName(newBrandName);
        UUID newBrandId = newBrand.getId();

        Category category = Category.builder().name(newCategoryName).build();
        Brand brand = Brand.builder().name(newBrandName).build();
        ProductUpdateRequest request = new ProductUpdateRequest(id, category, brand);
        Product updatedProduct = productService.update(request);
        assertEquals(newCategoryId, updatedProduct.getCategory().getId());
        assertEquals(newBrandId, updatedProduct.getBrand().getId());
    }

    @Test
    @DisplayName("product update success test 5")
    public void successProductUpdate5() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();

        UUID newCategoryId = categoryService.findByName(newCategoryName).getId();

        Category category = Category.builder().name(newCategoryName).build();
        Integer price = 100000;

        ProductUpdateRequest request5 = new ProductUpdateRequest(id, category, price);
        Product updatedProduct5 = productService.update(request5);
        assertEquals(newCategoryId, updatedProduct5.getCategory().getId());
        assertEquals(price, updatedProduct5.getPrice());
    }

    @Test
    @DisplayName("product update success test 6")
    public void successProductUpdate6() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();
        
        Brand newBrand = brandService.findByName(newBrandName);
        UUID newBrandId = newBrand.getId();

        Brand brand = Brand.builder().name(newBrandName).build();
        Integer price = 100000;

        ProductUpdateRequest request = new ProductUpdateRequest(id, brand, price);
        Product updatedProduct = productService.update(request);
        assertEquals(newBrandId, updatedProduct.getBrand().getId());
        assertEquals(price, updatedProduct.getPrice());
    }

    @Test
    @DisplayName("product update success test 7")
    public void successProductUpdate7() {
        // create a new product
        Product createdProduct = productService.add(createTestAddProductRequest());
        UUID id = createdProduct.getId();
        
        UUID newCategoryId = categoryService.findByName(newCategoryName).getId();
        
        Brand newBrand = brandService.findByName(newBrandName);
        UUID newBrandId = newBrand.getId();

        Category category = Category.builder().name(newCategoryName).build();
        Brand brand = Brand.builder().name(newBrandName).build();
        Integer price = 100000;

        ProductUpdateRequest request = new ProductUpdateRequest(id, category, brand, price);
        Product updatedProduct = productService.update(request);
        assertEquals(newCategoryId, updatedProduct.getCategory().getId());
        assertEquals(newBrandId, updatedProduct.getBrand().getId());
        assertEquals(price, updatedProduct.getPrice());
    }

    @Test
    @DisplayName("product delete test")
    public void successProductDelete() {
        Product createdProduct = productService.add(createTestAddProductRequest());
        productService.deleteByName(createdProduct.getName());
        assertThrows(ProductException.class, () ->  {
            productService.findById(createdProduct.getId());
        });
    }

    private ProductAddRequest createTestAddProductRequest() {
        Category category = Category.builder()
            .name("상의")
            .build();

        Brand brand = Brand.builder()
            .name("A")
            .build();

        return ProductAddRequest.builder()
            .name(productName + UUID.randomUUID())
            .category(category)
            .brand(brand)
            .price(100000)
            .build();
    }
}
