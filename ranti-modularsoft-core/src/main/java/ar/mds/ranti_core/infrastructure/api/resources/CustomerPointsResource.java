package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.services.CustomerPointsService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;

@Rest
@RequestMapping(CustomerPointsResource.CUSTOMER_POINTS)
public class CustomerPointsResource {
    public static final String CUSTOMER_POINTS = "/customer-points";

    public static final String UPDATED = "/updated";
    public static final String MOBILE = "/{mobile}";

    private final CustomerPointsService customerPointsService;

    @Autowired
    public CustomerPointsResource(CustomerPointsService customerPointsService) {
        this.customerPointsService = customerPointsService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<CustomerPoints> create(@Valid @RequestBody CustomerPoints customerPoints) {
        return this.customerPointsService.create(customerPoints);
    }

    @GetMapping(MOBILE)
    public Mono<CustomerPoints> read(@PathVariable String mobile) {
        return this.customerPointsService.findByMobileIfNotExistsReturnEmptyCustomerPoints(mobile);
    }

    @PutMapping(MOBILE)
    public Mono<CustomerPoints> update(@PathVariable String mobile, @Valid @RequestBody CustomerPoints customerPoints) {
        return this.customerPointsService.update(mobile, customerPoints);
    }

    @GetMapping(MOBILE + UPDATED)
    public Mono<CustomerPoints> findByMobileDateAndUpdate(@PathVariable String mobile) {
        LocalDate dateOneYearBefore = LocalDate.now().minusYears(1);
        return this.customerPointsService.findByMobileDateAndUpdate(mobile, dateOneYearBefore);
    }
}
