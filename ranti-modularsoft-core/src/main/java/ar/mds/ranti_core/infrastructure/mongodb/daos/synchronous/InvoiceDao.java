package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.InvoiceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceDao extends MongoRepository<InvoiceEntity, String> {
}
