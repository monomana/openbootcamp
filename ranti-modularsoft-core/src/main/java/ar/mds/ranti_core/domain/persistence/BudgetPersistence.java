package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.Budget;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BudgetPersistence {
    Mono<Budget> create(Budget budget);
    Mono<Budget> readByReference(String reference);
}
