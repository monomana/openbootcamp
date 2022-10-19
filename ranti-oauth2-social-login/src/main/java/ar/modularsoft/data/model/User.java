package ar.modularsoft.data.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

//@ApiModel("Entidad usuario para realizar la autentcacion")
@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {

   // @ApiModelProperty("Id autoincremental")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    // @ApiModelProperty("campo oculto y hasheado")
    private String password;
    private String firstName;
    private String lastName;
    private String dni;

    private String email;
    private String address;
    private String phone;
    private Date createdAt;
    private Date updatedAt;
    private String thumbnail;
    private Role role;
    private int state;

}


