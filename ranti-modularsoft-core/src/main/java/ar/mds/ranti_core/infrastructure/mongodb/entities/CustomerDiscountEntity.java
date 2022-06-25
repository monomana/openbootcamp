package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class CustomerDiscountEntity {
    @Id
    private String userMobile;
    private String username;
    private String note;
    private LocalDate registrationDate;
    private Integer discount;
    private Integer minimmumPurchase;

    public CustomerDiscountEntity(CustomerDiscount customerDiscount) {
        BeanUtils.copyProperties(customerDiscount, this);
        if (Objects.nonNull(customerDiscount.getUser())) {
            this.userMobile = customerDiscount.getUser().getMobile();
            this.username = customerDiscount.getUser().getFirstName();
        }
    }

    public CustomerDiscount toCustomerDiscount() {

        CustomerDiscount customerDiscount = new CustomerDiscount();
        BeanUtils.copyProperties(this, customerDiscount);

        if (Objects.nonNull(this.userMobile) && Objects.nonNull(this.username)) {
            customerDiscount.setUser(User.builder().mobile(this.getUserMobile()).firstName(this.getUsername()).build());
        }else if(Objects.nonNull(this.userMobile)){
            customerDiscount.setUser(User.builder().mobile(this.getUserMobile()).build());
        }

        return customerDiscount;
    }
}
