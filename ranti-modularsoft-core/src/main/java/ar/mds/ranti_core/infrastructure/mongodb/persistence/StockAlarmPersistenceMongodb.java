package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.ConflictException;
import ar.mds.ranti_core.domain.model.StockAlarm;
import ar.mds.ranti_core.domain.persistence.StockAlarmPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.StockAlarmReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.StockAlarmEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class StockAlarmPersistenceMongodb implements StockAlarmPersistence {
    private final StockAlarmReactive stockAlarmReactive;

    @Autowired
    public StockAlarmPersistenceMongodb(StockAlarmReactive stockAlarmReactive) {
        this.stockAlarmReactive = stockAlarmReactive;
    }

    @Override
    public Mono<StockAlarm> create(StockAlarm stockAlarm) {
        return this.assertStockAlarmNameNotExist(stockAlarm.getName())
                .then(Mono.justOrEmpty(stockAlarm))
                .map(StockAlarmEntity::new)
                .flatMap(this.stockAlarmReactive::save)
                .map(StockAlarmEntity::toStockAlarm);
    }

    private Mono<Void> assertStockAlarmNameNotExist(String name) {
        return this.stockAlarmReactive.findByName(name)
                .flatMap(stockAlarmEntity -> Mono.error(
                        new ConflictException("Alarm Name already exists : " + name)
                ));
    }
}
