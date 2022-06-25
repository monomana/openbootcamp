package ar.mds.ranti_core.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerPointsTest {

    @Test
    void testCustomer(){
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(88)
                .value(12)
                .lastDate(LocalDate.of(2022,12,24))
                .user(User.builder()
                        .dni("Y5247896X")
                        .firstName("Bartolo")
                        .email("bartolo@gmail.com")
                        .familyName("Gonzalez")
                        .mobile("888965321").build())
                .build();

        assertNotNull(customerPoints.getId());
        assertEquals(12, customerPoints.getValue());
        assertNotNull(customerPoints.getLastDate());
        assertNotNull(customerPoints.getUser());
        assertEquals("888965321",customerPoints.getUser().getMobile());
        assertEquals("Y5247896X",customerPoints.getUser().getDni());
        assertEquals("Gonzalez",customerPoints.getUser().getFamilyName());
    }
}
