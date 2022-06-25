package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.StockAuditPersistence;
import ar.mds.ranti_core.domain.model.StockAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class StockAuditService {
    private final StockAuditPersistence stockAuditPersistence;

    @Autowired
    public StockAuditService(StockAuditPersistence stockAuditPersistence) {
        this.stockAuditPersistence = stockAuditPersistence;
    }

    public Mono<StockAudit> create(StockAudit stockAudit) {
        stockAudit.setCreationDate(LocalDateTime.now());
        return this.stockAuditPersistence.create(stockAudit);
    }

    public Flux<StockAudit> readAll() {
        return this.stockAuditPersistence.readAll();
    }

    public Mono<StockAudit> read(String id) {
        return this.stockAuditPersistence.readById(id);
    }

}
