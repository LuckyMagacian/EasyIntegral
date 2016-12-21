package com.lanxi.easyintegral.entity;

public class IntegralMerchant {
	/**供应商状态-正常*/
	public static final String MERCHANT_STATUS_NORMAL			="0";
	/**供应商状态-异常*/
	public static final String MERCHANT_STATUS_ABNORMAL			="1";
	
	/**供应商编号*/
	private	String	id;
	/**供应商名称*/
	private	String	name;
	/**供应商地址*/
	private	String	addr;
	/**供应商注册时间*/
	private	String	registerTime;
	/**供应商状态*/
	private String  status;
	/**供应商联系方式*/
	private String 	phone;
	/**备注*/
	private String	remark;
	/**备用字段*/
	private String 	beiy;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
		return "IntegralMerchant [id=" + id + ", name=" + name + ", addr=" + addr + ", registerTime=" + registerTime
				+ ", status=" + status + ", phone=" + phone + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
	
}
