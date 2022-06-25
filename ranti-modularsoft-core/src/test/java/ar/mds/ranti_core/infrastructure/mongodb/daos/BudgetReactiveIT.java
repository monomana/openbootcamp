package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@TestConfig
class BudgetReactiveIT {

    @Autowired
    private BudgetReactive budgetReactive;

    @Test
    void testFindBudgetsByCreationDateAndReferenceNullSafe() {
        StepVerifier
                .create(this.budgetReactive.findBudgetsByCreationDateAndReferenceNullSafe("nUs81zZ4R_iuoq0_zC",
                        null, null))
                .expectNextMatches(budgetEntity ->  budgetEntity.getReference().contains("nUs81zZ4R_iuoq0_zC"))
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.budgetReactive.findBudgetsByCreationDateAndReferenceNullSafe("nUs81zZ4R_iuoq0_zC",
                        LocalDateTime.now().minusMonths(1), null))
                .expectNextMatches(budgetEntity ->  budgetEntity.getReference().contains("nUs81zZ4R_iuoq0_zC"))
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.budgetReactive.findBudgetsByCreationDateAndReferenceNullSafe("nUs81zZ4R_iuoq0_zC",
                        LocalDateTime.now().plusMonths(1), null))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.budgetReactive.findBudgetsByCreationDateAndReferenceNullSafe("nUs81zZ4R_iuoq0_zC",
                        LocalDateTime.now().plusMonths(1), LocalDateTime.now().minusMonths(1)))
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.budgetReactive.findBudgetsByCreationDateAndReferenceNullSafe("nUs81zZ4R_iuoq0_zC",
                        LocalDateTime.now().minusMonths(1), LocalDateTime.now().plusMonths(1)))
                .expectNextMatches(budgetEntity ->  budgetEntity.getReference().contains("nUs81zZ4R_iuoq0_zC"))
                .thenCancel()
                .verify();
    }

}
