package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CustomerPointsEntity {
    @Id
    private Integer id;
    private Integer value;
    private LocalDate lastDate;
    private String userMobile;

    public CustomerPointsEntity(CustomerPoints customerPoints) {
        BeanUtils.copyProperties(customerPoints, this);
        if (Objects.nonNull(customerPoints.getUser())) {
            this.userMobile = customerPoints.getUser().getMobile();
        }
    }

    public CustomerPoints toCustomerPoints() {

        CustomerPoints customerPoints = new CustomerPoints();
        BeanUtils.copyProperties(this, customerPoints);

        if (Objects.nonNull(this.userMobile)) {
            customerPoints.setUser(User.builder().mobile(this.getUserMobile()).build());
        }

        return customerPoints;
    }

    public CustomerPointsEntity from(CustomerPoints customerPoints) {
        BeanUtils.copyProperties(customerPoints, this, "id");
        return this;
    }
}
