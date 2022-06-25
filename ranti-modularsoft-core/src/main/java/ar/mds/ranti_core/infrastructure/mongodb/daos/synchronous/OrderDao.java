package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDao extends MongoRepository<OrderEntity, String> {
}
