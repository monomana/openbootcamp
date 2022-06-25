package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.infrastructure.api.dtos.QuarterOfYearDto;
import ar.mds.ranti_core.domain.model.ProviderInvoice;
import ar.mds.ranti_core.domain.persistence.ProviderInvoicePersistence;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ProviderInvoiceService {

    private final ProviderInvoicePersistence providerInvoicePersistence;

    @Autowired
    public ProviderInvoiceService(ProviderInvoicePersistence providerInvoicePersistence) {
        this.providerInvoicePersistence = providerInvoicePersistence;
    }

    public Mono<ProviderInvoice> create(ProviderInvoice providerInvoice) {
        providerInvoice.setCreationDate(LocalDateTime.now());
        return this.providerInvoicePersistence.create(providerInvoice);
    }

    public Flux<ProviderInvoice> findByProviderCompanyAndOrderReferenceNullSafe(
            String providerCompany, String orderReference) {
        String providerCompanyRegex;
        if (providerCompany == null || providerCompany.isBlank()) {
            providerCompanyRegex = "";
        } else {
            providerCompanyRegex = ".*" + providerCompany + ".*";
        }
        return this.providerInvoicePersistence
                .findByProviderCompanyAndOrderReferenceNullSafe(providerCompanyRegex, orderReference);
    }

    public Mono<ProviderInvoice> read(String identity) {
        return this.providerInvoicePersistence.read(identity);
    }

    public Mono<ProviderInvoice> update(String identity, ProviderInvoice providerInvoice) {
        return this.providerInvoicePersistence.read(identity)
                .map(oldProviderInvoice -> {
                    BeanUtils.copyProperties(providerInvoice, oldProviderInvoice);
                    return oldProviderInvoice;
                }).flatMap(updatedProviderInvoice -> this.providerInvoicePersistence.update(identity, updatedProviderInvoice));
    }

    public Flux<Integer> getYearsOfAllCreationDate() {
        return this.providerInvoicePersistence.getYearsOfAllCreationDate()
                .distinct()
                .sort();
    }

    public Mono<QuarterOfYearDto> getTotalForQuarterOfYear(Integer year, Integer quarter) {
        LocalDateTime baseDate, initDate, finalDate;
        baseDate = LocalDateTime.of(year, 1, 1, 0, 0);
        initDate = baseDate.plusMonths(3L * (quarter - 1));
        finalDate = baseDate.plusMonths(3L * quarter).minusDays(1).plusHours(23).plusMinutes(59);

        QuarterOfYearDto quarterOfYearDto = new QuarterOfYearDto(year, "Q" + quarter, new BigDecimal(0), new BigDecimal(0));
        return this.providerInvoicePersistence.findByCreationDateBetween(initDate, finalDate)
                .map(ProviderInvoice::ofBaseTaxTextValue)
                .map(providerInvoice -> {
                    quarterOfYearDto.setTotalBaseTax(quarterOfYearDto.getTotalBaseTax().add(providerInvoice.getBaseTax()));
                    quarterOfYearDto.setTotalTaxValue(quarterOfYearDto.getTotalTaxValue().add(providerInvoice.getTextValue()));
                    return quarterOfYearDto;
                }).then(Mono.just(quarterOfYearDto));
    }
}
