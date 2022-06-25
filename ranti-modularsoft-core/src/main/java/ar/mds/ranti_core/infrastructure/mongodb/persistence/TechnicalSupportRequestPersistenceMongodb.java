package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.TechnicalSupportRequest;
import ar.mds.ranti_core.domain.persistence.TechnicalSupportRequestPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.TechnicalSupportRequestReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.TechnicalSupportAnswerEntity;
import ar.mds.ranti_core.infrastructure.mongodb.entities.TechnicalSupportRequestEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class TechnicalSupportRequestPersistenceMongodb implements TechnicalSupportRequestPersistence {

    private final TechnicalSupportRequestReactive technicalSupportRequestReactive;

    @Autowired
    public TechnicalSupportRequestPersistenceMongodb(TechnicalSupportRequestReactive technicalSupportRequestReactive) {
        this.technicalSupportRequestReactive = technicalSupportRequestReactive;
    }

    @Override
    public Mono<TechnicalSupportRequest> create(TechnicalSupportRequest technicalSupportRequest) {
        TechnicalSupportRequestEntity technicalSupportRequestEntity = new TechnicalSupportRequestEntity(technicalSupportRequest);
        return this.technicalSupportRequestReactive.save(technicalSupportRequestEntity)
                .map(TechnicalSupportRequestEntity::toTechnicalSupportRequest);
    }

    @Override
    public Mono<TechnicalSupportRequest> update(String identifier, TechnicalSupportRequest technicalSupportRequest) {
        return this.technicalSupportRequestReactive.findByIdentifier(identifier)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent request")))
                .map(technicalSupportRequestEntity -> {
                    BeanUtils.copyProperties(technicalSupportRequest, technicalSupportRequestEntity);
                    if (Objects.nonNull(technicalSupportRequest.getAnswers())) {
                        technicalSupportRequestEntity.setAnswers(technicalSupportRequest.getAnswers().stream()
                                .map(technicalSupportAnswer -> {
                                    TechnicalSupportAnswerEntity newAnswerEntity = new TechnicalSupportAnswerEntity();
                                    newAnswerEntity.setAnswer(technicalSupportAnswer.getAnswer());
                                    newAnswerEntity.setDateSent(technicalSupportAnswer.getDateSent());
                                    return newAnswerEntity;
                                })
                                .collect(Collectors.toList())
                        );
                    }
                    return technicalSupportRequestEntity;
                })
                .flatMap(this.technicalSupportRequestReactive::save)
                .map(TechnicalSupportRequestEntity::toTechnicalSupportRequest);
    }

    @Override
    public Flux<TechnicalSupportRequest> findByRequestTextNullSafe(String request) {
        return this.technicalSupportRequestReactive.findByRequestTextNullSafe(request)
                .map(TechnicalSupportRequestEntity::toTechnicalSupportRequest);
    }

    @Override
    public Mono<TechnicalSupportRequest> readByIdentifier(String id) {
        return this.technicalSupportRequestReactive.findByIdentifier(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent request")))
                .map(TechnicalSupportRequestEntity::toTechnicalSupportRequest);
    }
}
