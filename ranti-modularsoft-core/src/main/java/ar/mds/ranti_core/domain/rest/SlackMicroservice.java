package ar.mds.ranti_core.domain.rest;

import ar.mds.ranti_core.domain.model.Cashier;
import ar.mds.ranti_core.domain.model.SlackPublication;
import reactor.core.publisher.Mono;

public interface SlackMicroservice {

    Mono<Void> publish(SlackPublication slackPublication);

    Mono<Cashier> closeCashier(Cashier lastCashier);
}
