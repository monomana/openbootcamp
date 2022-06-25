package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.Budget;
import ar.mds.ranti_core.domain.model.Shopping;
import ar.mds.ranti_core.domain.model.ShoppingState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfig
class BudgetPersistenceMongodbIT {

    @Autowired
    private BudgetPersistenceMongodb budgetPersistenceMongodb;

    @Test
    void testCreate() {
        Shopping shopping1 = Shopping.builder().barcode("8400000000017").amount(2)
                .discount(BigDecimal.ZERO).state(ShoppingState.COMMITTED).build();
        Shopping shopping2 = Shopping.builder().barcode("8400000000024").amount(3)
                .discount(BigDecimal.TEN).state(ShoppingState.NOT_COMMITTED).build();
        Budget budget = Budget.builder()
                .reference("RyR_8_SkT9igCikrWWWGkQ")
                .creationDate(LocalDateTime.now())
                .shoppings(List.of(shopping1, shopping2))
                .build();
        StepVerifier
                .create(this.budgetPersistenceMongodb.create(budget))
                .expectNextMatches(dbBudget -> {
                    assertNotNull(dbBudget.getCreationDate());
                    assertEquals("RyR_8_SkT9igCikrWWWGkQ", dbBudget.getReference());
                    assertEquals(2, dbBudget.getShoppings().size());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testReadByReference() {
        Budget budget = Budget.builder()
                .reference("RyR_8_SkT9igCikrWWWGkQ")
                .creationDate(LocalDateTime.now())
                .shoppings(List.of())
                .build();

        StepVerifier
                .create(this.budgetPersistenceMongodb.create(budget))
                .expectNextMatches(dbBudget -> {
                    assertNotNull(dbBudget.getCreationDate());
                    assertEquals("RyR_8_SkT9igCikrWWWGkQ", dbBudget.getReference());
                    assertEquals(0, dbBudget.getShoppings().size());
                    return true;
                })
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.budgetPersistenceMongodb.readByReference("RyR_8_SkT9igCikrWWWGkQ"))
                .expectNextMatches(dbBudget -> {
                    assertNotNull(dbBudget.getCreationDate());
                    assertEquals("RyR_8_SkT9igCikrWWWGkQ", dbBudget.getReference());
                    assertEquals(0, dbBudget.getShoppings().size());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}
