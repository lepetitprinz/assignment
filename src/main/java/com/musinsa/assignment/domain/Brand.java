package com.musinsa.assignment.domain;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Brand {
    @Id
    @Hidden
    @GeneratedValue(generator = "string")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Builder
    public Brand(String name) {
        this.name = name;
    }
}
