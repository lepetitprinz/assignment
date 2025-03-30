package com.musinsa.assignment.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceByBrandMinResponse {
    private PriceByBrandMin result;

    public PriceByBrandMinResponse(PriceByBrandMin result) {

        this.result = result;
    }
}
