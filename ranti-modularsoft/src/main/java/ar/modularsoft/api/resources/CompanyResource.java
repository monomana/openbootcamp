package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.CompanyDto;
import ar.modularsoft.api.dtos.UserDto;
import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.services.CompanyService;
import ar.modularsoft.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(CompanyResource.COMPANY)
public class CompanyResource {
    public static final String COMPANY = "/company";
    public static final String COMPANY_ID = "/{id}";
    public static final String CATEGORY_ID = "/category_id";


    private final CompanyService companyService;

    @Autowired
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

   @PreAuthorize("permitAll()")
   // @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public Stream<CompanyDto> getAllCompany() {
        return this.companyService.readAll()
                .map(CompanyDto::new);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(CATEGORY_ID)
    public Stream<CompanyDto> getCompanyByCategory(@RequestParam(required = false) int categoryId) {
        return this.companyService.getCompanyByCategory(categoryId)
                .map(CompanyDto::new);
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


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createCompany(@Valid @RequestBody CompanyDto creationCompanyDto) {
//        this.userService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
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



}
