package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.Budget;
import ar.mds.ranti_core.domain.services.utils.UUIDBase64;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class BudgetEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String reference;
    private LocalDateTime creationDate;
    private List<ShoppingEntity> shoppings;

    public void add(ShoppingEntity shoppingEntity) {
        this.shoppings.add(shoppingEntity);
    }

    public BudgetEntity(Budget budget) {
        BeanUtils.copyProperties(budget, this);
        this.id = UUIDBase64.URL.encode();
        this.shoppings = new ArrayList<>();
    }

    public Budget toBudget() {
        Budget budget = new Budget();
        BeanUtils.copyProperties(this, budget, "id");
        budget.setShoppings(this.shoppings.stream()
                .map(ShoppingEntity::toShopping)
                .collect(Collectors.toList())
        );
        return budget;
    }
}
