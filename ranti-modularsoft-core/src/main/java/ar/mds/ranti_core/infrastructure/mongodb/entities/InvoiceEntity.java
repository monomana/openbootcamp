package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.Invoice;
import ar.mds.ranti_core.domain.model.Ticket;
import ar.mds.ranti_core.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class InvoiceEntity {
    @Id
    private String id;
    private Integer identity;
    private LocalDateTime creationDate;
    private BigDecimal baseTax;
    private BigDecimal taxValue;
    private String userMobile;
    private String ticketId;

    public InvoiceEntity(Invoice invoice) {
        BeanUtils.copyProperties(invoice, this);
        if (Objects.nonNull(invoice.getUser())) {
            this.userMobile = invoice.getUser().getMobile();
        }
        if (Objects.nonNull(invoice.getTicket())) {
            this.ticketId = invoice.getTicket().getId();
        }
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(this, invoice);
        if (Objects.nonNull(this.userMobile)) {
            invoice.setUser(User.builder().mobile(this.userMobile).build());
        }
        if (Objects.nonNull(this.ticketId)) {
            invoice.setTicket(Ticket.builder().id(this.ticketId).build());
        }
        return invoice;
    }
}
