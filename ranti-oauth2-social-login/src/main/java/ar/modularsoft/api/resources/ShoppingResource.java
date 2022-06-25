package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.CategoryDto;
import ar.modularsoft.api.dtos.CompanyDto;
import ar.modularsoft.api.dtos.ShoppingDto;
import ar.modularsoft.services.ShoppingService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Stream;

@Api( tags = "Shopping")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ShoppingResource.SHOPPING)
public class ShoppingResource {
    public static final String SHOPPING = "/shopping";
    public static final String SHOPPING_BY_USER_ID = "/{user_id}";
    public static final String SHOPPING_BY_USER_ID_AND_STATE = "/by-state";
    private final ShoppingService shoppingService;

    @Autowired
    public ShoppingResource(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createShopping(@Valid @RequestBody  ShoppingDto shoppingDto) {
        // shoppingDto.setCreatedAt(new Date());
        this.shoppingService.createShopping(shoppingDto.toShopping());
    }
  // @PreAuthorize("permitAll()")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public Stream<ShoppingDto> getAllOrders() {
        return this.shoppingService.readAll()
                .map(ShoppingDto::new);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(SHOPPING_BY_USER_ID_AND_STATE)
    public Page<ShoppingDto> getAllOrdersByUserIdAndState(@RequestParam(required = true) int userId,
                                                  @RequestParam(required = true) int state,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortField,
                                                  @RequestParam(defaultValue = "true") boolean asc) {
        if(!asc)
            return  shoppingService.getShoppingByUserIdAndState(userId,state,
                            PageRequest.of(page, size, Sort.by(sortField).descending()))
                    .map(ShoppingDto::new);
        return  shoppingService.getShoppingByUserIdAndState(userId,state, PageRequest.of(page, size, Sort.by(sortField)))
                .map(ShoppingDto::new);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(SHOPPING_BY_USER_ID)
    public Stream<ShoppingDto> getAllOrdersByUserId(@RequestParam(required = false) int userId) {
        return this.shoppingService.readAllByUserId(userId)
                .map(ShoppingDto::new);
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
