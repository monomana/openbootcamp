package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.TechnicalSupportAnswer;
import ar.mds.ranti_core.domain.model.TechnicalSupportRequest;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static ar.mds.ranti_core.infrastructure.api.resources.TechnicalSupportResource.*;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
public class TechnicalSupportResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testFindByRequestNullSafe() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(TECHNICAL_SUPPORT_REQUESTS + SEARCH)
                        .queryParam("request", "payment")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TechnicalSupportRequest.class)
                .value(Assertions::assertNotNull)
                .value(technicalSupportRequests -> assertTrue(technicalSupportRequests
                        .stream().allMatch(technicalSupportRequest -> technicalSupportRequest.getRequest().toLowerCase().contains("payment"))));
    }

    @Test
    void testFindByIdentifierNotFoundException() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(TECHNICAL_SUPPORT_REQUESTS + REQUEST_ID, "..")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByIdentifier(){
        TechnicalSupportRequest technicalSupportRequest = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(TECHNICAL_SUPPORT_REQUESTS + REQUEST_ID, "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TechnicalSupportRequest.class)
                .value(Assertions::assertNotNull)
                .value(technicalSupportRequest1 -> {
                    assertEquals("1", technicalSupportRequest1.getIdentifier());
                    assertEquals("I can not see my articles.", technicalSupportRequest1.getRequest());
                })
                .returnResult()
                .getResponseBody();
        assertNotNull(technicalSupportRequest);
    }

    @Test void testCreate() {
        TechnicalSupportRequest technicalSupportRequest = new TechnicalSupportRequest();
        technicalSupportRequest.setRequest("Request TechnicalSupportResourceIT");
        technicalSupportRequest.setResolved(false);
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(TECHNICAL_SUPPORT_REQUESTS)
                .body(Mono.just(technicalSupportRequest), TechnicalSupportRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TechnicalSupportRequest.class)
                .value(technicalSupportRequestReturned -> {
                    assertNotNull(technicalSupportRequestReturned.getIdentifier());
                    assertNotNull(technicalSupportRequestReturned.getRequest());
                    assertNotNull(technicalSupportRequestReturned.getResolved());
                    assertFalse(technicalSupportRequestReturned.getResolved());
                    assertEquals( "Request TechnicalSupportResourceIT", technicalSupportRequestReturned.getRequest());
                })
                .returnResult()
                .getResponseBody();
    }

    @Test void testReadAndUpdate() {

        TechnicalSupportRequest technicalSupportRequest = this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(TECHNICAL_SUPPORT_REQUESTS + REQUEST_ID, "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TechnicalSupportRequest.class)
                .value(Assertions::assertNotNull)
                .returnResult()
                .getResponseBody();

        technicalSupportRequest.getAnswers().add(
                TechnicalSupportAnswer.builder()
                        .answer("TestUpdate")
                        .dateSent(LocalDateTime.now())
                        .build()
        );
        technicalSupportRequest.setResolved(false);

        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(TECHNICAL_SUPPORT_REQUESTS + REQUEST_ID, "1")
                .body(Mono.just(technicalSupportRequest), TechnicalSupportRequest.class)
                .exchange()
                .expectStatus().isOk();

        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(TECHNICAL_SUPPORT_REQUESTS + REQUEST_ID, "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TechnicalSupportRequest.class)
                .value(Assertions::assertNotNull)
                .value(technicalSupportRequest1 -> {
                    assertFalse(technicalSupportRequest1.getResolved());
                    assertEquals("TestUpdate", technicalSupportRequest1.getAnswers()
                            .get(technicalSupportRequest1
                                    .getAnswers()
                                    .size()-1)
                            .getAnswer()
                    );
                })
                .returnResult()
                .getResponseBody();

    }

    @Test void testUpdateNotFound() {
        TechnicalSupportRequest technicalSupportRequest = new TechnicalSupportRequest();
        technicalSupportRequest.setIdentifier("9999999999");
        technicalSupportRequest.setResolved(false);
        technicalSupportRequest.setRequest("TestNotFound");

        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(TECHNICAL_SUPPORT_REQUESTS + REQUEST_ID, technicalSupportRequest.getIdentifier())
                .body(Mono.just(technicalSupportRequest), TechnicalSupportRequest.class)
                .exchange()
                .expectStatus().isNotFound();
    }


}
