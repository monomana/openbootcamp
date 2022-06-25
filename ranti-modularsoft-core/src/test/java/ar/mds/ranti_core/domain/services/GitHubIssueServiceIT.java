package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.GitHubIssue;
import ar.mds.ranti_core.domain.model.GitHubIssueLabel;
import ar.mds.ranti_core.domain.rest.GitHubIssueMicroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestConfig
public class GitHubIssueServiceIT {

    @Autowired
    private GitHubIssueService gitHubIssueService;
    @MockBean
    private GitHubIssueMicroService gitHubIssueMicroService;
    private GitHubIssue gitHubIssue;

    @BeforeEach
    void setUp() {
        this.gitHubIssue = new GitHubIssue("TitleTestResource","BodyTestResource",GitHubIssueLabel.bug);
        BDDMockito.given(this.gitHubIssueMicroService.searchGitIssue())
                .willAnswer(arguments -> Flux.merge(Mono.just(this.gitHubIssue)));
    }

    @Test
    void readBugLabelIssuesTest(){
        StepVerifier.create(this.gitHubIssueService.searchGitIssue(GitHubIssueLabel.bug))
                .thenConsumeWhile(x -> true)
                .expectRecordedMatches(elements -> !elements.isEmpty())
                .expectComplete();
    }
}
