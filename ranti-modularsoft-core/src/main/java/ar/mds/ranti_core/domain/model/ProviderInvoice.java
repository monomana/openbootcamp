package ar.mds.ranti_core.domain.model;

import ar.mds.ranti_core.domain.model.validations.PositiveBigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderInvoice {
    private String identity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    @PositiveBigDecimal
    private BigDecimal baseTax;
    @PositiveBigDecimal
    private BigDecimal textValue;
    @NotBlank
    private String providerCompany;
    @NotBlank
    private String orderReference;

    public static ProviderInvoice ofBaseTaxTextValue(ProviderInvoice providerInvoice) {
        return ProviderInvoice.builder()
                .baseTax(providerInvoice.baseTax)
                .textValue(providerInvoice.textValue)
                .build();
    }
}
