package ar.modularsoft.api.resources;

import javax.validation.Valid;

import ar.modularsoft.api.dtos.TokenDto;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.modularsoft.dto.ApiResponse;
import ar.modularsoft.dto.JwtAuthenticationResponse;
import ar.modularsoft.dto.LocalUser;
import ar.modularsoft.dto.LoginRequest;
import ar.modularsoft.dto.SignUpRequest;
import ar.modularsoft.exception.UserAlreadyExistAuthenticationException;
import ar.modularsoft.security.jwt.TokenProvider;
import ar.modularsoft.util.GeneralUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

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
					String jwtRefreshToken = tokenProvider.createRefreshToken(user);
					userResponseDto.setRefreshToken(jwtRefreshToken);
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
			String jwtRefreshToken = tokenProvider.createRefreshToken(user);
			 userResponseDto= new UserResponseDto(user);
			userResponseDto.setToken(jwt);
			userResponseDto.setRefreshToken(jwtRefreshToken);


		} catch (UserAlreadyExistAuthenticationException e) {
			log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(userResponseDto);
		// return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
	}
	@ApiOperation(value = "Este metodo es usado para obtener un nuevo token")
	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenDto tokenRefreshRequest) {
		UserResponseDto userResponseDto = null;
		User user = null;

		try{
		if (!tokenProvider.validateToken(tokenRefreshRequest.getToken())) {
			log.error("Exception Ocurred", "Token Expired!");
			return new ResponseEntity<>(new ApiResponse(false, "Token Expired!"), HttpStatus.UNAUTHORIZED);
		}
		String email = tokenProvider.getUserEmailFromToken(tokenRefreshRequest.getToken());
		user = userService.findUserByEmail(email).get();
			String jwtToken = tokenProvider.createToken(user);
			String jwtRefreshToken = tokenProvider.createRefreshToken(user);
			tokenRefreshRequest.setToken(jwtToken);
			tokenRefreshRequest.setRefreshToken(jwtRefreshToken);
			//	String refreshToken = tokenProvider.createRefreshToken();
			userResponseDto= new UserResponseDto(user);
			userResponseDto.setToken(jwtToken);
			userResponseDto.setRefreshToken(jwtRefreshToken);
		System.err.println(user.getEmail());
		// String jwt = tokenProvider.createToken(authentication);

	} catch (Error e) {
		log.error("Exception Ocurred", e);
		return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
	}
		return ResponseEntity.ok(tokenRefreshRequest);
		// return ResponseEntity.ok(userResponseDto);



		// String requestRefreshToken = request.getRefreshToken();
	/*	return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
						"Refresh token is not in database!"));

	 */
	}

	@ApiOperation(value = "Este metodo es usado para autenticarse desde la web" )
	@PreAuthorize("permitAll()")
	@PostMapping("/signin-web")
	public ResponseEntity<?> authenticateUserForWeb(@Valid LoginRequest loginRequest) {
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
					String jwtRefreshToken = tokenProvider.createRefreshToken(user);
					userResponseDto.setRefreshToken(jwtRefreshToken);
					return userResponseDto;
				}));
		// return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
	}

	@ApiOperation(value = "Este metodo es usado para registar un usuario que no posea red social desde la web")
	@PostMapping("/signup-web")
	public ResponseEntity<?> registerUserForWeb(@Valid SignUpRequest signUpRequest) {
		UserResponseDto userResponseDto;
		try {
			User user=userService.registerNewUser(signUpRequest);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
			// SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.createToken(authentication);
			String jwtRefreshToken = tokenProvider.createRefreshToken(user);
			userResponseDto= new UserResponseDto(user);
			userResponseDto.setToken(jwt);
			userResponseDto.setRefreshToken(jwtRefreshToken);


		} catch (UserAlreadyExistAuthenticationException e) {
			log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(userResponseDto);
		// return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
	}

	@ApiOperation(value = "Este metodo es usado para obtener un nuevo token desde la web")
	@PostMapping("/refresh-token-web")
	public ResponseEntity<?> refreshtokenForWeb(@Valid TokenDto tokenRefreshRequest) {
		UserResponseDto userResponseDto = null;
		User user = null;

		try{
			if (!tokenProvider.validateToken(tokenRefreshRequest.getToken())) {
				log.error("Exception Ocurred", "Token Expired!");
				return new ResponseEntity<>(new ApiResponse(false, "Token Expired!"), HttpStatus.UNAUTHORIZED);
			}
			String email = tokenProvider.getUserEmailFromToken(tokenRefreshRequest.getToken());
			user = userService.findUserByEmail(email).get();
			String jwtToken = tokenProvider.createToken(user);
			String jwtRefreshToken = tokenProvider.createRefreshToken(user);
			tokenRefreshRequest.setToken(jwtToken);
			tokenRefreshRequest.setRefreshToken(jwtRefreshToken);
			//	String refreshToken = tokenProvider.createRefreshToken();
			userResponseDto= new UserResponseDto(user);
			userResponseDto.setToken(jwtToken);
			userResponseDto.setRefreshToken(jwtRefreshToken);
			System.err.println(user.getEmail());
			// String jwt = tokenProvider.createToken(authentication);

		} catch (Error e) {
			log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(tokenRefreshRequest);
	}
	private Role extractRoleClaims() {
		List< String > roleClaims = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		return Role.of(roleClaims.get(0));  // it must only be a role
	}

/*
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody String tokenRefreshRequest) {

		User user = null;
		//if (tokenProvider.validateToken(tokenRefreshRequest)) {
			String email = tokenProvider.getUserEmailFromToken(tokenRefreshRequest);
			user = userService.findUserByEmail(email).get();

			System.err.println(user.getEmail());
			// String jwt = tokenProvider.createToken(authentication);
		// }

		return ResponseEntity.ok(user);

		// String requestRefreshToken = request.getRefreshToken();
	/*	return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
						"Refresh token is not in database!"));

	 */
//	}



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