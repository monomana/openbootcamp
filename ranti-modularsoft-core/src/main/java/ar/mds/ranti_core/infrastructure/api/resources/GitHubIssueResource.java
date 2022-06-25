package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.GitHubIssue;
import ar.mds.ranti_core.domain.model.GitHubIssueLabel;
import ar.mds.ranti_core.domain.services.GitHubIssueService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Rest
@RequestMapping(GitHubIssueResource.GITHUBISSUE)
public class GitHubIssueResource {
    public static final String GITHUBISSUE = "/githubissues";
    public static final String SEARCH = "/search";
    public static final String LABEL = "/label";

    private final GitHubIssueService gitHubIssueService;

    @Autowired
    public GitHubIssueResource(GitHubIssueService gitHubIssueService)
    {
        this.gitHubIssueService = gitHubIssueService;
    }

    @GetMapping(LABEL)
    public Flux<GitHubIssue> findByLabel(@RequestParam(required = false) GitHubIssueLabel label){
        return this.gitHubIssueService.searchGitIssue(label);
    }
}
