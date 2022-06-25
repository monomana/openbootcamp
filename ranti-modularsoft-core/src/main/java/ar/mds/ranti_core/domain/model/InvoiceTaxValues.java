package ar.mds.ranti_core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceTaxValues {
    private Tax tax;
    private BigDecimal totalShopping;

    public BigDecimal getBaseTax() {
        return totalShopping.divide(
                BigDecimal.ONE.add(tax.getRate()
                        .divide(BigDecimal.valueOf(100), 3, RoundingMode.HALF_UP)
                ), RoundingMode.HALF_UP
        ).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTaxValue() {
        return totalShopping.subtract(this.getBaseTax()).setScale(2, RoundingMode.HALF_UP);
    }
}
