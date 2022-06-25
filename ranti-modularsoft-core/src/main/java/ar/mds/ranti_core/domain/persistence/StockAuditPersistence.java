package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.StockAudit;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockAuditPersistence {
    Mono<StockAudit> create(StockAudit stockAudit);

    Flux<StockAudit> readAll();

    Mono<StockAudit> readById(String id);
}
