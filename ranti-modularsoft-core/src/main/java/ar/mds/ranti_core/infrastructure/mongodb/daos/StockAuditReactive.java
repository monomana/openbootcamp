package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.StockAuditEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface StockAuditReactive extends ReactiveSortingRepository<StockAuditEntity, String> {
    Mono<StockAuditEntity> findById(String id);
}
