package ar.mds.ranti_core.domain.persistence;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderPersistence {
    Flux<String> findByOrderReferenceNullSafe(String orderReference);
}
