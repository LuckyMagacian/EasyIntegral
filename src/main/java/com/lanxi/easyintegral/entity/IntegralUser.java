package com.lanxi.easyintegral.entity;
/**
 * 用户类-拟作黑名单用户
 * @author 1
 *
 */
public class IntegralUser {
	/**用户订阅状态-订阅*/
	public static final String USER_SUBSTATUS_SUB	="0";
	/**用户订阅状态-取消订阅*/
	public static final String USER_SUBSTATUS_UNSUB	="1";
	
	/**用户编号*/
	private String	id;
	/**用户手机号码*/
	private String	phone;
	/**用户积分*/
	private Integer	pointValue;
	/**用户订阅状态*/
	private String	status;
	/**用户订阅时间*/
	private String	subTime;
	/**用户退订时间*/
	private String	unsubTime;
	/**备注*/
	private String	remark;
	/**备用*/
	private String	beiy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getPointValue() {
		return pointValue;
	}
	public void setPointValue(Integer pointValue) {
		this.pointValue = pointValue;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubTime() {
		return subTime;
	}
	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}
	public String getUnsubTime() {
		return unsubTime;
	}
	public void setUnsubTime(String unsubTime) {
		this.unsubTime = unsubTime;
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
	@Override
	public String toString() {
		return "IntegralUser [id=" + id + ", phone=" + phone + ", pointValue=" + pointValue + ", subStatus=" + status
				+ ", subTime=" + subTime + ", unsubTime=" + unsubTime + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}
