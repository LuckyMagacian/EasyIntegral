package com.lanxi.easyintegral.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lanxi.easyintegral.service.ReceiveService;
import com.lanxi.easyintegral.service.TimingService;
@Controller
public class EasyIntegralController {
	@Resource
	ReceiveService receiveService;
	@Resource
	TimingService timingService;
	@RequestMapping(value="/receiveSms",method=RequestMethod.POST)
	public void receiveService(HttpServletRequest req,HttpServletResponse res){
		receiveService.receive(req, res);
	}
//	@RequestMapping(value="/noticeUser")
	public void noticeUser(HttpServletRequest req,HttpServletResponse res){
		timingService.noticeUser();
	}
//	@RequestMapping(value="/sendSms")
	public void sendSms(HttpServletRequest req,HttpServletResponse res){
		timingService.sendSmsList();
	}
//	@RequestMapping(value="/outSms")
	public void outSms(HttpServletRequest req,HttpServletResponse res){
		timingService.outSms();
	}
//	@RequestMapping(value="/outGift")
	public void outGift(HttpServletRequest req,HttpServletResponse res){
		timingService.outGift();
	}
}
