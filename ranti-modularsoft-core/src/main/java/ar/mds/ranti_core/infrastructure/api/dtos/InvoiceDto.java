package ar.mds.ranti_core.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ar.mds.ranti_core.domain.model.Invoice;
import ar.mds.ranti_core.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDto {
    private Integer identity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    private BigDecimal baseTax;
    private BigDecimal taxValue;
    @NotBlank
    private String userMobile;
    private String ticketId;

    public InvoiceDto(Invoice invoice) {
        BeanUtils.copyProperties(invoice, this);
        if (Objects.nonNull(invoice.getUser())) {
            this.userMobile = invoice.getUser().getMobile();
        }
        if (Objects.nonNull(invoice.getTicket())) {
            this.ticketId = invoice.getTicket().getId();
        }
    }

    public static InvoiceDto ofIdentityTicketIdUserMobile(Invoice invoice) {
        return InvoiceDto.builder()
                .identity(invoice.getIdentity())
                .ticketId(invoice.getTicket().getId())
                .userMobile(invoice.getUser().getMobile())
                .build();
    }

    public Invoice toInvoiceOnlyUser() {
        return Invoice.builder()
                .identity(this.identity)
                .user(User.builder().mobile(this.userMobile).build())
                .build();
    }
}
