package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.entities.MessengerEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface MessengerReactive extends ReactiveSortingRepository<MessengerEntity, String> {
}
