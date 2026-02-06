package com.arrowhead.integration.controller;

import com.arrowhead.integration.service.PortfolioAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioAggregatorService portfolioService;

    @GetMapping("/{userId}")
    public Mono<Map<String, Object>> getPortfolio(@PathVariable String userId) {
        return portfolioService.getPortfolio(userId);
    }
}
