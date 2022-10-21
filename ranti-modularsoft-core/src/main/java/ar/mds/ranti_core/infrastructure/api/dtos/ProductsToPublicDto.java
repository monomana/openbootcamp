package ar.mds.ranti_core.infrastructure.api.dtos;


import ar.mds.ranti_core.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsToPublicDto {
    private Integer id;
    private String code;
    @NotNull
    @NotBlank
//    private String barcode;
    private String description;
    private String longDescription;
    //    private BigDecimal salePrice;
    private String thumbnail;
    private String frontImage;
    private String profileImage;
//    private Integer ivaType;

    private BranchDto branch;

    //    private BigDecimal stock;
    private Boolean active;
//    private LocalDateTime registrationDate;


    // List<ImageModel> images;
    // List<dynamic> categories;
    // String commerce;
    private BigDecimal priceWhole;

    public ProductsToPublicDto(Product product) {
        this.branch = new BranchDto(product.getBranch());
        BeanUtils.copyProperties(product, this);

    }

    public static ProductsToPublicDto ofTestRequest(Product product) {
        return ProductsToPublicDto.builder().description(product.getDescription())
                .priceWhole(product.getPriceWholer())
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

    public ProductsToPublicDto toProductDto(Product product) {

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
