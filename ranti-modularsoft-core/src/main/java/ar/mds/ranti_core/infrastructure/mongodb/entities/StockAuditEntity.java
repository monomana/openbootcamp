package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.StockAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class StockAuditEntity {
    @Id
    private String id;
    private LocalDateTime creationDate;
    private LocalDateTime closeDate;
    private BigDecimal lossValue;
    @DBRef
    private List<ArticleEntity> articlesWithoutAudit;
    @DBRef
    private List<ArticleLossEntity> articleLoss;

    public StockAuditEntity(StockAudit stockAudit) {
        BeanUtils.copyProperties(stockAudit, this);
        this.articlesWithoutAudit = new ArrayList<>();
        this.articleLoss = new ArrayList<>();
    }

    public StockAudit toStockAudit() {
        StockAudit stockAudit = new StockAudit();
        BeanUtils.copyProperties(this, stockAudit);
        stockAudit.setArticlesWithoutAudit(this.getArticlesWithoutAudit().stream()
                .map(ArticleEntity::toArticle)
                .collect(Collectors.toList()));
        stockAudit.setArticleLoss(this.getArticleLoss().stream()
                .map(ArticleLossEntity::toArticleLoss)
                .collect(Collectors.toList()));
        return stockAudit;
    }

    public void AddArticle(ArticleEntity articleEntity) {
        this.articlesWithoutAudit.add(articleEntity);
    }

    public void addArticleLoss(ArticleLossEntity articleLossEntity) {
        this.articleLoss.add(articleLossEntity);
    }
}
