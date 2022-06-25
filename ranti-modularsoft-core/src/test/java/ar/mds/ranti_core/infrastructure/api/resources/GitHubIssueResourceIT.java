package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.GitHubIssue;
import ar.mds.ranti_core.domain.model.GitHubIssueLabel;
import ar.mds.ranti_core.domain.services.GitHubIssueService;
import ar.mds.ranti_core.infrastructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static ar.mds.ranti_core.infrastructure.api.resources.ArticleResource.ARTICLES;
import static ar.mds.ranti_core.infrastructure.api.resources.ArticleResource.BARCODE;
import static org.mockito.ArgumentMatchers.any;

@RestTestConfig
public class GitHubIssueResourceIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @MockBean
    private GitHubIssueService gitHubService;

    private GitHubIssue gitHubIssue;

    @BeforeEach
    void setUp() {
        this.gitHubIssue = new GitHubIssue("TitleTestResource","BodyTestResource",GitHubIssueLabel.bug);
    }

    @Test
    void testFindByLabel(){
        BDDMockito.given(this.gitHubService.searchGitIssue(any(GitHubIssueLabel.class)))
                .willAnswer(arguments -> Flux.merge(Mono.just(this.gitHubIssue)));

        this.restClientTestService
                .loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ARTICLES + BARCODE)
                        .queryParam("label", GitHubIssueLabel.bug)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GitHubIssue.class)
                .value(Assertions::assertNotNull);
    }
}
