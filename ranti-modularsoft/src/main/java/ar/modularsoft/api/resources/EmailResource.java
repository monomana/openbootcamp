package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.EmailBodyDto;
import ar.modularsoft.api.dtos.UserDto;
import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.data.model.EmailBody;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.services.EmailService;
import ar.modularsoft.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(EmailResource.EMAIL)
public class EmailResource {
    public static final String EMAIL = "/sendmail";
    private final EmailService emailService;

    @Autowired
    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }



    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createUser(@Valid @RequestBody EmailBody emailBody) {
    this.emailService.sendEmail(emailBody);
    }
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
