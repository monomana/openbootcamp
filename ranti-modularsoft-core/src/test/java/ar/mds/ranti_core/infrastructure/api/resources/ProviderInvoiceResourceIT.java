package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.ProviderInvoice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import ar.mds.ranti_core.infrastructure.api.dtos.ProviderInvoiceOfYearDto;
import ar.mds.ranti_core.infrastructure.api.dtos.QuarterOfYearDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static ar.mds.ranti_core.infrastructure.api.resources.ProviderInvoiceResource.*;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
class ProviderInvoiceResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("pro1").orderReference("Order1")
                .baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12)).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(PROVIDER_INVOICES)
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProviderInvoice.class)
                .value(Assertions::assertNotNull)
                .value(returnedProviderInvoice -> {
                    assertNotNull(returnedProviderInvoice.getIdentity());
                    assertEquals("pro1", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order1", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1234), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(12), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                });
    }

    @Test
    void testCreateNotFoundProviderException() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("None").orderReference("Order1")
                .baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12)).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(PROVIDER_INVOICES)
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNotFoundOrderException() {
        ProviderInvoice providerInvoice = ProviderInvoice.builder().providerCompany("pro1").orderReference("None")
                .baseTax(new BigDecimal(1234)).textValue(new BigDecimal(12)).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(PROVIDER_INVOICES)
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testReadAndUpdate() {
        ProviderInvoice providerInvoice = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(PROVIDER_INVOICES + IDENTITY, "identity1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProviderInvoice.class)
                .value(Assertions::assertNotNull)
                .value(returnedProviderInvoice -> {
                    assertEquals("identity1", returnedProviderInvoice.getIdentity());
                    assertEquals("pro1", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order1", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1111), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(11), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                })
                .returnResult()
                .getResponseBody();
        assertNotNull(providerInvoice);

        providerInvoice.setProviderCompany("pro2");
        providerInvoice.setOrderReference("Order2");
        providerInvoice.setBaseTax(new BigDecimal(2222));
        providerInvoice.setTextValue(new BigDecimal(22));

        providerInvoice = this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(PROVIDER_INVOICES + IDENTITY, providerInvoice.getIdentity())
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProviderInvoice.class)
                .value(Assertions::assertNotNull)
                .value(returnedProviderInvoice -> {
                    assertEquals("pro2", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order2", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(2222), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(22), returnedProviderInvoice.getTextValue());
                })
                .returnResult()
                .getResponseBody();
        assertNotNull(providerInvoice);

        providerInvoice.setProviderCompany("pro1");
        providerInvoice.setOrderReference("Order1");
        providerInvoice.setBaseTax(new BigDecimal(1111));
        providerInvoice.setTextValue(new BigDecimal(11));

        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(PROVIDER_INVOICES + IDENTITY, providerInvoice.getIdentity())
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProviderInvoice.class)
                .value(Assertions::assertNotNull)
                .value(returnedProviderInvoice -> {
                    assertEquals("pro1", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order1", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1111), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(11), returnedProviderInvoice.getTextValue());
                });
    }

    @Test
    void testReadAndUpdateNotFoundProviderInvoiceOrProviderOrOrderException() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(PROVIDER_INVOICES + IDENTITY, "Joke")
                .exchange()
                .expectStatus().isNotFound();

        ProviderInvoice providerInvoice = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(PROVIDER_INVOICES + IDENTITY, "identity1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProviderInvoice.class)
                .value(Assertions::assertNotNull)
                .value(returnedProviderInvoice -> {
                    assertEquals("identity1", returnedProviderInvoice.getIdentity());
                    assertEquals("pro1", returnedProviderInvoice.getProviderCompany());
                    assertEquals("Order1", returnedProviderInvoice.getOrderReference());
                    assertEquals(new BigDecimal(1111), returnedProviderInvoice.getBaseTax());
                    assertEquals(new BigDecimal(11), returnedProviderInvoice.getTextValue());
                    assertTrue(returnedProviderInvoice.getCreationDate().isBefore(LocalDateTime.now()));
                })
                .returnResult()
                .getResponseBody();
        assertNotNull(providerInvoice);

        providerInvoice.setProviderCompany("Joke");

        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(PROVIDER_INVOICES + IDENTITY, providerInvoice.getIdentity())
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isNotFound();

        providerInvoice.setProviderCompany("pro1");
        providerInvoice.setOrderReference("Joke");

        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(PROVIDER_INVOICES + IDENTITY, providerInvoice.getIdentity())
                .body(Mono.just(providerInvoice), ProviderInvoice.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByProviderCompanyAndOrderReferenceNullSafe() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PROVIDER_INVOICES + SEARCH)
                        .queryParam("providerCompany", "pro")
                        .queryParam("orderReference", "ord")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProviderInvoice.class)
                .value(Assertions::assertNotNull)
                .value(providerInvoices -> assertTrue(providerInvoices.stream().allMatch(providerInvoice -> {
                    assertTrue(providerInvoice.getProviderCompany().toLowerCase().contains("pro"));
                    assertTrue(providerInvoice.getOrderReference().toLowerCase().contains("ord"));
                    return true;
                })));

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(PROVIDER_INVOICES + SEARCH)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProviderInvoice.class)
                .value(Assertions::assertNotNull);

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PROVIDER_INVOICES + SEARCH)
                        .queryParam("providerCompany", "kkkkkkk")
                        .queryParam("orderReference", "kkkkkkk")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArrayList.class)
                .value(response -> assertTrue(response.isEmpty()));
    }

    @Test
    void testGetYearsOfAllCreationDate() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(PROVIDER_INVOICES + CREATION_DATE + YEARS)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProviderInvoiceOfYearDto.class)
                .value(Assertions::assertNotNull)
                .value(providerInvoiceOfYearDto -> {
                    assertTrue(providerInvoiceOfYearDto.getYears().contains(LocalDateTime.now().getYear()));
                    assertTrue(providerInvoiceOfYearDto.getYears().contains(2021));
                });
    }

    @Test
    void testGetTotalForQuarterOfYear() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PROVIDER_INVOICES + QUARTER_OF_YEAR + SEARCH)
                        .queryParam("year", "2021")
                        .queryParam("quarter", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(QuarterOfYearDto.class)
                .value(Assertions::assertNotNull)
                .value(quarterOfYearDto -> {
                    assertEquals(2021, quarterOfYearDto.getYear());
                    assertEquals("Q1", quarterOfYearDto.getQuarter());
                    assertEquals(new BigDecimal(9999), quarterOfYearDto.getTotalBaseTax());
                    assertEquals(new BigDecimal(99), quarterOfYearDto.getTotalTaxValue());
                });

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(PROVIDER_INVOICES + QUARTER_OF_YEAR + SEARCH)
                        .queryParam("year", "2021")
                        .queryParam("quarter", "2")
                        .build())
                .exchange()
                .expectStatus().isNotFound();
    }
}
