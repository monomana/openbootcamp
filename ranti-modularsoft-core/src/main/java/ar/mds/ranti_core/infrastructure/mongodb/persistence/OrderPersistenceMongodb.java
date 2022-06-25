package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.persistence.OrderPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.OrderReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class OrderPersistenceMongodb implements OrderPersistence {

    private final OrderReactive orderReactive;

    @Autowired
    public OrderPersistenceMongodb(OrderReactive orderReactive) {
        this.orderReactive = orderReactive;
    }

    @Override
    public Flux<String> findByOrderReferenceNullSafe(String orderReference) {
        return this.orderReactive.findByOrderReferenceNullSafe(orderReference)
                .map(OrderEntity::getOrderReference);
    }
}
