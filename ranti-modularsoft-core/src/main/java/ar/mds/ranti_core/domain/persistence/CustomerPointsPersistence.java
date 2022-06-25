package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.CustomerPoints;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface CustomerPointsPersistence {

    Mono<CustomerPoints> create(CustomerPoints customerPoints);

    Mono<CustomerPoints> read(String mobile);

    Mono<CustomerPoints> findByMobileIfNotExistsReturnEmptyCustomerPoints(String mobile);

    Mono<CustomerPoints> update(String mobile, CustomerPoints customerPoints);

    Mono<CustomerPoints> findByMobileAndDate(String mobile, LocalDate dateOneYearBefore, LocalDate actualDate);
}
