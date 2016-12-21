package com.lanxi.easyintegral.test;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.report.BaoWen;
import com.lanxi.easyintegral.report.ReqHead;
import com.lanxi.easyintegral.report.ReqMsg;
import com.lanxi.easyintegral.service.ConfigServiceImpl;
import com.lanxi.easyintegral.service.EntityService;
import com.lanxi.easyintegral.service.ReceiveService;
import com.lanxi.easyintegral.servlet.Log4jInitServlet;
import com.lanxi.easyintegral.util.SignUtil;
import com.lanxi.easyintegral.util.SmsUtil;
import com.lanxi.easyintegral.util.TempSms;

import sun.misc.Signal;

public class TestReceiveService {

	private ApplicationContext ac;
	private ReceiveService service;
	private EntityService  entityService;
	@Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
        Log4jInitServlet.Log4jInit();
        service=ac.getBean(ReceiveService.class);
        entityService=ac.getBean(EntityService.class);
    }
	@Test
	public void testInit(){
		IntegralOrder order=entityService.getOrder("2016121508270001");
//		System.out.println(((ReqMsg)(order.toBaowen().getMsg())).toElement().asXML());
//		System.out.println(order.toBaowen().toDocument().asXML());
		System.out.println(service.submitOrder(order));
	}
	@Test
	public void testSign(){
		String src="1.010012016121920161219091331浙江省杭州市10000000000000001000000000000000蓝喜电子礼品营销平台2016121508270001测试1506861094030000000010测试f548e11713f845f2";
		System.out.println(SignUtil.md5LowerCase(src, "utf-8"));
	}
	@Test
	public void testBaown(){
		StringBuilder xml=new StringBuilder();
		Document doc=DocumentHelper.createDocument();
		doc.setXMLEncoding("GBK");
		Element root=DocumentHelper.createElement("JFDH");
		root.addElement("HEAD");
		root.addElement("MSG");
		Element head=root.element("HEAD");
		Element msg=root.element("MSG");
		head.addElement("VER");
		head.element("VER").setText("1.0");
		head.addElement("APP");
		head.element("APP").setText("杭州蓝喜积分兑换平台");
		head.addElement("MsgNo");
		head.element("MsgNo").setText("1001");
		head.addElement("CHKDate");
		head.element("CHKDate").setText("20160928");
		head.addElement("WorkDate");
		head.element("WorkDate").setText("20160928");
		head.addElement("WorkTime");
		head.element("WorkTime").setText("165800");
		head.addElement("ADD");
		head.element("ADD").setText("11");;
		head.addElement("SRC");
		head.element("SRC").setText("3311111111111110");
		head.addElement("DES");
		head.element("DES").setText("1000000000000000");
		head.addElement("MsgID");
		head.element("MsgID").setText("20010001");
		head.addElement("Reserve");
		head.element("Reserve").setText("我就试试");
		head.addElement("Sign");
		head.element("Sign").setText("0ab2f634bbac4bfa26203ad8561ab830");
		
		
		msg.addElement("Phone");
		msg.element("Phone").setText("15068610940");
		msg.addElement("SkuCode");
		msg.element("SkuCode").setText("3004");
		msg.addElement("Type");
		msg.element("Type").setText("40");
		msg.addElement("Count");
		msg.element("Count").setText("3");
		msg.addElement("NeedSend");
		msg.element("NeedSend").setText("0");
		msg.addElement("Remark");
		msg.element("Remark").setText("我就试试");
		
		doc.add(root);
		System.out.println(doc.selectSingleNode("/JFDH"));
		System.out.println(doc.asXML());
		BaoWen baowen=BaoWen.fromDocument(doc);
		System.out.println(baowen);
		System.out.println(baowen.sign());
	}
	@Test
	public void testNotice(){
		service.noticeDY("15068610940");
	}
	@Test
	public void testRe(){
		IntegralSmsTemplate template=entityService.getSmsTemplate("1006");
		IntegralSms sms=new IntegralSms();
		sms.setPhone("15068610940");
		String content=template.getContent();
		System.out.println(ConfigServiceImpl.get("bank"));
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		System.out.println(tempSms);
//		String rs=SmsUtil.postSms(tempSms);
	}
}
