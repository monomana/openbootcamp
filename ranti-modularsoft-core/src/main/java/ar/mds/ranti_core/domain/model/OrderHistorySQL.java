package ar.mds.ranti_core.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="order_his")
public class OrderHistorySQL {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORD_ID")
	private Integer id;
	@Column(name = "PROV_ID")
	private Integer providerId;
	@Column(name = "CLI_ID")
	private Integer customerId;
	@Column(name = "DEV_ID")
	private Integer deviceId;
	@Column(name = "SEL_ID")
	private Integer sellerId;
	@Column(name = "ORD_MOBILE_CREATION_DATE")
	private Date mobileCreationDate;
	@Column(name = "ORD_SERVER_CREATION_DATE")
	private Date serverCreationDate;
	@Column(name = "ORD_CONDITION")
	private Integer condition;
	@Column(name = "ORD_STATE")
	private Integer state;
	@Column(name = "ORD_NUMERO_COMPROBANTE")
	private String referenceNumber;
	@Column(name = "ORD_OBS_FP")
	private String observationFP;
//	@Column(name = "ORD_DATOS_CLI")
//	private String customerInfo;
	@Column(name = "ORD_BLOQUEAR_FP")
	private Integer blockFP;
//	@Column(name = "ORD_PAGO_ESTADO")
//	private Integer statePayment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "ORD_ID")
	private List<OrderHistoryItemSQL> items;

//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	@JoinColumn(name = "OCA_ID")
//	private List<OrderPaymentMethodSQL> paymentMethods;


//	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	private List<OrderItemSQL> items;

//	@OneToMany(mappedBy = "order_cache", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<OrderPaymentMethodSQL> paymentMethod;





}
