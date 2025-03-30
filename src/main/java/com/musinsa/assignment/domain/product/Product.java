package com.musinsa.assignment.domain.product;

import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Hidden
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = true)
    @Schema(defaultValue = "0")
    private Integer price;

    @Builder
    public Product(String name, Category category, Brand brand, int price) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }
}
