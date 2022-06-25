package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ProviderInvoiceEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ProviderInvoiceReactive extends ReactiveSortingRepository<ProviderInvoiceEntity, String> {

    @Query("{$and:["
            + "?#{ [0] == null ? {_id : {$ne:null}} : { orderReference : {$regex:[0], $options: 'i'} } }"
            + "] }")
    Flux<ProviderInvoiceEntity> findByOrderReferenceNullSafe(String orderReference);

    Flux<ProviderInvoiceEntity> findByCreationDateBetween(LocalDateTime initDate, LocalDateTime finalDate);
}
