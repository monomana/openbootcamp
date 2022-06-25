package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
public class CreditLineReactiveIT {

    @Autowired
    private CreditLineReactive creditLineReactive;

    @Test
    void testFindByMobileDiscontinuedNullSafe() {
        String mobile = "666666000";
        StepVerifier
                .create(this.creditLineReactive.findByMobile(mobile))
                .expectNextMatches(creditLine -> {
                    assertEquals(mobile,creditLine.getMobile());
                    return true;
                })
                .thenCancel()
                .verify();
    }
}