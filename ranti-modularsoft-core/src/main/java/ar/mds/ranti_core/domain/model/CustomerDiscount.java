package ar.mds.ranti_core.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDiscount {
    @NotNull
    private User user;
    private String note;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;
    @NotNull
    @Range(min=1, max=100)
    private Integer discount;
    @NotNull
    @Min(0)
    private Integer minimmumPurchase;

}
