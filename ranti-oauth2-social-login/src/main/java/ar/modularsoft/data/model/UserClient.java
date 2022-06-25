package ar.modularsoft.data.model;


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
@Entity(name = "client")
public class UserClient {

    /*	UNIQUE KEY `IDX_CLI_NAME` (`CLI_NAME`,`CLI_ID`) USING BTREE,
        KEY `IDX_ZON_CODE` (`ZON_CODE`) USING BTREE,
        KEY `IDX_SEL_CODE` (`SEL_CODE`) USING BTREE,
        KEY `IDX_CUIT` (`CLI_CUIT`)
                ) ENGINE=InnoDB AUTO_INCREMENT=473 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLI_ID")
    private int id;
    @Column(name = "CLI_NAME")
    private String name;
    @Column(name = "CLI_STATE")
    private int state;
    @Column(name = "ZON_CODE", columnDefinition = "default 0")
    private int zoneCode;
    @Column(name = "SEL_CODE", columnDefinition = "default 0")
    private int sellerCode;
    @Column(name = "CLI_CUIT")
    private String codeCUIT;
    @Column(name = "CLI_DIRECCION")
    private String address;
    @Column(name = "CLI_TIPOIVA")
    private String typeIVA;
    @Column(name = "CLI_DIASVISITA")
    private String visitDay;
    @Column(name = "CLI_ORDEN", columnDefinition = "default 0")
    private int orderClient;
    @Column(name = "CLI_CATEGORIA", columnDefinition = "default 0")
    private int category;
    @Column(name = "CLI_MAIL")
    private String email;
    @Column(name = "CLI_TEL1")
    private String phone;
    @Column(name = "CLI_DIRECCION_ENTREGA")
    private String deliveryAddress;
    @Column(name = "CLI_CP")
    private String postalCode;
    @Column(name = "CLI_LOCALIDAD")
    private String location;
    @Column(name = "CLI_CODIGO")
    private String code;
    @Column(name = "CLI_PROVINCIA")
    private String province;
    @Column(name = "CLI_USER")
    private String username;
    @Column(name = "CLI_PASS")
    private String password;
    @Column(name = "CLI_TIPO", columnDefinition = "default 0")
    private int typeCustomer;
    @Column(name = "CLI_CASA_CENTRAL_ID", columnDefinition = "default 0")
    private int centralId;
    @Column(name = "CLI_BLOQUEAR_CRED_PERS", columnDefinition = "default 0")
    private int lockPersonalCredit;
    @Column(name = "CLI_CRED_LIMITE", columnDefinition = "default 0")
    private BigDecimal creditLimit;
    @Column(name = "CLI_LISTA", columnDefinition = "default 0")
    private int list;
    @Column(name = "CLI_LIQ_IMPORTE", columnDefinition = "default 0")
    private BigDecimal liqImporte;
    @Column(name = "CLI_MAXPORCDESCTO", columnDefinition = "default 0")
    private BigDecimal maxPorcDescto;
    @Column(name = "CLI_THUMBNAIL")
    private String thumbnail;

/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(int zoneCode) {
        this.zoneCode = zoneCode;
    }

    public int getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(int sellerCode) {
        this.sellerCode = sellerCode;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCodeCUIT() {
        return codeCUIT;
    }

    public void setCodeCUIT(String codeCUIT) {
        this.codeCUIT = codeCUIT;
    }

    public String getTypeIVA() {
        return typeIVA;
    }

    public void setTypeIVA(String typeIVA) {
        this.typeIVA = typeIVA;
    }

    public String getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(String visitDay) {
        this.visitDay = visitDay;
    }

    public int getOrderClient() {
        return orderClient;
    }

    public void setOrderClient(int orderClient) {
        this.orderClient = orderClient;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(int typeCustomer) {
        this.typeCustomer = typeCustomer;
    }

    public int getCentralId() {
        return centralId;
    }

    public void setCentralId(int centralId) {
        this.centralId = centralId;
    }

    public int getLockPersonalCredit() {
        return lockPersonalCredit;
    }

    public void setLockPersonalCredit(int lockPersonalCredit) {
        this.lockPersonalCredit = lockPersonalCredit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }
    */
}


