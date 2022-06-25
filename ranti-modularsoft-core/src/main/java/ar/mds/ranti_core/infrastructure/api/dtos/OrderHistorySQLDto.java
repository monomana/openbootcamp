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

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderHistorySQLDto {

	private Integer id;
	private Integer providerId;
	private Integer customerId;
	private Integer deviceId;
	private Integer sellerId;
	private Date mobileCreationDate;
	private Integer serverCreationDate;
	private Integer condition;
	private Integer state;
	private String referenceNumber;
	private String observationFP;
	//private String customerInfo;
	private Integer blockFP;
	private Integer statePayment;

	private List<OrderHistoryItemSQL> items;
	private List<OrderHistoryPaymentMethodSQL> paymentMethods;

	public OrderHistorySQLDto(OrderHistorySQL orderHistory) {
		BeanUtils.copyProperties(orderHistory, this);

	}




}
