package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.StockAudit;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static ar.mds.ranti_core.infrastructure.api.resources.StockAuditResource.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
public class StockAuditResourceIT {


    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {

        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(STOCK_AUDIT)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(StockAudit.class)
                .value(Assertions::assertNotNull)
                .value(returnStockAudit -> {
                    assertNotNull(returnStockAudit.getCreationDate());
                    assertNotNull(returnStockAudit.getArticlesWithoutAudit());
                });
    }

    @Test
    void testReadById() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(STOCK_AUDIT + ID_ID, "id2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockAudit.class)
                .value(stockAudit -> {
                    assertNotNull(stockAudit.getArticlesWithoutAudit());
                    assertNotNull(stockAudit.getCreationDate());
                });

    }

    @Test
    void testReadAll() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(STOCK_AUDIT + SEARCH)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StockAudit.class)
                .value(stockAudits -> assertTrue(stockAudits.stream()
                        .anyMatch(stock -> stock.getId().contains("id2"))));
    }
}
