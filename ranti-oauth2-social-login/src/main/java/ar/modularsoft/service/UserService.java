package ar.modularsoft.service;

import java.util.Map;
import java.util.Optional;

import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.dto.ChangePasswordDto;
import ar.modularsoft.dto.UserUpdateDto;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import ar.modularsoft.dto.LocalUser;
import ar.modularsoft.dto.SignUpRequest;
import ar.modularsoft.exception.UserAlreadyExistAuthenticationException;
import ar.modularsoft.model.User;

/**
 * @author Modularsoft
 * @since 26/3/18
 */

public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	Optional<User> findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	boolean changePassword(ChangePasswordDto changePasswordDto);

	Optional<UserResponseDto> updateUser(UserUpdateDto userUpdateDto);

	String updateState(String email, boolean state);

}
