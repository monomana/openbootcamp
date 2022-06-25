package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.ShoppingCart;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ShoppingCartPersistence {
    /**
     * Query someone's shopping cart, and shopping items in the
     * shopping cart
     *
     * @param mobile the user's mobile
     * @return the object of shopping cart
     */
    Mono<ShoppingCart> findShoppingCardByMobile(String mobile);

    /**
     * Create shopping cart for someone
     *
     * @param cart the object of the shopping cart
     * @return the new created shopping cart
     */
    Mono<ShoppingCart> update(ShoppingCart cart);
}
