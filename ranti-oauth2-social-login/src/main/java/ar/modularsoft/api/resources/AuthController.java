package ar.modularsoft.api.resources;

import javax.validation.Valid;

import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.model.User;
import ar.modularsoft.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.modularsoft.dto.ApiResponse;
import ar.modularsoft.dto.JwtAuthenticationResponse;
import ar.modularsoft.dto.LocalUser;
import ar.modularsoft.dto.LoginRequest;
import ar.modularsoft.dto.SignUpRequest;
import ar.modularsoft.exception.UserAlreadyExistAuthenticationException;
import ar.modularsoft.security.jwt.TokenProvider;
import ar.modularsoft.util.GeneralUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Api( tags = "Auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
    UserService userService;

	@Autowired
	TokenProvider tokenProvider;


	@ApiOperation(value = "Este metodo es usado para autenticarse" )
	@PreAuthorize("permitAll()")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);
		LocalUser localUser = (LocalUser) authentication.getPrincipal();
		// userService.findUserByEmail(localUser.getEmail());
		return ResponseEntity.ok(userService.findUserByEmail(
				localUser.getUser().getEmail()).map(
				user ->
				{
					UserResponseDto userResponseDto= new UserResponseDto(user);
					userResponseDto.setToken(jwt);
					return userResponseDto;
				}));
		// return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
	}

	@ApiOperation(value = "Este metodo es usado para registar un usuario que no posea red social")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		UserResponseDto userResponseDto;
		try {

			User user=userService.registerNewUser(signUpRequest);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
			// SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.createToken(authentication);
		//	String refreshToken = tokenProvider.createRefreshToken();
			 userResponseDto= new UserResponseDto(user);
			userResponseDto.setToken(jwt);

		} catch (UserAlreadyExistAuthenticationException e) {
			log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(userResponseDto);
		// return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
	}
/*
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
						"Refresh token is not in database!"));
	}


 */
/*
{
		"nbf": 1655343616,
			"role": "USER",
			"iss": "ar-modularsoft-ranti",
			"name": "skip-jobs",
			"exp": 1655347216,
			"iat": 1655343616,
			"user": "testfullname@modularsoft.ar"
	}
 */

}