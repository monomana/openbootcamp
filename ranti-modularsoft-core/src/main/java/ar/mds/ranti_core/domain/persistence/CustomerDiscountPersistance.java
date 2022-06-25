package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.CustomerDiscount;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerDiscountPersistance {

    Mono<CustomerDiscount> create(CustomerDiscount customerDiscount);

    Mono<CustomerDiscount> read(String mobile);

    Mono<Void> delete(String mobile);

    Mono<CustomerDiscount> update(String mobile, CustomerDiscount customerDiscount);

    Flux<CustomerDiscount> getCustomerDiscounts();
}
