package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.InvoiceTaxValues;
import ar.mds.ranti_core.domain.model.Shopping;
import ar.mds.ranti_core.domain.model.Tax;
import ar.mds.ranti_core.domain.model.Ticket;
import ar.mds.ranti_core.domain.persistence.TicketPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ArticleReactive;
import ar.mds.ranti_core.infrastructure.mongodb.daos.TicketReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.ShoppingEntity;
import ar.mds.ranti_core.infrastructure.mongodb.entities.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class TicketPersistenceMongodb implements TicketPersistence {

    private final TicketReactive ticketReactive;
    private final ArticleReactive articleReactive;

    @Autowired
    public TicketPersistenceMongodb(TicketReactive ticketReactive, ArticleReactive articleReactive) {
        this.ticketReactive = ticketReactive;
        this.articleReactive = articleReactive;
    }

    @Override
    public Mono<Ticket> create(Ticket ticket) {
        TicketEntity ticketEntity = new TicketEntity(ticket);
        return Flux.fromStream(ticket.getShoppingList().stream())
                .flatMap(shopping -> {
                    ShoppingEntity shoppingEntity = new ShoppingEntity(shopping);
                    return this.articleReactive.findByBarcode(shopping.getBarcode())
                            .switchIfEmpty(Mono.error(new NotFoundException("Article: " + shopping.getBarcode())))
                            .map(articleEntity -> {
                                shoppingEntity.setArticleEntity(articleEntity);
                                shoppingEntity.setDescription(articleEntity.getDescription());
                                return shoppingEntity;
                            });
                })
                .doOnNext(ticketEntity::add)
                .then(this.ticketReactive.save(ticketEntity))
                .map(TicketEntity::toTicket);
    }

    @Override
    public Mono<Ticket> readById(String id) {
        return this.ticketReactive.findById(id)
                .map(TicketEntity::toTicket);
    }

    @Override
    public Flux<InvoiceTaxValues> findTaxValues(String id) {
        return this.ticketReactive.findById(id)
                .map(TicketEntity::getShoppingEntityList)
                .flatMapIterable(list -> list)
                .map(ShoppingEntity::toShopping)
                .flatMap(shopping -> {
                    InvoiceTaxValues invoiceTaxValues = new InvoiceTaxValues();
                    return this.articleReactive.findByBarcode(shopping.getBarcode())
                            .map(articleEntity -> {
                                invoiceTaxValues.setTotalShopping(shopping.totalShopping());
                                invoiceTaxValues.setTax(articleEntity.getTax() == null
                                        ? Tax.GENERAL : articleEntity.getTax());
                                return invoiceTaxValues;
                            });
                });
    }

    public Flux<Shopping> findAllArticlesfromTicketsByPurchaseDate(LocalDateTime fromDate, LocalDateTime untilDate) {
        return this.ticketReactive.findTicketsByPurchaseDate(fromDate, untilDate)
                .map(TicketEntity::toTicket)
                .map(Ticket::getShoppingList)
                .flatMap(Flux::fromIterable);
    }

    public Flux<Ticket> findTicketsPerBarcode(String barcode) {
        return this.ticketReactive.findAll()
                .filter(x -> x.getShoppingEntityList().stream()
                        .anyMatch(y -> y.getArticleEntity().getBarcode().equals(barcode)))
                .map(TicketEntity::toTicket);
    }

}
