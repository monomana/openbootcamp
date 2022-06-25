package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.ProviderInvoice;
import ar.mds.ranti_core.domain.persistence.ProviderInvoicePersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.OrderReactive;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ProviderInvoiceReactive;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ProviderReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.ProviderInvoiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class ProviderInvoicePersistenceMongodb implements ProviderInvoicePersistence {

    private final ProviderInvoiceReactive providerInvoiceReactive;
    private final ProviderReactive providerReactive;
    private final OrderReactive orderReactive;

    @Autowired
    public ProviderInvoicePersistenceMongodb(ProviderInvoiceReactive providerInvoiceReactive,
                                             ProviderReactive providerReactive, OrderReactive orderReactive) {
        this.providerInvoiceReactive = providerInvoiceReactive;
        this.providerReactive = providerReactive;
        this.orderReactive = orderReactive;
    }

    @Override
    public Mono<ProviderInvoice> create(ProviderInvoice providerInvoice) {
        ProviderInvoiceEntity providerInvoiceEntity = new ProviderInvoiceEntity();
        return this.providerReactive.findByCompany(providerInvoice.getProviderCompany())
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing company: " + providerInvoice.getProviderCompany())
                ))
                .map(providerEntity -> {
                    providerInvoiceEntity.setProviderInvoice(providerInvoice);
                    providerInvoiceEntity.setProviderEntity(providerEntity);
                    return providerInvoiceEntity;
                })
                .then(this.orderReactive.findByOrderReference(providerInvoice.getOrderReference()))
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Non existing order: " + providerInvoice.getOrderReference())
                ))
                .map(orderEntity -> {
                    providerInvoiceEntity.setOrderReference(orderEntity.getOrderReference());
                    return providerInvoiceEntity;
                })
                .flatMap(this.providerInvoiceReactive::save)
                .map(ProviderInvoiceEntity::toProviderInvoice);
    }

    @Override
    public Flux<ProviderInvoice> findByProviderCompanyAndOrderReferenceNullSafe(
            String providerCompanyRegex, String orderReference) {
        return this.providerInvoiceReactive.findByOrderReferenceNullSafe(orderReference)
                .filter(providerInvoiceEntity -> providerCompanyRegex.isBlank() ||
                        providerInvoiceEntity.getProviderEntity().getCompany().matches(providerCompanyRegex))
                .map(ProviderInvoiceEntity::toProviderInvoice);
    }

    @Override
    public Mono<ProviderInvoice> read(String identity) {
        return this.providerInvoiceReactive.findById(identity)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existing Provider Invoice: " + identity)))
                .map(ProviderInvoiceEntity::toProviderInvoice);
    }

    @Override
    public Mono<ProviderInvoice> update(String identity, ProviderInvoice providerInvoice) {
        Mono<ProviderInvoiceEntity> providerInvoiceEntityMono = this.providerInvoiceReactive.findById(identity);
        return providerInvoiceEntityMono
                .flatMap(providerInvoiceEntity -> {
                    providerInvoiceEntity.setProviderInvoice(providerInvoice);
                    return this.providerReactive.findByCompany(providerInvoice.getProviderCompany())
                            .switchIfEmpty(Mono.error(
                                    new NotFoundException("Non existing company: " + providerInvoice.getProviderCompany()))
                            )
                            .map(providerEntity -> {
                                providerInvoiceEntity.setProviderEntity(providerEntity);
                                return providerInvoiceEntity;
                            }).then(this.orderReactive.findByOrderReference(providerInvoice.getOrderReference()))
                            .switchIfEmpty(Mono.error(
                                    new NotFoundException("Non existing order: " + providerInvoice.getOrderReference()))
                            )
                            .map(orderEntity -> {
                                providerInvoiceEntity.setOrderReference(orderEntity.getOrderReference());
                                return providerInvoiceEntity;
                            });
                })
                .flatMap(this.providerInvoiceReactive::save)
                .map(ProviderInvoiceEntity::toProviderInvoice);
    }

    @Override
    public Flux<Integer> getYearsOfAllCreationDate() {
        return this.providerInvoiceReactive.findAll()
                .map(ProviderInvoiceEntity::getCreationDate)
                .map(LocalDateTime::getYear);
    }

    @Override
    public Flux<ProviderInvoice> findByCreationDateBetween(LocalDateTime initDate, LocalDateTime finalDate) {
        return this.providerInvoiceReactive.findByCreationDateBetween(initDate, finalDate)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Empty interval: from: " + initDate + "; to: " + finalDate))
                )
                .map(ProviderInvoiceEntity::toProviderInvoice);
    }
}
