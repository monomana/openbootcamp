package ar.mds.ranti_core.infrastructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import ar.mds.ranti_core.domain.model.CustomerPoints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointsDto {
    private Integer id;
    private Integer value;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastDate;
    private String userMobile;

    public  CustomerPointsDto(CustomerPoints customerPoints){
        BeanUtils.copyProperties(customerPoints, this);
        if (customerPoints.getUser() != null) {
            this.userMobile = customerPoints.getUser().getMobile();
        }
    }
}
