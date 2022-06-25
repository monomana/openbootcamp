package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.ProductDto;
import ar.modularsoft.api.dtos.UserDto;
import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.services.ProductService;
import ar.modularsoft.services.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApiIgnore
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
@RestController
@RequestMapping(ProductResource.PRODUCTS)
public class ProductResource {
    public static final String PRODUCTS = "/products";

    public static final String TOKEN = "/token";
    public static final String MOBILE_ID = "/{mobile}";
    public static final String COMPANY_ID = "/company/{id}";
    public static final String SEARCH = "/search";

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    // @PreAuthorize("hasRole('OPERATOR')")
    @ApiOperation(value = "Este metodo es usado para obtener todos los articulos")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    public Stream< ProductDto > readAll() {
        return this.productService .readAllByCompanyName("")
                .map(ProductDto::new);
    }

    /*
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createUser(@Valid @RequestBody UserDto creationUserDto) {
//        this.productService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
    }
*/
    /*
    @GetMapping("/paginator")
    public ResponseEntity<Stream<Page<ProductDto>>> pages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String order,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        Stream<Page<ProductDto>> product = productService.pages(PageRequest.of(page, size, Sort.by(order))).map(ProductDto::new);
        if(!asc)
            product = productService.pages(PageRequest.of(page, size, Sort.by(order).descending())).map(ProductDto::new);
        return new ResponseEntity<Stream<Page<ProductDto>>>(product, HttpStatus.OK);
    }
    */

/*
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(MOBILE_ID)
    public UserDto readUser(@PathVariable String mobile) {
        return new UserDto(this.productService.readByMobileAssured(mobile));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream< UserDto > readAll() {
        return this.productService.readAll(this.extractRoleClaims())
                .map(UserDto::ofMobileFirstName);
    }

 */
    /*
    @PreAuthorize("hasRole('OPERATOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(COMPANY_ID)
    public Stream< ProductDto > readAllByCompanyId(@PathVariable Integer id) {
        return this.productService .readAllByCompanyId(id)
               .map(ProductDto::new);
    }

     */





/*
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = SEARCH)
    public Stream< UserDto > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            @RequestParam(required = false) String mobile, @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String familyName, @RequestParam(required = false) String email,
            @RequestParam(required = false) String dni) {
        return this.productService.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.extractRoleClaims()
        ).map(UserDto::ofMobileFirstName);
    }

 */



}
