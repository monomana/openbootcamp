package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.TokenDto;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.services.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class RestClientTestService {

    @Autowired
    private JwtService jwtService;

    private String token;

    private boolean isRole(Role role) {
        return this.token != null && jwtService.role(token).equals(role.name());
    }

    private WebTestClient login(Role role, String mobile, WebTestClient webTestClient) {
        if (!this.isRole(role)) {
            return login(mobile, webTestClient);
        } else {
            return webTestClient.mutate()
                    .defaultHeader("Authorization", "Bearer " + this.token).build();
        }
    }

    public WebTestClient login(String mobile, WebTestClient webTestClient) {
        TokenDto tokenDto = webTestClient
                .mutate().filter(basicAuthentication(mobile, "123")).build()
                .post().uri(UserResource.USERS + UserResource.TOKEN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .value(Assertions::assertNotNull)
                .returnResult().getResponseBody();
        if (tokenDto != null) {
            this.token = tokenDto.getToken();
        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.token).build();
    }

    public WebTestClient loginAdmin(WebTestClient webTestClient) {
        return this.login(Role.ADMIN, "6", webTestClient);
    }

    public WebTestClient loginManager(WebTestClient webTestClient) {
        return this.login(Role.MANAGER, "666666001", webTestClient);
    }

    public WebTestClient loginOperator(WebTestClient webTestClient) {
        return this.login(Role.OPERATOR, "666666002", webTestClient);
    }
@Test
    public WebTestClient loginCustomer(WebTestClient webTestClient) {
        return this.login(Role.CUSTOMER, "user", webTestClient);
    }

    public String getToken() {
        return token;
    }

}
