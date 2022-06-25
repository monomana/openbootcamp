package ar.mds.ranti_core.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.mds.ranti_core.domain.model.CreditLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditLineDto {
    @NotBlank
    private String mobile;
    @NotBlank
    private String formattedCreationDate;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CreditLineDto(CreditLine creditLine) {
        this.mobile = creditLine.getUserMobile();
        this.formattedCreationDate = creditLine.getRegistrationDateTime().format(CreditLineDto.formatter);
    }

    public static CreditLineDto fromCreditLine(CreditLine creditLine) {
        return CreditLineDto.builder()
                .mobile(creditLine.getUserMobile())
                .formattedCreationDate(creditLine.getRegistrationDateTime().format(CreditLineDto.formatter))
                .build();
    }
}
