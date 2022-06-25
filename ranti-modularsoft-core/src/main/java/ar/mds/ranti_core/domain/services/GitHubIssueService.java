package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.GitHubIssue;
import ar.mds.ranti_core.domain.model.GitHubIssueLabel;
import ar.mds.ranti_core.domain.rest.GitHubIssueMicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GitHubIssueService {

    private final GitHubIssueMicroService gitIssueMicroservicio;

    @Autowired
    public GitHubIssueService(GitHubIssueMicroService gitIssueMicroservicio) {
        this.gitIssueMicroservicio = gitIssueMicroservicio;
    }

    public Flux<GitHubIssue> searchGitIssue(GitHubIssueLabel gitHubIssueLabel){
        return gitIssueMicroservicio.searchGitIssue();
    }
}
