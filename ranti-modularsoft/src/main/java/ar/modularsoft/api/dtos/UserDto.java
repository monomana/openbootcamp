package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.User;
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
public class UserDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = Validations.NINE_DIGITS)
    private String mobile;
    private String firstName;
    private String lastName;
    private String email;
    private String dni;
    private String address;
    private String phone;
    private String username;
    private String password;
    private Role role;
    private Boolean active;
    private LocalDateTime registrationDate;

    private String token;

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
        this.password = "secret";
    }

   public static UserDto ofMobileFirstName(User user) {
        return UserDto.builder().firstName(user.getFirstName()).lastName(user.getLastName()).build();
    }



    public void doDefault() {
        if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
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
        user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return user;
    }
}
