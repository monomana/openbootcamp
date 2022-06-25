package ar.modularsoft.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="articulos")
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ART_ID")
	private Integer id;
	@Column(name = "ART_CODIGO")
	private String code;
	@Column(name = "ART_DESCRIPCION")
	private String description;
	@Column(name = "ART_DESCLARGA")
	private String longDescription;
	@Column(name = "ART_CODBARRA")
	private String barcode;
	@Column(name = "ART_FRACCIONABLE")
	private Integer fractional;
	@Column(name = "ART_RAMO")
	private Integer branch;
	@Column(name = "ART_RUBRO")
	private Integer category;
	@Column(name = "ART_SUBRUBRO")
	private Integer subCategory;
	@Column(name = "ART_MARCA")
	private Integer trademark;
	@Column(name = "ART_TIPO")
	private String type;
	@Column(name = "ART_NPESO")
	private String nPeso;
	@Column(name = "ART_NUMED")
	private Integer numed;
	@Column(name = "ART_NUBULFLOTANTE",columnDefinition = "default 0")
	private BigDecimal nubulFloating;
	@Column(name = "ART_DIAS_GARANTIA")
	private Integer daysGuarantee;
	@Column(name = "ART_IMAGEN")
	private String image;
	@Column(name = "ART_CONSIGNACI")
	private Integer consignment;
	@Column(name = "ART_DISCONTINUADO")
	private Integer discontinued;
	@Column(name = "ART_AUTOMATIZADO")
	private Integer automated;
	@Column(name = "ART_SERVICIO",columnDefinition = "default 0")
	private BigDecimal service;
	@Column(name = "ART_NO_TRANSMITIR")
	private Integer doNotTransmit;
	@Column(name = "ART_COMANDA")
	private Integer command;
	@Column(name = "ART_NCOSTOSINIMPUESTO",columnDefinition = "default 0")
	private BigDecimal costWithoutTax;
	@Column(name = "ART_NCOSTOCONIMPUESTO",columnDefinition = "default 0")
	private BigDecimal costWithTax;
	@Column(name = "ART_NPRECIOCOSTO",columnDefinition = "default 0")
	private BigDecimal costPrice;
	@Column(name = "ART_FLETE",columnDefinition = "default 0")
	private BigDecimal freight;
	@Column(name = "ART_NPRECIOMAYORISTA",columnDefinition = "default 0")
	private BigDecimal priceWholer;
	@Column(name = "ART_NPRECIOVENTA",columnDefinition = "default 0")
	private BigDecimal salePrice;
	@Column(name = "ART_IVAINCLUIDO")
	private Integer includedIVA;
	@Column(name = "ART_IVT_ID")
	private Integer typeIVA;
	@Column(name = "ART_PRECIO_DOLARES")
	private Integer dollarPrice;
	@Column(name = "ART_NPORCIMPINT",columnDefinition = "default 0")
	private BigDecimal percentageOfInternalTax;
	@Column(name = "ART_NDECRETO802",columnDefinition = "default 0")
	private BigDecimal decree802;
	@Column(name = "ART_NPORCRENTAS",columnDefinition = "default 0")
	private BigDecimal percentageOfRents;
	@Column(name = "ART_NRECCF")
	private String recCF;
	@Column(name = "ART_NPREMIO")
	private String reward;
	@Column(name = "ART_EMPR_ID",columnDefinition = "default 1")
	private Integer IntegerernalCompanyId;
	@Column(name="ART_FECHA_UM")
	private Date editAt;
	@Column(name="ART_FECHA_ULTUPD")
	private Date updateAt;
	@Column(name = "ART_ESTADO",columnDefinition = "default 0")
	private Integer state;
	@Column(name = "ART_CUENTAII")
	private String accountII;
	@Column(name = "ART_CUENTACMV")
	private String accountCMV;
	@Column(name = "ART_CUENTA_COMPRAS")
	private String accountDashShopping;
	@Column(name = "ART_CUENTAVENTAS")
	private String accountSales;
	@Column(name = "ART_CUENTACOMPRAS")
	private String accountShopping;
	@Column(name = "ART_PERMITE_PRECIOS_UPD",columnDefinition = "default 0")
	private Integer allowsPriceUpdate;
	@Column(name = "ART_ES_COMBO",columnDefinition = "default 0")
	private Integer isCombo;
	@Column(name = "ART_CODCOLOR",columnDefinition = "default 0")
	private Integer colorCode;
	@Column(name = "ART_UNIDADES",columnDefinition = "default 0")
	private Integer units;
	@Column(name = "ART_NPORCENTAJE_FACTURACION")
	private String percentageBilling;
	@Column(name = "ART_CONTROL_FIN_TURNO",columnDefinition = "default 0")
	private Integer endOfShiftControl;
	@Column(name = "ART_EMBALAJE",columnDefinition = "default 0")
	private Integer packaging;
	@Column(name = "ART_PAQUETE",columnDefinition = "default 0")
	private Integer packet;
	@Column(name = "ART_UNIDAD_CONVERSION",columnDefinition = "default 0")
	private Integer conversionUnit;
	@Column(name = "ART_SERVICIO_PLAYA")
	private String beachService;
	@Column(name = "ART_ARTE_ID",columnDefinition = "default 0")
	private Integer arteId;
	@Column(name = "ART_OCULTAR",columnDefinition = "default 0")
	private Integer hide;
	@Column(name = "ART_PERMITE_MODPRECIO",nullable = false, columnDefinition = "TINYInteger", length = 1)
	private boolean allowsEditPrice;
	@Column(name="ART_FECHA_ALTA")
	private Date createdAt;
	@Column(name = "ART_IVA_COTIZACION",columnDefinition = "default 0")
	private BigDecimal quoteIVA;
	@Column(name = "ART_USR_ID",columnDefinition = "default 0")
	private Integer userId;
	@Column(name = "ART_COSTODESCINC",columnDefinition = "default 0")
	private BigDecimal costDiscountIncrement;
	@Column(name="ART_FECHAFINREGALO")
	private Date giftEndDate;
	@Column(name = "ART_NPORCPERCIVA",columnDefinition = "default 0")
	private BigDecimal percentagePerceptionIVA;
	@Column(name = "ART_PASAR_BALANZA",columnDefinition = "default 0")
	private Integer transferProductToScales;
	@Column(name = "ART_TIPO_PESO",columnDefinition = "default 0")
	private Integer typeWeight;
	@Column(name = "ART_TARA",columnDefinition = "default 0")
	private BigDecimal tara;
	@Column(name = "ART_VTO",columnDefinition = "default 0")
	private Integer expiration;
	@Column(name = "ART_ALQUILER",columnDefinition = "default 0")
	private Integer rental;
	@Column(name = "ART_PIEZA",columnDefinition = "default 0")
	private Integer piece;
	@Column(name = "ART_TALLE_ID",columnDefinition = "default 0")
	private Integer waist;
	@Column(name = "ART_NRO",columnDefinition = "default 0")
	private BigDecimal number;
	@Column(name = "ART_PIEZA_CANT",columnDefinition = "default 0")
	private Integer pieceQuantity;
	@Column(name = "ART_ALQUILER_PORC",columnDefinition = "default 0")
	private BigDecimal percentageRental;
	@Column(name = "ART_NPRECIOALQUILER",columnDefinition = "default 0")
	private BigDecimal rentalPrice;
	@Column(name = "ART_VENTACONENVASE",columnDefinition = "default 0")
	private Integer saleWithContainer;
	@Column(name = "ART_ID_ENVASE",columnDefinition = "default 0")
	private Integer containerId;
	@Column(name = "ART_COMISION_PORCMAY",columnDefinition = "default 0")
	private BigDecimal comisionPercentageWholesaler;
	@Column(name = "ART_COMISION_PORCMIN",columnDefinition = "default 0")
	private BigDecimal comisionPercentageRetailer;
	@Column(name = "ART_COMISION_UNIDMAY",columnDefinition = "default 0")
	private BigDecimal comisionUnitWholesaler;
	@Column(name = "ART_COMISION_UNIDMIN",columnDefinition = "default 0")
	private BigDecimal comisionUnitRetailer;
	@Column(name = "ART_COMISION_NOPAGA",columnDefinition = "default 0")
	private Integer comisionNotPay;
	@Column(name = "ART_NRO_SERIE")
	private String serialNumber;
	@Column(name = "ART_CODUNICO")
	private String uniqueCode;
	@Column(name = "ART_MEDIDA",columnDefinition = "default 0")
	private Integer measure;
	@Column(name = "ART_MODELO",columnDefinition = "default 0")
	private Integer model;
	@Column(name = "ART_RENAR",columnDefinition = "default 0")
	private Integer renar;
	@Column(name = "ART_CATWEB")
	private String webCategory;
	@Column(name = "ART_ESWEB",columnDefinition = "default 0")
	private Integer isWeb;
	@Column(name = "ART_IMAGEN_FRENTE")
	private String frontImage;
	@Column(name = "ART_IMAGEN_PERFIL")
	private String profileImage;
	@Column(name = "ART_CONTABLE",columnDefinition = "default 0")
	private Integer accountant;
	@Column(name = "ART_CATEGORIA_TARSOCIAL",columnDefinition = "default 0")
	private Integer socialCardCategory;
	@Column(name = "ART_DISP_FACT_COMP",columnDefinition = "default 0")
	private Integer availableForPurchaseInvoice;


/*
	@Column(name = "ART_STOCK",columnDefinition = "default 0")
	private BigDecimal stock;
	@Column(name = "ART_ORIGEN",columnDefinition = "default 0")
	private Integer origin;
	@Column(name = "ART_DESCUENTO",columnDefinition = "default 0")
	private BigDecimal discount;
	@Column(name = "ART_IMPUESTO_IntegerERNO",columnDefinition = "default 0")
	private BigDecimal IntegerernalTax;
	@Column(name = "ART_ALICUOTA_IVA",columnDefinition = "default 0")
	private BigDecimal aliquotIva;
	@Column(name = "ART_PRECIOLP1",columnDefinition = "default 0")
	private BigDecimal priceLP1;
	@Column(name = "ART_PRECIOMIN",columnDefinition = "default 0")
	private BigDecimal priceRetailer;
*/



}
