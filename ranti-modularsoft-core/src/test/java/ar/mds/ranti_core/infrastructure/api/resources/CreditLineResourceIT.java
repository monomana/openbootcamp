package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import ar.mds.ranti_core.infrastructure.api.dtos.CreditLineDto;
import ar.mds.ranti_core.infrastructure.api.dtos.CreditLineMobileDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RestTestConfig
public class CreditLineResourceIT {

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
        String mobile = "666666002";
        CreditLineMobileDto creditLineMobileDto = new CreditLineMobileDto(mobile);
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CreditLineResource.CREDIT_LINES)
                .body(Mono.just(creditLineMobileDto),CreditLineMobileDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreditLineDto.class)
                .value(returnCreditLineDto -> {
                    assertEquals(mobile,returnCreditLineDto.getMobile());
                    assertNotNull(returnCreditLineDto.getFormattedCreationDate());
                })
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testFindByUserMobileNullSafe() {
        String mobile = "666666000";
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CreditLineResource.CREDIT_LINES + '/' + mobile)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreditLineDto.class)
                .value(Assertions::assertNotNull)
                .value(creditLineDto -> assertEquals(mobile,creditLineDto.getMobile()));
    }

    @Test
    void testFindCreditLinesNullSafe() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CreditLineResource.CREDIT_LINES)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CreditLineDto.class)
                .value(Assertions::assertNotNull)
                .value(creditLineDtos -> assertTrue(creditLineDtos.size() > 0));
    }
}
