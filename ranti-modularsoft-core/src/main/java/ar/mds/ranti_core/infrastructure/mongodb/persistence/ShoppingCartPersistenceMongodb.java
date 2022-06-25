package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.ShoppingCart;
import ar.mds.ranti_core.domain.persistence.ShoppingCartPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ArticleReactive;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ShoppingCartReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.ShoppingCartEntity;
import ar.mds.ranti_core.infrastructure.mongodb.entities.ShoppingEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ShoppingCartPersistenceMongodb  implements ShoppingCartPersistence {
    private final ShoppingCartReactive shoppingCartReactive;
    private final ArticleReactive articleReactive;

    public ShoppingCartPersistenceMongodb(ShoppingCartReactive shoppingCartReactive,
                                          ArticleReactive articleReactive) {
        this.shoppingCartReactive = shoppingCartReactive;
        this.articleReactive = articleReactive;
    }

    @Override
    public Mono<ShoppingCart> findShoppingCardByMobile(String mobile) {
        return this.shoppingCartReactive.findFirstByMobile(mobile)
                .map(ShoppingCartEntity::toShoppingCart);
    }

    @Override
    public Mono<ShoppingCart> update(ShoppingCart cart) {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity(cart);
        return Flux.fromStream(cart.getShoppingItems().stream())
                .flatMap(shopping -> {
                    ShoppingEntity shoppingEntity = new ShoppingEntity(shopping);
                    return this.articleReactive.findByBarcode(shopping.getBarcode())
                            .switchIfEmpty(Mono.error(new NotFoundException("Article: " + shopping.getBarcode())))
                            .map(articleEntity -> {
                                shoppingEntity.setArticleEntity(articleEntity);
                                shoppingEntity.setDescription(articleEntity.getDescription());
                                return shoppingEntity;
                            });
                }).doOnNext(shoppingCartEntity::add)
                .then(this.shoppingCartReactive.save(shoppingCartEntity))
                .map(ShoppingCartEntity::toShoppingCart);
    }
}