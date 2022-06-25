package ar.mds.ranti_core.domain.model;

import ar.mds.ranti_core.domain.model.validations.PositiveBigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Voucher {
    @NotBlank
    private String reference;
    @PositiveBigDecimal
    private BigDecimal value;
    @PastOrPresent
    private LocalDate creationDate;
    @PastOrPresent
    private LocalDate dateOfUse;
    private User user;

}
