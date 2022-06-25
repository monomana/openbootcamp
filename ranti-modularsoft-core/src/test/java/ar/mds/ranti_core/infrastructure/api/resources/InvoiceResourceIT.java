package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import ar.mds.ranti_core.infrastructure.api.dtos.InvoiceDto;
import ar.mds.ranti_core.infrastructure.api.dtos.TicketIdDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static ar.mds.ranti_core.infrastructure.api.resources.InvoiceResource.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RestTestConfig
class InvoiceResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @MockBean
    private UserMicroservice userMicroservice;

    @BeforeEach
    void configureUserMicroservice() {
        BDDMockito.given(this.userMicroservice.readByMobile(anyString()))
                .willAnswer(arguments ->
                        Mono.just(User.builder().mobile("666-MOCK")
                                .dni("mockDNI")
                                .firstName("mock")
                                .familyName("mockFamily")
                                .address("mockAddress")
                                .build()));
    }

    @Test
    void testCreate() {
        TicketIdDto ticketIdDto = new TicketIdDto("5fa4608f4928694ef5980e4c");
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(INVOICES)
                .body(Mono.just(ticketIdDto), TicketIdDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InvoiceDto.class)
                .value(returnInvoiceDto -> {
                    assertNotNull(returnInvoiceDto.getIdentity());
                    assertNotNull(returnInvoiceDto.getCreationDate());
                    assertEquals(0, new BigDecimal("0.02").compareTo(returnInvoiceDto.getBaseTax()));
                    assertEquals(0, new BigDecimal("0.00").compareTo(returnInvoiceDto.getTaxValue()));
                    assertEquals("5fa4608f4928694ef5980e4c", returnInvoiceDto.getTicketId());
                    assertEquals("666666005", returnInvoiceDto.getUserMobile());
                })
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testCreateConflictTicketID() {
        TicketIdDto ticketIdDto = new TicketIdDto("5fa45f6f3a61083cb241289c");
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(INVOICES)
                .body(Mono.just(ticketIdDto), TicketIdDto.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testCreateTicketIdNotFound(){
        TicketIdDto ticketIdDto = new TicketIdDto("1234");
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(INVOICES)
                .body(Mono.just(ticketIdDto), TicketIdDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateAndRead() {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setIdentity(20221);
        invoiceDto.setUserMobile("666666003");
        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(INVOICES + IDENTITY_ID, invoiceDto.getIdentity().toString())
                .body(Mono.just(invoiceDto), InvoiceDto.class)
                .exchange()
                .expectStatus().isOk();
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(INVOICES + IDENTITY_ID, invoiceDto.getIdentity().toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(InvoiceDto.class)
                .value(Assertions::assertNotNull)
                .value(returnInvoiceDto -> assertEquals("666666003", returnInvoiceDto.getUserMobile()));
    }

    @Test
    void testFindByTicketIdAndUserMobileNullSafe() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(INVOICES + SEARCH)
                        .queryParam("ticketId", "5fa45f6f3a61083cb241289c")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(InvoiceDto.class)
                .value(Assertions::assertNotNull)
                .value(invoiceDtos -> assertTrue(
                        invoiceDtos.stream().allMatch(invoiceDto -> invoiceDto.getTicketId().contains("5fa45f6f3a61083cb241289c"))));
    }

    @Test
    void testReadReceipt() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(INVOICES + IDENTITY_ID + RECEIPT, "20221")
                .exchange()
                .expectStatus().isOk()
                .expectBody(byte[].class)
                .value(Assertions::assertNotNull);
    }
}
