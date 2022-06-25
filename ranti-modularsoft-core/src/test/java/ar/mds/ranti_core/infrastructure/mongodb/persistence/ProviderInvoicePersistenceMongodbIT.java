package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.ProviderInvoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ProviderInvoicePersistenceMongodbIT {

    @Autowired
    ProviderInvoicePersistenceMongodb providerInvoicePersistenceMongodb;

    @Test
    void testCreate() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.create(
                        ProviderInvoice.builder().providerCompany("pro3").orderReference("Order3")
                                .baseTax(new BigDecimal(1111)).textValue(new BigDecimal(11))
                                .creationDate(LocalDateTime.now()).build()))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertNotNull(returnedProviderInvoice.getIdentity());
                    assertEquals("pro3", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order3", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1111), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(11), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testCreateNotFoundProviderException() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.create(
                        ProviderInvoice.builder().providerCompany("None").orderReference("Order3")
                                .baseTax(new BigDecimal(1111)).textValue(new BigDecimal(11))
                                .creationDate(LocalDateTime.now()).build()))
                .expectError(NotFoundException.class);
    }

    @Test
    void testCreateNotFoundOrderException() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.create(
                        ProviderInvoice.builder().providerCompany("pro3").orderReference("None")
                                .baseTax(new BigDecimal(1111)).textValue(new BigDecimal(11))
                                .creationDate(LocalDateTime.now()).build()))
                .expectError(NotFoundException.class);
    }

    @Test
    void testReadAndUpdate() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.read("identity3"))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertEquals("identity3", returnedProviderInvoice.getIdentity());
                    assertEquals("pro3", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order3", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(3333), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(33), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();

        ProviderInvoice providerInvoice = ProviderInvoice.builder().identity("identity3").providerCompany("pro1")
                .orderReference("Order1").baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12))
                .creationDate(LocalDateTime.now()).build();

        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.update("identity3", providerInvoice))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertEquals("identity3", returnedProviderInvoice.getIdentity());
                    assertEquals("pro1", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order1", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1234), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(12), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();

        providerInvoice.setProviderCompany("pro3");
        providerInvoice.setOrderReference("Order3");
        providerInvoice.setBaseTax(new BigDecimal(3333));
        providerInvoice.setTextValue(new BigDecimal(33));

        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.update("identity3", providerInvoice))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertEquals("identity3", returnedProviderInvoice.getIdentity());
                    assertEquals("pro3", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order3", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(3333), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(33), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testReadNotFoundProviderInvoiceException() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.read("Joke"))
                .expectError(NotFoundException.class);
    }

    @Test
    void testUpdateNotFoundProviderOrOrderException() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("Joke").orderReference("Order3")
                .baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12)).build();

        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.update("identity3", providerInvoice))
                .expectError(NotFoundException.class);

        providerInvoice.setProviderCompany("pro3");
        providerInvoice.setOrderReference("Joke");

        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.update("identity3", providerInvoice))
                .expectError(NotFoundException.class);
    }

    @Test
    void testFindByProviderCompanyAndOrderReferenceNullSafe() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb
                        .findByProviderCompanyAndOrderReferenceNullSafe(".*pro.*", "ord"))
                .expectNextMatches(providerInvoice -> {
                    assertTrue(providerInvoice.getProviderCompany().toLowerCase().contains("pro"));
                    assertTrue(providerInvoice.getOrderReference().toLowerCase().contains("ord"));
                    return true;
                })
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.providerInvoicePersistenceMongodb
                        .findByProviderCompanyAndOrderReferenceNullSafe(".*kkkk.*", "kkkk"))
                .verifyComplete();

        StepVerifier
                .create(this.providerInvoicePersistenceMongodb
                        .findByProviderCompanyAndOrderReferenceNullSafe("", ""))
                .expectNextMatches(providerInvoice -> {
                    assertFalse(providerInvoice.getProviderCompany().isBlank());
                    assertFalse(providerInvoice.getOrderReference().isBlank());
                    return true;
                })
                .thenCancel()
                .verify();
    }

    @Test
    void testGetYearsOfAllCreationDate() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.getYearsOfAllCreationDate())
                .expectNextMatches(year -> year.equals(2022))
                .thenCancel()
                .verify();
    }

    @Test
    void testFindByCreationDateBetween() {
        StepVerifier
                .create(this.providerInvoicePersistenceMongodb.findByCreationDateBetween(
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
                .create(this.providerInvoicePersistenceMongodb.findByCreationDateBetween(
                        LocalDateTime.of(2021, 4, 1, 0, 0),
                        LocalDateTime.of(2021, 6, 30, 23, 59)
                ))
                .expectError(NotFoundException.class);
    }
}
