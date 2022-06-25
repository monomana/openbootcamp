package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.StockAuditEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockAuditDao extends MongoRepository<StockAuditEntity, String> {
}
