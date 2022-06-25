package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.model.SlackPublication;
import ar.mds.ranti_core.domain.model.SlackPublicationCategory;
import ar.mds.ranti_core.domain.rest.SlackMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static java.math.BigDecimal.ZERO;
import static org.mockito.ArgumentMatchers.any;

@TestConfig
class SlackServiceIT {

    @Autowired
    private SlackService slackService;
    @MockBean
    private SlackMicroservice slackMicroservice;

    private SlackPublication slackPublication;

    @BeforeEach
    void setUp() {
        this.slackPublication = new SlackPublication("titulo", "autor", "username",
                "email", SlackPublicationCategory.CRITICAL, "message");

        BDDMockito.given(this.slackMicroservice.publish(any(SlackPublication.class)))
                .willAnswer(arguments -> Mono.empty());

        BDDMockito.given(this.slackMicroservice.closeCashier(any(Cashier.class)))
                .willAnswer(arguments ->
                        Mono.just(Cashier.builder()
                                .cashSales(ZERO)
                                .cardSales(ZERO)
                                .finalCash(ZERO)
                                .closureDate(LocalDateTime.now())
                                .comment("test").build()));
    }

    @Test
    void testPublish() {
        StepVerifier
                .create(this.slackService.publish(this.slackPublication))
                .verifyComplete();
    }

    @Test
    void testCloseCashier() {
        StepVerifier
                .create(this.slackService.closeCashier(Cashier.builder()
                        .cashSales(ZERO)
                        .cardSales(ZERO)
                        .finalCash(ZERO)
                        .closureDate(LocalDateTime.now())
                        .comment("test").build()))
                .expectNextCount(1)
                .verifyComplete();
    }
}
