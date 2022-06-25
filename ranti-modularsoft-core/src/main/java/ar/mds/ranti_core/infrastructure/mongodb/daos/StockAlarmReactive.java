package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.StockAlarmEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface StockAlarmReactive extends ReactiveSortingRepository<StockAlarmEntity, String> {
    Mono<StockAlarmEntity> findByName(String name);
}
