package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.TicketPersistence;
import ar.mds.ranti_core.domain.services.utils.PdfTicketBuilder;
import ar.mds.ranti_core.domain.services.utils.UUIDBase64;
import ar.mds.ranti_core.domain.model.Shopping;
import ar.mds.ranti_core.domain.model.Ticket;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.persistence.ArticlePersistence;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductIntDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductPredDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketPersistence ticketPersistence;
    private final UserMicroservice userMicroservice;
    private final ArticlePersistence articlePersistence;
    private final CashierService cashierService;

    @Autowired
    public TicketService(TicketPersistence ticketPersistence, UserMicroservice userMicroservice, ArticlePersistence articlePersistence,
                         CashierService cashierService) {
        this.ticketPersistence = ticketPersistence;
        this.userMicroservice = userMicroservice;
        this.articlePersistence = articlePersistence;
        this.cashierService = cashierService;
    }

    public Mono< Ticket > create(Ticket ticket) {
        ticket.setId(null);
        ticket.setReference(UUIDBase64.URL.encode());
        ticket.setCreationDate(LocalDateTime.now());
        Mono< Void > articlesStock = this.updateArticlesStockAssuredSequentially(ticket);
        Mono< Void > cashierUpdated = this.cashierService.addSale(ticket.getCash(), ticket.getCard(), ticket.getVoucher()).then();
        Mono< Void > userMono = this.readUserByUserMobileNullSafe(ticket.getUser())
                .then();
        return Mono.when(articlesStock, cashierUpdated, userMono)
                .then(this.ticketPersistence.create(ticket));

    }

    private Mono< Void > updateArticlesStockAssuredSequentially(Ticket ticket) {
        return Flux.concat( // Flux<Article> but sequential!!!
                ticket.getShoppingList().stream() // Stream<Shopping>
                        .map(shopping -> // Stream< Mono< Article > >
                                this.articlePersistence.readAndWriteStockByBarcodeAssured(
                                        shopping.getBarcode(), -shopping.getAmount())
                        ).collect(Collectors.toList() // List< Mono<Article> >
                )
        ).then(); // Mono<Void>
    }

    public Mono< byte[] > readReceipt(String id) {
        return this.ticketPersistence.readById(id)
                .flatMap(ticket -> this.readUserByUserMobileNullSafe(ticket.getUser())
                        .map(user -> {
                            ticket.setUser(user);
                            return ticket;
                        })
                        .switchIfEmpty(Mono.just(ticket)))
                .map(new PdfTicketBuilder()::generateTicket);

    }

    private Mono< User > readUserByUserMobileNullSafe(User user) {
        if (user != null) {
            return this.userMicroservice.readByMobile(user.getMobile());
        } else {
            return Mono.empty();
        }
    }

    public Flux<Shopping> findAllArticlesfromTicketsByPurchaseDate(LocalDateTime fromDate, LocalDateTime untilDate) {
        return this.ticketPersistence.findAllArticlesfromTicketsByPurchaseDate(fromDate,untilDate);
    }

    public Flux<ProductPredDto> stockPrediction(String barcode) {

        Mono < Integer > yValue = this.findProductsPerBarcode(barcode)
                .map(ProductIntDto::getSold).skip(1).reduce(Integer::sum);

        Mono < Long > xValueMax = this.findProductsPerBarcode(barcode)
                .map(ProductIntDto::getPurchaseDate)
                .reduce((c1, c2) -> c1 > c2 ? c1 : c2);

        Mono < Long > xValueMin = this.findProductsPerBarcode(barcode)
                .map(ProductIntDto::getPurchaseDate)
                .reduce((c1, c2) -> c1 < c2 ? c1 : c2);

        Mono < Long > xValue = Mono.zip(xValueMax,
                        xValueMin, (c1 , c2) -> c1 - c2)
                .map(x -> x / (24 * 60 * 60 * 1000));

        Mono < Double > slope = Mono.zip(yValue,xValue).map(x -> (double) x.getT1() /(double) x.getT2());

        Mono < Integer > stock = this.articlePersistence.findStockByBarcode(barcode);

        return Flux.just(0,15,30,45,60,75,90)
                .flatMap(x -> createPredictionTuple(x,slope,stock))
                .map(ProductPredDto::new)
                .filter(x -> x.getStock() >= 0)
                .sort(Comparator.comparing(ProductPredDto::getDate));
    }

    public Mono<Tuple2<Double, LocalDate>> createPredictionTuple(Integer day, Mono <Double> slope , Mono <Integer> stock) {
        return Mono.zip(
                Mono.just(day).zipWith(slope).map(x -> x.getT2() * x.getT1()).zipWith(stock).map(y -> y.getT2() - y.getT1()),
                Mono.just(LocalDate.now().plusDays(day))
        );
    }


    public Flux<ProductIntDto> findProductsPerBarcode(String barcode) {
        return this.ticketPersistence.findTicketsPerBarcode(barcode)
                .map(ticket -> ProductIntDto.getProductPredictDto(ticket,barcode))
                .sort(Comparator.comparing(ProductIntDto::getPurchaseDate));
    }

}
