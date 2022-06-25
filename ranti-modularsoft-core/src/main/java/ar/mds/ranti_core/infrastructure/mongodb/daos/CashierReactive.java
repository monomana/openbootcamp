package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CashierEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface CashierReactive extends ReactiveSortingRepository<CashierEntity, String> {
    Mono<CashierEntity> findFirstByOrderByOpeningDateDesc();

    Flux<CashierEntity> findByClosureDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
