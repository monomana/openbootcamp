package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class ProviderPersistenceMongodbIT {

    @Autowired
    private ProviderPersistenceMongodb providerPersistenceMongodb;

    @Test
    void testCreate() {
        StepVerifier
                .create(this.providerPersistenceMongodb.create(Provider.builder().company("c1").nif("n1").active(true).build()))
                .expectNextMatches(provider -> {
                    assertEquals("c1", provider.getCompany());
                    assertEquals("n1", provider.getNif());
                    assertTrue(provider.getActive());
                    return true;
                })
                .expectComplete()
                .verify();
    }

}
