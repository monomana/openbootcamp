package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.Cashier;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CashierPersistence {

    Mono<Cashier> findLast();

    Mono<Cashier> create(Cashier cashier);

    Mono<Cashier> update(String id, Cashier lastCashier);

    Flux<Cashier> findByClosureDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

