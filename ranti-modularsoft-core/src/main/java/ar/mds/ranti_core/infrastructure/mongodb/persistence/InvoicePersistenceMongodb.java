package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.ConflictException;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.Invoice;
import ar.mds.ranti_core.domain.persistence.InvoicePersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.InvoiceReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.InvoiceEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class InvoicePersistenceMongodb implements InvoicePersistence {

    private final InvoiceReactive invoiceReactive;

    @Autowired
    public InvoicePersistenceMongodb(InvoiceReactive invoiceReactive) {
        this.invoiceReactive = invoiceReactive;
    }

    @Override
    public Mono<Integer> findLast() {
        return this.invoiceReactive.findFirstByOrderByCreationDateDesc()
                .map(InvoiceEntity::getIdentity);
    }

    @Override
    public Mono<Invoice> create(Invoice invoice) {
        InvoiceEntity invoiceEntity = new InvoiceEntity(invoice);
        return this.assertPositiveInvoiceByTicketIdNotExist(invoiceEntity.getTicketId())
                .then(this.invoiceReactive.save(invoiceEntity)
                        .map(InvoiceEntity::toInvoice));
    }

    private Mono<Void> assertPositiveInvoiceByTicketIdNotExist(String ticketID) {
        return this.invoiceReactive.findByTicketId(ticketID)
                .filter(invoiceEntity -> invoiceEntity.getBaseTax().signum() > 0)
                .next().flatMap(invoiceEntity -> Mono.error(
                        new ConflictException("Invoice with Ticket Id (" + ticketID + ") already exist")
                ))
                .then();
    }

    @Override
    public Flux<Invoice> findByTicketIdAndUserMobileNullSafe(String ticketId, String userMobile) {
        return this.invoiceReactive.findByTicketIdAndUserMobileAndOrderByCreationDateDescNullSafe(ticketId, userMobile)
                .map(InvoiceEntity::toInvoice);
    }

    @Override
    public Mono<Invoice> readByIdentity(int identity) {
        return this.invoiceReactive.findByIdentity(identity)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent invoice: " + identity)))
                .map(InvoiceEntity::toInvoice);
    }

    @Override
    public Mono<Invoice> update(int identity, Invoice invoice) {
        Mono<InvoiceEntity> invoiceEntityMono;
        invoiceEntityMono = this.invoiceReactive.findByIdentity(identity);
        return invoiceEntityMono
                .switchIfEmpty(Mono
                        .error(new NotFoundException("Non existent invoice: " + invoice)))
                .map(invoiceEntity -> {
                    BeanUtils.copyProperties(invoice, invoiceEntity);
                    invoiceEntity.setUserMobile(invoice.getUser().getMobile());
                    return invoiceEntity;
                })
                .flatMap(this.invoiceReactive::save)
                .map(InvoiceEntity::toInvoice);
    }
}
