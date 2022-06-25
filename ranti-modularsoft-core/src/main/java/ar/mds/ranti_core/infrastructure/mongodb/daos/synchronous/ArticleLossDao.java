package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.ArticleLossEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleLossDao extends MongoRepository<ArticleLossEntity, String> {
}
