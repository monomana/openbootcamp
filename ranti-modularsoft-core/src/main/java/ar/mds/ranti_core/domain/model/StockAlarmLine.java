package ar.mds.ranti_core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockAlarmLine {
    private Integer warning;
    private Integer critical;
    private Article article;
    private String barcode;

    /*public static StockAlarmLine ofNameDescriptionWarningCritical(StockAlarmLine stockAlarmLine) {
        return StockAlarmLine.builder()
                .barcode(stockAlarmLine.getArticle().getBarcode())
                .warning(stockAlarmLine.getWarning())
                .critical(stockAlarmLine.getCritical())
                .build();
    }*/
}
