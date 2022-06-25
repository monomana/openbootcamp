package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@TestConfig
class ProviderReactiveIT {

    @Autowired
    private ProviderReactive providerReactive;

    @Test
    void testFindByCompanyNullSafe() {
        StepVerifier
                .create(this.providerReactive.findByCompany(null))
                .expectComplete()
                .verify();
    }

}
