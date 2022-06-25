package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static ar.mds.ranti_core.infrastructure.api.resources.CustomerDiscountResource.MOBILE;
import static ar.mds.ranti_core.infrastructure.api.resources.CustomerPointsResource.CUSTOMER_POINTS;
import static ar.mds.ranti_core.infrastructure.api.resources.CustomerPointsResource.UPDATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@RestTestConfig
public class CustomerPointsResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @MockBean
    private UserMicroservice userMicroservice;

    @BeforeEach
    void readUser() {
        BDDMockito.given(this.userMicroservice.readByMobile(anyString()))
                .willAnswer(arguments ->
                        Mono.just(User.builder().mobile(arguments.getArgument(0)).firstName("mock").build()));
    }

    @Test
    void testCreate() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(14)
                .value(37)
                .lastDate(LocalDate.of(2022, 11, 11))
                .user(User.builder().mobile("555685647").build())
                .build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CUSTOMER_POINTS)
                .body(Mono.just(customerPoints), CustomerPoints.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerPoints.class)
                .value(Assertions::assertNotNull)
                .value(gotten -> {
                    assertNotNull(gotten);
                    assertNotNull(gotten.getUser());
                    assertNotNull(gotten.getLastDate());
                    assertEquals("555685647", gotten.getUser().getMobile());
                    assertEquals(37, gotten.getValue());
                });
    }

    @Test
    void testCreateUnauthorizedException() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(14)
                .value(37)
                .lastDate(LocalDate.of(2021, 10, 14))
                .user(User.builder().mobile("888685647").build())
                .build();

        webTestClient
                .post()
                .uri(CUSTOMER_POINTS)
                .body(Mono.just(customerPoints), CustomerPoints.class)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testRead() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(14)
                .value(37)
                .lastDate(LocalDate.of(2010, 3, 3))
                .user(User.builder().mobile("555685647").build())
                .build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CUSTOMER_POINTS)
                .body(Mono.just(customerPoints), CustomerPoints.class)
                .exchange()
                .expectStatus().isOk();

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CUSTOMER_POINTS + MOBILE, "555685647")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerPoints.class)
                .value(gotten -> {
                    assertNotNull(gotten.getUser());
                    assertEquals(customerPoints.getUser(), gotten.getUser());
                    assertEquals(14, gotten.getId());
                    assertEquals(37, gotten.getValue());
                });

    }

    @Test
    void testFindByMobileDateAndUpdate() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CUSTOMER_POINTS + MOBILE + UPDATED, "1478")
                .exchange()
                .expectStatus().isNotFound();
    }

}
