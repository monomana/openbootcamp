package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.UserDto;
import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.dto.*;
import ar.modularsoft.service.UserService;
import ar.modularsoft.services.UserServiceRanti;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "Usuarios")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String UPDATE_STATE = "/update-state";
    public static final String TOKEN = "/token";

    public static final String OAUTH2 = "/oauth2";
    public static final String MOBILE_ID = "/{mobile}";
    public static final String SEARCH = "/search";

    private final UserServiceRanti userServiceRanti;

    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();

    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    UserService userService;

    @Autowired
    public UserResource(UserServiceRanti userServiceRanti, ClientRegistrationRepository clientRegistrationRepository) {
        this.userServiceRanti = userServiceRanti;
        this.clientRegistrationRepository = clientRegistrationRepository;

    }

    @ApiOperation(value = "Este metodo es usado para el password de usuario")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(userService.changePassword(changePasswordDto));
    }

    @ApiOperation(value = "Este metodo permite cambiar los datos del usuario")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping()
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto){
        try {
            Optional<UserResponseDto> userResponseDto =userService.updateUser(userUpdateDto);
            if (!userResponseDto.isPresent()) {
                return new ResponseEntity<>(new ApiResponse(false, "user not found!"), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(userResponseDto);
        }catch (Error e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "user not found!"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Este metodo permite cambiar los datos del usuario")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = UPDATE_STATE)
    public ResponseEntity<?> updateState( @RequestParam String email,
                                          @RequestParam boolean state){
        try {
            return ResponseEntity.ok(userService.updateState(email,state));
        }catch (Error e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Error "+e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

/*
    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN+"s")
    public Optional<TokenDto> loginWhitMobile(@AuthenticationPrincipal User activeUser) {
        return userService.loginWithUserName(activeUser.getUsername())
                .map(TokenDto::new);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN+"-mobile")
    public Optional< TokenDto > loginFromMoblie(@AuthenticationPrincipal User activeUser) {
        LogManager.getLogger(activeUser.getUsername());
        System.out.println(activeUser.getUsername());

        return userService.loginWithUserName(activeUser.getUsername())
                .map(TokenDto::new);
    }
*/
    /*

@GetMapping(OAUTH2)
public String loginWhitOauth2(@AuthenticationPrincipal User activeUser) {
    Iterable<ClientRegistration> clientRegistrations = null;
    ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
            .as(Iterable.class);
    if (type != ResolvableType.NONE &&
            ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
        clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
    }

    clientRegistrations.forEach(registration ->
            oauth2AuthenticationUrls.put(registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
   // model.addAttribute("urls", oauth2AuthenticationUrls);
    return "oauth_login :: "+oauth2AuthenticationUrls;
}
*/
/*
    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Optional<UserResponseDto> loginWhitUserName(@AuthenticationPrincipal User activeUser) {
        LogManager.getLogger(activeUser.getUsername());
        System.out.println(activeUser.getUsername());
        return userServiceRanti.loginWithUserName(activeUser.getUsername());
                //.map(TokenDto::new);
    }
   // @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user")
    public Optional<UserResponseDto> user(@AuthenticationPrincipal OAuth2User principal) {
        return  userServiceRanti.loginWithUserName( principal.getAttribute("name"));
    }
    */


/*
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(MOBILE_ID)
    public UserDto readUser(@PathVariable String mobile) {
        return new UserDto(this.userService.readByMobileAssured(mobile));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream< UserDto > readAll() {
        return this.userService.readAll(this.extractRoleClaims())
                .map(UserDto::ofMobileFirstName);
    }

 */

    /*
    @PreAuthorize("hasRole('OPERATOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream< UserDto > readAllByTypeIva() {
        return this.userService.readAllByTypeIva("2")
                .map(UserDto::ofMobileFirstName);
    }

*/

/*
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = SEARCH)
    public Stream< UserDto > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            @RequestParam(required = false) String mobile, @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String familyName, @RequestParam(required = false) String email,
            @RequestParam(required = false) String dni) {
        return this.userService.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.extractRoleClaims()
        ).map(UserDto::ofMobileFirstName);
    }

 */

    private Role extractRoleClaims() {
        List< String > roleClaims = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Role.of(roleClaims.get(0));  // it must only be a role
    }

}
