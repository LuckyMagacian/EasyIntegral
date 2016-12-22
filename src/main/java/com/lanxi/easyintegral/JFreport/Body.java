package com.lanxi.easyintegral.JFreport;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class Body {
	/**账户类型-帐号*/
	public static final String CUST_ID_TYPE_ACCOUNT="0";
	/**账户类型-卡号*/
	public static final String CUST_ID_TYPE_CART="1";
	
	public static final String NAME="body";
	private String idType;		/**帐号类型 0-账户号 1-卡号*/
	private String idNo;		/**号码*/
	private String reducePoints;/**扣除积分值*/
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getReducePoints() {
		return reducePoints;
	}
	public void setReducePoints(String reducePoints) {
		this.reducePoints = reducePoints;
	}
	@Override
	public String toString() {
		return "Body [idType=" + idType + ", idNo=" + idNo + ", reducePoints=" + reducePoints + "]";
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(idType);
		element.addElement("idNo").setText(idNo);
		element.addElement("reducePoints").setText(reducePoints);
		return element;
	}
	public static Body fromElement(Element element){
		Body body=null;
		if(element.getName().trim().equals(NAME)){
			body=new Body();
			body.setIdType(element.elementText("idType"));
			body.setIdNo(element.elementText("idNo"));
			body.setReducePoints(element.elementText("reducePoints"));
		}
		return body;
	}
}
