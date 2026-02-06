package com.arrowhead.integration.mock;

import com.arrowhead.integration.model.MockModels.BankAccountDTO;
import com.arrowhead.integration.model.MockModels.CreditCardDTO;
import com.arrowhead.integration.model.MockModels.CryptoInvestmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/external")
public class ExternalServicesMockController {

    private final Random random = new Random();

    // 1. Core Bank Service: Fast (100-300ms), 99% Uptime
    @GetMapping("/bank-accounts/{userId}")
    public Flux<BankAccountDTO> getBankAccounts(@PathVariable String userId) {
        return Flux.just(
                BankAccountDTO.builder()
                        .accountId(UUID.randomUUID().toString())
                        .ownerId(userId)
                        .balance(BigDecimal.valueOf(1500.50))
                        .currency("USD")
                        .type("CHECKING").build(),
                BankAccountDTO.builder()
                        .accountId(UUID.randomUUID().toString())
                        .ownerId(userId)
                        .balance(BigDecimal.valueOf(5000.00))
                        .currency("USD")
                        .type("SAVINGS").build())
                .delayElements(Duration.ofMillis(100 + random.nextInt(200))) // 100-300ms delay
                .flatMap(data -> {
                    if (random.nextInt(100) < 1) { // 1% failure rate
                        return Mono.error(
                                new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Bank Service Down"));
                    }
                    return Mono.just(data);
                });
    }

    // 2. Card Service: Medium (500ms-1500ms), 90% Uptime
    @GetMapping("/credit-cards/{userId}")
    public Flux<CreditCardDTO> getCreditCards(@PathVariable String userId) {
        return Flux.just(
                CreditCardDTO.builder()
                        .cardId(UUID.randomUUID().toString())
                        .ownerId(userId)
                        .maskedNumber("****-****-****-1234")
                        .totalLimit(BigDecimal.valueOf(10000))
                        .usedAmount(BigDecimal.valueOf(1500))
                        .cardNetwork("VISA").build())
                .delayElements(Duration.ofMillis(500 + random.nextInt(1000))) // 500-1500ms
                .flatMap(data -> {
                    if (random.nextInt(100) < 10) { // 10% failure rate
                        return Mono.error(
                                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Card System Error"));
                    }
                    return Mono.just(data);
                });
    }

    // 3. Crypto Service: Slow (2s-5s), 70% Uptime (Very flaky)
    @GetMapping("/crypto/{userId}")
    public Flux<CryptoInvestmentDTO> getCryptoInvestments(@PathVariable String userId) {
        return Flux.just(
                CryptoInvestmentDTO.builder()
                        .investmentId(UUID.randomUUID().toString())
                        .ownerId(userId)
                        .cryptocurrency("BTC")
                        .amount(BigDecimal.valueOf(0.5))
                        .currentMarketValueUsd(BigDecimal.valueOf(25000)).build(),
                CryptoInvestmentDTO.builder()
                        .investmentId(UUID.randomUUID().toString())
                        .ownerId(userId)
                        .cryptocurrency("ETH")
                        .amount(BigDecimal.valueOf(10))
                        .currentMarketValueUsd(BigDecimal.valueOf(18000)).build())
                .delayElements(Duration.ofMillis(2000 + random.nextInt(3000))) // 2s-5s delay
                .flatMap(data -> {
                    if (random.nextInt(100) < 30) { // 30% failure rate
                        return Mono.error(
                                new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Crypto Exchange Timeout"));
                    }
                    return Mono.just(data);
                });
    }
}
