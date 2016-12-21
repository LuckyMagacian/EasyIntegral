package com.lanxi.easyintegral.entity;

public class IntegralSmsTemplate {
	/**短信模版状态-启用*/
	public static final String TEMPLATE_STATUS_NORMAL	="0";
	/**短信模版状态-不启用*/
	public static final String TEMPLATE_STATUS_ABNORMAL	="1";
	
	/**短信模板编号*/
	private	String	id;
	/**短信模板名称*/
	private String	name;
	/**短信模版状态*/
	private String status;
	/**短信模版类型*/
	private String type;
	/**短信模板内容*/
	private String	content;
	/**短信模板自定义内容*/
	private String	moreInfo;
	/**备注*/
	private	String	remark;
	/**备用*/
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMoreInfo() {
		return moreInfo;
	}
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "IntegralSmsTemplate [id=" + id + ", name=" + name + ", status=" + status + ", type=" + type
				+ ", content=" + content + ", moreInfo=" + moreInfo + ", remark=" + remark + ", beiy=" + beiy + "]";
	}
	
}
