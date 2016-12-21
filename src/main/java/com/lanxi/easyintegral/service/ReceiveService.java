package com.lanxi.easyintegral.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.report.Sku;

import javafx.beans.property.SimpleStringProperty;
/**
 * 本接口为接受用户回复用的接口
 * @author 1
 *
 */
public interface ReceiveService {
	public void receive(HttpServletRequest req,HttpServletResponse res);
	public IntegralOrder makeOrder(IntegralGift gift,IntegralSms sms);
	public String submitOrder(IntegralOrder order);
	public void unsub(String phone);
	public void sub(String phone);
	public void exchangeGift(String phone,String giftId);
	
	
	public void noticeTD(String  phone);
	public void noticeHaveTD(String phone);
	public void noticeDY(String  phone);
	public void noticeHaveDY(String phone);
	
	public void noticeNoSms(String phone);
	public void noticeSmsOutTime(String phone);
	public void noticeNeedDY(String phone);
	public void noticeExchangeTimeLimit(String phone);
	public void noticePointInsufficient(String phone);
	public void noticeNoSuchGiftInSms(String phone);
	public void noticeGiftException(String phone);
	public void noticeSubpointExceptino(String phone);
	public void noticeOrderException(String phone);
	public void noticeExchangeSuccess(String phone,IntegralOrder order,List<Sku> list);
}
