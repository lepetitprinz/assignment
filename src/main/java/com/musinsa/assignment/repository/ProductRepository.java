package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByName(String name);
    Boolean existsByName(String name);
    void deleteByName(String name);

    @Query(value = """
        SELECT
            p.category.name,
            p.brand.name,
            MIN(p.price)
        FROM Product p
        GROUP BY p.category.name, p.brand.name, p.price
        HAVING p.price = (
            SELECT MIN(p2.price)
            FROM Product p2
            WHERE p2.category.name = p.category.name
        )
    """)
    List<Object[]> findMinPriceProductsByCategory();

    @Query(value = """
        WITH brand_category_prices AS (
        SELECT
            b.name AS brand_name,
            c.name AS category_name,
            MIN(p.price) AS min_price
        FROM product p
        JOIN brand b ON p.brand_id = b.id
        JOIN category c ON p.category_id = c.id
        GROUP BY b.name, c.name
        ),
        brand_totals AS (
            SELECT
                brand_name,
                SUM(min_price) AS total_price,
                COUNT(*) AS category_count
            FROM brand_category_prices
            GROUP BY brand_name
            HAVING COUNT(*) = (SELECT COUNT(*) FROM category)
        ),
        best_brand AS (
            SELECT *
            FROM brand_totals
            ORDER BY total_price ASC
            LIMIT 1
        )
        SELECT
            bcp.brand_name,
            bcp.category_name,
            bcp.min_price,
            bb.total_price
        FROM brand_category_prices bcp
        JOIN best_brand bb ON bcp.brand_name = bb.brand_name
        ORDER BY bcp.category_name
        """, nativeQuery = true)
    List<Object[]> findMinTotalPriceByBrandForAllCategories();

    @Query(value = """
        SELECT 
            'min' AS price_type, 
            b.name AS brand, 
            p.price
        FROM product p
        JOIN brand b ON p.brand_id = b.id
        JOIN category c ON p.category_id = c.id
        WHERE c.name = :categoryName
          AND p.price = (
            SELECT MIN(p2.price)
            FROM product p2
            JOIN category c2 ON p2.category_id = c2.id
            WHERE c2.name = :categoryName
        )
        
        UNION ALL
        
        SELECT 'max' AS price_type, b.name AS brand, p.price
        FROM product p
        JOIN brand b ON p.brand_id = b.id
        JOIN category c ON p.category_id = c.id
        WHERE c.name = :categoryName
          AND p.price = (
            SELECT MAX(p2.price)
            FROM product p2
            JOIN category c2 ON p2.category_id = c2.id
            WHERE c2.name = :categoryName
        )
    """, nativeQuery = true)
    List<Object[]> findMinAndMaxPricesByCategory(@Param("categoryName") String categoryName);
}
