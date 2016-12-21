package com.lanxi.easyintegral.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.entity.IntegralUser;
import com.lanxi.easyintegral.util.CheckReplaceUtil;
import com.lanxi.easyintegral.util.ConfigUtil;
import com.lanxi.easyintegral.util.RandomUtil;
import com.lanxi.easyintegral.util.TimeUtil;
import com.sun.corba.se.spi.orbutil.fsm.State;

import sun.net.www.content.image.gif;
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
	private static Logger logger=Logger.getLogger(BusinessServiceImpl.class);
	@Resource
	private EntityService entityService;
	@Override
	public List<String> getUserIds(Integer start,Integer end) {
		logger.info("开始取用户 \nstart:"+start+"\nend:"+end);
		List<String> users=entityService.getUserIds(start, end);
		System.err.println(users.size());
		if(users.size()<Integer.parseInt((ConfigServiceImpl.get("userCount"))))
			ConfigServiceImpl.resetUserIndex();
		else 
			ConfigServiceImpl.addUserIndex();
		logger.info("取到的用户id:"+users);
		return users;
	}

	@Override
	public Integer getPoint(IntegralUser user) {
		logger.info("获取用户积分,user:"+user);
		Integer point=getPoint(user.getId());
		logger.info("积分值为:"+point);
		return point;
	}

	@Override
	public Integer getPoint(String userId) {
		Integer point=entityService.getUserPoint(userId);
		return point;
	}

	@Override
	public IntegralLevel getLevel(Integer point) {
		return entityService.getLevel(point);
	}

	@Override
	public String getLevelId(Integer point) {
		return entityService.getLevel(point).getId();
	}

	@Override
	public List<IntegralGift> getGifts(String levelId,String status) {
		return entityService.getGifts(levelId,status);
	}

	@Override
	public List<IntegralGift> getGifts(String levelId) {
		return entityService.getGifts(levelId,IntegralGift.GIFT_STATUS_NORMAL);
	}

	@Override
	public IntegralSmsTemplate getSmsTemplate(String templateId) {
		return entityService.getSmsTemplate(templateId);
	}

	@Override
	public IntegralSms makeSms(IntegralSmsTemplate template, List<IntegralGift> gifts, Integer point,String userId ,String phone) {
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank").trim());
		content=content.replace("[point]",getPoint(userId)+"");
		content=content.replace("[ad]",template.getMoreInfo().trim());
		StringBuffer temp=new StringBuffer();
		IntegralSms sms=new IntegralSms();
		sms.setId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(4));
		sms.setUserId(userId);
		sms.setTemplateId(template.getId());
		for(IntegralGift each:gifts){
			sms.setGiftId(CheckReplaceUtil.nullAsSpace(sms.getGiftId())+each.getId()+",");
			temp.append("回复"+each.getId().trim()+",");
			temp.append("消费"+each.getValue()+"积分,");
			temp.append("兑换"+each.getName().trim()+each.getCount()+"份");
		}
		content=content.replace("[gifts]",temp.toString());
		sms.setGiftId(sms.getGiftId().substring(0,sms.getGiftId().length()-1));
		sms.setStatus(IntegralSms.SMS_STATUS_SEND_READY);
		sms.setPhone(phone);
		sms.setContent(content);
		sms.setSendTimes(0);
		sms.setMaxSendTimes(ConfigServiceImpl.getMaxSend());
		sms.setReplyTimes(0);
		sms.setMaxReplyTimes(ConfigServiceImpl.getMaxReply());
		return sms;
	}

	@Override
	public boolean subPoint(IntegralUser user,Integer point) {
		return subPoint(user.getId(),point);
	}

	@Override
	public boolean subPoint(String userId,Integer point) {
		// TODO 扣积分 未实现
		return true;
	}

	@Override
	public String getPhone(String userId) {
		return entityService.getUserPhone(userId);
	}

	@Override
	public String getPhone(IntegralUser user) {
		return getPhone(user.getId());
	}

	@Override
	public boolean isBlackUser(String userId) {
		// TODO 用户表作为黑名单使用 且将手机号作为主键
		return isBlackPhone(userId);
	}

	@Override
	public boolean isBlackPhone(String phone) {
		// TODO 用户表作为黑名单使用 且将手机号作为主键
		return entityService.getUser(phone)==null?false:true;
	}

	@Override
	public boolean haveValidSms(String phone) {
		List<IntegralSms> list=new ArrayList<>();
		list.addAll(entityService.getSmsList(phone, IntegralSms.SMS_STATUS_REPLY_READY));
		list.addAll(entityService.getSmsList(phone, IntegralSms.SMS_STATUS_REPLY_RECEIVE));
		return !list.isEmpty();
	}

	@Override
	public IntegralGift isValidGift(String giftId) {
		IntegralGift gift=entityService.getGift(giftId);
		if(gift==null)
			return null;
		if(gift.getStatus().trim().equals(IntegralGift.GIFT_STATUS_NORMAL)||gift.getStatus().trim().equals(IntegralGift.GIFT_STATUS_EXCHANGE_ONLY))
			return gift;
		return null;
	}

	@Override
	public String getUserId(String phone) {
		return entityService.getUserIdByPhone(phone);
	}

}
