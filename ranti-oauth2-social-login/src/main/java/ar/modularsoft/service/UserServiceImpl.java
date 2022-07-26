package ar.modularsoft.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.dto.*;
import ar.modularsoft.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.modularsoft.exception.OAuth2AuthenticationProcessingException;
import ar.modularsoft.exception.UserAlreadyExistAuthenticationException;
import ar.modularsoft.model.Role;
import ar.modularsoft.model.User;
import ar.modularsoft.repo.RoleRepository;
import ar.modularsoft.repo.UserRepository;
import ar.modularsoft.security.oauth2.user.OAuth2UserInfo;
import ar.modularsoft.security.oauth2.user.OAuth2UserInfoFactory;
import ar.modularsoft.util.GeneralUtils;

/**
 * @author Modularsoft
 * @since 26/3/18
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedAt(now);
		user.setUpdatedAt(now);
		user = userRepository.save(user);
		userRepository.flush();
		return user;
	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		try {
			user.setUsername(formDTO.getDisplayName());
			user.setEmail(formDTO.getEmail());
			user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
			user.setFirstName(formDTO.getFirstName());
			user.setLastName(formDTO.getLastName());
			user.setAddress(formDTO.getAddress());
			user.setPhone(formDTO.getPhone());
			user.setDni(formDTO.getDni());
			user.setDni(formDTO.getDni());
			user.setThumbnail(formDTO.getThumbnail());
			final HashSet<Role> roles = new HashSet<Role>();
			roles.add(roleRepository.findByName(Role.ROLE_USER));
			user.setRoles(roles);
			user.setProvider(formDTO.getSocialProvider().getProviderType());
			user.setState(true);
			user.setProviderUserId(formDTO.getProviderUserId());
		}catch (Error e ){
			System.out.println("Error :: "+e);
		}

		return user;
	}

	@Override
	public Optional<User> findUserByEmail(final String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		Optional<User> userOptional=findUserByEmail(oAuth2UserInfo.getEmail());
		User user;
	//	User user = findUserByEmail(oAuth2UserInfo.getEmail()).get();
				//.orElseThrow(() -> new ResourceNotFoundException("User", "email", oAuth2UserInfo.getEmail()));
		// if (user != null ) {
		if(userOptional.isPresent()){
			user = userOptional.get();
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			 user = registerNewUser(userDetails);
		/*
		user= buildUser(userDetails);
			Date now = Calendar.getInstance().getTime();
			user.setCreatedDate(now);
			user.setModifiedDate(now);
		 */

		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	@Override
	public boolean changePassword(ChangePasswordDto changePasswordDto) {
		try {
			User user = findUserByEmail(changePasswordDto.getEmail()).get();
			boolean match = passwordEncoder.matches(changePasswordDto.getActualPassword(),user.getPassword());
			if (!match ) {
				return false;
			}
			user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
			userRepository.save(user);
			return true;
		}catch(Error e){
			return false;
		}

	}



	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {

		existingUser.setUsername(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	public Optional<UserResponseDto> updateUser(UserUpdateDto userUpdateDto) {
	//	User user = findUserByEmail(userUpdateDto.getEmail()).get();
	return	findUserByEmail(
				userUpdateDto.getEmail()).map(user -> {
					user=userUpdateDto.pasteFieldsToUpdate(user);
					return new UserResponseDto(userRepository.save(user));
				});
			//	UserResponseDto::new
	//);
//		user=userUpdateDto.pasteFieldsToUpdate(user);
//		return userRepository.save(user);
	}
	public String updateState(String email, boolean state) {
		String result ="" ;
		Optional<User> userOptional=findUserByEmail(email);
		if (!userOptional.isPresent()) {
			return "user not found";
		}
		User user= userOptional.get();
		Stream<Role> roles = user.getRoles().stream();
		if ((roles.anyMatch(rol -> rol.getName().contains("ADMIN")))) {
			return result="update don't allowed";
		}
		user.setState(state);
		userRepository.save(user);
		return "update OK";
	}
/*
	public Optional<UserResponseDto> updateState(String email, boolean state) {
		return	findUserByEmail(
				email).map(user -> {
			Stream<Role> roles = user.getRoles().stream();
			if ((roles.anyMatch(rol->rol.getName().contains("ADMIN")))) {
				System.out.println("ROLE ADMIN");
				return new UserResponseDto(user);
			}
			user.setState(state);
			return new UserResponseDto(userRepository.save(user));
		});
 */

//			User user = findUserByEmail(email).get();
//			user.setState(state);
//			return userRepository.save(user);
//	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}


}
