package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
public class CustomerDiscountPersistenceMongodbIT {

    @Autowired
    CustomerDiscountPersistenceMongodb customerDiscountPersistenceMongodb;

    @Test
    void testCreate(){
        CustomerDiscount customerDiscount = CustomerDiscount.builder()
                .user(User.builder().mobile("111111111").build())
                .registrationDate(LocalDate.of(2020,10,31))
                .discount(10)
                .minimmumPurchase(100)
                .build();

        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.create(customerDiscount))
                .expectNextMatches(dbCustomerDiscount ->{
                    assertEquals("111111111",dbCustomerDiscount.getUser().getMobile());
                    assertNotNull(dbCustomerDiscount.getUser());
                    assertNotNull(dbCustomerDiscount.getRegistrationDate());
                    assertEquals(10, dbCustomerDiscount.getDiscount());
                    assertEquals(100, dbCustomerDiscount.getMinimmumPurchase());
                    assertNull(dbCustomerDiscount.getNote());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testReadAndUpdate(){
        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.read("333344"))
                .expectNextMatches(dbCustomerDiscount ->{
                    assertNull(dbCustomerDiscount.getUser().getFirstName());
                    assertNotNull(dbCustomerDiscount.getNote());
                    assertNotNull(dbCustomerDiscount.getMinimmumPurchase());
                    assertEquals(15, dbCustomerDiscount.getDiscount());
                    return true;
                })
                .expectComplete()
                .verify();

        CustomerDiscount customerDiscount = CustomerDiscount.builder().note("updated").discount(25).build();

        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.update("333344", customerDiscount))
                .expectNextMatches(dbCustomerDiscount ->{
                    assertNull(dbCustomerDiscount.getUser().getFirstName());
                    assertEquals("updated",dbCustomerDiscount.getNote());
                    assertNull(dbCustomerDiscount.getMinimmumPurchase());
                    assertEquals(25, dbCustomerDiscount.getDiscount());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testReadNotFound(){
        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.read("notFound"))
                .expectError(NotFoundException.class);
    }

    @Test
    void testDelete(){
        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.delete("00000000"))
                .expectNextCount(0)
                .expectComplete().verify();
    }

    @Test
    void testUpdateNotFound(){
        CustomerDiscount customerDiscount = CustomerDiscount.builder().note("updated").discount(25).build();
        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.update("NotFound",customerDiscount))
                .expectError(NotFoundException.class);
    }

    @Test
    void testGetCustomerDiscounts(){
        StepVerifier
                .create(this.customerDiscountPersistenceMongodb.getCustomerDiscounts())
                .expectNextCount(5)
                .expectComplete().verify();
    }
}
