package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ProviderInvoiceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderInvoiceDao extends MongoRepository<ProviderInvoiceEntity, String> {
}
