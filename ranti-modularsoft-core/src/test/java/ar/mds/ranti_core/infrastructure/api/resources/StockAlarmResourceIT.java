package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.model.StockAlarm;
import ar.mds.ranti_core.domain.model.StockAlarmLine;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
public class StockAlarmResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {
        Article article = Article.builder().barcode("ta99000").description("tad1").retailPrice(BigDecimal.ONE)
                .providerCompany("tac1").tax(null).build();
        StockAlarmLine stockAlarmLine = StockAlarmLine
                .builder()
                .article(article)
                .build();
        List<StockAlarmLine> stockAlarmLineList = new ArrayList<>();
        stockAlarmLineList.add(stockAlarmLine);
        StockAlarm stockAlarm = StockAlarm.builder()
                .name("Alarm1")
                .description("Desc1")
                .warning(10)
                .critical(5)
                .stockAlarmLines(stockAlarmLineList)
                .build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(StockAlarmResource.STOCK_ALARM)
                .body(Mono.just(stockAlarm), StockAlarm.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockAlarm.class)
                .value(Assertions::assertNotNull)
                .value(returnStockAlarm -> {
                    assertEquals("Alarm1", returnStockAlarm.getName());
                    assertEquals("Desc1", returnStockAlarm.getDescription());
                    assertEquals(10, returnStockAlarm.getWarning());
                    assertEquals(5, returnStockAlarm.getCritical());
                })
                .returnResult()
                .getResponseBody();
    }
}
