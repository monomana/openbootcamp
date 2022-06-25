package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.ProviderInvoice;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ProviderInvoicePersistence {

    Mono<ProviderInvoice> create(ProviderInvoice providerInvoice);

    Flux<ProviderInvoice> findByProviderCompanyAndOrderReferenceNullSafe(String providerCompany, String orderReference);

    Mono<ProviderInvoice> read(String identity);

    Mono<ProviderInvoice> update(String identity, ProviderInvoice providerInvoice);

    Flux<Integer> getYearsOfAllCreationDate();

    Flux<ProviderInvoice> findByCreationDateBetween(LocalDateTime initDate, LocalDateTime finalDate);
}
