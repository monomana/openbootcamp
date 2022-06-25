package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Role;
import ar.modularsoft.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = Validations.NINE_DIGITS)
    private String mobile;
    private Long userid;
    @NotNull
    @NotBlank
    private String firstName;
    private String lastName;

    private String email;
    private String dni;
    private String address;
    private String phone;
   // private String password;
    private Role role;
    private Boolean active;
    private LocalDateTime registrationDate;
    private String token;
    private String thumbnail;

    // static  final String  THUMB = "https://concepto.de/wp-content/uploads/2015/03/paisaje-800x409.jpgº";

    public UserResponseDto(User user) {

        BeanUtils.copyProperties(user, this);
        this.userid=user.getId();

      //  this.password = "secret";
    }
    public static UserResponseDto ofMobileFirstName(User user) {
        return UserResponseDto.builder()
                .userid(user.getId())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .thumbnail(user.getThumbnail())
                .build();
    }

//    public static UserDto ofMobileFirstName(User user) {
//        return UserDto.builder().mobile(user.getMobile()).firstName(user.getFirstName()).build();
//    }

    public void doDefault() {
      /*  if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        } */
        if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }

    public User toUser() {
        this.doDefault();
        User user = new User();
        BeanUtils.copyProperties(this, user);
      //  user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return user;
    }
}
