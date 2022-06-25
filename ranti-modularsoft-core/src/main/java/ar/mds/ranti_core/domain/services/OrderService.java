package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.model.OrderHistorySQL;
import ar.mds.ranti_core.domain.model.OrderSQL;
import ar.mds.ranti_core.domain.persistence.OrderPersistence;
import ar.mds.ranti_core.infrastructure.sqldb.daos.OrderSQLDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final OrderPersistence orderPersistence;
    private final OrderSQLDao orderDao;

    @Autowired
    public OrderService(OrderPersistence orderPersistence, OrderSQLDao orderDao) {
        this.orderPersistence = orderPersistence;
        this.orderDao = orderDao;
    }
    public OrderSQL create(OrderSQL order) {
      //  order.setRegistrationDate(LocalDateTime.now());
        return this.orderDao.save(order);
    }
    public Flux<String> findByOrderReferenceNullSafe(String orderReference) {
        return this.orderPersistence.findByOrderReferenceNullSafe(orderReference);
    }

    public Stream<OrderSQL> getAllOrders() {
        return this.orderDao.findAll().stream();
    }
    public OrderSQL getOrderById(int id) {
        return this.orderDao.findById(id);
    }
}
