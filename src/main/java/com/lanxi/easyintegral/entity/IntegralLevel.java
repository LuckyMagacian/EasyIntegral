package com.lanxi.easyintegral.entity;

public class IntegralLevel {
	/**积分档次编号*/
	private String	id;
	/**积分档次名称*/
	private String	name;
	/**档次积分下限*/
	private Integer	floorValue;
	/**档次积分上限*/
	private Integer ceilValue;
	/**档次状态*/
	private String  status;
	/**备注*/
	private String	remark;
	/**备用字段*/
	private String	beiy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFloorValue() {
		return floorValue;
	}
	public void setFloorValue(Integer floorValue) {
		this.floorValue = floorValue;
	}
	public Integer getCeilValue() {
		return ceilValue;
	}
	public void setCeilValue(Integer ceilValue) {
		this.ceilValue = ceilValue;
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "IntegralLevel [id=" + id + ", name=" + name + ", floorValue=" + floorValue + ", ceilValue=" + ceilValue
				+ ", status=" + status + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}
