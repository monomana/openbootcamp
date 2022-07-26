package ar.modularsoft.dto;

import java.util.Date;
import java.util.List;


import ar.modularsoft.model.User;
import lombok.*;
import org.springframework.beans.BeanUtils;


//@Value
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserInfo {
	private Long userid;
	private String username;
	private String firstName;
	private String lastName;
	private String dni;
	private String address;
	private String phone;
	private String provider;
	private Date createdAt;
	private Date updatedAt;
	private String thumbnail;
	private boolean state;
	private List<String> roles;

	//public UserInfo(){}
	public UserInfo toUserInfo(User user, List<String> roles) {

		BeanUtils.copyProperties(user, this);
		this.roles=roles;
		this.userid=user.getId();
		// company.setPassword(new BCryptPasswordEncoder().encode(this.password));
		return this;
	}
}