package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;

import ar.mds.ranti_core.infrastructure.mongodb.entities.BudgetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BudgetDao extends MongoRepository<BudgetEntity, String> {
}
