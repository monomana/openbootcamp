package ar.mds.ranti_core.infrastructure.api.resources;
import ar.mds.ranti_core.domain.model.CustomerDiscount;
import ar.mds.ranti_core.domain.services.CustomerDiscountService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.CustomerDiscountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(CustomerDiscountResource.CUSTOMER_DISCOUNTS)
public class CustomerDiscountResource {

    public static final String CUSTOMER_DISCOUNTS = "/customer_discounts";
    public static final String MOBILE = "/{mobile}";

    private final CustomerDiscountService customerDiscountService;

    @Autowired
    public CustomerDiscountResource(CustomerDiscountService customerDiscountService){
        this.customerDiscountService  = customerDiscountService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<CustomerDiscountDto> create(@Valid @RequestBody CustomerDiscountDto customerDiscountDto) {
        return this.customerDiscountService.create(customerDiscountDto)
                .map(CustomerDiscountDto::new);
    }

    @GetMapping(MOBILE)
    public Mono<CustomerDiscountDto> read(@PathVariable String mobile){
        return this.customerDiscountService.read(mobile)
                .map(CustomerDiscountDto::new);
    }

    @DeleteMapping(MOBILE)
    public Mono<Void> delete(@PathVariable String mobile){
        return this.customerDiscountService.delete(mobile);
    }

    @PutMapping(MOBILE)
    public Mono<CustomerDiscountDto> update(@PathVariable String mobile, @Valid @RequestBody CustomerDiscount customerDiscount){
        return customerDiscountService.update(mobile, customerDiscount)
                .map(CustomerDiscountDto::new);
    }

    @GetMapping()
    public Flux<CustomerDiscountDto> getCustomerDiscounts(){
        return customerDiscountService.getCustomerDiscounts()
                .map(CustomerDiscountDto::new);
    }
}
