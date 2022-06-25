package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@TestConfig
public class CustomerPointsServiceIT {

    @Autowired
    CustomerPointsService customerPointsService;

    @MockBean
    private UserMicroservice userMicroservice;

    @BeforeEach
    void readUser() {
        BDDMockito.given(this.userMicroservice.readByMobile(any(String.class)))
                .willAnswer(arguments ->
                        Mono.just(User.builder().mobile(arguments.getArgument(0)).firstName("mock").build()));
    }

    @Test
    void testCreate() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2022, 12, 24))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsService.create(customerPoints))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getId());
                    assertEquals(58, gotten.getValue());
                    assertNotNull(gotten.getLastDate());
                    assertNotNull(gotten.getUser());
                    assertEquals("888965321", gotten.getUser().getMobile());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testRead() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2019, 12, 24))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsService.create(customerPoints))
                .then(() -> this.customerPointsService.read(customerPoints.getUser().getMobile()))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten);
                    assertNotNull(gotten.getId());
                    assertNotNull(gotten.getUser());
                    assertEquals(customerPoints.getUser(), gotten.getUser());
                    assertEquals("888965321", gotten.getUser().getMobile());
                    assertEquals(LocalDate.of(2019, 12, 24), gotten.getLastDate());
                    assertEquals(58, gotten.getValue());
                    return true;
                })
                .expectComplete()
                .verify();

    }

    @Test
    void testFindByMobileDateAndUpdate() {

        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2022, 1, 25))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsService.create(customerPoints))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getUser());
                    assertEquals(58, gotten.getValue());
                    assertEquals(LocalDate.of(2022, 1, 25), gotten.getLastDate());
                    return true;
                })
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.customerPointsService.findByMobileDateAndUpdate("888965321", LocalDate.now().minusYears(1)))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getUser());
                    assertEquals(customerPoints.getUser(), gotten.getUser());
                    assertNotNull(gotten.getId());
                    assertEquals(58, gotten.getValue());
                    assertEquals(LocalDate.of(2022, 1, 25), gotten.getLastDate());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}
