package com.lanxi.easyintegral.JFreport;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class ResBody extends Body {
	private String serialNo;	/**平台流水号*/
	private String points;		/**扣除的积分值*/
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "ResBody [serialNo=" + serialNo + ", points=" + points + "]";
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(getIdType());
		element.addElement("idNo").setText(getIdNo());
		element.addElement("reducePoints").setText(getReducePoints());
		element.addElement("serialNo").setText(serialNo);
		element.addElement("points").setText(points);
		return element;
	}
	public static ResBody fromElement(Element element){
		ResBody body=null;
		if(element.getName().trim().equals(NAME)){
			body=new ResBody();
			body.setIdType(element.elementText("idType"));
			body.setIdNo(element.elementText("idNo"));
			body.setReducePoints(element.elementText("reducePoints"));
			body.setSerialNo(element.elementText("serialNo"));
			body.setPoints(element.elementText("points"));
		}
		return body;
	}
}
