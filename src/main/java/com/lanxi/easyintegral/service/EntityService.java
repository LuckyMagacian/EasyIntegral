package com.lanxi.easyintegral.service;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralMerchant;
import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.entity.IntegralUser;
/**
 * 此接口包含所有与dao及其衍生的方法
 * @author 1
 *
 */
public interface EntityService {
// TODO gift---------------------------------------gift----------------------------------------------------
	/**
	 * 添加礼品
	 * @param gift
	 */
	public void addGift(IntegralGift gift);
	/**
	 * 上架礼品
	 * @param gift
	 */
	public void putawayGift(IntegralGift gift);
	/**
	 * 根据礼品编号上架礼品,若礼品不存在则不执行
	 * @param giftId
	 */
	public void putawayGift(String giftId);
	/**
	 * 下架礼品
	 * @param gift
	 */
	public void soldoutGift(IntegralGift gift);
	/**
	 * 根据礼品编号下架礼品
	 * @param giftId
	 */
	public void soldoutGift(String giftId);
	/**
	 * 强制下架,不经过兑换期
	 * @param gift
	 */
	public void soldoutForce(IntegralGift gift);
	/**
	 * 根据礼品编号强制下架礼品
	 * @param giftId
	 */
	public void soldoutForce(String giftId);
	/**
	 * 修改礼品
	 * @param gift
	 */
	public void modifyGift(IntegralGift gift);
	/**
	 * 删除礼品
	 * @param gift
	 */
	public void deleteGift(IntegralGift gift);
	/**
	 * 根据礼品编号删除礼品
	 * @param giftId
	 */
	public void deleteGift(String giftId);
	/**
	 * 根据档次获取礼品
	 * @param level
	 * @return
	 */
	public List<IntegralGift> getGifts(String String);
	/**
	 * 根据档次id获取礼品
	 * @param leveId
	 * @return
	 */
	public List<IntegralGift> getGifts(String leveId,String status);
	/**
	 * 根据礼品状态获取礼品
	 * @param status
	 * @return
	 */
	public List<IntegralGift> getGiftsByStatus(String status);
	/**
	 * 获取供应商提供的所有礼品
	 * @param merchant
	 * @return
	 */
	public List<IntegralGift> getGifts(IntegralMerchant merchant);
	/**
	 * 根据供应商id获取供应商提供的所有商品
	 * @param merchantId
	 * @return
	 */
	public List<IntegralGift> getGiftsByMerchantId(String merchantId);
	/**
	 * 根据礼品编号获取礼品
	 * @param giftId
	 * @return
	 */
	public IntegralGift getGift(String giftId);
// TODO level-------------------------------------level----------------------------------------------------
	/**
	 * 添加积档次
	 * @param level
	 */
	public void addLevel(IntegralLevel level);
	/**
	 * 修改积分档次
	 * @param level
	 */
	public void modifyLevel(IntegralLevel level);
	/**
	 * 删除积分档次 	仅当该档次没有对应礼品时方可删除
	 * @param level
	 */
	public void deleteLevel(IntegralLevel level);
	/**
	 * 根据档次id删除档次		仅当该档次没有对应礼品时方可删除
	 * @param levelId
	 */
	public void deleteLevel(String levelId);
	/**
	 * 根据积分获取积分档次
	 * @param point
	 * @return
	 */
	public IntegralLevel getLevel(int point);
	/**
	 * 根据积分档次id获取积分档次
	 * @param levelId
	 * @return
	 */
	public IntegralLevel getLevel(String levelId);
// TODO merchant----------------------------------merchant----------------------------------------------------
	/**
	 * 添加商品供应商
	 * @param merchant
	 */
	public void addMerchant(IntegralMerchant merchant);
	/**
	 * 修改供应商信息
	 * @param merchant
	 */
	public void	modifyMerchant(IntegralMerchant merchant);
	/**
	 * 删除商品供应商	仅当该供应商没有提供可兑换礼品时方可删除
	 * @param merchant
	 */
	public void deleteMerchant(IntegralMerchant merchant);
	/**
	 * 根据供应商id删除商品供应商	仅当该供应商没有提供可兑换礼品时方可删除
	 * @param merchantId
	 */
	public void deleteMerchant(String merchantId);
	/**
	 * 取消供应商供应	仅当该供应商所有商品被下架后方可执行
	 * @param merchant
	 */
	public void unableMerchant(IntegralMerchant merchant);
	/**
	 * 取消供应商供应	仅当该供应商所有商品被下架后方可执行
	 * @param merchantId
	 */
	public void unableMerchant(String merchantId);
	/**
	 * 根据供应商id获取供应商
	 * @param merchantId
	 * @return
	 */
	public IntegralMerchant getMerchant(String merchantId);
	/**
	 * 根据礼品选中供应商
	 * @param gift
	 * @return
	 */
	public IntegralMerchant getMerchant(IntegralGift gift);
	/**
	 * 根据礼品编号选中供应商
	 * @param giftId
	 * @return
	 */
	public IntegralMerchant getMerchantByGiftId(String giftId);
// TODO order-------------------------------------order----------------------------------------------------	
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(IntegralOrder order);
	/**
	 * 修改订单
	 * @param order
	 */
	public void modifyOrder(IntegralOrder order);
	/**
	 * 删除订单
	 * @param order
	 */
	public void deleteOrder(IntegralOrder order);
	/**
	 * 根据订单号删除订单
	 * @param orderId
	 */
	public void deleteOrder(String orderId);
	/**
	 * 根据订单号获取订单
	 * @param orderId
	 * @return
	 */
	public IntegralOrder getOrder(String orderId);
	/**
	 * 根据手机号获取相关订单列表
	 * @param phone
	 * @return
	 */
	public List<IntegralOrder> getOrdersByPhone(String phone);
	/**
	 * 根据用户编号获取相关订单列表
	 * @param userId
	 * @return
	 */
	public List<IntegralOrder> getOrdersByUserId(String userId);
	/**
	 * 根据订单状态选中订单列表
	 * @param status
	 * @return
	 */
	public List<IntegralOrder> getOrderByStatus(String status);
// TODO sms---------------------------------------sms----------------------------------------------------
	/**
	 * 添加备发短信
	 * @param sms
	 */
	public void addSms(IntegralSms sms);
	/**
	 * 根据短信状态筛选短信
	 * @param smsStatus
	 * @return
	 */
	public List<IntegralSms> getSmsList(String smsStatus);
	/**
	 * 根据手机号码筛选短信
	 * @param phone
	 * @return
	 */
	public List<IntegralSms> getSmsListByPhone(String phone);
	/**
	 * 根据手机号码和短信状态筛选短信
	 * @param phone
	 * @param smsStatus
	 * @return
	 */
	public List<IntegralSms> getSmsList(String phone,String smsStatus); 
	/**
	 * 修改短信
	 * @param sms
	 */
	public void modifySms(IntegralSms sms);
	/**
	 * 删除短信
	 * @param sms
	 */
	public void deleteSms(IntegralSms sms);
	/**
	 * 根据短信编号删除短信
	 * @param smsId
	 */
	public void deleteSms(String smsId);
// TODO smsTemplate-------------------------------smsTemplate----------------------------------------------------	
	/**
	 * 添加短信模版
	 * @param template
	 */
	public void addSmsTemplate(IntegralSmsTemplate template);
	/**
	 * 修改短信模版
	 * @param template
	 */
	public void modifySmsTemplate(IntegralSmsTemplate template);
	/**
	 * 删除短信模版	仅当该短信模版未启用时方可删除(未实现)
	 * @param template
	 */
	public void deleteSmsTemplate(IntegralSmsTemplate template);
	/**
	 * 根据短信模版编号删除短信模版	仅当该短信模版未启用时方可删除(未实现)
	 * @param templateId
	 */
	public void deleteSmsTemplate(String templateId);
	/**
	 * 启动短信模版
	 * @param template
	 */
	public void enableSmsTemplate(IntegralSmsTemplate template);
	/**
	 * 根据短信模版编号启动短信模版
	 * @param templateId
	 */
	public void enableSmsTemplate(String templateId);
	/**
	 * 弃用短信模版
	 * @param template
	 */
	public void unableSmsTemplate(IntegralSmsTemplate template);
	/**
	 * 根据短信模版编号弃短信模版
	 * @param templateId
	 */
	public void unableSmsTemplate(String templateId);
	/**
	 * 根据短信模版编号获取短信模版
	 * @param templateId
	 * @return
	 */
	public IntegralSmsTemplate getSmsTemplate(String templateId);
// TODO user--------------------------------------user----------------------------------------------------	
	/**
	 * 用户关注
	 * @param user
	 */
	public void subUser(IntegralUser user);
	/**
	 * 用户关注
	 * @param phone
	 */
	public void subUser(String phone);
	/**
	 * 用户取消关注 
	 * @param user
	 */
	public void unsubUser(IntegralUser user);
	/**
	 * 用户取消关注
	 * @param phone
	 */
	public void unsubUser(String phone);
	/**
	 * 添加用户到黑名单
	 * @param user
	 */
	public void addUser(IntegralUser user);
	/**
	 * 从黑名单中移除用户
	 * @param user
	 */
	public void deleteUser(IntegralUser user);
	/**
	 * 从黑名单中移除用户
	 * @param phone
	 */
	public void deleteUser(String phone);
	/**
	 * 获取用户
	 * @param phone
	 * @return
	 */
	public IntegralUser getUser(String phone);
	/**
	 * 通过手机号获取用户id
	 * @param phone
	 * @return
	 */
	public String getUserId(String phone);
	/**
	 * 取指定区间的用户
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> getUserIds(Integer start,Integer end);
	/**
	 * 获取指定用户的总积分
	 * @param userId
	 * @return
	 */
	public Integer getUserPoint(String userId);
	/**
	 * 获取指定用户的手机号
	 * @param userId
	 * @return
	 */
	public String  getUserPhone(String userId);
	
	/**
	 * 根据用户手机号获取用户积分最高的一个账号
	 */
	public String getUserIdByPhone(String phone);
}
