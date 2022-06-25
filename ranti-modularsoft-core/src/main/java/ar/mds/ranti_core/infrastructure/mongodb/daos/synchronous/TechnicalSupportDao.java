package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.TechnicalSupportRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TechnicalSupportDao extends MongoRepository<TechnicalSupportRequestEntity, String > {
}
