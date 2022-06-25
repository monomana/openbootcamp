package ar.mds.ranti_core.domain.rest;

import ar.mds.ranti_core.domain.model.GitHubIssue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GitHubIssueMicroService {

    Mono<Void> createGitIssue (GitHubIssue gitHubIssue);
    Flux<GitHubIssue> searchGitIssue();
}
