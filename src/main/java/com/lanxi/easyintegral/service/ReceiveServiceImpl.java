package com.lanxi.easyintegral.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.entity.IntegralUser;
import com.lanxi.easyintegral.report.BaoWen;
import com.lanxi.easyintegral.report.ResHead;
import com.lanxi.easyintegral.report.ResMsg;
import com.lanxi.easyintegral.report.Sku;
import com.lanxi.easyintegral.util.AppException;
import com.lanxi.easyintegral.util.CheckReplaceUtil;
import com.lanxi.easyintegral.util.HttpUtil;
import com.lanxi.easyintegral.util.RandomUtil;
import com.lanxi.easyintegral.util.SmsUtil;
import com.lanxi.easyintegral.util.TempSms;
import com.lanxi.easyintegral.util.TimeUtil;
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {
	private static Logger logger=Logger.getLogger(ReceiveServiceImpl.class);
	@Resource
	BusinessService bisService;
	@Resource 
	EntityService  entityService;
	@Override
	public void receive(HttpServletRequest req, HttpServletResponse res) {
		try{
			req.setCharacterEncoding(ConfigServiceImpl.getCharset());
			String phone=CheckReplaceUtil.spaceAsNull(req.getParameter("phone"));
			String content=CheckReplaceUtil.spaceAsNull(req.getParameter("content"));
			logger.info("收到用户回复,phone:"+phone+"  content:"+content);
			if(phone==null){
				logger.info("手机号为空!");
				return ;
			}
			if(content==null){
				logger.info("回复内容为空!");
				noticeNoSuchGiftInSms(phone);
				return ;
			}
			switch (content) {
			case "TD":
				if(bisService.isBlackPhone(phone)){
					logger.info("该用户重复退订!");
					noticeHaveTD(phone);
				}else{
					logger.info("该用户退订了易积分兑换业务!");
					unsub(phone);
					logger.info("该用户加入黑名单!");
					noticeTD(phone);
				}
				return;
			case "DY":
				if(!bisService.isBlackPhone(phone)){
					logger.info("该用户重复订阅!");
					noticeHaveDY(phone);
				}else{
					logger.info("该用户订阅了易积分兑换业务!");
					sub(phone);
					noticeDY(phone);
				}
				return;
			}
			if(bisService.isBlackPhone(phone)){
				logger.info("该用户是黑名单用户,无法兑换礼品!");
				noticeNeedDY(phone);
				return ;
			}
			if(!bisService.haveValidSms(phone)){
				logger.info("该用户没有效期内的兑换通知!");
				List<IntegralSms> temp=entityService.getSmsList(phone, IntegralSms.SMS_STATUS_OVERTIME);
				logger.info("查找该用户往期兑换通知短信!");
				temp.addAll(entityService.getSmsList(phone, IntegralSms.SMS_STATUS_REPLY_TIME_LIMIT));
				logger.info("查找该用户本期兑换超限短信!");
				logger.info("合并查找结果,list"+temp);
				if(temp==null||temp.isEmpty()||temp.size()==0){
					logger.info("该用户没有通知短信!");
					noticeNoSms(phone);
					return;
				}
				if(temp.get(0).getStatus().trim().equals(IntegralSms.SMS_STATUS_REPLY_TIME_LIMIT)){
					logger.info("该用户本期兑换已达兑换次数上限!sms:"+temp.get(0));
					noticeExchangeTimeLimit(phone);
					return;
				}
				if(temp.get(0).getStatus().trim().equals(IntegralSms.SMS_STATUS_OVERTIME)){
					logger.info("该用户没有本期兑换短信,往期兑换短信已过期!sms:"+temp.get(0));
					noticeSmsOutTime(phone);
					return ;
				}
			}
			List<IntegralSms> smsList=entityService.getSmsList(phone, IntegralSms.SMS_STATUS_REPLY_READY);
			logger.info("查找该用户回复的对应未回复通知短信!");
			smsList.addAll(entityService.getSmsList(phone, IntegralSms.SMS_STATUS_REPLY_RECEIVE));
			logger.info("查找该用户回复的可重复回复通知短信!");
			logger.info("合并查找结果,list:"+smsList);
			IntegralSms sms=smsList.get(0);
			logger.info("该用户回复的对应下发短信是,sms:"+sms);
			String gifts=sms.getGiftId();
			logger.info("获取该用户对应的通知礼品列表!gifts:"+gifts);
			String[] giftIds=gifts.split(",");
			boolean flag=false;
			for(String each:giftIds)
				flag|=each.trim().equals(content.trim());
			if(!flag){
				logger.info("该用户的通知礼品中没有包含回复内容!");
				noticeNoSuchGiftInSms(phone);
				return;
			}
			IntegralGift gift=entityService.getGift(content.trim());
			logger.info("用户回复正确,尝试兑换的礼品为,gift:"+gift);
			String       userId=sms.getUserId();
			logger.info("获取用户综合积分账户,userId:"+userId);
			Integer point=bisService.getPoint(userId);
			logger.info("获取用户账户积分,point:"+point);
			if(point<gift.getValue()){
				logger.info("用户剩余积分不足!兑换失败!");
				noticePointInsufficient(phone);
				return;
			}
			if(bisService.isValidGift(content)==null){
				logger.info("用户兑换的商品状态不正常,可能已下架或者兑罄!");
				noticeGiftException(phone);
				return;
			}
			IntegralOrder order=makeOrder(gift, sms);
			logger.info("创建订单完成,order:"+order);
			entityService.addOrder(order);
			logger.info("保存订单完成!");
			if(!bisService.subPoint(order)){
				logger.info("用户积分扣除失败!下单失败!");
				noticeSubpointExceptino(phone);
				return ;
			}
			logger.info("用户积分兑换完成!");
			String rs=submitOrder(order);
			logger.info("提交订单到礼品平台!下单结果,rsult:"+rs);
			BaoWen result=BaoWen.formDocumentStr(rs);
			if(!((ResHead)result.getHead()).getResCode().trim().equals("0000")){
				logger.info("下单失败,订单异常,将转人工处理!");
				order.setStatus(IntegralOrder.ORDER_STATUS_FAIL);
				noticeOrderException(phone);
			}else{
				order.setStatus(IntegralOrder.ORDER_STATUS_SUCCESS);
				order.setBeiy(((ResMsg)result.getMsg()).getSkuList().toString());
				logger.info("下单成功!");
			}
			order.setResCode(((ResHead)result.getHead()).getResCode());
			order.setResMsg(((ResHead)result.getHead()).getResMsg());
			entityService.modifyOrder(order);
			logger.info("更新订单信息!");
			if(order.getStatus().trim().equals(IntegralOrder.ORDER_STATUS_SUCCESS)){
				noticeExchangeSuccess(phone, order,((ResMsg)result.getMsg()).getSkuList());
				logger.info("兑换成功,通知用户兑换结果!");
			}
		}catch (Exception e) {
			throw new AppException("接收回复消息异常",e);
		}
	}
	@Override
	public IntegralOrder makeOrder(IntegralGift gift, IntegralSms sms) {
		IntegralOrder order=new IntegralOrder();
		order.setId(TimeUtil.getDateTime()+RandomUtil.getRandomNumber(4));
		order.setSmsId(sms.getId());
		order.setUserId(sms.getUserId());
		order.setPhone(sms.getPhone());
		order.setGiftId(gift.getId());
		order.setGiftCount(gift.getCount());
		order.setTotalPrice(gift.getPrice()*order.getGiftCount());
		order.setTotalValue(gift.getValue()*order.getGiftCount());
		order.setStatus(IntegralOrder.ORDER_STATUS_WAIT);
		order.setRemark("测试");
		return order;
	}
	@Override
	public String submitOrder(IntegralOrder order) {
		BaoWen baoWen=order.toBaowen();
		String rs=HttpUtil.postXml(baoWen.toDocument().asXML(), ConfigServiceImpl.getGiftUrl(), ConfigServiceImpl.getCharset(), 10000);
		return rs;
	}
	@Override
	public void unsub(String phone) {
		IntegralUser user=new IntegralUser();
		user.setPhone(phone);
		user.setId(phone);
		user.setUnsubTime(TimeUtil.getDateTime());
		entityService.addUser(user);
	}
	@Override
	public void sub(String phone) {
		entityService.deleteUser(phone);
	}
	@Override
	public void exchangeGift(String phone, String giftId) {
		// TODO 不需要写了
	}
	@Override
	public void noticeTD(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1009");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("退订通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeHaveTD(String phone) {
		noticeTD(phone);
	}
	@Override
	public void noticeDY(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1008");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("订阅通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeHaveDY(String phone) {
		noticeDY(phone);
	}
	@Override
	public void noticeNoSms(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1012");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("未收到通知通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeSmsOutTime(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1010");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("短信过期通知短信发送结果,rs:"+rs);
		
	}
	@Override
	public void noticeNeedDY(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1011");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("退订兑换通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeExchangeTimeLimit(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1013");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("兑换次数上限通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticePointInsufficient(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1004");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("积分不足通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeNoSuchGiftInSms(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1002");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("礼品不再通知中通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeGiftException(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1003");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("礼品异常通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeOrderException(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1006");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("订单异常通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeExchangeSuccess(String phone, IntegralOrder order,List<Sku> list) {
		IntegralGift gift=entityService.getGift(order.getId());
		IntegralSmsTemplate template=entityService.getSmsTemplate("1007");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		content=content.replace("[gift]",gift.getName()+gift.getCount()+"份");
		content=content.replace("[point]",bisService.getPoint(order.getUserId())+"");
		StringBuffer temp=new StringBuffer();
		for(int i=1;i<list.size()+1;i++){
			temp.append("兑换券码"+i+":");
			temp.append(list.get(i-1).getCode()+",");
		}
		content=content.replace("[code]",temp.toString());
		content=content.replace("[overtime]",list.get(0).getEndTime());
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("兑换成功通知短信发送结果,rs:"+rs);
	}
	@Override
	public void noticeSubpointExceptino(String phone) {
		IntegralSmsTemplate template=entityService.getSmsTemplate("1005");
		IntegralSms sms=new IntegralSms();
		sms.setPhone(phone);
		String content=template.getContent().trim();
		content=content.replace("[bank]",ConfigServiceImpl.get("bank"));
		sms.setContent(content);
		TempSms tempSms=sms.toTempSms();
		SmsUtil.signSms(tempSms);
		String rs=SmsUtil.postSms(tempSms);
		logger.info("积分扣除异常通知短信发送结果,rs:"+rs);
	}

}
