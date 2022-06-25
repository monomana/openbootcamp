package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.CreditLine;
import ar.mds.ranti_core.domain.model.CreditSale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class CreditLineEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String mobile;
    private LocalDateTime registrationDateTime;
    private List<CreditSale> creditSales;

    public CreditLineEntity(CreditLine creditLine) {
        this.creditSales = creditLine.getCreditSales();
        this.registrationDateTime = creditLine.getRegistrationDateTime();
        this.mobile = creditLine.getUserMobile();
    }

    public CreditLine toCreditLine() {
        CreditLine creditLine = new CreditLine();
        creditLine.setCreditSales(this.creditSales);
        creditLine.setRegistrationDateTime(this.registrationDateTime);
        if(Objects.nonNull(this.mobile)){
            creditLine.setUserMobile(this.mobile);
        }
        return creditLine;
    }
}
