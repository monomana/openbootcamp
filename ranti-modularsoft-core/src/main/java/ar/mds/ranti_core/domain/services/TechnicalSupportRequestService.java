package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.TechnicalSupportRequestPersistence;
import ar.mds.ranti_core.domain.model.TechnicalSupportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TechnicalSupportRequestService {

    private final TechnicalSupportRequestPersistence technicalSupportRequestPersistence;

    @Autowired
    public TechnicalSupportRequestService(TechnicalSupportRequestPersistence technicalSupportRequestPersistence){

        this.technicalSupportRequestPersistence = technicalSupportRequestPersistence;
    }

    public Flux<TechnicalSupportRequest> findByRequestNullSafe(String request) {
        return this.technicalSupportRequestPersistence.findByRequestTextNullSafe(request);
    }

    public Mono<TechnicalSupportRequest> read(String id) {
        return this.technicalSupportRequestPersistence.readByIdentifier(id);
    }

    public Mono<TechnicalSupportRequest> create(TechnicalSupportRequest technicalSupportRequest) {
        return this.technicalSupportRequestPersistence.create(technicalSupportRequest);
    }

    public Mono<TechnicalSupportRequest> update(String identifier, TechnicalSupportRequest technicalSupportRequest) {
        return this.technicalSupportRequestPersistence.update(identifier, technicalSupportRequest);
    }
}
