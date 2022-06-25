package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;


import ar.mds.ranti_core.infrastructure.mongodb.entities.ArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleDao extends MongoRepository< ArticleEntity, String > {
    List< ArticleEntity > findByBarcode(String barcode);
}
