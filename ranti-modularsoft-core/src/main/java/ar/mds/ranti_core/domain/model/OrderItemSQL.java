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
@Entity(name="order_cache_detail")
public class OrderItemSQL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OCD_ID")
	private Integer id;
	@Column(name = "PRD_ID")
	private Integer productId;
	@Column(name = "PRD_CODE")
	private String productCode;
	 @Column(name = "OCA_ID")
	 private Integer orderId;
	@Column(name = "OCD_QUANTITY",columnDefinition = "default 0")
	private BigDecimal quantity;
	@Column(name = "OCD_DISCOUNT_PERCENTAGE",columnDefinition = "default 0")
	private BigDecimal discountPercentage;
	@Column(name = "OCD_PRICE",columnDefinition = "default 0")
	private BigDecimal price;
	@Column(name = "OCD_DISCOUNT_AMOUNT",columnDefinition = "default 0")
	private BigDecimal discountAmount;
	@Column(name = "OCD_TOTAL_AMOUNT",columnDefinition = "default 0")
	private BigDecimal totalAmount;
	@Column(name = "OCD_IMPUESTO_INTERNO",columnDefinition = "default 0")
	private BigDecimal internalTax;
	@Column(name = "OCD_OBSERVACION")
	private String observation;
	@Column(name = "OCD_IVA",columnDefinition = "default 0")
	private BigDecimal customerInfo;
	@Column(name = "OCD_AOF_ID")
	private Integer offerId;
//
	@ManyToOne()
	@JoinColumn(name = "PRD_ID",insertable = false,updatable = false)
	private Product product;

}
