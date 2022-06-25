package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.ProviderInvoice;
import ar.mds.ranti_core.domain.services.ProviderInvoiceService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.ProviderInvoiceOfYearDto;
import ar.mds.ranti_core.infrastructure.api.dtos.QuarterOfYearDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(ProviderInvoiceResource.PROVIDER_INVOICES)
public class ProviderInvoiceResource {
    public static final String PROVIDER_INVOICES = "/provider-invoices";

    public static final String SEARCH = "/search";
    public static final String IDENTITY = "/{identity}";
    public static final String CREATION_DATE = "/creation-date";
    public static final String YEARS = "/years";
    public static final String QUARTER_OF_YEAR = "/quarter-of-year";

    private final ProviderInvoiceService providerInvoiceService;

    @Autowired
    public ProviderInvoiceResource(ProviderInvoiceService providerInvoiceService) {
        this.providerInvoiceService = providerInvoiceService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<ProviderInvoice> create(@Valid @RequestBody ProviderInvoice providerInvoice) {
        return this.providerInvoiceService.create(providerInvoice);
    }

    @GetMapping(ProviderInvoiceResource.SEARCH)
    public Flux<ProviderInvoice> findByProviderCompanyAndOrderReferenceNullSafe(
            @RequestParam(required = false) String providerCompany,
            @RequestParam(required = false) String orderReference) {
        return this.providerInvoiceService
                .findByProviderCompanyAndOrderReferenceNullSafe(providerCompany, orderReference);
    }

    @GetMapping(ProviderInvoiceResource.IDENTITY)
    public Mono<ProviderInvoice> read(@PathVariable String identity) {
        return this.providerInvoiceService.read(identity);
    }

    @PutMapping(ProviderInvoiceResource.IDENTITY)
    public Mono<ProviderInvoice> update(@PathVariable String identity, @Valid @RequestBody ProviderInvoice providerInvoice) {
        return this.providerInvoiceService.update(identity, providerInvoice);
    }

    @GetMapping(ProviderInvoiceResource.CREATION_DATE + ProviderInvoiceResource.YEARS)
    public Mono<ProviderInvoiceOfYearDto> getYearsOfAllCreationDate() {
        return this.providerInvoiceService.getYearsOfAllCreationDate()
                .collectList()
                .map(ProviderInvoiceOfYearDto::new);
    }

    @GetMapping(ProviderInvoiceResource.QUARTER_OF_YEAR + ProviderInvoiceResource.SEARCH)
    public Mono<QuarterOfYearDto> getTotalForQuarterOfYear(@RequestParam Integer year, @RequestParam Integer quarter) {
        return this.providerInvoiceService.getTotalForQuarterOfYear(year, quarter);
    }
}
