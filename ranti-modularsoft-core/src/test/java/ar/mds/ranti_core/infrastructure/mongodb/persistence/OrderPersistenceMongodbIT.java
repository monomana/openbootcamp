package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class OrderPersistenceMongodbIT {

    @Autowired
    OrderPersistenceMongodb orderPersistenceMongodb;

    @Test
    void testFindByOrderReferenceNullSafe() {
        StepVerifier
                .create(this.orderPersistenceMongodb.findByOrderReferenceNullSafe("ord"))
                .expectNextMatches(reference -> {
                    assertTrue(reference.toLowerCase().contains("ord"));
                    return true;
                })
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.orderPersistenceMongodb.findByOrderReferenceNullSafe("kkkk"))
                .verifyComplete();

        StepVerifier
                .create(this.orderPersistenceMongodb.findByOrderReferenceNullSafe(""))
                .expectNextMatches(reference -> {
                    assertFalse(reference.isBlank());
                    return true;
                })
                .thenCancel()
                .verify();
    }
}
