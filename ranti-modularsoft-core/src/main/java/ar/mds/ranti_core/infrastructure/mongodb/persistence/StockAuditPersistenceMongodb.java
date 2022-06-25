package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.StockAudit;
import ar.mds.ranti_core.domain.persistence.StockAuditPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ArticleReactive;
import ar.mds.ranti_core.infrastructure.mongodb.daos.StockAuditReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.StockAuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class StockAuditPersistenceMongodb implements StockAuditPersistence {
    private final StockAuditReactive stockAuditReactive;
    private final ArticleReactive articleReactive;

    @Autowired
    public StockAuditPersistenceMongodb(StockAuditReactive stockAuditReactive, ArticleReactive articleReactive) {
        this.stockAuditReactive = stockAuditReactive;
        this.articleReactive = articleReactive;
    }

    @Override
    public Mono<StockAudit> create(StockAudit stockAudit) {
        StockAuditEntity stockAuditEntity = new StockAuditEntity(stockAudit);
        return this.articleReactive.findAll()
                .map(articleEntity -> {
                    stockAuditEntity.AddArticle(articleEntity);
                    return stockAuditEntity;
                })
                .then(this.stockAuditReactive.save(stockAuditEntity))
                .map(StockAuditEntity::toStockAudit);
    }

    @Override
    public Flux<StockAudit> readAll() {
        return this.stockAuditReactive.findAll().map(StockAuditEntity::toStockAudit);
    }

    @Override
    public Mono<StockAudit> readById(String id) {
        return this.stockAuditReactive.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("" + id)))
                .map(StockAuditEntity::toStockAudit);
    }
}

