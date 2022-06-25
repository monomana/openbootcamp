package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.domain.exceptions.NotFoundException;
import ar.mds.ranti_core.domain.model.Budget;
import ar.mds.ranti_core.domain.persistence.BudgetPersistence;
import ar.mds.ranti_core.infrastructure.mongodb.daos.BudgetReactive;
import ar.mds.ranti_core.infrastructure.mongodb.entities.BudgetEntity;
import ar.mds.ranti_core.infrastructure.mongodb.entities.ShoppingEntity;
import ar.mds.ranti_core.infrastructure.mongodb.daos.ArticleReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BudgetPersistenceMongodb implements BudgetPersistence {

    private final BudgetReactive budgetReactive;
    private final ArticleReactive articleReactive;

    @Autowired
    public BudgetPersistenceMongodb(BudgetReactive budgetReactive, ArticleReactive articleReactive) {
        this.budgetReactive = budgetReactive;
        this.articleReactive = articleReactive;
    }

    @Override
    public Mono<Budget> create(Budget budget) {
        BudgetEntity budgetEntity = new BudgetEntity(budget);
        return Flux.fromStream(budget.getShoppings().stream())
                .flatMap(shopping -> {
                    ShoppingEntity shoppingEntity = new ShoppingEntity(shopping);
                    return this.articleReactive.findByBarcode(shopping.getBarcode())
                            .switchIfEmpty(Mono.error(new NotFoundException("Article: " + shopping.getBarcode())))
                            .map(articleEntity -> {
                                shoppingEntity.setArticleEntity(articleEntity);
                                shoppingEntity.setDescription(articleEntity.getDescription());
                                return shoppingEntity;
                            });
                })
                .doOnNext(budgetEntity::add)
                .then(this.budgetReactive.save(budgetEntity))
                .map(BudgetEntity::toBudget);
    }

    @Override
    public Mono<Budget> readByReference(String reference) {
        return this.budgetReactive.findByReference(reference)
                .map(BudgetEntity::toBudget);
    }
}
