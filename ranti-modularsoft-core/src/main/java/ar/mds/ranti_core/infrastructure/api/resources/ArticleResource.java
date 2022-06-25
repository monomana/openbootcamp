package ar.mds.ranti_core.infrastructure.api.resources;


import ar.mds.ranti_core.domain.services.TicketService;
import ar.mds.ranti_core.domain.model.Article;
import ar.mds.ranti_core.domain.services.ArticleService;
import ar.mds.ranti_core.domain.services.ProductService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.ArticleBarcodesDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.stream.Stream;

@Rest
@RequestMapping("${ranti.company}"+ArticleResource.ARTICLES)
public class ArticleResource {
    public static final String ARTICLES = "/articles";
    public static final String BARCODE_ID = "/{barcode}";
    public static final String SEARCH = "/search";
    public static final String UNFINISHED = "/unfinished";
    public static final String BARCODE = "/barcode";
    public static final String COMPANY = "/company";
    public static final String PROVIDER_COMPANY = "/{providerCompany}";
    public static final String OFFERS = "/offers";

    private final ArticleService articleService;
    private final TicketService ticketService;
    private final ProductService productService;

    @Autowired
    public ArticleResource(ArticleService articleService, TicketService ticketService,ProductService productService) {
        this.articleService = articleService;
        this.ticketService = ticketService;
        this.productService = productService;
    }

    @PostMapping(produces = {"application/json"})
    public Mono< Article > create(@Valid @RequestBody Article article) {
        article.doDefault();
        return this.articleService.create(article);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(BARCODE_ID)
    public Mono< Article > read(@PathVariable String barcode) {
        return this.articleService.read(barcode);
    }

    @PutMapping(BARCODE_ID)
    public Mono< Article > update(@PathVariable String barcode, @Valid @RequestBody Article article) {
        article.doDefault();
        return this.articleService.update(barcode, article);
    }

    @GetMapping(SEARCH)
    public Flux< Article > findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
            @RequestParam(required = false) String barcode, @RequestParam(required = false) String description, @
            RequestParam(required = false) String reference, @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Boolean discontinued) {
        return this.articleService.findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
                barcode, description, reference, stock, discontinued)
                .map(Article::ofBarcodeDescriptionStock);
    }

    @GetMapping(BARCODE)
    public Mono< ArticleBarcodesDto > findByBarcodeNullSafe(@RequestParam(required = false) String barcode) {
        return this.articleService.findByBarcodeAndNotDiscontinuedNullSafe(barcode)
                .collectList()
                .map(ArticleBarcodesDto::new);
    }

    @GetMapping(UNFINISHED)
    public Flux< Article > findByUnfinished() {
        return this.articleService.findByUnfinished();
    }


    @GetMapping( COMPANY + PROVIDER_COMPANY)
    public Flux< Article > findByProviderCompanyLike(@PathVariable(required = false) String providerCompany) {
        return this.articleService.findByProviderCompanyLike(providerCompany);
    }

    @GetMapping( "/test/{message}")
    public Mono< String > showMessage(@PathVariable(required = false) String message) {
        return Mono.just("Hola desde " + "${ranti.company}" + message);
    }

   // @PreAuthorize("hasRole('OPERATOR')")
   // @SecurityRequirement(name = "bearerAuth")
   @PreAuthorize("permitAll()")
    @GetMapping(value="/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductsDto> getAllgetAllProducts() {
        return Flux.fromStream( this.productService.getAllProducts()
                .map(ProductsDto::new));
    }

    @GetMapping(OFFERS)
    public Flux<ProductsDto> getOfertas() {
        return Flux.fromStream( this.productService.readAllByCompanyName("")
                .map(ProductsDto::new));
    }


    @GetMapping
    public Mono<Page<ProductsDto> > productsPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String field,
            @RequestParam(defaultValue = "true") boolean asc
    ){
      //  Flux<Page<ProductsDto>> product = Flux.fromStream(productService.pages(PageRequest.of(page, size, Sort.by(field))).map(ProductsDto::new));
        Page<ProductsDto> product = (productService.pages(PageRequest.of(page, size, Sort.by(field))).map(ProductsDto::new));
        if(!asc)
            product =  (productService.pages(PageRequest.of(page, size, Sort.by(field).descending())).map(ProductsDto::new));
        // Mono.just(product);
        return  Mono.just(product);
    }
/* FUNCIONA BIEN PERO NO ENVIA INFO DE LA PAGINACION SOLO EL CONTENIDO

  @GetMapping("/filter")
    public Flux<ProductsDto>searByDescription(
            @RequestParam(defaultValue = "",required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String field,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        if(!asc)
            return Flux.fromStream(  (productService.findByDescriptionOrCode(description, PageRequest.of(page, size, Sort.by(field).descending())).map(ProductsDto::new)));
        return  Flux.fromStream(  (productService.findByDescriptionOrCode(description, PageRequest.of(page, size, Sort.by(field))).map(ProductsDto::new)));
    }
 */
    @GetMapping("/filter")
    public Mono<Page<ProductsDto>>searByDescription(
            @RequestParam(defaultValue = "",required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String field,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        if(!asc)
            return Mono.just(  (productService.findByDescriptionOrCode(description,
                    PageRequest.of(page, size, Sort.by(field).descending())).map(ProductsDto::new)));
        return  Mono.just(  (productService.findByDescriptionOrCode(description, PageRequest.of(page, size, Sort.by(field))).map(ProductsDto::new)));
    }
/*
 @GetMapping
    public Mono<Page<ProductsDto> >searByDescription(
            @RequestParam(defaultValue = "true") String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String field,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        //  Flux<Page<ProductsDto>> product = Flux.fromStream(productService.pages(PageRequest.of(page, size, Sort.by(field))).map(ProductsDto::new));
        Page<ProductsDto> product = (productService.findByDescriptionOrCode(PageRequest.of(page, size, Sort.by(field))).map(ProductsDto::new));
        if(!asc)
            product =  (productService.findByDescriptionOrCode(PageRequest.of(page, size, Sort.by(field).descending())).map(ProductsDto::new));
        // Mono.just(product);
        return  Mono.just(product);
    }
 */
}
