package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.InvoiceTaxValues;
import ar.mds.ranti_core.domain.model.Shopping;
import ar.mds.ranti_core.domain.model.Ticket;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface TicketPersistence {
    Mono< Ticket > create(Ticket ticket);

    Mono< Ticket > readById(String id);

    Flux<InvoiceTaxValues> findTaxValues(String id);

    Flux<Shopping> findAllArticlesfromTicketsByPurchaseDate(LocalDateTime fromDate, LocalDateTime untilDate);

    Flux< Ticket > findTicketsPerBarcode(String barcode);
}
