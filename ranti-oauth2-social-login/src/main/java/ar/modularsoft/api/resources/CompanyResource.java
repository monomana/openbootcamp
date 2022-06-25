package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.CompanyDto;
import ar.modularsoft.services.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@Api( tags = "Empresas")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
@RestController
@RequestMapping(CompanyResource.COMPANY)
public class CompanyResource {
    public static final String COMPANY = "/company";
    public static final String COMPANY_ID = "/{id}";
    public static final String CATEGORY_ID = "/category_id";
    public static final String COMPANY_PAGINATION = "/search-pagination";


    private final CompanyService companyService;

    @Autowired
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

   @ApiOperation(value = "Este metodo es usado para obtener todas las empresas")
   @PreAuthorize("permitAll()")
   // @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public Stream<CompanyDto> getAllCompany() {
        return this.companyService.readAll()
                .map(CompanyDto::new);
    }

    @ApiOperation(value = "Este metodo es usado para obtener una empresa de determinada categoria")
    @PreAuthorize("permitAll()")
    @GetMapping(CATEGORY_ID)
    public Stream<CompanyDto> getCompanyByCategory(@RequestParam(required = false) int categoryId) {
        return this.companyService.getCompanyByCategory(categoryId)
                .map(CompanyDto::new);
    }

    @ApiOperation(value = "Este metodo es usado para obtener empresas con paginacion")
    @PreAuthorize("permitAll()")
    @GetMapping(COMPANY_PAGINATION)
    public Page<CompanyDto>getCompanyByNameWithPagination(
            @RequestParam(defaultValue = "",required = false) String searchName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "companyName") String sortField,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        if(!asc)
            return  companyService.getCompanyByNameContains(searchName,
                    PageRequest.of(page, size, Sort.by(sortField).descending()))
                    .map(CompanyDto::new);
        return  companyService.getCompanyByNameContains(searchName, PageRequest.of(page, size, Sort.by(sortField)))
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

/*
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createCompany(@Valid @RequestBody CompanyDto creationCompanyDto) {
//        this.userService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
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
