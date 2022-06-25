package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.Messenger;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MessengerPersistence {
    Mono<Messenger> create(Messenger messenger);

    Mono<Messenger> read(String id);
}
