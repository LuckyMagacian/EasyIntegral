package com.lanxi.easyintegral.JFreport;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class Head {
	public static final String NAME="head";
	private String businessId;		/**业务流水号*/
	private String reqDate;			/**时间按yyyyMMddHHmmss*/
	private String orgId;			/**发起机构号*/
	private String reserve;			/**备用*/
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	@Override
	public String toString() {
		return "Head [businessId=" + businessId + ", reqDate=" + reqDate + ", orgId=" + orgId + ", reserve=" + reserve
				+ "]";
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("businessId").setText(businessId);
		element.addElement("reqDate").setText(reqDate);
		element.addElement("orgId").setText(orgId);
		element.addElement("reserve").setText(reserve==null?"":reserve);
		return element;
	}
	public static Head fromElement(Element element){
		Head head=null;
		if(element.getName().trim().equals(NAME)){
			head=new Head();
			head.setBusinessId(element.elementText("businessId"));
			head.setOrgId(element.elementText("orgId"));
			head.setReqDate(element.elementText("reqDate"));
			head.setReserve(element.elementText("reserve"));
		}
		return head;
	}
}
