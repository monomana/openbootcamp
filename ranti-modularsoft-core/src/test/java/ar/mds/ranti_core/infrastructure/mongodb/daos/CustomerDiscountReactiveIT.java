package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@TestConfig
public class CustomerDiscountReactiveIT {

    @Autowired
    CustomerDiscountReactive customerDiscountReactive;

    @Test
    void testFindByUserMobile(){
        StepVerifier
                .create(this.customerDiscountReactive.findByUserMobile(null))
                .expectComplete()
                .verify();
    }

    @Test
    void testDeleteByUserMobile(){
        CustomerDiscount customerDiscount = CustomerDiscount.builder().user(User.builder().mobile("0101").build()).discount(10).build();
        StepVerifier
                .create(this.customerDiscountReactive.deleteByUserMobile(customerDiscount.getUser().getMobile()))
                .verifyComplete();
    }

}
