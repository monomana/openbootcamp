package ar.mds.ranti_core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashMovement {
    private BigDecimal cash;
    private CashMovementType type;
    private String description;
}
