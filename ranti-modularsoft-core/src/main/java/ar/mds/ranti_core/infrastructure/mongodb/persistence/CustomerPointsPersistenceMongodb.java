package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.persistence.CustomerPointsPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.CustomerPointsReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.CustomerPointsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public class CustomerPointsPersistenceMongodb implements CustomerPointsPersistence {

    private final CustomerPointsReactive customerPointsReactive;

    @Autowired
    public CustomerPointsPersistenceMongodb(CustomerPointsReactive customerPointsReactive) {
        this.customerPointsReactive = customerPointsReactive;
    }

    @Override
    public Mono<CustomerPoints> create(CustomerPoints customerPoints) {
        CustomerPointsEntity customerPointsEntity = new CustomerPointsEntity(customerPoints);
        return this.customerPointsReactive.save(customerPointsEntity)
                .map(CustomerPointsEntity::toCustomerPoints);
    }

    @Override
    public Mono<CustomerPoints> read(String mobile) {
        return this.customerPointsReactive.findByUserMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent mobile: " + mobile)))
                .map(CustomerPointsEntity::toCustomerPoints);

    }

    @Override
    public Mono<CustomerPoints> findByMobileIfNotExistsReturnEmptyCustomerPoints(String mobile) {
        CustomerPoints customerPoints = CustomerPoints
                .builder()
                .id(0)
                .lastDate(LocalDate.now())
                .value(0)
                .user(User.builder().mobile("888").build())
                .build();
        return this.customerPointsReactive.findByUserMobile(mobile)
                .switchIfEmpty(Mono.just(new CustomerPointsEntity(customerPoints)))
                .map(CustomerPointsEntity::toCustomerPoints);

    }

    @Override
    public Mono<CustomerPoints> update(String mobile, CustomerPoints customerPoints) {
        return this.customerPointsReactive.findByUserMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent points for customer with mobile: " + mobile)))
                .map(gottenEntity -> gottenEntity.from(customerPoints))
                .flatMap(this.customerPointsReactive::save)
                .map(CustomerPointsEntity::toCustomerPoints);
    }

    @Override
    public Mono<CustomerPoints> findByMobileAndDate(String mobile, LocalDate dateOneYearBefore, LocalDate actualDate) {
        return this.customerPointsReactive.findByUserMobileAndDate(mobile, dateOneYearBefore, LocalDate.now())
                .switchIfEmpty(this.customerPointsReactive.findByUserMobile(mobile)
                        .switchIfEmpty(Mono.error(new NotFoundException("Non existent points for customer with mobile: " + mobile)))
                        .flatMap(gottenEntity -> {
                            gottenEntity.setValue(0);
                            return this.customerPointsReactive.save(gottenEntity);
                        })
                    )
                .map(CustomerPointsEntity::toCustomerPoints);
    }
}
