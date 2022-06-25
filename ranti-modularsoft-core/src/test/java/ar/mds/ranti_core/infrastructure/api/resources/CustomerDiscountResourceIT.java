package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.model.*;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import ar.mds.ranti_core.infrastructure.api.dtos.CustomerDiscountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static ar.mds.ranti_core.infrastructure.api.resources.CustomerDiscountResource.CUSTOMER_DISCOUNTS;
import static ar.mds.ranti_core.infrastructure.api.resources.CustomerDiscountResource.MOBILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@RestTestConfig
public class CustomerDiscountResourceIT {

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
    void testCreate(){
        CustomerDiscountDto customerDiscountDto = CustomerDiscountDto.builder()
                .userMobile("111111111")
                .registrationDate(LocalDate.of(2020,10,31))
                .note("nota")
                .discount(5)
                .minimmumPurchase(50)
                .build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CUSTOMER_DISCOUNTS)
                .body(Mono.just(customerDiscountDto), CustomerDiscountDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDiscountDto.class)
                .value(Assertions::assertNotNull)
                .value(returnCustomerDiscount ->{
                    assertEquals("111111111", returnCustomerDiscount.getUserMobile());
                    assertNotNull(returnCustomerDiscount.getNote());
                    assertNotNull(returnCustomerDiscount.getRegistrationDate());
                    assertEquals(5,returnCustomerDiscount.getDiscount());
                    assertEquals(50, returnCustomerDiscount.getMinimmumPurchase());
                });
    }

    @Test
    void testCreateUnauthorizedException() {
        CustomerDiscountDto customerDiscountDto = CustomerDiscountDto.builder()
                .userMobile("111111111")
                .note("note")
                .registrationDate(LocalDate.of(2021,10,21))
                .discount(5)
                .minimmumPurchase(50)
                .build();

        webTestClient
                .post()
                .uri(CUSTOMER_DISCOUNTS)
                .body(Mono.just(customerDiscountDto), CustomerDiscountDto.class)
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @Test
    void testReadNotFoundException(){
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "0909")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testReadUnauthorizedException(){
        webTestClient
                .get()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "333344")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testDelete(){
        CustomerDiscountDto customerDiscountDto = CustomerDiscountDto.builder()
                .userMobile("111111111")
                .note("note")
                .registrationDate(LocalDate.of(2021,10,21))
                .discount(5)
                .minimmumPurchase(50)
                .build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CUSTOMER_DISCOUNTS)
                .body(Mono.just(customerDiscountDto), CustomerDiscountDto.class)
                .exchange()
                .expectStatus().isOk();

        this.restClientTestService.loginAdmin(webTestClient)
                .delete()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, customerDiscountDto.getUserMobile())
                .exchange()
                .expectStatus().isOk();

    }


    @Test
    void testDeleteNotFoundException(){

        this.restClientTestService.loginAdmin(webTestClient)
                .delete()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "0987")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteUnauthorizedException(){
        CustomerDiscountDto customerDiscountDto = CustomerDiscountDto.builder()
                .userMobile("111111111")
                .note("note")
                .registrationDate(LocalDate.of(2021,10,21))
                .discount(5)
                .minimmumPurchase(50)
                .build();

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(CUSTOMER_DISCOUNTS)
                .body(Mono.just(customerDiscountDto), CustomerDiscountDto.class)
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .delete()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, customerDiscountDto.getUserMobile())
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testReadAndUpdate(){

        CustomerDiscountDto customerDiscountDto = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "333344")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDiscountDto.class)
                .value(returnCustomerDiscount ->{
                    assertEquals(15, returnCustomerDiscount.getDiscount());
                    assertEquals(120, returnCustomerDiscount.getMinimmumPurchase());
                    assertEquals("nota", returnCustomerDiscount.getNote());
                })
                .returnResult().getResponseBody();

        assertNotNull(customerDiscountDto);
        customerDiscountDto.setNote("updatedNote");
        customerDiscountDto.setDiscount(10);
/*
        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "333344")
                .bodyValue(customerDiscountDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDiscountDto.class)
                .value(returnCustomerDiscount ->{
                    assertEquals(10, returnCustomerDiscount.getDiscount());
                    assertEquals(120, returnCustomerDiscount.getMinimmumPurchase());
                    assertEquals("updatedNote", returnCustomerDiscount.getNote());
                });

*/
    }

    @Test
    void testUpdateNotFoundException(){

        CustomerDiscount customerDiscount = CustomerDiscount.builder()
                .user(User.builder().mobile("xxx").build())
                .discount(13)
                .note("nota")
                .minimmumPurchase(120)
                .build();

        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "notFound")
                .bodyValue(customerDiscount)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateUnauthorizedException(){

        CustomerDiscount customerDiscount = CustomerDiscount.builder()
                .user(User.builder().mobile("xxx").build())
                .discount(13)
                .note("nota")
                .minimmumPurchase(120)
                .build();

        webTestClient
                .put()
                .uri(CUSTOMER_DISCOUNTS + MOBILE, "Unauthorized")
                .bodyValue(customerDiscount)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testGetCustomerDiscounts(){
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(CUSTOMER_DISCOUNTS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDiscount.class);
    }

    @Test
    void testGetCustomerDiscountsUnauthorizedException(){
        webTestClient
                .get()
                .uri(CUSTOMER_DISCOUNTS)
                .exchange()
                .expectStatus().isUnauthorized();
    }


}
