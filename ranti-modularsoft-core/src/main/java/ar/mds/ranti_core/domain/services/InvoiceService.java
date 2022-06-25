package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.persistence.InvoicePersistence;
import ar.mds.ranti_core.domain.persistence.TicketPersistence;
import ar.mds.ranti_core.domain.services.utils.PdfInvoiceBuilder;
import ar.mds.ranti_core.domain.model.Invoice;
import ar.mds.ranti_core.domain.model.InvoiceTaxValues;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;

@Service
public class InvoiceService {

    private final InvoicePersistence invoicePersistence;
    private final TicketPersistence ticketPersistence;
    private final UserMicroservice userMicroservice;

    @Autowired
    public InvoiceService(InvoicePersistence invoicePersistence, TicketPersistence ticketPersistence,
                          UserMicroservice userMicroservice) {
        this.invoicePersistence = invoicePersistence;
        this.ticketPersistence = ticketPersistence;
        this.userMicroservice = userMicroservice;
    }

    public Mono<Invoice> create(String ticketId) {
        Invoice invoice = new Invoice();
        invoice.setCreationDate(LocalDateTime.now());
        Mono<Void> idMono = this.setIdentity(invoice).then();
        Mono<Void> ticketMono = this.setTicketAndUser(invoice, ticketId).then();
        Mono<Void> baseTaxMono = this.setBaseTax(invoice, ticketId).then();
        Mono<Void> taxValueMono = this.setTaxValue(invoice, ticketId).then();
        return Mono.when(idMono, ticketMono, baseTaxMono, taxValueMono)
                .thenReturn(invoice)
                .flatMap(this.invoicePersistence::create);
    }

    private Mono<Invoice> setBaseTax(Invoice invoice, String ticketId) {
        return this.ticketPersistence.findTaxValues(ticketId)
                .map(InvoiceTaxValues::getBaseTax)
                .reduce(BigDecimal::add)
                .map(baseTax -> {
                    invoice.setBaseTax(baseTax);
                    return invoice;
                });
    }

    private Mono<Invoice> setTaxValue(Invoice invoice, String ticketId) {
        return this.ticketPersistence.findTaxValues(ticketId)
                .map(InvoiceTaxValues::getTaxValue)
                .reduce(BigDecimal::add)
                .map(taxValue -> {
                    invoice.setTaxValue(taxValue);
                    return invoice;
                });
    }

    private Mono<Invoice> setTicketAndUser(Invoice invoice, String ticketId) {
        return this.ticketPersistence.readById(ticketId)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("TicketId not found:" + ticketId)
                ))
                .map(ticket -> {
                    invoice.setTicket(ticket);
                    invoice.setUser(ticket.getUser());
                    return invoice;
                });
    }

    public Mono<Invoice> setIdentity(Invoice invoice) {
        int year = LocalDateTime.now().getYear();
        return this.invoicePersistence.findLast()
                .map(identity -> {
                    invoice.setIdentity(identity + 1);
                    return invoice;
                })
                .switchIfEmpty(this.newYearIdentity(invoice, year));
    }

    private Mono<Invoice> newYearIdentity(Invoice invoice, int year) {
        invoice.setIdentity(parseInt("" + year + 1));
        return Mono.just(invoice);
    }

    public Flux<Invoice> findByTicketIdAndUserMobileNullSafe(String ticketId, String userMobile) {
        return this.invoicePersistence.findByTicketIdAndUserMobileNullSafe(ticketId, userMobile);
    }

    public Mono<Invoice> read(int identity) {
        return this.invoicePersistence.readByIdentity(identity);
    }

    public Mono<Invoice> update(int identity, Invoice invoice) {
        return this.invoicePersistence.readByIdentity(identity)
                .map(dbInvoice -> {
                    dbInvoice.setUser(invoice.getUser());
                    return dbInvoice;
                })
                .flatMap(dbInvoice -> this.invoicePersistence.update(identity, dbInvoice));
    }

    public Mono<byte[]> readReceipt(int identity) {
        return this.invoicePersistence.readByIdentity(identity)
                .flatMap(invoice -> this.userMicroservice.readByMobile(invoice.getUser().getMobile())
                        .map(user -> {
                            invoice.setUser(user);
                            return invoice;
                        }))
                .map(new PdfInvoiceBuilder()::generateInvoice);
    }
}
