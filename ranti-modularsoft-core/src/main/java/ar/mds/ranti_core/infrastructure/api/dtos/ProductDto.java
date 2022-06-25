package ar.mds.ranti_core.infrastructure.api.dtos;

import ar.mds.ranti_core.domain.model.Shopping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotBlank
    private String barcode;
    @NotBlank
    private String description;
    private Integer sold;

    public  ProductDto(Shopping shopping){
        BeanUtils.copyProperties(shopping, this);
        if (shopping.getBarcode() != null) {
            this.barcode = shopping.getBarcode();
        }
        if (shopping.getDescription() != null) {
            this.description = shopping.getDescription();
        }
        if (shopping.getAmount() != null) {
            this.sold = shopping.getAmount();
        }
    }
}
