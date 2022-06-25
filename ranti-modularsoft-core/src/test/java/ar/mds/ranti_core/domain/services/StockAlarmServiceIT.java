package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.model.StockAlarm;
import ar.mds.ranti_core.domain.model.StockAlarmLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class StockAlarmServiceIT {

    @Autowired
    private StockAlarmService stockAlarmService;
    @Autowired
    private ArticleService articleService;

    @Test
    void testCreate(){
        Article article = articleService.read("1").block();
        //Article article = Article.builder().barcode("ta99000").description("tad1").retailPrice(BigDecimal.ONE)
                //.providerCompany("tac1").tax(null).build();
        StockAlarmLine stockAlarmLine = StockAlarmLine
                .builder()
                .article(article)
                .build();
        List<StockAlarmLine> stockAlarmLineList = new ArrayList<>();
        stockAlarmLineList.add(stockAlarmLine);
        StockAlarm stockAlarm = StockAlarm.builder()
                .name("Alarm2")
                .description("Desc2")
                .warning(10)
                .critical(5)
                .stockAlarmLines(stockAlarmLineList)
                .build();
        StepVerifier
                .create(this.stockAlarmService.create(stockAlarm))
                .expectNextMatches(stockAlarm1 -> {
                    assertEquals("Alarm2",stockAlarm1.getName());
                    assertEquals("Desc2",stockAlarm1.getDescription());
                    assertEquals(10,stockAlarm1.getWarning());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}
