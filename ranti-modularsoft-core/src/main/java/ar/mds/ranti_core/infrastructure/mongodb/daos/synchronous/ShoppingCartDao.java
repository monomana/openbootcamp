package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ShoppingCartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingCartDao extends MongoRepository<ShoppingCartEntity, String> {
}
