package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.persistence.BudgetPersistence;
import ar.mds.ranti_core.domain.services.utils.PdfBudgetBuilder;
import ar.mds.ranti_core.domain.services.utils.UUIDBase64;
import ar.mds.ranti_core.domain.model.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class BudgetService {

    private final BudgetPersistence budgetPersistence;

    @Autowired
    public BudgetService(BudgetPersistence budgetPersistence) {
        this.budgetPersistence = budgetPersistence;
    }

    public Mono<Budget> create(Budget budget) {
        budget.setReference(UUIDBase64.URL.encode());
        budget.setCreationDate(LocalDateTime.now());
        return this.budgetPersistence.create(budget);
    }

    public Mono<byte[]> readReceipt(String reference) {
        return this.budgetPersistence.readByReference(reference)
                .map(new PdfBudgetBuilder()::generateBudget);
    }
}
