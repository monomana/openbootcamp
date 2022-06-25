package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.TechnicalSupportRequest;
import ar.mds.ranti_core.domain.services.TechnicalSupportRequestService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(TechnicalSupportResource.TECHNICAL_SUPPORT_REQUESTS)
public class TechnicalSupportResource {
    public static final String TECHNICAL_SUPPORT_REQUESTS = "/technical-support-requests";
    public static final String SEARCH = "/search";
    public static final String REQUEST_ID = "/{id}";

    private final TechnicalSupportRequestService technicalSupportRequestService;

    @Autowired
    public TechnicalSupportResource(TechnicalSupportRequestService technicalSupportRequestService){
        this.technicalSupportRequestService = technicalSupportRequestService;
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PostMapping(produces = {"application/json"})
    public Mono<TechnicalSupportRequest> create(@Valid @RequestBody TechnicalSupportRequest technicalSupportRequest) {
        return this.technicalSupportRequestService.create(technicalSupportRequest);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Flux<TechnicalSupportRequest> findByRequestNullSafe(
            @RequestParam(required = false) String request) {

        return this.technicalSupportRequestService.findByRequestNullSafe(
                request)
                .map(TechnicalSupportRequest::ofRequestText);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(REQUEST_ID)
    public Mono<TechnicalSupportRequest> read(@PathVariable String id) {
        return this.technicalSupportRequestService.read(id);
    }

    @PutMapping(REQUEST_ID)
    public Mono<TechnicalSupportRequest> update(@PathVariable String id, @Valid @RequestBody TechnicalSupportRequest technicalSupportRequest) {
        return this.technicalSupportRequestService.update(id, technicalSupportRequest);
    }
}
