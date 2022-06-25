package ar.mds.ranti_core.infrastructure.api.dtos;


import ar.mds.ranti_core.domain.model.OrderHistoryItemSQL;
import ar.mds.ranti_core.domain.model.OrderItemSQL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemViewSQLDto {
	/*
	"id": 1,
			"code": "10010",
			"barcode": "10010",
			"description": "COE SOFT MINI X 40GR.",
			"longDescription": "Material de rebase temporal acrílico para amortiguar la presión masticatoria en dentaduras parciales y totales.\r\nSe trata de un material de rebase temporal acrílico. Evita la irritación del tejido oral. Para rebases temporales de larga duración, 3-6 meses.Un ajuste óptimo de la prótesis",
			"salePrice": 504.5718,
			"thumbnail": "https://www.freeiconspng.com/uploads/no-image-icon-15.png",
			"frontImage": "",
			"profileImage": ""
*/
	private Integer id;
	private Integer code;
	private String barcode;
	private String description;
	private String longDescription;
	// private Integer orderId;
	private BigDecimal quantity;
	// private BigDecimal discountPercentage;
	private BigDecimal salePrice;
	private String thumbnail;
	private String frontImage;
	private String profileImage;
	// private BigDecimal discountAmount;
	// private BigDecimal totalAmount;
	// private BigDecimal internalTax;
	// private String observation;
	// private BigDecimal customerInfo;
	// private Integer offerId;

	// private OrderSQL order;

	public OrderItemViewSQLDto(OrderItemSQL orderItem) {
		BeanUtils.copyProperties(orderItem, this);
		this.barcode = orderItem.getProductCode();
		this.salePrice=orderItem.getPrice();
		BeanUtils.copyProperties(orderItem.getProduct(), this);
	}


	public OrderItemViewSQLDto(OrderHistoryItemSQL orderHistoryItemSQL) {
		BeanUtils.copyProperties(orderHistoryItemSQL, this);
		this.barcode = orderHistoryItemSQL.getProductCode();
		this.salePrice=orderHistoryItemSQL.getPrice();
		BeanUtils.copyProperties(orderHistoryItemSQL.getProduct(), this);
	}
}
