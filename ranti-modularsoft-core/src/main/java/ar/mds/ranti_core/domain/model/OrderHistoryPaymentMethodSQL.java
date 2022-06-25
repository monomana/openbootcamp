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
@Entity(name="order_his_fp")
public class OrderHistoryPaymentMethodSQL {
/*
'OCAF_ID', 'int(10) unsigned', 'NO', 'PRI', '', 'auto_increment'
'OCAF_FP_ID', 'int(10) unsigned', 'NO', '', '', ''
'OCAF_IMPORTE', 'decimal(14,4)', 'NO', '', '0.0000', ''
'OCAF_CANT_CUOTAS', 'int(10) unsigned', 'NO', '', '0', ''
'OCAF_PORCENTAJE', 'decimal(14,4)', 'NO', '', '0.0000', ''
'OCAF_BLOQUEAR_FP', 'int(10) unsigned', 'NO', '', '0', ''
'OCA_ID', 'int(10) unsigned', 'NO', '', '', ''
 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OCAF_ID")
	private Integer id;
	@Column(name = "OCAF_FP_ID")
	private Integer paymentMethodId;
	@Column(name = "OCAF_IMPORTE",columnDefinition = "default 0")
	private BigDecimal amount;
	@Column(name = "OCAF_CANT_CUOTAS")
	private Integer quantityFee;
	@Column(name = "OCAF_PORCENTAJE",columnDefinition = "default 0")
	private BigDecimal percentege;
	@Column(name = "OCAF_BLOQUEAR_FP")
	private Integer blockPaymentMethod;
	@Column(name = "OCA_ID")
	private Integer orderId;

//	@ManyToOne()
//	@JoinColumn(name = "OCA_ID",insertable = false,updatable = false)
//	private OrderSQL order;






}
