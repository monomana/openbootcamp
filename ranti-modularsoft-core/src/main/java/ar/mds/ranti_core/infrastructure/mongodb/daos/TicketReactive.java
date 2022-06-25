package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.TicketEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface TicketReactive extends ReactiveSortingRepository<TicketEntity, String > {

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { creationDate : {$gte:[0]} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { creationDate : {$lt:[1]} } },"
            + "] }")
    Flux<TicketEntity> findTicketsByPurchaseDate(
            LocalDateTime fromDate, LocalDateTime untilDate);

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { shoppingEntityList : { $elemMatch : { description : {$regex:[0], $options: 'i'} } } } },"
            + "] }")
    Flux<TicketEntity> findTicketsByDescription(
            String barcode);

}
