package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.OrderEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderReactive extends ReactiveSortingRepository<OrderEntity, String> {

    Mono<OrderEntity> findByOrderReference(String orderReference);

    @Query("{$and:["
            + "?#{ [0] == null ? {_id : {$ne:null}} : { orderReference : {$regex:[0], $options: 'i'} } }"
            + "] }")
    Flux<OrderEntity> findByOrderReferenceNullSafe(String orderReference);
}
