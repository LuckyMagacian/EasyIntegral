package com.lanxi.easyintegral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

/**
 * 消息头接口
 * @author 1
 *
 */
public interface Head {
	public static final String NAME="HEAD";
	public DOMElement toElement();
	public String getSign();
	public void   setSign(String sign);
//	public Head fromElement(Element element);
}
