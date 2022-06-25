package ar.mds.ranti_core.infrastructure.api.dtos;


import ar.mds.ranti_core.domain.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderViewSQLDto {

	private Integer id;
//	private Integer providerId;
	private Integer customerId;
	private Integer deviceId;
	private Integer sellerId;
	private Date mobileCreationDate;
	//private Integer serverCreationDate;
	private Integer condition;
	private Integer state;
	private String referenceNumber;
	private String observationFP;
	//private String customerInfo;
	private Integer blockFP;
	// private Integer statePayment;

	private List<OrderItemViewSQLDto> products;
	// private List<OrderHistoryPaymentMethodSQL> paymentMethods;

	public OrderViewSQLDto(OrderHistorySQL orderHistory) {
		BeanUtils.copyProperties(orderHistory, this);
		// BeanUtils.copyProperties(orderHistory.getItems(), this.products);
		this.products= orderHistory.getItems().stream().map(OrderItemViewSQLDto::new ).collect(Collectors.toList()); ;// new OrderItemViewSQLDto(orderHistory.getItems());
	}
	/* public static OrderViewSQLDto orderView(OrderHistorySQL orderHistorySQL) {
		return OrderViewSQLDto.builder()
				.id(orderHistorySQL.getId())

				.build();
	}
		 */

	public OrderViewSQLDto(OrderSQL order) {
		BeanUtils.copyProperties(order, this);
		// BeanUtils.copyProperties(orderHistory.getItems(), this.products);
		this.products= order.getItems().stream().map(OrderItemViewSQLDto::new ).collect(Collectors.toList()); ;// new OrderItemViewSQLDto(orderHistory.getItems());
	}



}
