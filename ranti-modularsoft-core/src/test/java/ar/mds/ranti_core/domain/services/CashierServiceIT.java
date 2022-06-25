package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.exceptions.BadRequestException;
import ar.mds.ranti_core.domain.model.CashMovement;
import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.model.CashierClose;
import ar.mds.ranti_core.domain.rest.SlackMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ar.mds.ranti_core.domain.model.CashMovementType.WITHDRAWAL;
import static java.math.BigDecimal.ZERO;
import static org.mockito.ArgumentMatchers.any;

@TestConfig
class CashierServiceIT {

    @Autowired
    private CashierService cashierService;

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
    void testCloseBadRequestException() {
        StepVerifier.create(this.cashierService.close(new CashierClose(ZERO, ZERO, "")))
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void testOpenBadRequestException() {
        StepVerifier.create(this.cashierService.createOpened())
                .expectComplete()
                .verify();
        StepVerifier.create(this.cashierService.createOpened())
                .expectError(BadRequestException.class)
                .verify();
        StepVerifier.create(this.cashierService.close(new CashierClose(ZERO, ZERO, "")))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByClosureDateBetween() {
        LocalDateTime startDate = LocalDateTime.of(2021, 2, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);
        StepVerifier.create(this.cashierService.findByClosureDateBetween(startDate, endDate))
                .expectNextMatches(cashier -> cashier.getId().equals("chasierId3"))
                .expectComplete()
                .verify();
    }

    @Test
    void testAddCashMovement() {
        CashMovement cashMovement = new CashMovement(new BigDecimal(15), WITHDRAWAL, "");
        StepVerifier.create(this.cashierService.createOpened())
                .expectComplete()
                .verify();
        StepVerifier.create(this.cashierService.addCashMovement(cashMovement))
                .expectNextMatches(cashier -> cashier.getWithdrawal().equals(new BigDecimal(15)))
                .expectComplete()
                .verify();
        StepVerifier.create(this.cashierService.close(new CashierClose(ZERO, ZERO, "")))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
}
