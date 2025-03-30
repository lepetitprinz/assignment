package com.musinsa.assignment.service;

import com.musinsa.assignment.controller.dto.BrandAddRequest;
import com.musinsa.assignment.domain.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @Test
    @DisplayName("Brand add success test")
    public void successBrandAdd() {
        BrandAddRequest request = createTestAddRequest();

        // add the new brand
        brandService.add(request);

        Brand brand = brandService.findByName(request.getName());
        assertThat(brand).isNotNull();
        assertEquals(brand.getName(), request.getName());
    }

    private BrandAddRequest createTestAddRequest() {
        return BrandAddRequest.builder()
            .name("new-brand")
            .build();
    }
}

