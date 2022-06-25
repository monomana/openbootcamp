package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.ShoppingCartPersistence;
import ar.mds.ranti_core.domain.model.Shopping;
import ar.mds.ranti_core.domain.model.ShoppingCart;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ShoppingCartService {
    private final ShoppingCartPersistence shoppingCartPersistence;
    private final UserMicroservice userMicroservice;

    public ShoppingCartService(ShoppingCartPersistence shoppingCartPersistence,
                               UserMicroservice userMicroservice) {
        this.shoppingCartPersistence = shoppingCartPersistence;
        this.userMicroservice = userMicroservice;
    }

    public Mono<ShoppingCart> mineCart(String mobile) {
        // check if mobile exists
        userMicroservice.readByMobile(mobile);
        return shoppingCartPersistence.findShoppingCardByMobile(mobile);
    }

    public Mono<ShoppingCart> updateCart(ShoppingCart shoppingCart) {
        Mono<ShoppingCart> exists = mineCart(shoppingCart.getMobile());

        if (exists != null && shoppingCart.getId() == null) {
            throw new IllegalArgumentException("shopping cart is invalid");
        }

        StringBuilder builder = new StringBuilder();
        builder.append("update cart ")
            .append(shoppingCart.getId() == null ? "\n" : shoppingCart.getId() + "\n");
        for (Shopping shoppingItem : shoppingCart.getShoppingItems()) {
            builder.append(shoppingItem.getBarcode()).append(": ").append(shoppingItem.getAmount());
        }
        log.info(builder.toString());
        shoppingCart.setUpdateDate(LocalDateTime.now());
        return shoppingCartPersistence.update(shoppingCart);
    }
}
