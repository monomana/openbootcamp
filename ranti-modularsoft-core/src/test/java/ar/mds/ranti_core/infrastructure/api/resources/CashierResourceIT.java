package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.CashMovement;
import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.model.CashierClose;
import ar.mds.ranti_core.domain.model.CashierState;
import ar.mds.ranti_core.domain.rest.SlackMicroservice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import ar.mds.ranti_core.infrastructure.api.dtos.CashierClosuresDto;
import ar.mds.ranti_core.infrastructure.api.dtos.CashierLastDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ar.mds.ranti_core.domain.model.CashMovementType.DEPOSIT;
import static ar.mds.ranti_core.infrastructure.api.resources.ArticleResource.SEARCH;
import static ar.mds.ranti_core.infrastructure.api.resources.CashierResource.*;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RestTestConfig
class CashierResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @MockBean
    private SlackMicroservice slackMicroservice;

    @BeforeEach
    void setUp() {
        BDDMockito.given(this.slackMicroservice.closeCashier(any(Cashier.class)))
                .willAnswer(arguments ->
                        Mono.just(Cashier.builder()
                                .cashSales(ZERO)
                                .cardSales(ZERO)
                                .finalCash(ZERO)
                                .closureDate(LocalDateTime.now())
                                .comment("test").build()));
    }

    @Test
    void testFindLast() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get().uri(CASHIERS + LAST)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CashierLastDto.class)
                .value(Assertions::assertNotNull)
                .value(cashier -> assertTrue(cashier.getClosed()));
    }

    @Test
    void testFindLastUnauthorizedException() {
        webTestClient
                .get().uri(CASHIERS + LAST)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testCreateAndFindLastState() {
        this.restClientTestService.loginAdmin(webTestClient)
                .post().uri(CASHIERS)
                .exchange()
                .expectStatus().isOk();
        this.restClientTestService.loginAdmin(webTestClient)
                .get().uri(CASHIERS + LAST)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CashierLastDto.class)
                .value(Assertions::assertNotNull)
                .value(cashier -> assertFalse(cashier.getClosed()));
        this.restClientTestService.loginAdmin(webTestClient)
                .get().uri(CASHIERS + LAST + STATE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CashierState.class)
                .value(Assertions::assertNotNull);
        this.restClientTestService.loginAdmin(webTestClient)
                .patch().uri(CASHIERS + LAST)
                .body(Mono.just(new CashierClose(BigDecimal.ZERO, BigDecimal.ZERO, "test")), CashierClose.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindLastState() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get().uri(CASHIERS + LAST + STATE)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testOpenCloseCashier() {
        this.restClientTestService.loginAdmin(webTestClient)
                .post().uri(CASHIERS)
                .exchange()
                .expectStatus().isOk();
        this.restClientTestService.loginAdmin(webTestClient)
                .patch().uri(CASHIERS + LAST)
                .body(Mono.just(new CashierClose(ZERO, ZERO, "test")), CashierClose.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CashierLastDto.class)
                .value(Assertions::assertNotNull)
                .value(cashier -> assertTrue(cashier.getClosed()));
    }

    @Test
    void testFindByClosureDateBetween() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get().uri(uriBuilder -> uriBuilder
                        .path(CASHIERS + SEARCH)
                        .queryParam("startDate", "2021-01-01T00:00:00.000Z")
                        .queryParam("endDate", "2022-01-01T00:00:00.000Z")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CashierClosuresDto.class)
                .value(Assertions::assertNotNull)
                .value(cashierClosuresDto -> {
                    cashierClosuresDto.getCashiers().remove(0);
                    assertTrue(cashierClosuresDto.getCashiers()
                            .stream().allMatch(cashier -> cashier.getClosureDate().getYear() == 2021));
                });
    }

    @Test
    void testAddCashMovement() {
        this.restClientTestService.loginAdmin(webTestClient)
                .post().uri(CASHIERS)
                .exchange()
                .expectStatus().isOk();
        this.restClientTestService.loginAdmin(webTestClient)
                .patch().uri(CASHIERS + CASH_MOVEMENT)
                .body(Mono.just(new CashMovement(new BigDecimal(100), DEPOSIT, "")),
                        CashMovement.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cashier.class)
                .value(Assertions::assertNotNull)
                .value(cashier -> assertEquals(new BigDecimal(100), cashier.getDeposit()));
        this.restClientTestService.loginAdmin(webTestClient)
                .patch().uri(CASHIERS + LAST)
                .body(Mono.just(new CashierClose(ZERO, ZERO, "")), CashierClose.class)
                .exchange()
                .expectStatus().isOk();
    }
}
