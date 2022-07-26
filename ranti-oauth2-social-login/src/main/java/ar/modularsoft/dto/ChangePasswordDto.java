package ar.modularsoft.dto;

import ar.modularsoft.validator.ChangePasswordMatches;
import ar.modularsoft.validator.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



/**
 * @author Modularsoft
 * @since 26/3/18
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ChangePasswordMatches
public class ChangePasswordDto {

	@NotEmpty
	private String email;

	@NotEmpty
	private String actualPassword;

	@Size(min = 6, message = "{Size.userDto.password}")
	private String password;

	@NotEmpty
	private String matchingPassword;

}
