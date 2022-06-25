package ar.mds.ranti_core.domain.services;


import ar.mds.ranti_core.domain.persistence.CustomerDiscountPersistance;
import ar.mds.ranti_core.infrastructure.api.dtos.CustomerDiscountDto;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class CustomerDiscountService {

    private final CustomerDiscountPersistance customerDiscountPersistance;
    private final UserMicroservice userMicroservice;


    @Autowired
    public CustomerDiscountService( CustomerDiscountPersistance customerDiscountPersistance,UserMicroservice userMicroservice) {
        this.customerDiscountPersistance = customerDiscountPersistance;
        this.userMicroservice = userMicroservice;
    }

    public Mono<CustomerDiscount> create(CustomerDiscountDto customerDiscountDto) {
        CustomerDiscount customerDiscount = copyPropertiesFromDto(customerDiscountDto);

        return this.userMicroservice.readByMobile(customerDiscount.getUser().getMobile())
                .then(this.customerDiscountPersistance.create(customerDiscount));

    }

    public Mono<CustomerDiscount> read(String mobile) {
        return this.customerDiscountPersistance.read(mobile);
    }

    public Mono<Void> delete(String mobile) {
        return this.customerDiscountPersistance.delete(mobile);
    }

    public Mono<CustomerDiscount> update(String mobile, CustomerDiscount customerDiscount) {
        return customerDiscountPersistance.update(mobile, customerDiscount);
    }

    public Flux<CustomerDiscount> getCustomerDiscounts() {
        return customerDiscountPersistance.getCustomerDiscounts();
    }

    CustomerDiscount copyPropertiesFromDto(CustomerDiscountDto customerDiscountDto){
        CustomerDiscount customerDiscount = new CustomerDiscount();
        customerDiscount.setUser(new User());
        customerDiscount.getUser().setMobile(customerDiscountDto.getUserMobile());
        customerDiscount.setRegistrationDate(LocalDate.now());
        customerDiscount.setDiscount(customerDiscountDto.getDiscount());
        customerDiscount.setNote(customerDiscountDto.getNote());
        customerDiscount.setMinimmumPurchase(customerDiscountDto.getMinimmumPurchase());
        return  customerDiscount;
    }
}
