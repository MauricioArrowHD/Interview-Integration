package com.arrowhead.integration.service;

import com.arrowhead.integration.model.AuditLog;
import com.arrowhead.integration.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioAggregatorService {

    private final WebClient webClient;
    private final AuditLogRepository auditLogRepository;

    /**
     * TODO: Implement the Scatter-Gather pattern to fetch data from all 3 sources
     * in PARALLEL.
     * 
     * Requirements:
     * 1. Fetch data from /external/bank-accounts/{userId}
     * 2. Fetch data from /external/credit-cards/{userId}
     * 3. Fetch data from /external/crypto/{userId}
     * 
     * Resilience Requirements:
     * - Total operation must not exceed 2.5 seconds. If it takes longer, return
     * partial results (whatever completed).
     * - If Crypto service fails (500/Timeout), ignore the error and return empty
     * list for crypto.
     * - If Bank or Card service fails, log the error but still return other
     * available data if possible.
     * 
     * Audit:
     * - Save an audit log entry (asynchronously) indicating the result of the
     * aggregation.
     */
    public Mono<Map<String, Object>> getPortfolio(String userId) {
        String requestId = UUID.randomUUID().toString();

        // TODO: Replace this dummy implementation with actual parallel calls
        return Mono.just(Map.of("message", "Not Implemented Yet"));
    }

    // Helper method to save audit log
    private Mono<AuditLog> saveAuditLog(String requestId, String status, String details) {
        return auditLogRepository.save(AuditLog.builder()
                .requestId(requestId)
                .action("PORTFOLIO_AGGREGATION")
                .status(status)
                .details(details)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
