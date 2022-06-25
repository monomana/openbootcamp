package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.CreditLinePersistence;
import ar.mds.ranti_core.domain.model.CreditLine;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CreditLineService {

    private final UserMicroservice userMicroservice;
    private final CreditLinePersistence creditLinePersistence;

    @Autowired
    public CreditLineService(UserMicroservice userMicroservice, CreditLinePersistence creditLinePersistence) {
        this.userMicroservice = userMicroservice;
        this.creditLinePersistence = creditLinePersistence;
    }

    public Mono<CreditLine> create(String mobile) {
        CreditLine creditLine = new CreditLine();
        creditLine.setRegistrationDateTime(LocalDateTime.now());
        creditLine.setCreditSales(new ArrayList<>());
        creditLine.setUserMobile(mobile);

        return this.userMicroservice.readByMobile(mobile)
                .then(this.creditLinePersistence.create(creditLine));
    }

    public Mono<CreditLine> findByUserMobileNullSafe(String mobile) {
        return this.creditLinePersistence.findByUserMobileNullSafe(mobile);
    }

    public Flux<CreditLine> findCreditLinesNullSafe() {
        return this.creditLinePersistence.findCreditLinesNullSafe();
    }
}
