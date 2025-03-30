package com.musinsa.assignment.controller.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceByCategoryDTO {
    private String category;
    private int price;

    public PriceByCategoryDTO(String category, int price) {
        this.category = category;
        this.price = price;
    }
}
