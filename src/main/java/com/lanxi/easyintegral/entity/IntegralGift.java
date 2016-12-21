package com.lanxi.easyintegral.entity;

public class IntegralGift {
	/**商品状态-正常*/
	public static final String GIFT_STATUS_NORMAL		="1";
	/**商品状态-仅限兑换*/
	public static final String GIFT_STATUS_EXCHANGE_ONLY="2";
	/**商品状态-兑罄*/
	public static final String GIFT_STATUS_NONE			="4";
	/**商品状态-下架*/
	public static final String GIFT_STATUS_SOLDOUT		="3";

	
	
	
	/**商品编号*/
	private String 	id;
	/**第三方（供应商）商品编号*/
	private String	thirdId;
	/**供应商编号*/
	private String  merchantId;
	/**对应积分档次编号*/
	private String 	levelId;
	/**商品名称*/
	private String	name;
	/**商品数量 默认1*/
	private Integer	count;
	/**商品库存数量*/
	private Integer lessCount;
	/**商品类型*/
	private String	type;
	/**商品价格*/
	private Double 	price;
	/**商品积分值*/
	private Integer	value;
	/**商品状态*/
	private String	status;
	/**商品上架时间*/
	private String	putawayTime;
	/**商品下架时间*/
	private String	soldoutTime;
	/**商品修改时间*/
	private String	modifyTime;
	/**商品备注*/
	private String	remark;
	/**备用字段*/
	private String	beiy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getLessCount() {
		return lessCount;
	}
	public void setLessCount(Integer lessCount) {
		this.lessCount = lessCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPutawayTime() {
		return putawayTime;
	}
	public void setPutawayTime(String putawayTime) {
		this.putawayTime = putawayTime;
	}
	public String getSoldoutTime() {
		return soldoutTime;
	}
	public void setSoldoutTime(String soldoutTime) {
		this.soldoutTime = soldoutTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBeiy() {
		return beiy;
	}
	public void setBeiy(String beiy) {
		this.beiy = beiy;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	@Override
	public String toString() {
		return "IntegralGift [id=" + id + ", thirdId=" + thirdId + ", merchantId=" + merchantId + ", levelId=" + levelId
				+ ", name=" + name + ", count=" + count + ", lessCount=" + lessCount + ", type=" + type + ", price="
				+ price + ", value=" + value + ", status=" + status + ", putawayTime=" + putawayTime + ", soldoutTime="
				+ soldoutTime + ", modifyTime=" + modifyTime + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}
