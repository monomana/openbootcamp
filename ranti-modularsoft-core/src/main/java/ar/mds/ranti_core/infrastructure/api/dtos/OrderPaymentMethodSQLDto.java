package ar.mds.ranti_core.infrastructure.api.dtos;


import ar.mds.ranti_core.domain.model.OrderItemSQL;
import ar.mds.ranti_core.domain.model.OrderPaymentMethodSQL;
import ar.mds.ranti_core.domain.model.OrderSQL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.math.BigDecimal;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPaymentMethodSQLDto {

	private Integer id;
	private Integer paymentMethodId;
	private BigDecimal amount;
	private Integer quantityFee;
	private BigDecimal percentege;
	private Integer blockPaymentMethod;
	private Integer orderId;

	// private OrderSQL order;

	public OrderPaymentMethodSQLDto(OrderPaymentMethodSQL orderPaymentMethodSQL) {
		BeanUtils.copyProperties(orderPaymentMethodSQL, this);

	}




}
