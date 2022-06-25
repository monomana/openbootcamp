package ar.mds.ranti_core.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Order_his_detail")
public class OrderHistoryItemSQL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ODE_ID")
	private Integer id;
	@Column(name = "PRD_ID")
	private Integer productId;
	@Column(name = "PRD_CODE")
	private String productCode;
	 @Column(name = "ORD_ID")
	 private Integer orderId;
	@Column(name = "ODE_QUANTITY",columnDefinition = "default 0")
	private BigDecimal quantity;
	@Column(name = "ODE_DISCOUNT_PERCENTAGE",columnDefinition = "default 0")
	private BigDecimal discountPercentage;
	@Column(name = "ODE_PRICE",columnDefinition = "default 0")
	private BigDecimal price;
	@Column(name = "ODE_DISCOUNT_AMOUNT",columnDefinition = "default 0")
	private BigDecimal discountAmount;
	@Column(name = "ODE_TOTAL_AMOUNT",columnDefinition = "default 0")
	private BigDecimal totalAmount;
	@Column(name = "ODE_IMPUESTO_INTERNO",columnDefinition = "default 0")
	private BigDecimal internalTax;
	@Column(name = "ODE_OBSERVACION")
	private String observation;
	@Column(name = "ODE_IVA",columnDefinition = "default 0")
	private BigDecimal customerInfo;

	@ManyToOne()
	@JoinColumn(name = "PRD_ID",insertable = false,updatable = false)
	private Product product;
//
//	@ManyToOne()
//	@JoinColumn(name = "OCA_ID",insertable = false,updatable = false)
//	private ODEerSQL ODEer;

}
