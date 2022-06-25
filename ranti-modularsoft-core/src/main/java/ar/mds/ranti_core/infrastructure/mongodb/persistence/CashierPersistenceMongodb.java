package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.persistence.CashierPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.CashierReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.CashierEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class CashierPersistenceMongodb implements CashierPersistence {

    private final CashierReactive cashierReactive;

    @Autowired
    public CashierPersistenceMongodb(CashierReactive cashierReactive) {
        this.cashierReactive = cashierReactive;
    }

    @Override
    public Mono<Cashier> findLast() {
        return this.cashierReactive.findFirstByOrderByOpeningDateDesc()
                .map(CashierEntity::toCashier);
    }

    @Override
    public Mono<Cashier> create(Cashier cashier) {
        return this.cashierReactive.save(new CashierEntity(cashier))
                .map(CashierEntity::toCashier);
    }

    @Override
    public Mono<Cashier> update(String id, Cashier cashier) {
        return this.cashierReactive.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent cashier: " + id)))
                .map(cashierEntity -> cashierEntity.from(cashier))
                .flatMap(this.cashierReactive::save)
                .map(CashierEntity::toCashier);
    }

    @Override
    public Flux<Cashier> findByClosureDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return this.cashierReactive.findByClosureDateBetween(startDate, endDate)
                .map(CashierEntity::toCashier);
    }
}
