package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.persistence.MessengerPersistence;
import ar.mds.ranti_core.domain.model.Messenger;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessengerService {

    private final UserMicroservice userMicroservice;
    private final MessengerPersistence messengerPersistence;

    @Autowired
    public MessengerService(MessengerPersistence messengerPersistence, UserMicroservice userMicroservice) {
        this.messengerPersistence = messengerPersistence;
        this.userMicroservice = userMicroservice;
    }

    public Mono<Messenger> create(Messenger messenger) {
            //verify receiver and addressee mobiles exists
            Mono< User > fromUserMono = this.userMicroservice.readByMobile(messenger.getFromUser());
            Mono< User > toUserMono = this.userMicroservice.readByMobile(messenger.getToUser());
            return Mono.when(fromUserMono, toUserMono)
                    .then(this.messengerPersistence.create(messenger));
    }

    public Mono<Messenger> read(String id) {
        return this.messengerPersistence.read(id).switchIfEmpty(Mono.error(new NotFoundException("Messenger: " + id)));
    }
}
