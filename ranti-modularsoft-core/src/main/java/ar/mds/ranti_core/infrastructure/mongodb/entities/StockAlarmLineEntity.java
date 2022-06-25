package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.StockAlarmLine;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document(collection = "stockAlarmLines")
public class StockAlarmLineEntity {
    @Id
    private String id;
    private Integer warning;
    private Integer critical;
    @DBRef
    private ArticleEntity article;

    public StockAlarmLineEntity(StockAlarmLine stockAlarmLine, ArticleEntity articleEntity) {
        BeanUtils.copyProperties(stockAlarmLine, this);
        this.article = articleEntity;
    }

    public StockAlarmLine toStockAlarmLine(){
        StockAlarmLine stockAlarmLine = new StockAlarmLine();
        BeanUtils.copyProperties(this, stockAlarmLine);
        if (Objects.nonNull(this.getArticle())) {
            stockAlarmLine.setBarcode(this.getArticle().getBarcode());
        }
        return stockAlarmLine;
    }
}
