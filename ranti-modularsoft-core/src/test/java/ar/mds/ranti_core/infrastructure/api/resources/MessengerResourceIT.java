package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.Messenger;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.model.*;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@RestTestConfig
class MessengerResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @MockBean
    private UserMicroservice userMicroservice;

    @BeforeEach
    void configureUserMicroservice() {
        BDDMockito.given(this.userMicroservice.readByMobile(any(String.class)))
                .willAnswer(arguments ->
                        Mono.just(User.builder().mobile(arguments.getArgument(0)).firstName("mock").build()));
    }

    @Test
    void testCreate() {
        Messenger messenger = Messenger.builder().fromUser("6").toUser("6").subject("Subject").text("New Messenger").read(Boolean.FALSE).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(MessengerResource.MESSENGERS)
                .body(Mono.just(messenger), Messenger.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Messenger.class)
                .value(Assertions::assertNotNull)
                .value(returnMessenger -> {
                    assertEquals("6", returnMessenger.getFromUser());
                    assertEquals("6", returnMessenger.getToUser());
                    assertEquals("Subject", returnMessenger.getSubject());
                    assertEquals("New Messenger", returnMessenger.getText());
                    assertEquals(returnMessenger.getRead(),Boolean.FALSE);
                })
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testRead() {
        Messenger messenger = Messenger.builder().id("1").fromUser("6").toUser("6").subject("Subject").text("New Messenger").read(Boolean.FALSE).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(MessengerResource.MESSENGERS)
                .body(Mono.just(messenger), Messenger.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Messenger.class)
                .value(Assertions::assertNotNull)
                .value(returnMessenger -> {
                     messenger.setId(returnMessenger.getId());
                });

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(MessengerResource.MESSENGERS + "/" + messenger.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Messenger.class)
                .value(returnMessenger -> {
                    assertEquals("6", returnMessenger.getFromUser());
                    assertEquals("6", returnMessenger.getToUser());
                    assertEquals("Subject", returnMessenger.getSubject());
                    assertEquals("New Messenger", returnMessenger.getText());
                    assertEquals(returnMessenger.getRead(),Boolean.FALSE);
                });
    }
}
