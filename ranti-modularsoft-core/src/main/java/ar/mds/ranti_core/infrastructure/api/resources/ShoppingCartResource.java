package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.ShoppingCart;
import ar.mds.ranti_core.domain.services.ShoppingCartService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Rest
@RequestMapping(ShoppingCartResource.SHOPPING_CART)
public class ShoppingCartResource {
    public static final String SHOPPING_CART = "/shopping-cart";

    public static final String USER_ID = "/{mobile}";
    public static final String CART_UPDATE = "/";

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = USER_ID)
    public Mono<ShoppingCart> queryShoppingCart(@Valid @NotBlank @PathVariable String mobile) {
        return this.shoppingCartService.mineCart(mobile);
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = CART_UPDATE)
    public Mono<ShoppingCart> createUpdateShoppingCart(@Valid @RequestBody ShoppingCart cart) {
        return this.shoppingCartService.updateCart(cart);
    }
}