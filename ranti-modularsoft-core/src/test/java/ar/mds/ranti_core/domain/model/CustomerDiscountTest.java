package ar.mds.ranti_core.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerDiscountTest {

    @Test
    void testCustomerDiscount(){
        CustomerDiscount customerDiscount = CustomerDiscount.builder()
                .user(User.builder()
                        .mobile("88")
                        .firstName("Sara")
                        .build())
                .note("nota")
                .registrationDate(LocalDate.of(2020, 10,31))
                .discount(20)
                .minimmumPurchase(120)
                .build();

        assertEquals("88", customerDiscount.getUser().getMobile());
        assertEquals("Sara", customerDiscount.getUser().getFirstName());
        assertEquals("nota", customerDiscount.getNote());
        assertEquals(LocalDate.of(2020, 10,31),customerDiscount.getRegistrationDate());
        assertEquals(20, customerDiscount.getDiscount());
        assertEquals(120, customerDiscount.getMinimmumPurchase());

    }
}
