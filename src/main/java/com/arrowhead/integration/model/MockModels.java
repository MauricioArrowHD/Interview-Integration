package com.arrowhead.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class MockModels {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankAccountDTO {
        private String accountId;
        private String ownerId;
        private BigDecimal balance;
        private String currency;
        private String type; // CHECKING, SAVINGS
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditCardDTO {
        private String cardId;
        private String ownerId;
        private String maskedNumber;
        private BigDecimal totalLimit;
        private BigDecimal usedAmount;
        private String cardNetwork; // VISA, MASTERCARD
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CryptoInvestmentDTO {
        private String investmentId;
        private String ownerId;
        private String cryptocurrency; // BTC, ETH
        private BigDecimal amount;
        private BigDecimal currentMarketValueUsd;
    }
}
