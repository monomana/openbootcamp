package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ShoppingCartEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface ShoppingCartReactive extends ReactiveSortingRepository<ShoppingCartEntity, String> {
    Mono<ShoppingCartEntity> findFirstByMobile(String mobile);
}