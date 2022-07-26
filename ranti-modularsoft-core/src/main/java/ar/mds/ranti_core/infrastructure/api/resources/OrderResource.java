package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.model.OrderSQL;
import ar.mds.ranti_core.domain.services.OrderService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.OrderReferenceDto;
import ar.mds.ranti_core.infrastructure.api.dtos.OrderSQLDto;
import ar.mds.ranti_core.infrastructure.api.dtos.OrderViewSQLDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping("${ranti.company}" + OrderResource.ORDERS)
public class OrderResource {
    public static final String ORDERS = "/orders";
    public static final String ORDERS_PENDING = "/orders-pending";
    public static final String ORDER_ID_PENDING = "/pending/{order_id}";
    public static final String REFERENCE = "/reference";

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(OrderResource.REFERENCE)
    public Mono<OrderReferenceDto> findByOrderReferenceNullSafe(@RequestParam(required = false) String orderReference) {
        return this.orderService.findByOrderReferenceNullSafe(orderReference)
                .collectList()
                .map(OrderReferenceDto::new);
    }

  /*  @GetMapping()
    public Flux<OrderSQLDto> getAllOrders() {
        return Flux.fromStream( this.orderService.getAllOrders()
                .map(OrderSQLDto::new));
    }
       */

  @PostMapping(produces = {"application/json"})
  public Mono<OrderSQL> create(@Valid @RequestBody OrderSQL order) {
       order.doDefault();
      return Mono.just(this.orderService.create(order));
  }

    @GetMapping()
    public Flux<OrderViewSQLDto> getAllOrdersPending() {
        return Flux.fromStream( this.orderService.getAllOrders()
                .map(OrderViewSQLDto::new));
    }
    @GetMapping(ORDERS_PENDING)
    public Mono<OrderViewSQLDto> getOrderPendingById(@RequestParam(required = false) int orderId) {
        return Mono.just( this.orderService.getOrderById(orderId))
                .map(OrderViewSQLDto::new);
    }
}
