package ar.mds.ranti_core.infrastructure.api.resources;


import ar.mds.ranti_core.domain.model.CashMovement;
import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.model.CashierClose;
import ar.mds.ranti_core.domain.model.CashierState;
import ar.mds.ranti_core.domain.services.CashierService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.CashierClosuresDto;
import ar.mds.ranti_core.infrastructure.api.dtos.CashierLastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Rest
@RequestMapping(CashierResource.CASHIERS)
public class CashierResource {
    public static final String CASHIERS = "/cashiers";

    public static final String LAST = "/last";
    public static final String STATE = "/state";
    public static final String SEARCH = "/search";
    public static final String CASH_MOVEMENT = "/cash-movement";

    private final CashierService cashierService;

    @Autowired
    public CashierResource(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Void> createOpened() {
        return this.cashierService.createOpened();
    }

    @GetMapping(value = LAST)
    public Mono<CashierLastDto> findLast() {
        return this.cashierService.findLast()
                .map(CashierLastDto::new);
    }

    @GetMapping(value = LAST + STATE)
    public Mono<CashierState> findLastState() {
        return this.cashierService.findLastState();
    }

    @PatchMapping(value = LAST)
    public Mono<Cashier> closeCashier(@Valid @RequestBody CashierClose cashierClose) {
        return cashierService.close(cashierClose);
    }

    @GetMapping(SEARCH)
    public Mono<CashierClosuresDto> findByClosureDateBetween(@RequestParam String startDate,
                                                             @RequestParam String endDate) {
        return this.cashierService.findByClosureDateBetween(
                        LocalDateTime.ofInstant(Instant.parse(startDate), ZoneId.systemDefault()),
                        LocalDateTime.ofInstant(Instant.parse(endDate), ZoneId.systemDefault()))
                .collectList()
                .map(CashierClosuresDto::new);
    }

    @PatchMapping(value = CASH_MOVEMENT)
    public Mono<Cashier> addCashMovement(@Valid @RequestBody CashMovement cashMovement) {
        return cashierService.addCashMovement(cashMovement);
    }
}
