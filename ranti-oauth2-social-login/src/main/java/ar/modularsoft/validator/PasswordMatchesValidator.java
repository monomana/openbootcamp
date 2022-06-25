package ar.modularsoft.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ar.modularsoft.dto.ChangePasswordDto;
import ar.modularsoft.dto.SignUpRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest> {

	@Override
	public boolean isValid(final SignUpRequest user, final ConstraintValidatorContext context) {

		return user.getPassword().equals(user.getMatchingPassword());
	}

}
