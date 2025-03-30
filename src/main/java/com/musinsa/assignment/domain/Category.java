package com.musinsa.assignment.domain;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @Hidden
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    @Builder
    public Category(String name) {
        this.name = name;
    }
}
