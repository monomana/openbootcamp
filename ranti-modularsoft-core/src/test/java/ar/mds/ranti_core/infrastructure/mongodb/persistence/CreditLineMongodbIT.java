package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.CreditLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class CreditLineMongodbIT {

    @Autowired
    private CreditLinePersistenceMongodb creditLinePersistenceMongodb;

    @Test
    void testCreate() {
        String mobile = "666000333";
        LocalDateTime registrationTime = LocalDateTime.now();
        CreditLine creditLine = CreditLine.builder()
                .userMobile(mobile)
                .registrationDateTime(registrationTime)
                .build();

        StepVerifier
                .create(this.creditLinePersistenceMongodb.create(creditLine))
                .expectNextMatches(dbCreditLine -> {
                    assertEquals(mobile,dbCreditLine.getUserMobile());
                    assertEquals(registrationTime,dbCreditLine.getRegistrationDateTime());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testFindByUserMobileNullSafe() {
        String mobile = "666666000";
        StepVerifier
                .create(this.creditLinePersistenceMongodb.findByUserMobileNullSafe(mobile))
                .expectNextMatches(creditLine -> creditLine.getUserMobile().equals(mobile))
                .expectComplete()
                .verify();
    }
}
