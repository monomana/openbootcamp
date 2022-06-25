package ar.mds.ranti_core.infrastructure.rest_client;

import ar.mds.ranti_core.domain.exceptions.BadGatewayException;
import ar.mds.ranti_core.domain.exceptions.ForbiddenException;
import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.GitHubIssue;
import ar.mds.ranti_core.domain.rest.GitHubIssueMicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service("gitHubClient")
public class GitHubIssueMicroServiceRest implements GitHubIssueMicroService {

    public static final String GIT_HUB_URI = "/repos/miw-upm/betca-tpv/issues";
    public static final String GIT_HUB_USERNAME = "miw-upm";
    private final String gitHubrUri;
    private final String gitHubToken;
    private final String username;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public GitHubIssueMicroServiceRest(@Value("${GITHUB_SECRET :GITHUB_SECRET}") String gitHubToken,
                                       WebClient.Builder webClientBuilder) {
        this.gitHubrUri = GIT_HUB_URI;
        this.gitHubToken = gitHubToken;
        this.username = GIT_HUB_USERNAME;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> createGitIssue(GitHubIssue gitHubIssue) {
        return null;
    }

    @Override
    public Flux<GitHubIssue> searchGitIssue() {
        return
                webClientBuilder
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build()
                        .mutate()
                        .defaultHeader("Authorization", "Basic " + Base64Utils
                                        .encodeToString((username + ":" + this.gitHubToken).getBytes(UTF_8)))
                        .build()
                        .get()
                        .uri(gitHubrUri)
                        .exchange()
                        .flatMapMany(gitHubResponse -> {

                            if (HttpStatus.UNAUTHORIZED.equals(gitHubResponse.statusCode()))
                                return Mono.error(new ForbiddenException("issues. Acceso no autorizado"));
                            else
                            if (HttpStatus.NOT_FOUND.equals(gitHubResponse.statusCode()))
                                return Flux.error(new NotFoundException("issues "));
                            else if (gitHubResponse.statusCode().isError())
                                return Flux.error(new BadGatewayException("Unexpected error: GitHub API."));
                            else
                                return gitHubResponse.bodyToFlux(GitHubIssue.class);
                        });
    }
}
