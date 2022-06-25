package ar.mds.ranti_core.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import ar.mds.ranti_core.domain.model.StockAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAuditDto {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeDate;
    private BigDecimal lossValue;

    public StockAuditDto(StockAudit stockAudit) {
        BeanUtils.copyProperties(stockAudit, this);
    }

    public static StockAuditDto ofIdCreationAndCloseDataAndLossvalue(StockAudit stockAudit) {
        return StockAuditDto.builder()
                .id(stockAudit.getId())
                .creationDate(stockAudit.getCreationDate())
                .closeDate(stockAudit.getCloseDate())
                .lossValue(stockAudit.getLossValue())
                .build();
    }

}
