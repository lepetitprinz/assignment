package com.musinsa.assignment.controller.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BrandAddRequest {
    @Hidden
    private UUID id;
    private String name;

    @Builder
    BrandAddRequest(String name) {
        this.name = name;
    }
}
