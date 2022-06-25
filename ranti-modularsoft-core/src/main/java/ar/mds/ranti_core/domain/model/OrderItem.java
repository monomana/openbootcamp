package ar.mds.ranti_core.domain.model;

import ar.mds.ranti_core.domain.model.validations.PositiveBigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItem {
    @NotBlank
    private String barcode;
    private String description;
    private BigDecimal retailPrice;
    @NotNull
    private Integer amount;
    @PositiveBigDecimal
    private BigDecimal discount;
    @NotNull
    private ShoppingState state;




    public BigDecimal totalUnit() {
        this.discount = discount.setScale(6, RoundingMode.HALF_UP);
        BigDecimal totalUnit = retailPrice.multiply(
                BigDecimal.ONE.subtract(this.discount.divide(new BigDecimal("100"), RoundingMode.HALF_UP)));
        return totalUnit.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal totalShopping() {
        return totalUnit().multiply(new BigDecimal(amount));
    }

}
