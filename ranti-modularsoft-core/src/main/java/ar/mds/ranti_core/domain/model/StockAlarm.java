package ar.mds.ranti_core.domain.model;

import ar.mds.ranti_core.domain.model.validations.ListNotEmpty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockAlarm {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Integer warning;
    private Integer critical;
    @ListNotEmpty
    private List<StockAlarmLine> stockAlarmLines;

    /*public StockAlarm() {
        stockAlarmLines = new ArrayList<>();
    }*/

   /* public static StockAlarm ofNameDescriptionWarningCritical(StockAlarm stockAlarm) {
        return StockAlarm.builder()
                .name(stockAlarm.getName())
                .description(stockAlarm.getDescription())
                .warning(stockAlarm.getWarning())
                .critical(stockAlarm.getCritical())
                .build();
    }*/

    public void doDefault() {
        if (Objects.isNull(warning)) {
            this.warning = 5;
        }
        if (Objects.isNull(critical)) {
            this.critical = 3;
        }
    }

    public void addStockAlarmLine(StockAlarmLine stockAlarmLine) {
        this.stockAlarmLines.add(stockAlarmLine);
    }

    public void removeStockAlarmLine(StockAlarmLine stockAlarmLine) {
        this.stockAlarmLines.remove(stockAlarmLine);
    }

    public List<StockAlarmLine> contents() {
        return this.stockAlarmLines;
    }
}
