package ar.modularsoft.dto;

import ar.modularsoft.data.model.Company;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
//    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private String address;
    private String phone;
    private String thumbnail;

    public User toUser() {
//        this.doDefault();
        User user = new User();
        BeanUtils.copyProperties(this, user);
        // company.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return user;
    }
    public User pasteFieldsToUpdate(User user) {
        BeanUtils.copyProperties(this, user);
        // company.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return user;
    }
}
