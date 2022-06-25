package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.CompositeArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompositeArticleDao extends MongoRepository<CompositeArticleEntity, String > {
}
