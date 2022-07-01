package ar.modularsoft.data.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shopping_history")
public class Shopping {

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
    @NotNull
    private int state;
    private String suggestion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId",insertable = false,updatable = false)
    private Company company;

    public void setRegistrationDate(LocalDateTime now) {
        this.createdAt=now;

    }
}


