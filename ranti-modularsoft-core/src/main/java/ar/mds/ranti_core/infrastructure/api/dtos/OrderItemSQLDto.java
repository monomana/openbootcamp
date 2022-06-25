package ar.mds.ranti_core.infrastructure.api.dtos;


import ar.mds.ranti_core.domain.model.OrderItemSQL;
import ar.mds.ranti_core.domain.model.OrderSQL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemSQLDto {

	private Integer id;
	private Integer productId;
	private String productCode;
	private Integer orderId;
	private BigDecimal quantity;
	private BigDecimal discountPercentage;
	private BigDecimal price;
	private BigDecimal discountAmount;
	private BigDecimal totalAmount;
	private BigDecimal internalTax;
	private String observation;
	private BigDecimal customerInfo;
	private Integer offerId;

	// private OrderSQL order;

	public OrderItemSQLDto(OrderItemSQL orderItem) {
		BeanUtils.copyProperties(orderItem, this);

	}




}
