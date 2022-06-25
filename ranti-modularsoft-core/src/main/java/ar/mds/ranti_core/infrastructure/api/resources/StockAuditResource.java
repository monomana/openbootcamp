package ar.mds.ranti_core.infrastructure.api.resources;


import ar.mds.ranti_core.domain.model.StockAudit;
import ar.mds.ranti_core.domain.services.StockAuditService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.StockAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Rest
@RequestMapping(StockAuditResource.STOCK_AUDIT)
public class StockAuditResource {
    public static final String STOCK_AUDIT = "/stock-audit";
    public static final String SEARCH = "/search";
    public static final String ID_ID = "/{id}";

    private final StockAuditService stockAuditService;

    @Autowired
    public StockAuditResource(StockAuditService stockAuditService) {
        this.stockAuditService = stockAuditService;
    }


    @PostMapping(produces = {"application/json"})
    public Mono<StockAudit> create() {
        StockAudit stockAudit = new StockAudit();
        return this.stockAuditService.create(stockAudit);
    }

    @GetMapping(SEARCH)
    public Flux<StockAuditDto> readAllStockAudit() {
        return this.stockAuditService.readAll()
                .map(StockAuditDto::ofIdCreationAndCloseDataAndLossvalue);
    }

    @GetMapping(ID_ID)
    public Mono<StockAudit> readById(@PathVariable String id) {
        return this.stockAuditService.read(id);
    }

}
