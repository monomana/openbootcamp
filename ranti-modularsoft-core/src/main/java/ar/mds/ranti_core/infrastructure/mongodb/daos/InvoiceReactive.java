package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.InvoiceEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InvoiceReactive extends ReactiveSortingRepository<InvoiceEntity, String> {
    Mono<InvoiceEntity> findFirstByOrderByCreationDateDesc();

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { ticketId : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { userMobile : {$regex:[1], $options: 'i'} } },"
            + "] }")
    Flux<InvoiceEntity> findByTicketIdAndUserMobileAndOrderByCreationDateDescNullSafe(
            String ticketId, String userMobile
    );

    Mono<InvoiceEntity> findByIdentity(int identity);

    Flux<InvoiceEntity> findByTicketId(String ticketId);
}
