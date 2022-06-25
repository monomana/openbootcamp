package ar.mds.ranti_core.infrastructure.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import reactor.util.function.Tuple2;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPredDto {

    private Double stock;
    private String date;

    public ProductPredDto(Tuple2<Double, LocalDate> obj){
        this.stock = Math.floor(obj.getT1());
        this.date = obj.getT2().toString();

    }
}