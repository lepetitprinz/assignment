package com.musinsa.assignment.controller;

import com.musinsa.assignment.controller.dto.BrandAddRequest;
import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Brand add(@RequestBody BrandAddRequest request) throws ExecutionException, InterruptedException {
        return brandService.add(request);
    }
}
