package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.StockAlarm;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StockAlarmPersistence {

    Mono<StockAlarm> create(StockAlarm stockAlarm);
}
