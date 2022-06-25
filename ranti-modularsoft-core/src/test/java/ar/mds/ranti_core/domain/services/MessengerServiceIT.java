package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.Messenger;
import ar.mds.ranti_core.domain.model.User;
import ar.mds.ranti_core.domain.rest.UserMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestConfig
class MessengerServiceIT {

    @Autowired
    MessengerService messengerService;

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
        StepVerifier
                .create(this.messengerService.create(messenger))
                .expectNextMatches(returnedMessenger -> {
                    assertNotNull(returnedMessenger.getId());
                    assertEquals("6", returnedMessenger.getFromUser());
                    assertEquals("6", returnedMessenger.getToUser());
                    assertEquals("Subject", returnedMessenger.getSubject());
                    assertEquals("New Messenger", returnedMessenger.getText());
                    assertEquals(Boolean.FALSE, returnedMessenger.getRead());
                    return true;
                })
                .expectComplete();
    }

    @Test
    void testCreateNotExistingMobileFromUser() {
        Messenger messenger = Messenger.builder().fromUser("1").toUser("6").subject("Subject").text("New Messenger").read(Boolean.FALSE).build();
        StepVerifier
                .create(this.messengerService.create(messenger))
                .expectError(NotFoundException.class);
    }


}
