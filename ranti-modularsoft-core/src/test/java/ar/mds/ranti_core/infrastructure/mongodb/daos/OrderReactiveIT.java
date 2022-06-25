package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class OrderReactiveIT {

    @Autowired
    OrderReactive orderReactive;

    @Test
    void testFindByOrderReferenceNullSafe() {
        StepVerifier
                .create(this.orderReactive.findByOrderReferenceNullSafe("ord"))
                .expectNextMatches(orderEntity -> {
                    assertTrue(orderEntity.getOrderReference().toLowerCase().contains("ord"));
                    return true;
                })
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.orderReactive.findByOrderReferenceNullSafe("kkkk"))
                .verifyComplete();

        StepVerifier
                .create(this.orderReactive.findByOrderReferenceNullSafe(""))
                .expectNextMatches(orderEntity -> {
                    assertFalse(orderEntity.getOrderReference().isBlank());
                    return true;
                })
                .thenCancel()
                .verify();
    }

    @Test
    void testFindByOrderReference() {
        StepVerifier
                .create(this.orderReactive.findByOrderReference("Order1"))
                .expectNextMatches(orderEntity -> {
                    assertEquals("Order1", orderEntity.getOrderReference());
                    return true;
                })
                .verifyComplete();

        StepVerifier
                .create(this.orderReactive.findByOrderReference("kkkkk"))
                .verifyComplete();

        StepVerifier
                .create(this.orderReactive.findByOrderReference(""))
                .verifyComplete();
    }
}
