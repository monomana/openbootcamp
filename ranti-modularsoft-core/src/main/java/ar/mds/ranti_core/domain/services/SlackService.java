package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.model.SlackPublication;
import ar.mds.ranti_core.domain.rest.SlackMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SlackService {

    private final SlackMicroservice slackMicroservice;

    @Autowired
    public SlackService(SlackMicroservice slackMicroservice) {
        this.slackMicroservice = slackMicroservice;
    }

    public Mono<Void> publish(SlackPublication slackPublication) {
        return this.slackMicroservice.publish(slackPublication);
    }

    public Mono<Cashier> closeCashier(Cashier lastCashier) {
        return this.slackMicroservice.closeCashier(lastCashier);
    }
}
