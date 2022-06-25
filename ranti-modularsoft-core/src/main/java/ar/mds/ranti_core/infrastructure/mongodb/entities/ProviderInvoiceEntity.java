package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.ProviderInvoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class ProviderInvoiceEntity {
    @Id
    private String id;
    private LocalDateTime creationDate;
    private BigDecimal baseTax;
    private BigDecimal textValue;
    @DBRef
    private ProviderEntity providerEntity;
    private String orderReference;

    public ProviderInvoice toProviderInvoice() {
        ProviderInvoice providerInvoice = new ProviderInvoice();
        providerInvoice.setIdentity(this.id);
        BeanUtils.copyProperties(this, providerInvoice, "id");
        providerInvoice.setProviderCompany(this.getProviderEntity().getCompany());
        return providerInvoice;
    }

    public void setProviderInvoice(ProviderInvoice providerInvoice) {
        BeanUtils.copyProperties(providerInvoice, this);
    }
}
