package ar.mds.ranti_core.domain.rest;

import ar.mds.ranti_core.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserMicroservice {

    Mono< User > readByMobile(String mobile);
}
