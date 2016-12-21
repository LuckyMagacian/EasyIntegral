package com.lanxi.easyintegral.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibm.db2.jcc.am.u;
import com.lanxi.easyintegral.dao.IntegralGiftDao;
import com.lanxi.easyintegral.dao.IntegralLevelDao;
import com.lanxi.easyintegral.dao.IntegralMerchantDao;
import com.lanxi.easyintegral.dao.IntegralOrderDao;
import com.lanxi.easyintegral.dao.IntegralSmsDao;
import com.lanxi.easyintegral.dao.IntegralSmsTemplateDao;
import com.lanxi.easyintegral.dao.IntegralUserDao;
import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralMerchant;
import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.entity.IntegralUser;
import com.lanxi.easyintegral.util.AppException;
import com.lanxi.easyintegral.util.SqlUtilForDB;
import com.lanxi.easyintegral.util.TimeUtil;
@Service("entityService")
public class EntityServiceImpl implements EntityService {
	@Resource
	private IntegralGiftDao giftDao;
	@Resource 
	private IntegralLevelDao levelDao;
	@Resource
	private IntegralMerchantDao merchantDao;
	@Resource
	private IntegralOrderDao orderDao;
	@Resource
	private IntegralSmsDao smsDao;
	@Resource
	private IntegralSmsTemplateDao templateDao;
	@Resource 
	private IntegralUserDao userDao;
	@Override
	public void addGift(IntegralGift gift) {
		giftDao.addIntegralGift(gift);
	}
	@Override
	public void putawayGift(IntegralGift gift) {
		if(getMerchant(gift.getMerchantId())!=null&&getMerchant(gift.getMerchantId()).getStatus().trim().equals(IntegralMerchant.MERCHANT_STATUS_NORMAL)){
			gift.setStatus(IntegralGift.GIFT_STATUS_NORMAL);
			gift.setSoldoutTime("");
			giftDao.updateIntegralGift(gift);
		}
	}
	@Override
	public void putawayGift(String giftId) {
		IntegralGift gift=giftDao.selectIntegralGiftById(giftId);
		if(gift!=null){
			IntegralMerchant merchant=getMerchant(gift.getMerchantId());
			System.err.println(merchant);
			if(merchant!=null&&merchant.getStatus().trim().equals(IntegralMerchant.MERCHANT_STATUS_NORMAL)){
				putawayGift(gift);
			}
		}
	}
	@Override
	public void soldoutGift(IntegralGift gift) {
		Date date=new Date();
		date.setDate(date.getDate()+30);
		gift.setSoldoutTime(TimeUtil.formatDateTime(date));
		gift.setStatus(IntegralGift.GIFT_STATUS_EXCHANGE_ONLY);
		giftDao.updateIntegralGift(gift);
	}
	@Override
	public void soldoutGift(String giftId) {
		IntegralGift gift=giftDao.selectIntegralGiftById(giftId);
		if(gift!=null)	
			soldoutGift(gift);
	}
	@Override
	public void soldoutForce(IntegralGift gift) {
		Date date=new Date();
		gift.setSoldoutTime(TimeUtil.formatDateTime(date));
		gift.setStatus(IntegralGift.GIFT_STATUS_SOLDOUT);
		giftDao.updateIntegralGift(gift);
	}
	@Override
	public void soldoutForce(String giftId) {
		IntegralGift gift=giftDao.selectIntegralGiftById(giftId);
		if(gift!=null)
			soldoutForce(gift);
	}
	@Override
	public void modifyGift(IntegralGift gift) {
		giftDao.updateIntegralGift(gift);
	}
	@Override
	public void deleteGift(IntegralGift gift) {
		giftDao.deleteIntegralGift(gift);
	}
	@Override
	public void deleteGift(String giftId) {
		giftDao.deleteIntegralGiftById(giftId);
	}
	@Override
	public void addLevel(IntegralLevel level) {
		levelDao.addIntegralLevel(level);
	}
	@Override
	public void modifyLevel(IntegralLevel level) {
		levelDao.updateIntegralLevel(level);
	}
	@Override
	public void deleteLevel(IntegralLevel level) {
		List<IntegralGift> gifts=getGifts(level.getId());
		if(gifts.isEmpty())
			levelDao.deleteIntegralLevel(level);
	}
	@Override
	public void deleteLevel(String levelId) {
		List<IntegralGift> gifts=getGifts(levelId,IntegralGift.GIFT_STATUS_NORMAL);
		gifts.addAll(getGifts(levelId, IntegralGift.GIFT_STATUS_EXCHANGE_ONLY));
		if(gifts.isEmpty())
			levelDao.deleteIntegralLevelById(levelId);
	}
	@Override
	public void addMerchant(IntegralMerchant merchant) {
		merchantDao.addIntegralMerchant(merchant);
	}
	@Override
	public void modifyMerchant(IntegralMerchant merchant) {
		merchantDao.updateIntegralMerchant(merchant);
	}
	@Override
	public void deleteMerchant(IntegralMerchant merchant) {
		List<IntegralGift> gifts=getGifts(merchant);
		boolean flag=true;
		for(IntegralGift each:gifts)
			flag|=each.getStatus().trim().equals(IntegralGift.GIFT_STATUS_SOLDOUT);
		if(flag)
			merchantDao.deleteIntegralMerchant(merchant);
	}
	@Override
	public void deleteMerchant(String merchantId) {
		List<IntegralGift> gifts=getGiftsByMerchantId(merchantId);
		boolean flag=true;
		for(IntegralGift each:gifts)
			flag|=each.getStatus().trim().equals(IntegralGift.GIFT_STATUS_SOLDOUT);
		if(flag)
			merchantDao.deleteIntegralMerchantById(merchantId);
	}
	@Override
	public void unableMerchant(IntegralMerchant merchant) {
		List<IntegralGift> gifts=getGifts(merchant);
		boolean flag=true;
		for(IntegralGift each:gifts)
			flag|=each.getStatus().trim().equals(IntegralGift.GIFT_STATUS_SOLDOUT);
		if(flag){
			merchant.setStatus(IntegralMerchant.MERCHANT_STATUS_ABNORMAL);
			merchantDao.updateIntegralMerchant(merchant);
		}
	}
	@Override
	public void unableMerchant(String merchantId) {
		IntegralMerchant merchant=merchantDao.selectIntegralMerchantById(merchantId);
		List<IntegralGift> gifts=getGifts(merchant);
		boolean flag=true;
		for(IntegralGift each:gifts)
			flag|=each.getStatus().trim().equals(IntegralGift.GIFT_STATUS_SOLDOUT);
		if(flag){
			merchant.setStatus(IntegralMerchant.MERCHANT_STATUS_ABNORMAL);
			merchantDao.updateIntegralMerchant(merchant);
		}
	}
	@Override
	public void addOrder(IntegralOrder order) {
		orderDao.addIntegralOrder(order);
	}
	@Override
	public void modifyOrder(IntegralOrder order) {
		orderDao.updateIntegralOrder(order);
	}
	@Override
	public void deleteOrder(IntegralOrder order) {
		orderDao.deleteIntegralOrder(order);
	}
	@Override
	public void deleteOrder(String orderId) {
		orderDao.deleteIntegralOrderById(orderId);
	}
	@Override
	public void addSms(IntegralSms sms) {
		smsDao.addIntegralSms(sms);
	}
	@Override
	public List<IntegralSms> getSmsList(String smsStatus) {
		IntegralSms sms=new IntegralSms();
		sms.setStatus(smsStatus);
		return smsDao.selectIntegralSms(sms);
	}
	@Override
	public void modifySms(IntegralSms sms) {
		smsDao.updateIntegralSms(sms);
	}
	@Override
	public void deleteSms(IntegralSms sms) {
		smsDao.deleteIntegralSms(sms);
	}
	@Override
	public void deleteSms(String smsId) {
		smsDao.deleteIntegralSmsById(smsId);
	}
	@Override
	public void addSmsTemplate(IntegralSmsTemplate template) {
		templateDao.addIntegralSmsTemplate(template);
	}
	@Override
	public void modifySmsTemplate(IntegralSmsTemplate template) {
		templateDao.updateIntegralSmsTemplate(template);
	}
	@Override
	public void deleteSmsTemplate(IntegralSmsTemplate template) {
		templateDao.deleteIntegralSmsTemplate(template);
	}
	@Override
	public void deleteSmsTemplate(String templateId) {
		templateDao.deleteIntegralSmsTemplateById(templateId);
	}
	@Override
	public void enableSmsTemplate(IntegralSmsTemplate template) {
		template.setStatus(IntegralSmsTemplate.TEMPLATE_STATUS_NORMAL);
		templateDao.updateIntegralSmsTemplate(template);
	}
	@Override
	public void enableSmsTemplate(String templateId) {
		IntegralSmsTemplate template=templateDao.selectIntegralSmsTemplateById(templateId);
		enableSmsTemplate(template);
	}
	@Override
	public void unableSmsTemplate(IntegralSmsTemplate template) {
		template.setStatus(IntegralSmsTemplate.TEMPLATE_STATUS_ABNORMAL);
		templateDao.updateIntegralSmsTemplate(template);
	}
	@Override
	public void unableSmsTemplate(String templateId) {
		IntegralSmsTemplate template=templateDao.selectIntegralSmsTemplateById(templateId);
		unableSmsTemplate(template);
	}
	@Override
	public void subUser(IntegralUser user) {
		//TODO 此處修改用戶為關注狀態是將用戶從表中刪除
		userDao.deleteIntegralUser(user);
	}
	@Override
	public void subUser(String phone) {
		userDao.deleteIntegralUserById(phone);
	}
	@Override
	public void unsubUser(IntegralUser user) {
		userDao.addIntegralUser(user);
	}
	@Override
	public void unsubUser(String phone) {
		IntegralUser user=userDao.selectIntegralUserById(phone);
		if(user==null){
			user=new IntegralUser();
			user.setId(phone);
			user.setPhone(phone);
			user.setStatus(IntegralUser.USER_SUBSTATUS_UNSUB);
			userDao.addIntegralUser(user);
		}
	}
	@Override
	public void addUser(IntegralUser user) {
		IntegralUser userTemp=userDao.selectIntegralUserById(user.getPhone());
		if(userTemp!=null)
			return ;
		user.setStatus(IntegralUser.USER_SUBSTATUS_UNSUB);
		userDao.addIntegralUser(user);
	}
	@Override
	public void deleteUser(IntegralUser user) {
		userDao.deleteIntegralUser(user);
	}
	@Override
	public void deleteUser(String phone) {
		userDao.deleteIntegralUserById(phone);
	}
	@Override
	public List<IntegralGift> getGifts(String levelId) {
		return getGifts(levelId,null);
	}
	@Override
	public List<IntegralGift> getGifts(String levelId,String status) {
		IntegralGift temp=new IntegralGift();
		temp.setLevelId(levelId);
		temp.setStatus(status);
		return giftDao.selectIntegralGift(temp);
	}
	@Override
	public List<IntegralGift> getGiftsByStatus(String status) {
		IntegralGift temp=new IntegralGift();
		temp.setStatus(status);
		return giftDao.selectIntegralGift(temp);
	}
	@Override
	public IntegralLevel getLevel(int point) {
		return levelDao.selectIntegralLevelByPoint(point);
	}
	@Override
	public IntegralLevel getLevel(String levelId) {
		return levelDao.selectIntegralLevelById(levelId);
	}
	@Override
	public IntegralMerchant getMerchant(String merchantId) {
		return merchantDao.selectIntegralMerchantById(merchantId);
	}
	@Override
	public IntegralMerchant getMerchant(IntegralGift gift) {
		return merchantDao.selectIntegralMerchantById(gift.getMerchantId());
	}
	@Override
	public IntegralMerchant getMerchantByGiftId(String giftId) {
		IntegralGift gift=giftDao.selectIntegralGiftById(giftId);
		return getMerchant(gift);
	}
	@Override
	public IntegralOrder getOrder(String orderId) {
		return orderDao.selectIntegralOrderById(orderId);
	}
	@Override
	public List<IntegralOrder> getOrdersByPhone(String phone) {
		IntegralOrder temp=new IntegralOrder();
		temp.setPhone(phone);
		return orderDao.selectIntegralOrder(temp);
	}
	@Override
	public List<IntegralOrder> getOrdersByUserId(String userId) {
		IntegralOrder temp=new IntegralOrder();
		temp.setUserId(userId);
		return orderDao.selectIntegralOrder(temp);
	}
	@Override
	public List<IntegralOrder> getOrderByStatus(String status) {
		IntegralOrder temp=new IntegralOrder();
		temp.setStatus(status);
		return orderDao.selectIntegralOrder(temp);
	}
	@Override
	public List<IntegralSms> getSmsListByPhone(String phone) {
		IntegralSms temp=new IntegralSms();
		temp.setPhone(phone);
		return smsDao.selectIntegralSms(temp);
	}
	@Override
	public List<IntegralSms> getSmsList(String phone, String smsStatus) {
		IntegralSms temp=new IntegralSms();
		temp.setPhone(phone);
		temp.setStatus(smsStatus);
		return smsDao.selectIntegralSms(temp);
	}
	@Override
	public IntegralSmsTemplate getSmsTemplate(String templateId) {
		return templateDao.selectIntegralSmsTemplateById(templateId);
	}
	@Override
	public IntegralUser getUser(String phone) {
		return userDao.selectIntegralUserById(phone);
	}
	@Override
	public IntegralGift getGift(String giftId) {
		return giftDao.selectIntegralGiftById(giftId);
	}
	@Override
	public List<IntegralGift> getGifts(IntegralMerchant merchant) {
		IntegralGift temp=new IntegralGift();
		temp.setMerchantId(merchant.getId());
		return giftDao.selectIntegralGift(temp);
	}
	@Override
	public List<IntegralGift> getGiftsByMerchantId(String merchantId) {
		IntegralGift temp=new IntegralGift();
		temp.setMerchantId(merchantId);
		return giftDao.selectIntegralGift(temp);
	}
	@Override
	public String getUserId(String phone) {
		return  getUserIdByPhone(phone);
	}
	@Override
	public List<String> getUserIds(Integer start, Integer end) {
		Connection conn=SqlUtilForDB.getConnection();
		try{
			List<String> users=new ArrayList<>();
			String sql="select CUST_NO from(select ROW_NUMBER() OVER(ORDER BY CUST_NO) AS ROWNUM,CUST_NO from t_POINT_ACCOUNT) where ROWNUM between "+start+" and "+end;
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next())
				users.add(rs.getString(1));
			return users;
		}catch (Exception e) {
			throw new AppException("获取指定区间的用户异常",e);
		}finally {
			try {
				if(!conn.isClosed())
					SqlUtilForDB.closeConnection(conn);
			} catch (Exception e) {
				throw new AppException("关闭连接异常",e);
			}
		}
		
	}
	@Override
	public Integer getUserPoint(String userId) {
		Connection conn=SqlUtilForDB.getConnection();
		try{
			// TODO 需要过滤过期的积分  未实现
			String sql="select SUM (POINTS_VAL) from (select POINTS_VAL from t_POINT_ACCOUNT where CUST_NO ='"+userId+"')";
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			Integer point=null;
			if(rs.next())
				point=rs.getInt(1);
			return point;
		}catch (Exception e) {
			throw new AppException("获取用户积分异常",e);
		}finally {
			try {
				if(!conn.isClosed())
					SqlUtilForDB.closeConnection(conn);
			} catch (Exception e) {
				throw new AppException("关闭连接异常",e);
			}
		}
	}
	@Override
	public String getUserPhone(String userId) {
		Connection conn=SqlUtilForDB.getConnection();
		try{
			String sql="select PHONE from t_POINT_CUST_INFO where CUST_NO='"+userId+"'";
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			String phone=null;
			if(rs.next())
				phone=rs.getString(1);
			return phone;
		}catch (Exception e) {
			throw new AppException("获取用户手机号异常",e);
		}finally {
			try {
				if(!conn.isClosed())
					SqlUtilForDB.closeConnection(conn);
			} catch (Exception e) {
				throw new AppException("关闭连接异常",e);
			}
		}
	}
	@Override
	public String getUserIdByPhone(String phone) {
		Connection conn=SqlUtilForDB.getConnection();
		try{
			String sql="select CUST_NO from t_POINT_CUST_INFO where PHONE='"+phone+"'";
			List<String> users=new ArrayList<>();
			Statement statement=conn.createStatement();
			ResultSet rs=statement.executeQuery(sql);
			String userId=null;
			while(rs.next())
				users.add(rs.getString(1));
			if(users.size()==1)
				userId=rs.getString(1);
			else{
				Map<String, Integer> map=new HashMap<String,Integer>();
				for(String each:users)
					map.put(each, getUserPoint(each));
				for(Map.Entry<String, Integer> each:map.entrySet())
						userId=userId==null?each.getKey():map.get(userId)<each.getValue()?each.getKey():userId;
			}
			return userId;
		}catch (Exception e) {
			throw new AppException("获取用户帐号异常",e);
		}finally {
			try {
				if(!conn.isClosed())
					SqlUtilForDB.closeConnection(conn);
			} catch (Exception e) {
				throw new AppException("关闭连接异常",e);
			}
		}
	}
	
	
}
