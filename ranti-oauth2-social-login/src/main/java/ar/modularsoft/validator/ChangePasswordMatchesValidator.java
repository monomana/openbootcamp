package ar.modularsoft.validator;

import ar.modularsoft.dto.ChangePasswordDto;
import ar.modularsoft.dto.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangePasswordMatchesValidator implements ConstraintValidator<ChangePasswordMatches, ChangePasswordDto> {

	@Override
	public boolean isValid(final ChangePasswordDto user, final ConstraintValidatorContext context) {

		return user.getPassword().equals(user.getMatchingPassword());
	}

}
