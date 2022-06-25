package ar.mds.ranti_core.domain.services;

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
class ProviderInvoiceServiceIT {

    @Autowired
    ProviderInvoiceService providerInvoiceService;

    @Test
    void testCreate() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("pro2").orderReference("Order2")
                .baseTax(new BigDecimal(4321)).textValue(new BigDecimal(43)).build();
        StepVerifier
                .create(this.providerInvoiceService.create(providerInvoice))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertNotNull(returnedProviderInvoice.getIdentity());
                    assertEquals("pro2", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order2", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(4321), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(43), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testCreateNotFoundProviderException() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("None").orderReference("Order2")
                .baseTax(new BigDecimal(4321)).textValue(new BigDecimal(43)).build();
        StepVerifier
                .create(this.providerInvoiceService.create(providerInvoice))
                .expectError(NotFoundException.class);
    }

    @Test
    void testCreateNotFoundOrderException() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("pro2").orderReference("None")
                .baseTax(new BigDecimal(4321)).textValue(new BigDecimal(43)).build();
        StepVerifier
                .create(this.providerInvoiceService.create(providerInvoice))
                .expectError(NotFoundException.class);
    }

    @Test
    void testReadAndUpdate() {
        StepVerifier
                .create(this.providerInvoiceService.read("identity2"))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertEquals("identity2", returnedProviderInvoice.getIdentity());
                    assertEquals("pro2", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order2", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(2222), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(22), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();

        ProviderInvoice providerInvoice = ProviderInvoice.builder().identity("identity2").providerCompany("pro1")
                .orderReference("Order1").baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12))
                .creationDate(LocalDateTime.now()).build();

        StepVerifier
                .create(this.providerInvoiceService.update("identity2", providerInvoice))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertEquals("identity2", returnedProviderInvoice.getIdentity());
                    assertEquals("pro1", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order1", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1234), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(12), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();

        providerInvoice.setProviderCompany("pro2");
        providerInvoice.setOrderReference("Order2");
        providerInvoice.setBaseTax(new BigDecimal(2222));
        providerInvoice.setTextValue(new BigDecimal(22));

        StepVerifier
                .create(this.providerInvoiceService.update("identity2", providerInvoice))
                .expectNextMatches(returnedProviderInvoice -> {
                    assertEquals("identity2", returnedProviderInvoice.getIdentity());
                    assertEquals("pro2", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order2", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(2222), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(22), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testReadNotFoundProviderInvoiceException() {
        StepVerifier
                .create(this.providerInvoiceService.read("Joke"))
                .expectError(NotFoundException.class);
    }

    @Test
    void testUpdateNotFoundProviderOrOrderException() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("Joke").orderReference("Order3")
                .baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12)).build();

        StepVerifier
                .create(this.providerInvoiceService.update("identity3", providerInvoice))
                .expectError(NotFoundException.class);

        providerInvoice.setProviderCompany("pro3");
        providerInvoice.setOrderReference("Joke");

        StepVerifier
                .create(this.providerInvoiceService.update("identity3", providerInvoice))
                .expectError(NotFoundException.class);
    }

    @Test
    void testFindByProviderCompanyAndOrderReferenceNullSafe() {
        StepVerifier
                .create(this.providerInvoiceService
                        .findByProviderCompanyAndOrderReferenceNullSafe("pro", "ord"))
                .expectNextMatches(providerInvoice -> {
                    assertTrue(providerInvoice.getProviderCompany().toLowerCase().contains("pro"));
                    assertTrue(providerInvoice.getOrderReference().toLowerCase().contains("ord"));
                    return true;
                })
                .thenCancel()
                .verify();

        StepVerifier
                .create(this.providerInvoiceService
                        .findByProviderCompanyAndOrderReferenceNullSafe("kkkk", "kkkk"))
                .verifyComplete();

        StepVerifier
                .create(this.providerInvoiceService
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
                .create(this.providerInvoiceService.getYearsOfAllCreationDate())
                .expectNextMatches(year -> year.equals(2021))
                .thenCancel()
                .verify();
    }

    @Test
    void testGetTotalForQuarterOfYear() {
        StepVerifier
                .create(this.providerInvoiceService.getTotalForQuarterOfYear(2021, 1))
                .expectNextMatches(quarterOfYearDto -> {
                    assertEquals(2021, quarterOfYearDto.getYear());
                    assertEquals("Q1", quarterOfYearDto.getQuarter());
                    assertEquals(new BigDecimal(9999), quarterOfYearDto.getTotalBaseTax());
                    assertEquals(new BigDecimal(99), quarterOfYearDto.getTotalTaxValue());
                    return true;
                })
                .verifyComplete();

        StepVerifier
                .create(this.providerInvoiceService.getTotalForQuarterOfYear(2021, 2))
                .expectError(NotFoundException.class);
    }
}
