package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@TestConfig
class CashierPersistenceMongodbIT {

    @Autowired
    private CashierPersistenceMongodb cashierPersistenceMongodb;

    @Test
    void testFindByClosureDateBetween() {
        LocalDateTime startDate = LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().minusMonths(1).getMonth(), 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);
        StepVerifier.create(this.cashierPersistenceMongodb.findByClosureDateBetween(startDate, endDate))
                .expectNextMatches(cashier -> cashier.getId().equals("chasierId2"))
                .expectComplete()
                .verify();
    }
}
