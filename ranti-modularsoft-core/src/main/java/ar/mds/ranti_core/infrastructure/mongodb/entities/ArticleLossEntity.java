package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.ArticleLoss;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class ArticleLossEntity {
    @Id
    private String barcode;
    private Integer amount;

    public ArticleLossEntity(ArticleLoss articleLoss) {
        BeanUtils.copyProperties(articleLoss, this);
    }

    public ArticleLoss toArticleLoss() {
        ArticleLoss articleLoss = new ArticleLoss();
        BeanUtils.copyProperties(this, articleLoss);
        return articleLoss;
    }
}
