package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    @NotNull
    @NotBlank
    private String barcode;
    private String description;
    private BigDecimal priceWhole;
    private BigDecimal stock;
    private String address;
    private String password;
    private Role role;
    private Boolean active;
    private LocalDateTime registrationDate;
    private String token;

    public ProductDto(Product product) {
        BeanUtils.copyProperties(product, this);

    }
   public static ProductDto ofTestRequest(Product product) {
        return ProductDto.builder().description(product.getDescription()).barcode(product.getBarcode())
                .priceWhole(product.getPriceWholer())
                .stock(product.getSalePrice())
                .build();
    }

//    public static UserDto ofMobileFirstName(Product product) {
//        return UserDto.builder().mobile(product.getMobile()).firstName(product.getFirstName()).build();
//    }

    public void doDefault() {
      /*

      if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }

       */
    }
    public ProductDto toProductDto(Product product) {

        BeanUtils.copyProperties(product, this);
        return this;
    }
    public Product toProduct() {
        this.doDefault();
        Product product = new Product();
        BeanUtils.copyProperties(this, product);
        return product;
    }
}
