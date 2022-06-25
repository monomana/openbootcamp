package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
public class CreditLineServiceIT {

    @Autowired
    CreditLineService creditLineService;

    @Test
    void testCreate() {
        String mobile = "666666001";
        StepVerifier
                .create(this.creditLineService.create(mobile))
                .expectNextMatches(dbCreditLine -> {
                    assertEquals(mobile,dbCreditLine.getUserMobile());
                    assertNotNull(dbCreditLine.getRegistrationDateTime());
                    assertEquals(0,dbCreditLine.getCreditSales().size());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByUserMobileNullSafe() {
        String mobile = "666666000";
        StepVerifier
                .create(this.creditLineService.findByUserMobileNullSafe(mobile))
                .expectNextMatches(creditLine -> creditLine.getUserMobile().equals(mobile))
                .expectComplete()
                .verify();
    }
}
