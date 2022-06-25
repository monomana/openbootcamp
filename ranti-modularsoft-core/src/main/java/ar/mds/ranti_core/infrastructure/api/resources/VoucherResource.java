package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.Voucher;
import ar.mds.ranti_core.domain.services.VoucherService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Rest
@RequestMapping(VoucherResource.VOUCHERS)
public class VoucherResource {
    public static final String VOUCHERS = "/vouchers";
    public static final String REFERENCE = "/{reference}";

    private final VoucherService voucherService;

    @Autowired
    public VoucherResource(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @GetMapping(REFERENCE)
    public Mono<Voucher> read(@PathVariable String reference){
        return this.voucherService.read(reference);
    }

    @GetMapping()
    public Flux<Voucher> findByReferenceLike(@RequestParam() String reference){
        return Flux.empty();
    }

}
