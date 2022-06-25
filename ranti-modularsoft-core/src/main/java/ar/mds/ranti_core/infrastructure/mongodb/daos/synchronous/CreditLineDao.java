package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CreditLineEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditLineDao extends MongoRepository<CreditLineEntity, String> {
}
