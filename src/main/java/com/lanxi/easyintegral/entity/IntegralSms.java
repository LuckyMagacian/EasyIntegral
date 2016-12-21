package com.lanxi.easyintegral.entity;

import com.lanxi.easyintegral.util.RandomUtil;
import com.lanxi.easyintegral.util.SignUtil;
import com.lanxi.easyintegral.util.TempSms;
import com.lanxi.easyintegral.util.TimeUtil;

public class IntegralSms {
	/**短信状态-等待发送*/
	public static final String SMS_STATUS_SEND_READY		="1";
	/**短信状态-发送失败*/
	public static final String SMS_STATUS_SEND_FAIL			="2";
	/**短信状态-发送成功*/
	public static final String SMS_STATUS_SEND_SUCCESS		="3";
	/**短信状态-等待回复*/
	public static final String SMS_STATUS_REPLY_READY		="4";
	/**短信状态-收到回复且可以继续回复*/
	public static final String SMS_STATUS_REPLY_RECEIVE		="5";
	/**短信状态-回复次数限制*/
	public static final String SMS_STATUS_REPLY_TIME_LIMIT	="6";
	/**短信状态-过期*/
	public static final String SMS_STATUS_OVERTIME			="7";
	
	/**短信通知-兑换成功*/
	public static final String SMS_NOTICE_SUCCESS			="1";
	/**短信通知-兑换异常*/
	public static final String SMS_NOTICE_EXCEPTION			="2";
	/**短信通知-回复错误*/
	public static final String SMS_NOTICE_REPLY_ERROR		="3";
	/**短信通知-兑换次数受限*/
	public static final String SMS_NOTICE_TIME_LIMIT		="4";
	/**短信通知-积分不足*/
	public static final String SMS_NOTICE_POINT_LACK		="5";
	/**短信通知-短信过期*/
	public static final String SMS_NOTICE_OVERTIME			="6";
	/**短信通知-没有收到兑换短信*/
	public static final String SMS_NOTICE_NOSMS				="7";
	/**短信通知-无需回复*/
	public static final String SMS_NOTICE_NOREPLY			="8";
	
	/**短信通知-订阅成功*/
	public static final String SMS_NOTICE_SUB				="9";
	/**短信通知-退订成功*/
	public static final String SMS_NOTICE_UNSUB				="10";
	/**短信编号*/
	private String 	id;
	/**用户编号*/
	private String 	userId;
	/**短信模板编号*/
	private String	templateId;
	/**商品编号-用‘,’连接*/
	private String	giftId;
	/**短信发送状态*/
	private String	status;
	/**手机号码*/
	private String	phone;
	/**短信内容*/
	private String	content;
	/**发送时间*/
	private String	sendTime;
	/**过期时间*/
	private String  overTime;
	/**发送次数-包括失败*/
	private Integer	sendTimes;
	/**最大发送次数*/
	private Integer	maxSendTimes;
	/**最近回复时间*/
	private String	replyTime;
	/**总有效回复次数*/
	private Integer	replyTimes;
	/**最大有效回复次数*/
	private Integer	maxReplyTimes;
	/**短信平台相应码*/
	private String	resCode;
	/**短信平台相应信息*/
	private String	resMsg;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
	public Integer getMaxSendTimes() {
		return maxSendTimes;
	}
	public void setMaxSendTimes(Integer maxSendTimes) {
		this.maxSendTimes = maxSendTimes;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public Integer getReplyTimes() {
		return replyTimes;
	}
	public void setReplyTimes(Integer replyTimes) {
		this.replyTimes = replyTimes;
	}
	public Integer getMaxReplyTimes() {
		return maxReplyTimes;
	}
	public void setMaxReplyTimes(Integer maxReplyTimes) {
		this.maxReplyTimes = maxReplyTimes;
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
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public TempSms toTempSms(){
		TempSms temp=new TempSms();
		temp.setMchtId(TempSms.SMS_MCHTID);
		temp.setOrderId(temp.getMchtId()+TimeUtil.getDateTime()+RandomUtil.getRandomChar(4));
		temp.setMobile(this.phone);
		temp.setContent(this.content);
		temp.setTradeDate(TimeUtil.getDate());
		temp.setTradeTime(TimeUtil.getTime());
		temp.setTdId(TempSms.SMS_TDID_WODONG);
		return temp;
	}
	@Override
	public String toString() {
		return "IntegralSms [id=" + id + ", userId=" + userId + ", templateId=" + templateId + ", giftId=" + giftId
				+ ", status=" + status + ", phone=" + phone + ", content=" + content + ", sendTime=" + sendTime
				+ ", overTime=" + overTime + ", sendTimes=" + sendTimes + ", maxSendTimes=" + maxSendTimes
				+ ", replyTime=" + replyTime + ", replyTimes=" + replyTimes + ", maxReplyTimes=" + maxReplyTimes
				+ ", resCode=" + resCode + ", resMsg=" + resMsg + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}
