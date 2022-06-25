package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.model.StockAlarm;
import ar.mds.ranti_core.domain.model.StockAlarmLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class StockAlarmPersistenceMongodbIT {
    @Autowired
    private StockAlarmPersistenceMongodb stockAlarmPersistenceMongodb;

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
        StepVerifier
                .create(this.stockAlarmPersistenceMongodb.create(stockAlarm))
                .expectNextMatches(stockAlarm1 -> {
                    assertEquals("Alarm1",stockAlarm1.getName());
                    assertEquals("Desc1",stockAlarm1.getDescription());
                    return true;
                })
                .verifyComplete();
    }
}