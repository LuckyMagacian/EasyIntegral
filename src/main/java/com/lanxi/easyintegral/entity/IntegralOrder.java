package com.lanxi.easyintegral.entity;

import java.util.Random;

import com.lanxi.easyintegral.report.BaoWen;
import com.lanxi.easyintegral.report.Head;
import com.lanxi.easyintegral.report.Msg;
import com.lanxi.easyintegral.report.ReqHead;
import com.lanxi.easyintegral.report.ReqMsg;
import com.lanxi.easyintegral.util.RandomUtil;
import com.lanxi.easyintegral.util.TimeUtil;

public class IntegralOrder {
	/**订单状态-等待扣除积分*/
	public static final String ORDER_STATUS_WAIT	="1";
	/**订单状态-等待发送给交易平台*/
	public static final String ORDER_STATUS_READY	="2";
	/**订单状态-成功*/
	public static final String ORDER_STATUS_SUCCESS	="3";
	/**订单状态-失败*/
	public static final String ORDER_STATUS_FAIL	="4";
	/**订单状态-结束*/
	public static final String ORDER_STATUS_FINISH	="5";
	
	/**订单编号*/
	private String 	id;
	/**交易序号-提交订单的时间*/
	private String transitionSequence;
	/**订单对应下发短信编号*/
	private	String	smsId;
	/**订单对应用户编号*/
	private String	userId;
	/**用户手机号码*/
	private String  phone;
	/**订单对应礼品编号*/
	private String	giftId;
	/**订单礼品数量*/
	private Integer	giftCount;
	/**订单总价*/
	private Double	totalPrice;
	/**订单总积分*/
	private Integer	totalValue;
	/**订单交易时间*/
	private String	workTime;
	/**订单状态*/
	private String	status;
	/**交易平台响应码*/
	private String 	resCode;
	/**交易平台相应信息*/
	private String 	resMsg;
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
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public Integer getGiftCount() {
		return giftCount;
	}
	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(Integer totalValue) {
		this.totalValue = totalValue;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTransitionSequence() {
		return transitionSequence;
	}
	public void setTransitionSequence(String transitionSequence) {
		this.transitionSequence = transitionSequence;
	}
	@Override
	public String toString() {
		return "IntegralOrder [id=" + id + ", transitionSequence=" + transitionSequence + ", smsId=" + smsId
				+ ", userId=" + userId + ", phone=" + phone + ", giftId=" + giftId + ", giftCount=" + giftCount
				+ ", totalPrice=" + totalPrice + ", totalValue=" + totalValue + ", workTime=" + workTime
				+ ", orderStatus=" + status + ", resCode=" + resCode + ", resMsg=" + resMsg + ", remark=" + remark
				+ ", beiy=" + beiy + "]";
	}
	public BaoWen toBaowen(){
		setTransitionSequence(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(4));
		
		BaoWen 	baoWen=new BaoWen();
		Head	head=new ReqHead();
		Msg		msg	=new ReqMsg();
		baoWen.setHead(head);
		baoWen.setMsg(msg);
		
		ReqHead reqHead=(ReqHead)head;
		ReqMsg  reqMsg =(ReqMsg) msg;
		
		reqHead.setMsgNo("1001");
		reqHead.setMsgId(getId());
		reqHead.setReserve("测试");
				
		reqMsg.setCount("1");
		reqMsg.setNeedSend("0");
		reqMsg.setPhone(getPhone());
		reqMsg.setSkuCode("0000000");
		reqMsg.setType("40");
		reqMsg.setRemark("测试");
		baoWen.sign();
		return baoWen;
	}
	
}
