package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ArticlesTreeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticlesTreeDao extends MongoRepository< ArticlesTreeEntity, String > {
    List< ArticlesTreeEntity > findByReference(String reference);
}
