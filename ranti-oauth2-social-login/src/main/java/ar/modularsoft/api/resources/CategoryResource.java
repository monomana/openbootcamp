package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.CategoryDto;
import ar.modularsoft.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@Api( tags = "Categorias")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
@RestController
@RequestMapping(CategoryResource.CATEGORY)
public class CategoryResource {
    public static final String CATEGORY = "/category";
    public static final String CATEGORY_ID = "/{id}";


    private final CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Este metodo es usado para obtner las categorias")
   @PreAuthorize("permitAll()")
   // @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public Stream<CategoryDto> getAllCategory() {
        return this.categoryService.readAll()
                .map(CategoryDto::new);
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
    public void createCategory(@Valid @RequestBody CategoryDto creationCategoryDto) {
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
