package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfig
class InvoiceServiceIT {

    @Autowired
    InvoiceService invoiceService;

    @Test
    void testCreate() {
        String ticketId = "5fa4608f4928694ef5980e4c";
        StepVerifier
                .create(this.invoiceService.create(ticketId))
                .expectNextMatches(dbInvoice -> {
                    assertNotNull(dbInvoice.getIdentity());
                    assertNotNull(dbInvoice.getCreationDate());
                    assertEquals(new BigDecimal("0.02"), dbInvoice.getBaseTax().setScale(2, RoundingMode.HALF_UP));
                    assertEquals(0, new BigDecimal("0.00").compareTo(dbInvoice.getTaxValue().setScale(2, RoundingMode.HALF_UP)));
                    assertEquals("5fa4608f4928694ef5980e4c", dbInvoice.getTicket().getId());
                    assertEquals("666666005", dbInvoice.getUser().getMobile());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}
