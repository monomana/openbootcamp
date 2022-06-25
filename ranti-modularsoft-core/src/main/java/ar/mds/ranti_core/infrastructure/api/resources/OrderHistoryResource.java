package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.services.OrderHistoryService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Rest
@RequestMapping("${ranti.company}" + OrderHistoryResource.ORDERS_HISTORY)
public class OrderHistoryResource {
    public static final String ORDERS_HISTORY = "/orders/history";
    public static final String ORDERS_HISTORY_ID = "/{order_id}";
    public static final String REFERENCE = "/reference";

    private final OrderHistoryService orderHistoryService;

    @Autowired
    public OrderHistoryResource(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping(OrderHistoryResource.REFERENCE)
    public Mono<OrderReferenceDto> findByOrderReferenceNullSafe(@RequestParam(required = false) String orderReference) {
        return this.orderHistoryService.findByOrderReferenceNullSafe(orderReference)
                .collectList()
                .map(OrderReferenceDto::new);
    }

    @GetMapping()
    public Flux<OrderViewSQLDto> getAllOrders() {
        return Flux.fromStream( this.orderHistoryService.getAllOrders()
                .map(OrderViewSQLDto::new));
    }
    @GetMapping(ORDERS_HISTORY_ID)
    public Mono<OrderViewSQLDto> findByBarcodeNullSafe(@RequestParam(required = false) int orderId) {
        return Mono.just( this.orderHistoryService.getOrderById(orderId))
                .map(OrderViewSQLDto::new);
    }

}
