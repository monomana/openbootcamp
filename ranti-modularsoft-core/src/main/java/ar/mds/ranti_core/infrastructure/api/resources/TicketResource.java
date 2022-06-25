package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.Ticket;
import ar.mds.ranti_core.domain.services.TicketService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductPredDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;

@Rest
@RequestMapping(TicketResource.TICKETS)
public class TicketResource {
    public static final String TICKETS = "/tickets";
    public static final String SEARCH = "/search";
    public static final String DATE = "/date";
    public static final String ID_ID = "/{id}";
    public static final String RECEIPT = "/receipt";

    private final TicketService ticketService;

    @Autowired
    public TicketResource(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono< Ticket > create(@Valid @RequestBody Ticket ticket) {
        return this.ticketService.create(ticket);
    }

    @GetMapping(value = ID_ID + RECEIPT, produces = {"application/pdf", "application/json"})
    public Mono< byte[] > readReceipt(@PathVariable String id) {
        return this.ticketService.readReceipt(id);
    }

    @GetMapping(SEARCH + DATE)
    public Flux<ProductDto> findAllArticlesfromTicketsByPurchaseDate(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false)  String untilDate){
        return this.ticketService.findAllArticlesfromTicketsByPurchaseDate(
                        fromDate == null ? null : LocalDate.parse(fromDate).atStartOfDay(),
                        untilDate == null ? null : LocalDate.parse(untilDate).plusDays(1).atStartOfDay())
                .map(ProductDto::new)
                .sort(Comparator.comparing(ProductDto::getBarcode));
    }

    @GetMapping(SEARCH)
    public Flux<ProductPredDto> stockPrediction(String barcode) {
        return this.ticketService.stockPrediction(barcode);
    }

}
