package ar.mds.ranti_core.configuration;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.infrastructure.api.http_errors.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestConfig
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testCreateToken() {
        String token = jwtService.createToken("666666000", "adm", Role.ADMIN.name());
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length);
    }
}
