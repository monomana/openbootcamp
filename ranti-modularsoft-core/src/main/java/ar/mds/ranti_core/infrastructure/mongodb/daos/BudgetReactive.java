package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.BudgetEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface BudgetReactive extends ReactiveSortingRepository<BudgetEntity, String> {
    Mono<BudgetEntity> findByReference(String reference);

    @Query("{$and:["
            + "?#{ [0] == null ? { reference : {$ne:null}} : { reference : {$regex:[0], $options: 'i'}} },"
            + "?#{ [1] == null ? { reference : {$ne:null}} : { creationDate : {$gte:[1]} } },"
            + "?#{ [2] == null ? { reference : {$ne:null}} : { creationDate : {$lt:[2]} } }"
            + "] }")
    Flux<BudgetEntity> findBudgetsByCreationDateAndReferenceNullSafe(
            String reference, LocalDateTime fromDate, LocalDateTime untilDate);
}

