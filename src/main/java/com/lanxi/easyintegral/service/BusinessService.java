package com.lanxi.easyintegral.service;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.entity.IntegralUser;

public interface BusinessService {
	/**
	 * 每次取用户
	 * @param start	开始位置
	 * @param end	结束位置
	 * @return
	 */
	public List<String> getUserIds(Integer start,Integer end);
	/**
	 * 校验用户是否是黑名单中的用户*(即取消了订阅的用户)
	 * 是黑名单用户返回true
	 * 不是黑名单哟过户返回false
	 * @param userId 实际为手机号码作id
	 * @return
	 */
	public boolean isBlackUser(String userId);
	/**
	 * 校验用户是否是黑名单中的用户*(即取消了订阅的用户)
	 * 是黑名单用户返回true
	 * 不是黑名单哟过户返回false
	 * @param phone 用户手机号码
	 * @return
	 */
	public boolean isBlackPhone(String phone);
	/**
	 * 查询用户是否有未过期的下发的兑换通知短信
	 * 有返回true
	 * 没有返回false
	 * @param phone
	 * @return
	 */
	public boolean haveValidSms(String phone);
	/**
	 * 查询礼品并判断礼品状态是否是可以兑换 礼品存在且可以兑换则返回礼品,否则返回null
	 * 是返回商品
	 * 否返回null
	 * @param giftId
	 * @return
	 */
	public IntegralGift isValidGift(String giftId);
	/**
	 * 根据用户编号获取用户手机号码
	 * @param userId
	 * @return
	 */
	public String  getPhone(String userId);
	/**
	 * 获取用户手机号码
	 * @param user
	 * @return
	 */
	public String  getPhone(IntegralUser user);
	/**
	 * 获取用户积分
	 * @param user
	 * @return
	 */
	public Integer getPoint(IntegralUser user);
	/**
	 * 根据用户id获取用户积分
	 * @param userId
	 * @return
	 */
	public Integer getPoint(String userId);
	/**
	 * 根据积分获取积分档次
	 * @param point
	 * @return
	 */
	public IntegralLevel getLevel(Integer point);
	/**
	 * 根据积分获取积分档次id
	 * @param point
	 * @return
	 */
	public String getLevelId(Integer point);
	/**
	 * 根据积分档次id和礼品状态获取礼品列表
	 * @param level
	 * @return
	 */
	public List<IntegralGift> getGifts(String levelId,String status);
	/**
	 * 根据积分档次id获取有效礼品列表
	 * @param levelId
	 * @return
	 */
	public List<IntegralGift> getGifts(String levelId);
	/**
	 * 根据短信模版编号获取短信模版
	 * @param templateId
	 * @return
	 */
	public IntegralSmsTemplate getSmsTemplate(String templateId);
	/**
	 * 组装备发短信
	 * @param template	短信模版
	 * @param gifts		礼品列表
	 * @param point		积分
	 * @param phone		用户手机号码
	 * @return
	 */
	public IntegralSms makeSms(IntegralSmsTemplate template, List<IntegralGift> gifts, Integer point,String userId ,String phone);
	/**
	 * 扣除用户积分
	 * @param phone
	 * @return
	 */
	public boolean subPoint(IntegralOrder order);
	/**
	 * 根据手机号码获取用户id
	 * @param phone
	 * @return
	 */
	public String getUserId(String phone);
	
}
