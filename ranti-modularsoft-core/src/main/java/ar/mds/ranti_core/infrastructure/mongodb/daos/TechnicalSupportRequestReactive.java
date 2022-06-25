package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.TechnicalSupportRequestEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TechnicalSupportRequestReactive extends ReactiveSortingRepository<TechnicalSupportRequestEntity, String> {

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { request : {$regex:[0], $options: 'i'} } }"
            + "] }")
    Flux<TechnicalSupportRequestEntity> findByRequestTextNullSafe(String request);

    Mono<TechnicalSupportRequestEntity> findByIdentifier(String id);
}
