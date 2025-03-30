package com.musinsa.assignment.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ProductDeleteRequest {
    private UUID id;
    private String name;

    public ProductDeleteRequest(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    @JsonCreator
    public ProductDeleteRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
