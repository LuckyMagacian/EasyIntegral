package com.lanxi.easyintegral.JFreport;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.ibm.db2.jcc.am.ne;

public class Original {
	public static final String NAME="original";

	private Head head;/**报文头*/
	private Body body;/**报文信息*/
	
	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.add(head.toElement());
		element.add(body.toElement());
		return element;
	}
	public static Original fromElement(Element element){
		Original original=null;
		if(element.getName().trim().equals(NAME)){
			original=new Original();
			original=new Original();
			original.setHead(ResHead.fromElement(element.element(Head.NAME)));
			original.setBody(ResBody.fromElement(element.element(Body.NAME)));
		}
		return original;
	}
}
