package ar.mds.ranti_core.infrastructure.api.dtos;

import ar.mds.ranti_core.domain.model.Ticket;
import ar.mds.ranti_core.domain.model.Shopping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIntDto {

    private String barcode;
    private Integer sold;
    private Long purchaseDate;

    public static ProductIntDto getProductPredictDto(Ticket ticket, String barcode) {

        return ProductIntDto.builder()
                .purchaseDate(Timestamp.valueOf(ticket.getCreationDate()).getTime())
                .barcode(ticket.getShoppingList().stream()
                        .map(Shopping::getBarcode)
                        .filter(shoppingBarcode -> shoppingBarcode.equals(barcode)).findFirst().get())
                .sold(ticket.getShoppingList().stream()
                        .filter(shopping -> shopping.getBarcode().equals(barcode))
                        .map(Shopping::getAmount).reduce(Integer::sum).get())
                .build();

    }

}
