package com.arrowhead.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("audit_log")
public class AuditLog {

    @Id
    private Long id;
    private String requestId;
    private String action; // e.g., "PORTFOLIO_REQUEST"
    private String status; // SUCCESS, PARTIAL_SUCCESS, FAILED
    private String details;
    private LocalDateTime createdAt;
}
