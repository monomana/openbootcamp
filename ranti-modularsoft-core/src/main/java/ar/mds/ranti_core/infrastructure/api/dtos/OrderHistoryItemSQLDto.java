package ar.mds.ranti_core.infrastructure.api.dtos;


import ar.mds.ranti_core.domain.model.OrderItemSQL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderHistoryItemSQLDto {

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
	private BigDecimal ivaPercentage;
	private Integer offerId;

	// private OrderSQL order;

	public OrderHistoryItemSQLDto(OrderItemSQL orderItem) {
		BeanUtils.copyProperties(orderItem, this);

	}




}
