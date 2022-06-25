package ar.mds.ranti_core.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="order_cache")
public class OrderSQL {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OCA_ID")
	private Integer id;
	@Column(name = "PROV_ID")
	private Integer providerId;
	@Column(name = "CLI_ID")
	private Integer customerId;
	@Column(name = "DEV_ID")
	private Integer deviceId;
	@Column(name = "SEL_ID")
	private Integer sellerId;
	@Column(name = "OCA_MOBILE_CREATION_DATE")
	private Date mobileCreationDate;
	@Column(name = "OCA_SERVER_CREATION_DATE")
	private Date serverCreationDate;
	@Column(name = "OCA_CONDITION")
	private Integer condition;
	@Column(name = "OCA_STATE")
	private Integer state;
	@Column(name = "OCA_NUMERO_COMPROBANTE")
	private String referenceNumber;
	@Column(name = "OCA_OBS_FP")
	private String observationFP;
	@Column(name = "OCA_DATOS_CLI")
	private String customerInfo;
	@Column(name = "OCA_BLOQUEAR_FP")
	private Integer blockFP;
	@Column(name = "OCA_PAGO_ESTADO")
	private Integer statePayment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "OCA_ID")
	private List<OrderItemSQL> items;

	public void doDefault() {
		if (Objects.isNull(observationFP)) {
			observationFP = "";
		}
		if (Objects.isNull(statePayment)) {
			statePayment = 0;
		}

	}

//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	@JoinColumn(name = "OCA_ID")
//	private List<OrderPaymentMethodSQL> paymentMethods;


//	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	private List<OrderItemSQL> items;

//	@OneToMany(mappedBy = "order_cache", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<OrderPaymentMethodSQL> paymentMethod;





}
