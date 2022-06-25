package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import ar.mds.ranti_core.infrastructure.api.dtos.OrderReferenceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static ar.mds.ranti_core.infrastructure.api.resources.OrderResource.*;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
class OrderResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testFindByOrderReferenceNullSafe() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ORDERS + REFERENCE)
                        .queryParam("orderReference", "ord")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderReferenceDto.class)
                .value(Assertions::assertNotNull)
                .value(orderReferenceDto -> assertTrue(orderReferenceDto.getReferences().stream().allMatch(reference ->
                        reference.toLowerCase().contains("ord")))
                );

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ORDERS + REFERENCE)
                        .queryParam("orderReference", "kkkkkk")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderReferenceDto.class)
                .value(Assertions::assertNotNull)
                .value(orderReferenceDto -> assertTrue(orderReferenceDto.getReferences().isEmpty()));

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(ORDERS + REFERENCE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderReferenceDto.class)
                .value(Assertions::assertNotNull)
                .value(orderReferenceDto -> assertFalse(orderReferenceDto.getReferences().isEmpty()));
    }
}
