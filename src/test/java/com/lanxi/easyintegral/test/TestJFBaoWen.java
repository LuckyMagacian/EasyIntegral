package com.lanxi.easyintegral.test;

import org.dom4j.DocumentHelper;
import org.junit.Test;

import com.lanxi.easyintegral.JFreport.Body;
import com.lanxi.easyintegral.JFreport.Head;
import com.lanxi.easyintegral.JFreport.JFBaoWen;
import com.lanxi.easyintegral.JFreport.JFPoints;
import com.lanxi.easyintegral.JFreport.Original;
import com.lanxi.easyintegral.util.TimeUtil;

public class TestJFBaoWen {

	@Test
	public void test(){
		Head head=new Head();
		head.setBusinessId(TimeUtil.getDateTime());
		head.setOrgId("1001");
		head.setReqDate(TimeUtil.getDate());
		head.setReserve("");
//		System.out.println(head.toElement().asXML());
		Body body=new Body();
		body.setIdNo(TimeUtil.getDateTime());
		body.setIdType(Body.CUST_ID_TYPE_ACCOUNT);
		body.setReducePoints(100+"");
//		System.out.println(body.toElement().asXML());
		Original original=new Original();
		original.setHead(head);
		original.setBody(body);
//		System.out.println(original.toElement().asXML());
		JFPoints points=new JFPoints();
		points.setOriginal(original);
		points.sign();
		System.out.println(points.toElement().asXML());
		JFBaoWen baoWen=new JFBaoWen();
		baoWen.setPoints(points);
		JFBaoWen rsBaoWen=JFBaoWen.fromDocStr(baoWen.toDocument().asXML());
		System.out.println(rsBaoWen.getPoints().getOriginal().getHead());
	}
}
