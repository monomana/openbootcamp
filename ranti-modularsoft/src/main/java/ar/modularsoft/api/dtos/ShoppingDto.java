package ar.modularsoft.api.dtos;


import ar.modularsoft.data.model.Company;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.Shopping;
import ar.modularsoft.data.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
    @NotNull
    private int userId  ;
    @NotNull
    private int companyId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    // private boolean state;
    @NotNull
    private int userCompanyId;
    @NotNull
    private int orderId;
    private int state;

    private User user;

    private Company company;

    public ShoppingDto(Shopping shoppingHistory) {
        BeanUtils.copyProperties(shoppingHistory, this);
    }

    public Shopping toShopping() {
        this.doDefault();
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(this, shopping);
        //  user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return shopping;
    }
    public void doDefault() {
       if (Objects.isNull(amount)) {
            amount=new BigDecimal(1);
        }
//        if (Objects.isNull(role)) {
//            this.role = Role.CUSTOMER;
//        }
//        if (Objects.isNull(active)) {
//            this.active = true;
//        }
    }
}


