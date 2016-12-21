package com.lanxi.easyintegral.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanxi.easyintegral.service.BusinessService;

public class TestBusinessService {
	private ApplicationContext ac;
	private BusinessService service;
	@Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("xml/spring-mvc.xml");
        service=ac.getBean(BusinessService.class);
    }
	@Test
	public void testInit(){
		System.out.println(service.getUserIds(0, 0));
	}
	@Test
	public void testGetUsers(){
		System.out.println(service.getGifts("1001"));
	}
	@Test
	public void test(){
		System.out.println(service.isBlackPhone("15068610940"));
		System.out.println(service.isBlackUser("15068610940"));
		System.out.println(service.haveValidSms("15068610940"));
		System.out.println(service.isValidGift("201612140001"));
		System.out.println(service.getPhone("15068610940"));
		System.out.println(service.getUserId("15068610940"));
		System.out.println(service.getPoint("15068610940"));
	}
}
