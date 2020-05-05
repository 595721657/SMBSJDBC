package com.dragon.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
	private Integer id;   //id 
	private String billCode; //璐﹀崟缂栫爜 
	private String productName; //鍟嗗搧鍚嶇О 
	private String productDesc; //鍟嗗搧鎻忚堪 
	private String productUnit; //鍟嗗搧鍗曚綅
	private BigDecimal productCount; //鍟嗗搧鏁伴噺 
	private BigDecimal totalPrice; //鎬婚噾棰�
	private Integer isPayment; //鏄惁鏀粯 
	private Integer providerId; //渚涘簲鍟咺D 
	private Integer createdBy; //鍒涘缓鑰�
	private Date creationDate; //鍒涘缓鏃堕棿
	private Integer modifyBy; //鏇存柊鑰�
	private Date modifyDate;//鏇存柊鏃堕棿
	
	private String providerName;//渚涘簲鍟嗗悕绉�
	
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public BigDecimal getProductCount() {
		return productCount;
	}
	public void setProductCount(BigDecimal productCount) {
		this.productCount = productCount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getIsPayment() {
		return isPayment;
	}
	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
