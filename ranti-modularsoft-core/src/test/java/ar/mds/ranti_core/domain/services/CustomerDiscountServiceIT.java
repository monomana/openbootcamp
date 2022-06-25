package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.dtos.CustomerDiscountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;



@TestConfig
public class CustomerDiscountServiceIT {

    @Autowired
    CustomerDiscountService customerDiscountService;

    @MockBean
    private UserMicroservice userMicroservice;

    @BeforeEach
    void readUser(){
        BDDMockito.given(this.userMicroservice.readByMobile(any(String.class)))
                //.willReturn(Mono.just(User.builder().mobile("666666666").firstName("mock").build()))
                .willAnswer(arguments ->
                        Mono.just(User.builder().mobile(arguments.getArgument(0)).firstName("mock").build()));
    }


    @Test
    void testCreate(){
        CustomerDiscountDto customerDiscountDto = CustomerDiscountDto.builder()
                .userMobile("111111111")
                .registrationDate(LocalDate.of(2020,10,31))
                .discount(10)
                .minimmumPurchase(100)
                .build();

        StepVerifier
                .create(this.customerDiscountService.create(customerDiscountDto))
                .expectNextMatches(dbCustomerDiscount ->{
                    assertNotNull(dbCustomerDiscount.getUser());
                    assertNull(dbCustomerDiscount.getNote());
                    assertNotNull(dbCustomerDiscount.getRegistrationDate());
                    assertNotNull(dbCustomerDiscount.getDiscount());
                    assertNotNull(dbCustomerDiscount.getMinimmumPurchase());
                    assertEquals("111111111", dbCustomerDiscount.getUser().getMobile());
                    return true;
                })
                .expectComplete()
                .verify();
    }


    @Test
    void testRead(){
        StepVerifier
                .create(this.customerDiscountService.read("010101"))
                .expectNextMatches(dbCustomerDiscount -> {
                    assertEquals("Ana", dbCustomerDiscount.getUser().getFirstName());
                    assertNull(dbCustomerDiscount.getNote());
                    assertEquals(5,dbCustomerDiscount.getDiscount());
                    assertEquals(100, dbCustomerDiscount.getMinimmumPurchase());
                    return true;
                })
                .expectComplete()
                .verify();

    }


    @Test
    void testDeleteNotFoundException(){
        CustomerDiscountDto customerDiscountDto = CustomerDiscountDto.builder()
                .userMobile("111111111")
                .registrationDate(LocalDate.of(2020,10,31))
                .discount(10)
                .minimmumPurchase(100)
                .build();

        StepVerifier
                .create(this.customerDiscountService.create(customerDiscountDto))
                .expectNextMatches(dbCustomerDiscount -> {
                    assertEquals(10,dbCustomerDiscount.getDiscount());
                    assertEquals(100, dbCustomerDiscount.getMinimmumPurchase());
                    return true;
                })
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.customerDiscountService.delete(customerDiscountDto.getUserMobile()))
                .expectNextCount(0)
                .expectComplete()
                .verify();

        StepVerifier
                .create(this.customerDiscountService.read(customerDiscountDto.getUserMobile()))
                .expectError(NotFoundException.class);
    }


    @Test
    void testReadAndUpdate(){
        StepVerifier
                .create(this.customerDiscountService.read("010101"))
                .expectNextMatches(dbCustomerDiscount -> {
                    assertEquals("Ana", dbCustomerDiscount.getUser().getFirstName());
                    assertNull(dbCustomerDiscount.getNote());
                    assertEquals(5,dbCustomerDiscount.getDiscount());
                    return true;
                })
                .expectComplete()
                .verify();

        CustomerDiscount customerDiscount = CustomerDiscount.builder()
                .note("updated note")
                .discount(10)
                .minimmumPurchase(150)
                .build();

        StepVerifier
                .create(this.customerDiscountService.update("010101", customerDiscount))
                .expectNextMatches(dbCustomerDiscount ->{
                    assertEquals("Ana", dbCustomerDiscount.getUser().getFirstName());
                    assertEquals(10, dbCustomerDiscount.getDiscount());
                    assertEquals(150, dbCustomerDiscount.getMinimmumPurchase());
                    assertNotNull(dbCustomerDiscount.getNote());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testUpdateNotFoundException(){
        CustomerDiscount customerDiscount = CustomerDiscount.builder()
                .note("updated note")
                .discount(10)
                .minimmumPurchase(150)
                .build();

        StepVerifier
                .create(this.customerDiscountService.update("notFound", customerDiscount))
                .expectError(NotFoundException.class);
    }

    @Test
    void testGetCustomerDiscounts(){
        StepVerifier
                .create(this.customerDiscountService.getCustomerDiscounts())
                .expectNextCount(5)
                .expectComplete()
                .verify();
    }


}
