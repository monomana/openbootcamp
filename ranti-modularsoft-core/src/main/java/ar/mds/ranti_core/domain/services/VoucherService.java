package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.Voucher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class VoucherService {
    public Mono<Voucher> read(String reference) {
        return Mono.just(new Voucher(reference, BigDecimal.TEN, LocalDate.now(), null, null));
    }
}
