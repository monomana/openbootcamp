package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.TechnicalSupportRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TechnicalSupportRequestPersistence {
    Flux<TechnicalSupportRequest> findByRequestTextNullSafe(String request);

    Mono<TechnicalSupportRequest> readByIdentifier(String id);

    Mono<TechnicalSupportRequest> create(TechnicalSupportRequest technicalSupportRequest);

    Mono<TechnicalSupportRequest> update(String identifier, TechnicalSupportRequest technicalSupportRequestReturned);
}
