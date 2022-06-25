package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.services.CreditLineService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.CreditLineDto;
import ar.mds.ranti_core.infrastructure.api.dtos.CreditLineMobileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(CreditLineResource.CREDIT_LINES)
public class CreditLineResource {
    public static final String CREDIT_LINES = "/credit-lines";
    public static final String MOBILE = "/{mobile}";

    private final CreditLineService creditLineService;

    @Autowired
    public CreditLineResource(CreditLineService creditLineService) {
        this.creditLineService = creditLineService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<CreditLineDto> create(@Valid @RequestBody CreditLineMobileDto creditLineMobileDto) {
        return this.creditLineService.create(creditLineMobileDto.getMobile())
                .map(CreditLineDto::new);
    }

    @GetMapping(MOBILE)
    public Mono<CreditLineDto> findByUserMobileNullSafe(@PathVariable String mobile) {
        return this.creditLineService.findByUserMobileNullSafe(mobile)
                .map(CreditLineDto::fromCreditLine);
    }

    @GetMapping
    public Flux<CreditLineDto> findCreditLinesNullSafe() {
        return this.creditLineService.findCreditLinesNullSafe()
                .map(CreditLineDto::fromCreditLine);
    }
}
