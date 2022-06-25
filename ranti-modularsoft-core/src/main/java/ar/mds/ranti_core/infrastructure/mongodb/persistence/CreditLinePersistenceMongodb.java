package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.ConflictException;
import ar.mds.ranti_core.domain.model.CreditLine;
import ar.mds.ranti_core.domain.persistence.CreditLinePersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.CreditLineReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.CreditLineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CreditLinePersistenceMongodb implements CreditLinePersistence {

    private final CreditLineReactive creditLineReactive;

    @Autowired
    public CreditLinePersistenceMongodb(CreditLineReactive creditLineReactive) {
        this.creditLineReactive = creditLineReactive;
    }

    @Override
    public Mono<CreditLine> create(CreditLine creditLine) {
        CreditLineEntity creditLineEntity = new CreditLineEntity(creditLine);

        return this.assertMobileNotExist(creditLine.getUserMobile()).
                then(this.creditLineReactive.save(creditLineEntity)
                        .map(CreditLineEntity::toCreditLine));
    }

    @Override
    public Mono<CreditLine> findByUserMobileNullSafe(String mobile) {
        return this.creditLineReactive.findByMobile(mobile)
                .map(CreditLineEntity::toCreditLine);
    }

    @Override
    public Flux<CreditLine> findCreditLinesNullSafe() {
        return this.creditLineReactive.findAll()
                .map(CreditLineEntity::toCreditLine);
    }

    private Mono< Void > assertMobileNotExist(String mobile) {
        return this.creditLineReactive.findByMobile(mobile)
                .flatMap(creditLineEntity -> Mono.error(
                        new ConflictException("A credit line already exists for " + mobile)
                ));
    }
}
