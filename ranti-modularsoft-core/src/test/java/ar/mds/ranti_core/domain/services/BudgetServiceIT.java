package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.*;
import ar.mds.ranti_core.domain.model.*;
import ar.mds.ranti_core.domain.rest.SlackMicroservice;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@TestConfig
class BudgetServiceIT {

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private CashierService cashierService;
    @MockBean
    private SlackMicroservice slackMicroservice;
    @MockBean
    private UserMicroservice userMicroservice;

    @BeforeEach
    void openCashier() {
        StepVerifier
                .create(this.cashierService.createOpened())
                .verifyComplete();
        BDDMockito.given(this.userMicroservice.readByMobile(any(String.class)))
                //.willReturn(Mono.just(User.builder().mobile("666666666").firstName("mock").build()))
                .willAnswer(arguments ->
                        Mono.just(User.builder().mobile(arguments.getArgument(0)).firstName("mock").build()));
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
    void testCreate() {
        Shopping shopping1 = Shopping.builder().barcode("8400000000093").amount(1)
                .discount(ZERO).state(ShoppingState.COMMITTED).build();
        Shopping shopping2 = Shopping.builder().barcode("8400000000093").amount(2)
                .discount(BigDecimal.TEN).state(ShoppingState.NOT_COMMITTED).build();
        Budget budget = Budget.builder()
                .shoppings(List.of(shopping1, shopping2))
                .creationDate(LocalDateTime.now())
                .build();
        StepVerifier
                .create(this.budgetService.create(budget))
                .expectNextMatches(dbBudget -> {
                    assertNotNull(dbBudget.getReference());
                    assertNotNull(dbBudget.getCreationDate());
                    assertNotNull(dbBudget.getShoppings());
                    assertEquals(2, dbBudget.getShoppings().size());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testCreateNotFoundException() {
        Shopping shopping = Shopping.builder().barcode("barcodeNotFound").amount(2)
                .discount(BigDecimal.TEN).state(ShoppingState.NOT_COMMITTED).build();
        Budget budget = Budget.builder()
                .shoppings(List.of(shopping))
                .creationDate(LocalDateTime.now())
                .build();
        StepVerifier
                .create(this.budgetService.create(budget))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void testReceipt() {
        StepVerifier
                .create(this.budgetService.readReceipt("nUs81zZ4R_iuoq0_zCRm6A"))
                .expectNextCount(1)
                .verifyComplete();
    }

    @AfterEach
    void closeCashier() {
        StepVerifier
                .create(this.cashierService.close(new CashierClose(ZERO, ZERO, "test")))
                .expectNextCount(1)
                .verifyComplete();
    }
}
