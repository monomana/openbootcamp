package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CustomerDiscountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerDiscountDao extends MongoRepository<CustomerDiscountEntity, String > {
}
