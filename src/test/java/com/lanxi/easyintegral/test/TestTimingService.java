package com.lanxi.easyintegral.test;

import static java.lang.Integer.parseInt;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.service.BusinessService;
import com.lanxi.easyintegral.service.ConfigServiceImpl;
import com.lanxi.easyintegral.service.EntityService;
import com.lanxi.easyintegral.service.TimingService;
import com.lanxi.easyintegral.util.SmsUtil;
import com.lanxi.easyintegral.util.TempSms;
import com.lanxi.easyintegral.util.TimeUtil;


public class TestTimingService {
	private ApplicationContext ac;
	private TimingService service;
	private EntityService   entityService;
	private BusinessService bisService;
	@Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
        service=ac.getBean(TimingService.class);
        entityService=ac.getBean(EntityService.class);
        bisService=ac.getBean(BusinessService.class);
    }
	@Test
	public void testInit(){
		System.out.println();
	}
	@Test
	public void testNotice(){
		List<String> userIds=bisService.getUserIds(parseInt(ConfigServiceImpl.get("userIndex")),parseInt(ConfigServiceImpl.get("userIndex"))+parseInt(ConfigServiceImpl.get("userCount")));
		if(userIds==null){
			System.out.println("no user");
			return;
		}
		System.out.println(ConfigServiceImpl.get("userIndex"));
		ConfigServiceImpl.addUserIndex();
		System.out.println(ConfigServiceImpl.get("userIndex"));
		ConfigServiceImpl.addUserIndex();
		System.out.println(ConfigServiceImpl.get("userIndex"));
		System.out.println(userIds);
		for(String each:userIds){
			String  phone=bisService.getPhone(each);
			System.out.println(phone);
			if(!bisService.isBlackPhone(phone)&&bisService.haveValidSms(phone)){
				System.out.println("black");
				continue;
			}
			Integer point=bisService.getPoint(each);
			System.out.println(point);
			IntegralLevel level=bisService.getLevel(point);
			if(level==null){
				System.out.println("no suit level");
				continue;
			}
			System.out.println(level);
			List<IntegralGift> gifts=bisService.getGifts(level.getId());
			if(gifts==null||gifts.isEmpty()){
				System.out.println("no suit gift");
				continue;
			}
			System.out.println(gifts);
			IntegralSmsTemplate template=bisService.getSmsTemplate("2016121508500001");
			System.out.println(template);
			IntegralSms sms=bisService.makeSms(template, gifts, point,each, phone);
			System.out.println(sms);
//			entityService.addSms(sms);
		}
	}
	@Test
	public void testSendSms(){
		List<IntegralSms> smsList=entityService.getSmsList(IntegralSms.SMS_STATUS_SEND_READY);
		if(smsList==null||smsList.isEmpty()){
			System.out.println("no sms need send");
			return ;
		}
		System.out.println(smsList);
		for(IntegralSms each:smsList){
			if(!bisService.isBlackPhone(each.getPhone())){
				System.out.println("user is black");
				continue;
			}
			TempSms sms=each.toTempSms();
			SmsUtil.signSms(sms);
			System.out.println(sms);
			String rs=SmsUtil.postSms(sms);
			System.out.println(rs);
			JSONObject jobj=JSONObject.parseObject(rs);
			if(jobj.getString("retCode").equals("0000")){
				each.setStatus(IntegralSms.SMS_STATUS_REPLY_READY);
				each.setSendTime(TimeUtil.getDateTime());
				Date now=new Date();
				now.setDate(now.getDate()+ConfigServiceImpl.getSmsIndate());
				each.setOverTime(TimeUtil.formatDateTime(now));
				System.out.println("send success");
			}
			each.setResCode(jobj.getString("retCode"));
			each.setResMsg(jobj.getString("retDesc"));
			each.setSendTimes(each.getSendTimes()+1);
			if(each.getSendTimes()==each.getMaxSendTimes()&&!each.getStatus().equals(IntegralSms.SMS_STATUS_REPLY_READY)){
				each.setStatus(IntegralSms.SMS_STATUS_SEND_FAIL);
				each.setSendTime(TimeUtil.getDateTime());
				each.setOverTime(TimeUtil.getDateTime());
				System.out.println("send fail");
			}
			entityService.modifySms(each);
		}
	}
	@Test
	public void testOutSms(){
		List<IntegralSms> smsList=entityService.getSmsList(IntegralSms.SMS_STATUS_SEND_SUCCESS);
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_REPLY_READY));
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_REPLY_RECEIVE));
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_REPLY_TIME_LIMIT));
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_SEND_FAIL));
		if(smsList==null||smsList.isEmpty()){
			System.out.println("no sms need outtime");
			return ;
		}
		System.out.println(smsList);
		for(IntegralSms each:smsList){
			if(each.getOverTime().compareTo(TimeUtil.getDateTime())<1){
				each.setStatus(IntegralSms.SMS_STATUS_OVERTIME);
				entityService.modifySms(each);
				System.out.println("sms out time");
			}
		}
	}
	@Test
	public void testSql(){
		System.out.println(entityService.getUserPoint("101061851633217018312"));
	}
}
