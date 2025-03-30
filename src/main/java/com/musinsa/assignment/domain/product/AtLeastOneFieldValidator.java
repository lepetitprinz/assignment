package com.musinsa.assignment.domain.product;

import com.musinsa.assignment.controller.dto.ProductUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, ProductUpdateRequest> {

    @Override
    public boolean isValid(ProductUpdateRequest request, ConstraintValidatorContext context) {
        return request != null &&
            (request.getName() != null ||
                request.getPrice() != null ||
                request.getBrand() != null ||
                request.getCategory() != null);
    }
}
