package ar.mds.ranti_core.domain.persistence;

import ar.mds.ranti_core.domain.model.Article;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ArticlePersistence {

    Mono<Article> create(Article article);

    Mono< Article > readByBarcode(String barcode);

    Flux< Article > findByAnyNullField();

    Flux< Article > findByBarcodeAndDescriptionAndReferenceAndStockLessThanAndDiscontinuedNullSafe(
            String barcode, String description, String reference, Integer stock, Boolean discontinued);

    Mono< Article > update(String barcode, Article article);

    Mono< Article > readAndWriteStockByBarcodeAssured(String barcode, Integer stockIncrement);

    Flux< String > findByBarcodeAndNotDiscontinuedNullField(String barcode);

    Flux< Article > findByProviderCompanyLike(String providerCompany);

    Mono<Integer> findStockByBarcode(String barcode);
}
