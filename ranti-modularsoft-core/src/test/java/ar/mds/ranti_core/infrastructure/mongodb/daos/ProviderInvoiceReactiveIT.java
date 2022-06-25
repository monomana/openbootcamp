package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class ProviderInvoiceReactiveIT {

    @Autowired
    ProviderInvoiceReactive providerInvoiceReactive;

    @Test
    void testFindByOrderReferenceNullSafe() {
        StepVerifier
                .create(this.providerInvoiceReactive.findByOrderReferenceNullSafe("ord"))
                .expectNextMatches(providerInvoiceEntity -> {
                    assertTrue(providerInvoiceEntity.getOrderReference().toLowerCase().contains("ord"));
                    return true;
                })
                .thenCancel()
                .verify();
    }

    @Test
    void testFindByCreationDateBetween() {
        StepVerifier
                .create(this.providerInvoiceReactive.findByCreationDateBetween(
                        LocalDateTime.of(2021, 1, 1, 0, 0),
                        LocalDateTime.of(2021, 3, 31, 23, 59)
                ))
                .expectNextMatches(providerInvoice -> {
                    assertTrue(providerInvoice.getCreationDate()
                            .isAfter(LocalDateTime.of(2020, 12, 31, 23, 59)));
                    assertTrue(providerInvoice.getCreationDate()
                            .isBefore(LocalDateTime.of(2021, 4, 1, 0, 0)));
                    return true;
                })
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.providerInvoiceReactive.findByCreationDateBetween(
                        LocalDateTime.of(2021, 4, 1, 0, 0),
                        LocalDateTime.of(2021, 6, 30, 23, 59)
                ))
                .verifyComplete();
    }
}
