package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class OrderServiceIT {

    @Autowired
    OrderService orderService;

    @Test
    void testFindByOrderReferenceNullSafe() {
        StepVerifier
                .create(this.orderService.findByOrderReferenceNullSafe("ord"))
                .expectNextMatches(reference -> {
                    assertTrue(reference.toLowerCase().contains("ord"));
                    return true;
                })
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.orderService.findByOrderReferenceNullSafe("kkkk"))
                .verifyComplete();

        StepVerifier
                .create(this.orderService.findByOrderReferenceNullSafe(""))
                .expectNextMatches(reference -> {
                    assertFalse(reference.isBlank());
                    return true;
                })
                .thenCancel()
                .verify();
    }
}
