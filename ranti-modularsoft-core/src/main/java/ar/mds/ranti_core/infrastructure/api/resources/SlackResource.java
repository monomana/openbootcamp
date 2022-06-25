package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.SlackPublication;
import ar.mds.ranti_core.domain.services.SlackService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Rest
@RequestMapping(SlackResource.SLACK)
public class SlackResource {

    public static final String SLACK = "/slack";

    private final SlackService slackService;

    @Autowired
    public SlackResource(SlackService slackService) {
        this.slackService = slackService;
    }

    @PostMapping()
    public Mono<Void> publish(@RequestBody SlackPublication slackPublication) {
        return this.slackService.publish(slackPublication);
    }
}
