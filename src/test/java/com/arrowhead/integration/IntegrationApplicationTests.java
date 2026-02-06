package com.arrowhead.integration;


import com.arrowhead.integration.model.MockModels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class IntegrationApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", () -> String.format("r2dbc:postgresql://%s:%d/%s",
				postgres.getHost(),
				postgres.getFirstMappedPort(),
				postgres.getDatabaseName()));
		registry.add("spring.r2dbc.username", postgres::getUsername);
		registry.add("spring.r2dbc.password", postgres::getPassword);

		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
		assertThat(postgres.isRunning()).isTrue();
	}

	@Test
	void testMockEndpointsAreReachable() {
		// Verify Bank Mock
		webTestClient.get()
				.uri("/external/bank-accounts/user1")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(MockModels.BankAccountDTO.class);
	}

	@Test
	void testPortfolioSkeletonReturnsNotImplemented() {
		webTestClient.get()
				.uri("/api/v1/portfolio/user1")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.message").isEqualTo("Not Implemented Yet");
	}

}
