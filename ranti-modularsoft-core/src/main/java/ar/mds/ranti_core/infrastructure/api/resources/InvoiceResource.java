package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.services.InvoiceService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.InvoiceDto;
import ar.mds.ranti_core.infrastructure.api.dtos.TicketIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static java.lang.Integer.parseInt;

@Rest
@RequestMapping(InvoiceResource.INVOICES)
public class InvoiceResource {
    public static final String INVOICES = "/invoices";
    public static final String SEARCH = "/search";
    public static final String IDENTITY_ID = "/{identity}";
    public static final String RECEIPT = "/receipt";

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceResource(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<InvoiceDto> create(@Valid @RequestBody TicketIdDto ticketIdDto) {
        return this.invoiceService.create(ticketIdDto.getTicketId())
                .map(InvoiceDto::new);
    }

    @GetMapping(SEARCH)
    public Flux<InvoiceDto> findByTicketIdAndUserMobileNullSafe(
            @RequestParam(required = false) String ticketId, @RequestParam(required = false) String userMobile
    ) {
        return this.invoiceService.findByTicketIdAndUserMobileNullSafe(ticketId, userMobile)
                .map(InvoiceDto::ofIdentityTicketIdUserMobile);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(IDENTITY_ID)
    public Mono<InvoiceDto> read(@PathVariable String identity) {
        return this.invoiceService.read(parseInt(identity))
                .map(InvoiceDto::new);
    }

    @PutMapping(IDENTITY_ID)
    public Mono<InvoiceDto> update(@PathVariable String identity, @Valid @RequestBody InvoiceDto invoiceDto) {
        return this.invoiceService.update(Integer.parseInt(identity), invoiceDto.toInvoiceOnlyUser())
                .map(InvoiceDto::new);
    }

    @GetMapping(value = IDENTITY_ID + RECEIPT, produces = {"application/pdf", "application/json"})
    public Mono<byte[]> readReceipt(@PathVariable String identity) {
        return this.invoiceService.readReceipt(parseInt(identity));
    }
}
