package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.persistence.CustomerDiscountPersistance;
import ar.mds.ranti_core.infrastructure.mongodb.daos.CustomerDiscountReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.CustomerDiscountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerDiscountPersistenceMongodb implements CustomerDiscountPersistance {

    private final CustomerDiscountReactive customerDiscountReactive;

    @Autowired
    public CustomerDiscountPersistenceMongodb(CustomerDiscountReactive customerDiscountReactive){
        this.customerDiscountReactive = customerDiscountReactive;
    }
    @Override
    public Mono<CustomerDiscount> create(CustomerDiscount customerDiscount) {
        CustomerDiscountEntity customerDiscountEntity = new CustomerDiscountEntity(customerDiscount);

        return this.customerDiscountReactive.save(customerDiscountEntity)
                .map(CustomerDiscountEntity:: toCustomerDiscount);
    }

    @Override
    public Mono<CustomerDiscount> read(String mobile) {
        return this.customerDiscountReactive.findByUserMobile(mobile)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent mobile: " + mobile)))
                .map(CustomerDiscountEntity::toCustomerDiscount);

    }

    @Override
    public Mono<Void> delete(String mobile) {
        return this.customerDiscountReactive.findByUserMobile(mobile)
               .switchIfEmpty(Mono.error(new NotFoundException("Non existent mobile: " + mobile)))
                .then(this.customerDiscountReactive.deleteByUserMobile(mobile));

    }

    @Override
    public Mono<CustomerDiscount> update(String mobile, CustomerDiscount customerDiscount) {

        return this.customerDiscountReactive.findByUserMobile(mobile)
                .switchIfEmpty(Mono
                        .error(new NotFoundException("Non existent customerDiscount: " + mobile)))
                .map(customerDiscountEntity ->{
                    BeanUtils.copyProperties(customerDiscount, customerDiscountEntity);
                    return customerDiscountEntity;
                })
                .flatMap(this.customerDiscountReactive::save)
                .map(CustomerDiscountEntity::toCustomerDiscount);
    }

    @Override
    public Flux<CustomerDiscount> getCustomerDiscounts() {
        return this.customerDiscountReactive.findAll()
                .switchIfEmpty(Mono
                        .error(new NotFoundException("Non existent customerDiscounts")))
                .map(CustomerDiscountEntity::toCustomerDiscount);
    }
}
