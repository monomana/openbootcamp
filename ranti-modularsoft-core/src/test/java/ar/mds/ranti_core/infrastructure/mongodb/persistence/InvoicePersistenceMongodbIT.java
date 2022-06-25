package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.*;
import ar.mds.ranti_core.domain.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfig
class InvoicePersistenceMongodbIT {
    @Autowired
    private InvoicePersistenceMongodb invoicePersistenceMongodb;

    @Test
    void testCreate() {
        Shopping shopping1 = Shopping.builder().barcode("8400000000017").amount(2)
                .discount(BigDecimal.ZERO).state(ShoppingState.COMMITTED).build();
        Ticket ticket = Ticket.builder().id("ticketId").reference("RyR_8_SkT9igCikrWWWGkQ").cash(new BigDecimal("200"))
                .card(BigDecimal.ZERO).voucher(BigDecimal.ZERO).note("note").creationDate(LocalDateTime.now())
                .shoppingList(List.of(shopping1)).build();
        User user = User.builder().mobile("666666003").build();
        Invoice invoice = Invoice.builder().identity(20226).creationDate(LocalDateTime.now())
                .baseTax(BigDecimal.ONE).taxValue(BigDecimal.TEN).ticket(ticket)
                .user(user).build();
        StepVerifier
                .create(this.invoicePersistenceMongodb.create(invoice))
                .expectNextMatches(dbInvoice -> {
                    assertEquals(20226, dbInvoice.getIdentity());
                    assertNotNull(dbInvoice.getCreationDate());
                    assertEquals(BigDecimal.ONE, dbInvoice.getBaseTax());
                    assertEquals(BigDecimal.TEN, dbInvoice.getTaxValue());
                    assertEquals(ticket.getId(), dbInvoice.getTicket().getId());
                    assertEquals(user.getMobile(), dbInvoice.getUser().getMobile());
                    return true;
                })
                .verifyComplete();
    }
}
