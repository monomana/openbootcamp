package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.StockAlarm;
import ar.mds.ranti_core.domain.model.StockAlarmLine;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "stockAlarms")
public class StockAlarmEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private Integer warning;
    private Integer critical;
    private List<StockAlarmLineEntity> stockAlarmLines;

    public StockAlarmEntity(StockAlarm stockAlarm) {
        BeanUtils.copyProperties(stockAlarm, this);
        this.stockAlarmLines = new ArrayList<>();
    }

    public void add(StockAlarmLineEntity stockAlarmLineEntity) {
        this.stockAlarmLines.add(stockAlarmLineEntity);
    }

    public void remove(StockAlarmLineEntity stockAlarmLineEntity) {
        this.stockAlarmLines.remove(stockAlarmLineEntity);
    }

    public List<StockAlarmLineEntity> contents() {
        return this.stockAlarmLines;
    }

    public StockAlarm toStockAlarm() {
        final StockAlarm stockAlarm = new StockAlarm();
        BeanUtils.copyProperties(this, stockAlarm);
        if (!this.stockAlarmLines.isEmpty()) {
            final List<StockAlarmLine> stockAlarmLines = this.stockAlarmLines.stream()
                    .map(StockAlarmLineEntity::toStockAlarmLine)
                    .collect(Collectors.toList());
            stockAlarm.setStockAlarmLines(stockAlarmLines);
        }
        return stockAlarm;
    }
}
