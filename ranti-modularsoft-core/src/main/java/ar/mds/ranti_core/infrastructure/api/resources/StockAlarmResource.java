package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.StockAlarm;
import ar.mds.ranti_core.domain.services.StockAlarmService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(StockAlarmResource.STOCK_ALARM)
public class StockAlarmResource {
    public static final String STOCK_ALARM = "/stockAlarm";

    private final StockAlarmService stockAlarmService;

    @Autowired
    public StockAlarmResource(StockAlarmService stockAlarmService) {
        this.stockAlarmService = stockAlarmService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<StockAlarm> create(@Valid @RequestBody StockAlarm stockAlarm) {
        stockAlarm.doDefault();
        return this.stockAlarmService.create(stockAlarm);
    }
}
