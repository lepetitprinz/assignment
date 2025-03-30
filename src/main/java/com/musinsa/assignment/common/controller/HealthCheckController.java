package com.musinsa.assignment.common.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HealthCheckController {
    @GetMapping(value = "/health")
    public HealthStatus healthCheck() { return new HealthStatus("ok"); }

    record HealthStatus(String status) {
    }
}
