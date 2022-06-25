package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.Messenger;
import ar.mds.ranti_core.domain.services.MessengerService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(MessengerResource.MESSENGERS)
public class MessengerResource {
    public static final String MESSENGERS = "/messengers";
    public static final String MESSENGERS_ID = "/{id}";

    private final MessengerService messengerService;

    @Autowired
    public MessengerResource(MessengerService messengerService) {
        this.messengerService = messengerService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Messenger> create(@Valid @RequestBody Messenger messenger) {
        return this.messengerService.create(messenger);
    }

    @GetMapping(MESSENGERS_ID)
    public Mono<Messenger> read(@PathVariable String id) {
        return this.messengerService.read(id);
    }
}
