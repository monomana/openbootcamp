package ar.mds.ranti_core.infrastructure.api.resources;


import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.services.ArticleService;
import ar.mds.ranti_core.domain.services.ProductService;
import ar.mds.ranti_core.domain.services.TicketService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.ArticleBarcodesDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductsDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductsToPublicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping("${ranti.company}" + ProductResource.PRODUCTS)
public class ProductResource {
    public static final String PRODUCTS = "/products";


    private final ArticleService articleService;
    private final TicketService ticketService;
    private final ProductService productService;

    @Autowired
    public ProductResource(ArticleService articleService, TicketService ticketService, ProductService productService) {
        this.articleService = articleService;
        this.ticketService = ticketService;
        this.productService = productService;
    }

    @GetMapping("/all")
    public Mono<Page<ProductsToPublicDto>> productsPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String field,
            @RequestParam(defaultValue = "true") boolean asc
    ) {

        Page<ProductsToPublicDto> product = (productService.pages(PageRequest.of(page, size, Sort.by(field))).map(ProductsToPublicDto::new));
        if (!asc)
            product = (productService.pages(PageRequest.of(page, size, Sort.by(field).descending())).map(ProductsToPublicDto::new));

        return Mono.just(product);
    }

    @PreAuthorize("permitAll()")
    @GetMapping()
    public Mono<Page<ProductsToPublicDto>> findAllByDescription(
            @RequestParam(defaultValue = "", required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String field,
            @RequestParam(defaultValue = "true") boolean asc
    ) {
        if (!asc)
            return Mono.just((productService.findByDescriptionOrCode(description,
                    PageRequest.of(page, size, Sort.by(field).descending())).map(ProductsToPublicDto::new)));
        return Mono.just((productService.findByDescriptionOrCode(description, PageRequest.of(page, size, Sort.by(field))).map(ProductsToPublicDto::new)));
    }

}
