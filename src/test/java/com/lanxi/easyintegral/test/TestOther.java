package com.lanxi.easyintegral.test;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.junit.Test;

import com.lanxi.easyintegral.report.BaoWen;
import com.lanxi.easyintegral.report.ReqHead;
import com.lanxi.easyintegral.report.ReqMsg;
import com.lanxi.easyintegral.util.HttpUtil;

public class TestOther {

	@Test
	public void testCompare(){
		String str1="012345";
		String str2="123456";
		String str3="abc123";
		String str4="abc234";
		String str5="bcd123";
		System.out.println(str1.compareTo(str2));
		System.out.println(str3.compareTo(str4));
		System.out.println(str5.compareTo(str4));
		System.out.println(str1.compareTo(str3));
	}
	@Test
	public void testDomE(){
		DOMElement element=new DOMElement("666");
		element.addElement("TotalAmt").setText("123"+"");
		Element list=element.addElement("SkuList");
		System.out.println(list.getClass());
	}
	@Test
	public void testXpath(){
		BaoWen baoWen=new BaoWen();
		baoWen.setHead(new ReqHead());
		baoWen.setMsg(new ReqMsg());
		System.out.println(baoWen.toDocument().asXML());
//		System.out.println(baoWen.toDocument().getRootElement().selectSingleNode("//Sign"));
		System.out.println(baoWen.sign());
	}
	@Test
	public void testReplace(){
		String str="O.O[bank]+_+[bank]|_|";
		System.out.println(str.replace("[bank]", "666"));
	}
	@Test
	public void testRec(){
		String phone="15068610940";
		String content="12345";
		HttpUtil.postStr(phone,"http://localhost/easyintegral/receiveSms", "GBK");
	}
}

