package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.Shopping;
import ar.mds.ranti_core.domain.model.ShoppingState;
import ar.mds.ranti_core.domain.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfig
class TicketPersistenceMongodbIT {

    @Autowired
    private TicketPersistenceMongodb ticketPersistenceMongodb;
    private ArticlePersistenceMongodb articlePersistenceMongodb;

    @Test
    void tesCreate() {
        Shopping shopping1 = Shopping.builder().barcode("8400000000017").amount(2)
                .discount(BigDecimal.ZERO).state(ShoppingState.COMMITTED).build();
        Shopping shopping2 = Shopping.builder().barcode("8400000000024").amount(3)
                .discount(BigDecimal.TEN).state(ShoppingState.NOT_COMMITTED).build();
        Ticket ticket = Ticket.builder().reference("RyR_8_SkT9igCikrWWWGkQ").cash(new BigDecimal("200"))
                .card(BigDecimal.ZERO).voucher(BigDecimal.ZERO).note("note").creationDate(LocalDateTime.now())
                .shoppingList(List.of(shopping1, shopping2)).build();
        StepVerifier
                .create(this.ticketPersistenceMongodb.create(ticket))
                .expectNextMatches(dbTicket -> {
                    assertNotNull(dbTicket.getId());
                    assertNotNull(dbTicket.getCreationDate());
                    assertEquals("RyR_8_SkT9igCikrWWWGkQ", dbTicket.getReference());
                    assertEquals(2, dbTicket.getShoppingList().size());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testfindAllArticlesfromTicketsByPurchaseDate() {
        LocalDateTime untilDate = LocalDateTime.of(2020,8,31,12,22);
        LocalDateTime fromDate = LocalDateTime.of(2020,8,1,12,22);
        StepVerifier
                .create(this.ticketPersistenceMongodb.findAllArticlesfromTicketsByPurchaseDate(fromDate,untilDate)
                        .filter(x->x.getBarcode().equals("8400000000101")))
                .expectNextMatches(shopping -> {
                    assertEquals("Zarzuela - Polo T5", shopping.getDescription());
                    assertEquals(3, shopping.getAmount());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void findTicketsPerBarcode() {
        String barcode = "8400000000017";
        StepVerifier
                .create(this.ticketPersistenceMongodb.findTicketsPerBarcode(barcode)
                        .filter(x->x.getId().equals("5fa45e863d6e834d642689ac")))
                .expectNextMatches(ticket -> {
                    assertEquals("nUs81zZ4R_iuoq0_zCRm6A", ticket.getReference());
                    return true;
                })
                .expectComplete()
                .verify();
    }

}
