package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.CreditLine;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CreditLinePersistence {

    Mono<CreditLine> create(CreditLine creditLine);

    Mono<CreditLine> findByUserMobileNullSafe(String mobile);

    Flux<CreditLine> findCreditLinesNullSafe();
}
