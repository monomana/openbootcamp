package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ShoppingCartEntity {
    @Id
    private String id;
    private List<ShoppingEntity> shoppingEntities;
    private LocalDateTime updateDate;
    private String mobile;

    public ShoppingCartEntity(ShoppingCart cart) {
        BeanUtils.copyProperties(cart, this);
        if (Objects.nonNull(cart.getMobile())){
            this.mobile = cart.getMobile();
        }
        this.shoppingEntities = new ArrayList<>();
    }

    public void add(ShoppingEntity shoppingEntity) {
        this.shoppingEntities.add(shoppingEntity);
    }

    public ShoppingCart toShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(this, shoppingCart);
        shoppingCart.setShoppingItems(this.shoppingEntities.stream()
                .map(ShoppingEntity::toShopping)
                .collect(Collectors.toList())
        );
        if (Objects.nonNull(this.mobile)) {
            shoppingCart.setMobile(this.mobile);
        }

        return shoppingCart;
    }
}
