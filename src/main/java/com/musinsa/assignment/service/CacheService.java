package com.musinsa.assignment.service;

import com.musinsa.assignment.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private static final String redisHashKeyBrand = "brand";
    private static final String redisHashKeyPrice = "price";
    private static final String redisPrefixMinPrice = "price:min:";
    private static final String redisPrefixMaxPrice = "price:max:";
    private static final String redisPrefixMinPriceAll = "price:all";

    private final RedisService redisService;

    public Map<Object, Object> getMinPriceByAllCategory() {
        try {
            CompletableFuture<Boolean> isExist = redisService.exist(redisPrefixMinPriceAll);
            if (isExist.get()) {
                return redisService.getAllHashEntries(redisPrefixMinPriceAll).get();
            }
        }  catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Transactional
    protected void updatePrice(Product product) {
        Boolean isMinUpdated = updateMinPriceByCategory(product);
        if (isMinUpdated) {
            updatePriceByMinAll(product);
        }
    }

    @Transactional
    protected void updatePriceByMinAll(Product product) {
        String categoryName = product.getCategory().getName();
        String brandName = product.getBrand().getName();
        String price = product.getPrice().toString();
        try {
            CompletableFuture<Boolean> isKeyExist = redisService.exist(redisPrefixMinPriceAll);
            if (isKeyExist.get()) {
                CompletableFuture<Boolean> isFieldExist = redisService.hashFieldHashMap(redisPrefixMinPrice, categoryName);
                if (isFieldExist.get()) {
                    updateCategoryHashMap(redisPrefixMinPriceAll, categoryName, brandName, price);

                } else {
                    addCategoryHashMap(redisPrefixMinPriceAll, categoryName, brandName, price);
                }
            } else {
                addCategoryHashMap(redisPrefixMinPriceAll, categoryName, brandName, price);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    protected Boolean updateMinPriceByCategory(Product product) {
        String categoryId = product.getCategory().getId().toString();
        String brandId = product.getBrand().getId().toString();
        Integer price = product.getPrice();

        String redisKeyMinPrice = redisPrefixMinPrice + categoryId;

        try {
            CompletableFuture<Boolean> existMinPriceByCategory = redisService.exist(redisKeyMinPrice);
            if (existMinPriceByCategory.get()) {
                Integer minPriceByCategory = getPrice(redisKeyMinPrice, "min");
                if (price < minPriceByCategory) {
                    deleteProductHashMapKey(redisKeyMinPrice);
                    addProductHashMap(redisKeyMinPrice, brandId, price.toString());
                    return true;
                } else {
                    return false;
                }
            } else {
                addProductHashMap(redisKeyMinPrice, brandId, price.toString());
                return true;
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Transactional
    protected Boolean updateMaxPriceByCategory(Product product) {
        String categoryId = product.getCategory().getId().toString();
        String brandId = product.getBrand().getId().toString();
        Integer price = product.getPrice();

        String redisKeyMaxPrice = redisPrefixMaxPrice + categoryId;

        try {
            CompletableFuture<Boolean> existMaxPriceByCategory = redisService.exist(redisKeyMaxPrice);
            if (existMaxPriceByCategory.get()) {
                Integer maxPriceByCategory = getPrice(redisKeyMaxPrice, "max");
                if (price > maxPriceByCategory) {
                    deleteProductHashMapKey(redisKeyMaxPrice);
                    addProductHashMap(redisKeyMaxPrice, brandId, price.toString());
                    return true;
                } else {
                    return false;
                }
            } else {
                addProductHashMap(redisKeyMaxPrice, brandId, price.toString());
                return true;
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    private Integer getPrice(String key, String kind) {
        try {
            String priceObj = (String) redisService.getHashMap(key, redisHashKeyPrice).get();
            if (priceObj != null) {
                return Integer.parseInt(priceObj);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return (Objects.equals(kind, "min")) ? Integer.MAX_VALUE : 0;
    }

    @Transactional
    protected void addProductHashMap(String key, String brandId,  String price) {
        redisService.addHashMap(key, redisHashKeyBrand, brandId);
        redisService.addHashMap(key, redisHashKeyPrice, price);
    }

    @Transactional
    protected void deleteProductHashMapKey(String key) {
        redisService.delete(key);
    }

    @Transactional
    protected void updateCategoryHashMap(String key, String field, String brandId, String price) {
        deleteCategoryHashMap(key, field);
        addCategoryHashMap(key, field, brandId, price);
    }

    @Transactional
    protected void addCategoryHashMap(String key, String categoryId, String brandId, String price) {
        String value = brandId + ":" + price;
        redisService.addHashMap(key, categoryId, value);
    }

    @Transactional
    protected void deleteCategoryHashMap(String key, String categoryId) {
        redisService.deleteHashMap(key, categoryId);
    }
}
