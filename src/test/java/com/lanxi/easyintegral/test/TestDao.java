package com.lanxi.easyintegral.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.ibm.db2.jcc.am.vc;
import com.lanxi.easyintegral.entity.*;
import com.lanxi.easyintegral.service.EntityService;
import com.lanxi.easyintegral.util.BeanUtil;
import com.lanxi.easyintegral.util.CheckReplaceUtil;
import com.lanxi.easyintegral.util.SqlUtilForDB;
import com.lanxi.easyintegral.util.TempSms;
import com.lanxi.easyintegral.util.TimeUtil;
public class TestDao {
	private ApplicationContext ac;
	private EntityService service;
	@Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
        service=ac.getBean(EntityService.class);
    }
	@Test
	public void testInit(){
		System.out.println(service.getUser("15068610940"));
	}
	@Test
	public void testAdd() throws Exception{
		Class<?> clazz=IntegralSmsTemplate.class;
		Map<String, Field> map=BeanUtil.getFieldsNoStatic(clazz);
		Object obj=clazz.newInstance();
		Scanner input=new Scanner(System.in);
		String goon="";
		while(!goon.equals("no")){
		for(Map.Entry<String, Field> each:map.entrySet()){
			String name	=each.getKey();
			Field  field=each.getValue();
			field.setAccessible(true);
			System.out.println("input "+CheckReplaceUtil.firstCharLowcase(clazz.getSimpleName())+"'s "+name+" (type is "+field.getType().getSimpleName()+") :");
			String value=input.nextLine();
			if(field.getType().equals(String.class)){
				field.set(obj, value);
				continue;
			}
			if(field.getType().equals(Double.class)){
				field.set(obj, Double.parseDouble(value));
				continue;
			}
			if(field.getType().equals(Integer.class)){
				field.set(obj, Integer.parseInt(value));
				continue;
			}
			System.out.println("error");
		}
		System.out.println(JSONObject.toJSONString(obj));
		
		service.addSmsTemplate((IntegralSmsTemplate)obj);
		System.out.println("go on ? yes/no:");
		goon=input.nextLine();
		}
		input.close();
	}
	@Test
	public void testOrder(){
		
	}
	@Test
	public void addTemplate(){
		IntegralSmsTemplate template=new IntegralSmsTemplate();
		template.setId("1001");
		template.setName("兑换上限");
		template.setContent(
				"【[bank]】尊敬的用户，您的综合积分已累计[point]分，我们为您推出短信积分兑换服务，[gifts]。[ad]本短信有效期至[overtime]，详询[bank][回 TD退订]。"
				);
		template.setStatus("0");
		template.setType("0");
		template.setMoreInfo("这里是广告哦!");
		template.setRemark("测试");
		template.setBeiy("测试");
		service.modifySmsTemplate(template);
	}
	@Test
	public void addLevel(){
		IntegralLevel level=new IntegralLevel();
		level.setId("1002");
		level.setName("测试2档");
		level.setStatus("0");
		level.setFloorValue(6000);
		level.setCeilValue(18000);
		level.setRemark("测试");
		level.setBeiy("测试");
		service.addLevel(level);
	}
	@Test
	public void addGift(){
		IntegralGift gift=new IntegralGift();
		gift.setId("1001");
		gift.setThirdId("10086");
		gift.setLevelId("1001");
		gift.setMerchantId("1001");
		gift.setName("测试商品1");
		gift.setCount(1);
		gift.setLessCount(999999);
		gift.setType("0");
		gift.setPrice(9.9);
		gift.setValue(500);
		gift.setStatus("0");
		gift.setPutawayTime(TimeUtil.getDateTime());
		gift.setRemark("测试");
		gift.setBeiy("测试");
		service.modifyGift(gift);
	}
	@Test
	public void addMerchant(){
		IntegralMerchant merchant=new IntegralMerchant();
		merchant.setId("1001");
		merchant.setName("杭州蓝喜");
		merchant.setAddr("浙江省杭州市滨江区江南大道588号");
		merchant.setRegisterTime(TimeUtil.getDateTime());
		merchant.setStatus("0");
		merchant.setPhone("15068610940");
		merchant.setRemark("测试");
		merchant.setBeiy("测试");
		service.addMerchant(merchant);
	}
	@Test
	public void get(){
		System.out.println(service.getGift("1001"));
		service.putawayGift("1001");
		System.out.println(service.getGift("1001"));
		System.out.println(service.getMerchant("1001"));
	}
	@Test
	public void testSql(){
		service.subUser("15068610940");
		System.out.println(service.getUserIds(164100, 164101));
	}
	@Test
	public void modifyTemplate() throws Exception{
		for(int i=1;i<14;i++){
		String id=i>10?"10"+i:"100"+i;
		System.out.println(id);
		IntegralSmsTemplate template=service.getSmsTemplate(id);
		System.out.println(template);
//		Map<String,Field> fields=BeanUtil.getFieldsNoStatic(IntegralSmsTemplate.class);
//		for(Map.Entry<String,Field> each:fields.entrySet()){
//			String name=each.getKey();
//			Field field=each.getValue();
//			Object obj=field.get(template);
//			String value=(String) (obj==null?"":obj);
//			System.out.println(value);
//			field.setAccessible(true);
//			field.set(template,CheckReplaceUtil.nullAsSpace(value).trim());
//		}
//		service.modifySmsTemplate(template);
		}
	}
	@Test
	public void addSmsTemplate() throws Exception{
		Map<String,Field> fields=BeanUtil.getFieldsNoStatic(IntegralSmsTemplate.class);
		List<Field> list=new ArrayList<>();
		for(Map.Entry<String,Field> each:fields.entrySet()){
			Field field=each.getValue();
			field.setAccessible(true);
			list.add(field);
		}
		Connection conn=SqlUtilForDB.getConnection();
		String sql="select * from "+"INTEGRAL_SMS_TEMPLATE";
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		ResultSetMetaData metaData=rs.getMetaData();
		int count=metaData.getColumnCount();
		int index=1;
		while(rs.next()){
			count=metaData.getColumnCount();
			IntegralSmsTemplate temp=new IntegralSmsTemplate();
			for(int i=1;i<count;i++){
				String value=rs.getString(i);
				list.get(i-1).set(temp, value.trim());
			}
			temp.setId(index<10?"200"+index:"20"+index);
			index++;
			System.out.println(temp);
			service.addSmsTemplate(temp);
		}
	}
}
