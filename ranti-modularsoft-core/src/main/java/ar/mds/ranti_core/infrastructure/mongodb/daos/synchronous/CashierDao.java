package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;


import ar.mds.ranti_core.infrastructure.mongodb.entities.CashierEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CashierDao extends MongoRepository<CashierEntity, String > {
    Optional< CashierEntity > findFirstByOrderByOpeningDateDesc();
}
