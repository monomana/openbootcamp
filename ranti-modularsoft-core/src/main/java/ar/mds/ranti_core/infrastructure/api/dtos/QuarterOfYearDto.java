package ar.mds.ranti_core.infrastructure.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterOfYearDto {
    private Integer year;
    private String quarter;
    private BigDecimal totalBaseTax;
    private BigDecimal totalTaxValue;
}
