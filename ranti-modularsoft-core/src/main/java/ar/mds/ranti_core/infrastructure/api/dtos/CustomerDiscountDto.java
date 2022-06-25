package ar.mds.ranti_core.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDiscountDto {
    @NotBlank
    private String userMobile;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;
    private String note;
    @NotNull
    private Integer discount;
    @NotNull
    private Integer minimmumPurchase;

    public CustomerDiscountDto(CustomerDiscount customerDiscount) {
        BeanUtils.copyProperties(customerDiscount, this);
        if (Objects.nonNull(customerDiscount.getUser())) {
            this.userMobile = customerDiscount.getUser().getMobile();
        }
    }
}
