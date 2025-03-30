package com.musinsa.assignment.controller;


import com.musinsa.assignment.controller.dto.ProductAddRequest;
import com.musinsa.assignment.controller.dto.ProductDeleteRequest;
import com.musinsa.assignment.controller.dto.ProductUpdateRequest;
import com.musinsa.assignment.domain.product.Product;
import com.musinsa.assignment.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Product add(@RequestBody ProductAddRequest request) throws ExecutionException, InterruptedException {
        return productService.add(request);
    }

    @DeleteMapping("/delete/id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestBody ProductDeleteRequest request) {
        productService.deleteById(request.getId());
    }

    @DeleteMapping("/delete/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(@RequestBody ProductDeleteRequest request) {
        productService.deleteByName(request.getName());
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ProductUpdateRequest request) {
        productService.update(request);
    }
}
