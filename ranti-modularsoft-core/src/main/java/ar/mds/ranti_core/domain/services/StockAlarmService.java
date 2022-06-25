package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.StockAlarmPersistence;
import ar.mds.ranti_core.domain.model.StockAlarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockAlarmService {
    private final StockAlarmPersistence stockAlarmPersistence;

    @Autowired
    public StockAlarmService(StockAlarmPersistence stockAlarmPersistence) {
        this.stockAlarmPersistence = stockAlarmPersistence;
    }

    public Mono<StockAlarm> create(StockAlarm stockAlarm) {
        return this.stockAlarmPersistence.create(stockAlarm);
    }
}
