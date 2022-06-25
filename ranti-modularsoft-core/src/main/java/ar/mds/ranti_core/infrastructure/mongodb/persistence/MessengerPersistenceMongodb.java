package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.model.Messenger;
import ar.mds.ranti_core.domain.persistence.MessengerPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.MessengerReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.MessengerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MessengerPersistenceMongodb implements MessengerPersistence {

    private final MessengerReactive messengerReactive;

    @Autowired
    public MessengerPersistenceMongodb(MessengerReactive messengerReactive) {
        this.messengerReactive = messengerReactive;
    }

    @Override
    public Mono<Messenger> create(Messenger messenger) {
        MessengerEntity messengerEntity = new MessengerEntity(messenger);
        return this.messengerReactive.save(messengerEntity)
                .map(MessengerEntity::toMessenger);
    }

    @Override
    public Mono<Messenger> read(String id) {
        return this.messengerReactive.findById(id)
                .map(MessengerEntity::toMessenger);
    }
}
