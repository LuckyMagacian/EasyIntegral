package com.lanxi.easyintegral.service;

import static java.lang.Integer.parseInt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.util.SmsUtil;
import com.lanxi.easyintegral.util.TempSms;
import com.lanxi.easyintegral.util.TimeUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
@Service("timingService")
public class TimingServiceImpl implements TimingService {
	private static Logger logger=Logger.getLogger(TimingServiceImpl.class);
	@Resource
	private BusinessService bisService;
	@Resource
	private EntityService   entityService;
	
	@Override
	public void noticeUser() {
		logger.info("开始处理用户分析任务!");
		List<String> userIds=bisService.getUserIds(parseInt(ConfigServiceImpl.get("userIndex")),parseInt(ConfigServiceImpl.get("userIndex"))+parseInt(ConfigServiceImpl.get("userCount"))-1);
		for(String each:userIds){	
			if(each==null||each.trim().equals("")){
				logger.info("用户为空!");
				continue;
			}
			logger.info("开始处理用户,userId:"+each);
			String  phone=bisService.getPhone(each);
			logger.info("获取用户手机号,phone:"+phone);
			if(phone==null||phone.trim().equals("")){
				logger.info("用户手机号不存在,跳过后续处理!");
				continue;
			}
			if(bisService.isBlackPhone(phone)){
				logger.info("该用户是黑名单用户,跳过后续处理");
				continue;
			}
			if(bisService.haveValidSms(phone)){
				logger.info("该用户存在未过期的兑换通知短信,跳过后续处理!");
				continue;
			}
			Integer point=bisService.getPoint(each);
			logger.info("取到的用户积分,point:"+point);
			if(point==null||point<=0){
				logger.info("该用户没有积分!");
				continue;
			}
			IntegralLevel level=bisService.getLevel(point);
			logger.info("根据用户积分取积分档次,level:"+level);
			List<IntegralGift> gifts=bisService.getGifts(level.getId());
			if(gifts.isEmpty()){
				logger.info("该用户所在的积分档次没有对应的礼品");
				return ;
			}
			logger.info("根据用户的积分档次获取可以兑换的礼品列表,gifts:"+gifts);
			IntegralSmsTemplate template=bisService.getSmsTemplate("1001");
			logger.info("获取兑换通知短信模版,template:"+template);
			IntegralSms sms=bisService.makeSms(template, gifts, point,each, phone);
			logger.info("根据用户信息生成备发短信,sms:"+sms);
			entityService.addSms(sms);
			logger.info("添加备发短信完成,userId:"+each);
		}
		logger.info("用户分析完成~");
	}
	@SuppressWarnings("deprecation")
	@Override
	public void sendSmsList() {
		logger.info("开始发送通知短信任务!");
		List<IntegralSms> smsList=entityService.getSmsList(IntegralSms.SMS_STATUS_SEND_READY);
		logger.info("当前等待发送的短信列表,list:"+smsList);
		if(smsList==null||smsList.isEmpty()){
			logger.info("备发短信表为空,跳过后续处理!");
			return;
		}
		for(IntegralSms each:smsList){
			logger.info("开始处理备发短信,smsId:"+each.getId());
			if(bisService.isBlackPhone(each.getPhone())){
				logger.info("当前处理用户为黑名单用户跳过后续处理,userId:"+each.getUserId()+"  phone:"+each.getPhone());
				continue;
			}
			Date now=new Date();
			now.setDate(now.getDate()+ConfigServiceImpl.getSmsIndate());
			TempSms sms=each.toTempSms();
			sms.setContent(sms.getContent().replace("[overtime]",new SimpleDateFormat("yyyy年MM月dd日").format(now)));
			logger.info("备发短信转换为下发短信形式,tempSms:"+sms);
			SmsUtil.signSms(sms);
			logger.info("下发短信签名,sign:"+sms.getSign());
			String rs=SmsUtil.postSms(sms);
			logger.info("短信发送结果,result:"+rs);
			JSONObject jobj=JSONObject.parseObject(rs);
			if(jobj.getString("retCode").trim().equals("0000")){
				logger.info("短信发送成功!");
				each.setStatus(IntegralSms.SMS_STATUS_REPLY_READY);
				each.setSendTime(TimeUtil.getDateTime());
				each.setOverTime(TimeUtil.formatDateTime(now));
			}else{
				logger.info("短信发送失败!");
			}
			each.setResCode(jobj.getString("retCode"));
//			each.setResMsg(jobj.getString("retDesc"));
			each.setSendTimes(each.getSendTimes()+1);
			if(each.getSendTimes()==each.getMaxSendTimes()){
				logger.info("短信发送次数已达上限将不再发送!");
				each.setStatus(IntegralSms.SMS_STATUS_SEND_FAIL);
				each.setSendTime(TimeUtil.getDateTime());
				each.setOverTime(TimeUtil.getDateTime());
			}
			entityService.modifySms(each);
			logger.info("更新短信信息!");
		}
		logger.info("发送通知短信任务完成");
	}

	@Override
	public void outSms() {
		logger.info("开始清理过期短信任务!");
		List<IntegralSms> smsList=entityService.getSmsList(IntegralSms.SMS_STATUS_SEND_SUCCESS);
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_REPLY_READY));
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_REPLY_RECEIVE));
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_REPLY_TIME_LIMIT));
		smsList.addAll(entityService.getSmsList(IntegralSms.SMS_STATUS_SEND_FAIL));
		logger.info("获取所有等待回复,可重复回复,发送失败,兑换次数上限,发送成功的短信,list:"+smsList);
		for(IntegralSms each:smsList){
			logger.info("开始处理短信,smsId:"+each.getId());
			if(each.getOverTime().compareTo(TimeUtil.getDateTime())<1){
				logger.info("短信已过期!");
				each.setStatus(IntegralSms.SMS_STATUS_OVERTIME);
				entityService.modifySms(each);
				logger.info("更新短信状态!");
			}else{
				logger.info("该短信未过期!");
			}
			logger.info("处理本条短信完成");
		}
		logger.info("过期短信清理任务完成!");
	}

	@Override
	public void outGift() {
		logger.info("开始执行礼品下架任务!");
		List<IntegralGift> gifts=entityService.getGiftsByStatus(IntegralGift.GIFT_STATUS_EXCHANGE_ONLY);
		logger.info("获取所有仅限兑换的礼品,gifts:"+gifts);
		for(IntegralGift each:gifts){
			logger.info("开始处理礼品,giftId:"+each.getId());
			if(each.getSoldoutTime().compareTo(TimeUtil.getDateTime())==-1){
				logger.info("该礼品下架!");
				each.setStatus(IntegralGift.GIFT_STATUS_SOLDOUT);
				entityService.modifyGift(each);
				logger.info("更新礼品信息");
			}else{
				logger.info("该礼品未过期!");
			}
		}
		logger.info("礼品下架任务完成!");
	}
}
