package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.Invoice;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface InvoicePersistence {
    Mono<Integer> findLast();

    Mono<Invoice> create(Invoice invoice);

    Flux<Invoice> findByTicketIdAndUserMobileNullSafe(String ticketId, String userMobile);

    Mono<Invoice> readByIdentity(int identity);

    Mono<Invoice> update(int identity, Invoice invoice);
}
