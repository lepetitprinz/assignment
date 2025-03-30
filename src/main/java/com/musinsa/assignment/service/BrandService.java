package com.musinsa.assignment.service;

import com.musinsa.assignment.controller.dto.BrandAddRequest;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.exception.BrandErrorCode;
import com.musinsa.assignment.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public Brand add(BrandAddRequest request) {
        Optional<Brand> brand = brandRepository.findByName(request.getName());
        if (brand.isPresent()) {
            throw BrandErrorCode.BRAND_ALREADY_EXISTS.exception();
        }

        Brand newBrand = Brand.builder().
            name(request.getName())
            .build();

        return brandRepository.save(newBrand);
    }

    @Transactional
    public Brand findByName(String name) {
        return brandRepository.findByName(name)
            .orElseThrow(BrandErrorCode.BRAND_NOT_FOUND::exception);
    }
}
