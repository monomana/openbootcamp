package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CustomerDiscountEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface CustomerDiscountReactive extends ReactiveSortingRepository<CustomerDiscountEntity, String> {

    Mono<CustomerDiscountEntity> findByUserMobile(String userMobile);

    Mono<Void> deleteByUserMobile(String userMobile);
}
