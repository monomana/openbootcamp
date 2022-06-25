package ar.mds.ranti_core.domain.model;

import ar.mds.ranti_core.domain.model.validations.ListNotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Budget {
    @NotBlank
    private String reference;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    @ListNotEmpty
    private List<Shopping> shoppings;

    public BigDecimal total() {
        return this.shoppings.stream()
                .map(Shopping::totalShopping)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

