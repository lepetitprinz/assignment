package com.musinsa.assignment.service;

import com.musinsa.assignment.domain.Category;
import com.musinsa.assignment.exception.CategoryErrorCode;
import com.musinsa.assignment.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category findByName(String name) {
        return categoryRepository.findByName(name)
            .orElseThrow(CategoryErrorCode.CATEGORY_NOT_FOUND::exception);
    }
}
