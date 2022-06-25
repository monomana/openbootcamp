package ar.modularsoft.services;

import ar.modularsoft.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUserForbidden() {
//        User user = User.builder().mobile("666000666").firstName("k").role(Role.ADMIN).build();
//        assertThrows(ForbiddenException.class, () -> this.userService.createUser(user, Role.MANAGER));
    }
}
