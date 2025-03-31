package com.musinsa.assignment.service;

import com.musinsa.assignment.controller.dto.ProductAddRequest;
import com.musinsa.assignment.controller.dto.ProductUpdateRequest;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import com.musinsa.assignment.domain.product.Product;
import com.musinsa.assignment.exception.BrandErrorCode;
import com.musinsa.assignment.exception.CategoryErrorCode;
import com.musinsa.assignment.exception.ProductErrorCode;
import com.musinsa.assignment.repository.BrandRepository;
import com.musinsa.assignment.repository.CategoryRepository;
import com.musinsa.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    private final CacheService cacheService;

    @Transactional
    public Product findById(UUID id) {
        return productRepository.findById(id)
            .orElseThrow(ProductErrorCode.PRODUCT_NOT_FOUND::exception);
    }

    @Transactional
    public Product findByName(String name) {
        return productRepository.findByName(name)
            .orElseThrow(ProductErrorCode.PRODUCT_NOT_FOUND::exception);
    }

    @Transactional
    public Product add(ProductAddRequest request) {
        Category categoryId = categoryRepository.findByName(request.getCategory().getName())
            .orElseThrow(CategoryErrorCode.CATEGORY_NOT_FOUND::exception);

        Brand brandId = brandRepository.findByName(request.getBrand().getName())
            .orElseThrow(BrandErrorCode.BRAND_NOT_FOUND::exception);

        Optional<Product> productName = productRepository.findByName(request.getName());
        if (productName.isPresent()) {
            throw ProductErrorCode.PRODUCT_ALREADY_EXISTS.exception();
        }

        if (request.getPrice() == null) {
            throw ProductErrorCode.PRICE_NOT_EXIST.exception();
        }

        Product newProduct = Product.builder()
            .name(request.getName())
            .category(categoryId)
            .brand(brandId)
            .price(request.getPrice())
            .build();

        // update cache
        cacheService.updatePrice(newProduct);

        return productRepository.save(newProduct);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!productRepository.existsById(id)) {
            throw ProductErrorCode.PRODUCT_NOT_FOUND.exception();
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteByName(String name) {
        if (!productRepository.existsByName(name)) {
            throw ProductErrorCode.PRODUCT_NOT_FOUND.exception();
        }
        productRepository.deleteByName(name);
    }

    @Transactional
    public Product update(ProductUpdateRequest request) {
        Product product = productRepository.findById(request.getId())
            .orElseThrow(ProductErrorCode.PRODUCT_NOT_FOUND::exception);

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getCategory() != null) {
            Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseThrow(CategoryErrorCode.CATEGORY_NOT_FOUND::exception);
            product.setCategory(category);
        }

        if (request.getBrand() != null) {
            Brand brand = brandRepository.findByName(request.getBrand().getName())
                .orElseThrow(BrandErrorCode.BRAND_NOT_FOUND::exception);
            product.setBrand(brand);
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        // update cache
        cacheService.updatePrice(product);

        return product;
    }
}
