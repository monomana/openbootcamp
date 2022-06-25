package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CreditLineEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface CreditLineReactive extends ReactiveSortingRepository<CreditLineEntity, String> {
    Mono< CreditLineEntity > findByMobile(String mobile);
}
