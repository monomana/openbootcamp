package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.CustomerPointsPersistence;
import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class CustomerPointsService {

    private final CustomerPointsPersistence customerPointsPersistence;
    private final UserMicroservice userMicroservice;

    @Autowired
    public CustomerPointsService(CustomerPointsPersistence customerPointsPersistence, UserMicroservice userMicroservice) {
        this.customerPointsPersistence = customerPointsPersistence;
        this.userMicroservice = userMicroservice;
    }

    public Mono<CustomerPoints> create(CustomerPoints customerPoints) {
        return this.userMicroservice.readByMobile(customerPoints.getUser().getMobile())
                .then(customerPointsPersistence.create(customerPoints));
    }

    public Mono<CustomerPoints> read(String mobile) {
        return this.customerPointsPersistence.read(mobile);
    }

    public Mono<CustomerPoints> findByMobileIfNotExistsReturnEmptyCustomerPoints(String mobile) {
        return this.customerPointsPersistence.findByMobileIfNotExistsReturnEmptyCustomerPoints(mobile);
    }

    public Mono<CustomerPoints> update(String mobile, CustomerPoints customerPoints) {
        return this.customerPointsPersistence.read(mobile)
                .map(gotten -> {
                    BeanUtils.copyProperties(customerPoints, gotten);
                    return gotten;
                }).flatMap(gotten -> this.customerPointsPersistence.update(mobile, gotten));
    }

    public Mono<CustomerPoints> findByMobileDateAndUpdate(String mobile, LocalDate dateOneYearBefore) {
        return this.customerPointsPersistence.findByMobileAndDate(mobile, dateOneYearBefore, LocalDate.now());
    }
}