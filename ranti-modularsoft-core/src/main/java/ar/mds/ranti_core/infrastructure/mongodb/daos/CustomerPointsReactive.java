package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CustomerPointsEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CustomerPointsReactive extends ReactiveSortingRepository<CustomerPointsEntity, String> {
    
    Mono<CustomerPointsEntity> findByUserMobile(String userMobile);

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { userMobile : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { lastDate : {$gt:[1]} } },"
            + "?#{ [2] == null ? {_id : {$ne:null}} : { lastDate : {$lte:[2]} } },"
            + "] }")
    Mono<CustomerPointsEntity> findByUserMobileAndDate(String userMobile, LocalDate dateToCompare, LocalDate actualDate);
}
