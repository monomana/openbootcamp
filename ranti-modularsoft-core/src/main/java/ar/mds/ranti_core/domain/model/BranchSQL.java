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
@Entity(name="ramos")
public class BranchSQL {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RAM_ID")
	private Integer id;
	@Column(name = "RAM_DESCRIPCION")
	private String description;
	@Column(name = "RAM_ABREV")
	private String abrev;
	@Column(name = "RAM_CODIGO")
	private String code;
	@Column(name = "RAM_EMPR_ID")
	private Integer companyId;
	@Column(name = "RAM_ESTADO")
	private Integer state;
	@Column(name = "RAM_PARA_WEB")
	private int web;

/*
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "ORD_ID")
	private List<OrderHistoryItemSQL> items;
*/
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	@JoinColumn(name = "OCA_ID")
//	private List<OrderPaymentMethodSQL> paymentMethods;


//	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
//	private List<OrderItemSQL> items;

//	@OneToMany(mappedBy = "order_cache", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<OrderPaymentMethodSQL> paymentMethod;





}
