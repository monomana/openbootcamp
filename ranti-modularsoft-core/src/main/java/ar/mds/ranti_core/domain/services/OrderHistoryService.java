package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.OrderHistorySQL;
import ar.mds.ranti_core.domain.model.OrderSQL;
import ar.mds.ranti_core.domain.persistence.OrderPersistence;
import ar.mds.ranti_core.infrastructure.sqldb.daos.OrderHistorySQLDao;
import ar.mds.ranti_core.infrastructure.sqldb.daos.OrderSQLDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@Service
public class OrderHistoryService {

    private final OrderPersistence orderPersistence;
    private final OrderHistorySQLDao orderHistoryDao;

    @Autowired
    public OrderHistoryService(OrderPersistence orderPersistence, OrderHistorySQLDao orderHistoryDao) {
        this.orderPersistence = orderPersistence;
        this.orderHistoryDao = orderHistoryDao;
    }

    public Flux<String> findByOrderReferenceNullSafe(String orderReference) {
        return this.orderPersistence.findByOrderReferenceNullSafe(orderReference);
    }

    public Stream<OrderHistorySQL> getAllOrders() {
        return this.orderHistoryDao.findAll().stream();
    }

    public OrderHistorySQL getOrderById(int id) {
        return this.orderHistoryDao.findById(id);
    }

}
