package ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous;


import ar.mds.ranti_core.infrastructure.mongodb.entities.TicketEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketDao extends MongoRepository<TicketEntity, String > {
}
