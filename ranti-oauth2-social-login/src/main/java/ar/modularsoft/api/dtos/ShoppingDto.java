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
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

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
    // @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    // private boolean state;
    @NotNull
    private int userCompanyId;
    @NotNull
    private int orderId;
    private int state;
    private String suggestion;

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
       if (Objects.isNull(createdAt) ) {

       TimeZone.setDefault(TimeZone.getTimeZone("GMT-3:00"));
           createdAt=LocalDateTime.now();
                 /*  atZone(ZoneId.of("GMT-3")).
                  toLocalDateTime();
           System.out.println("Date in UTC: " +  createdAt);

                  */


          // createdAt=LocalDateTime.now(Clock.systemUTC());
           //createdAt= LocalDateTime.from(LocalDateTime.now().atZone(ZoneId.systemDefault()));
           // atZone(ZoneId.systemDefault()) ZoneId.of("UTC")
           // createdAt= LocalDateTime.from(LocalDateTime.now().atZone(ZoneId.of("America/Cordoba")));
        }
//        if (Objects.isNull(role)) {
//            this.role = Role.CUSTOMER;
//        }
//        if (Objects.isNull(active)) {
//            this.active = true;
//        }
    }
}


