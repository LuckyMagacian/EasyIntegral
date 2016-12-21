package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralSms;

public interface IntegralSmsDao {
	/**
	 * 添加短信
	 * @param integralSms
	 */
	public void addIntegralSms(IntegralSms integralSms);
	/**
	 * 以默认值添加短信
	 * @param integralSms
	 */
	public void addIntegralSmsDefault(IntegralSms integralSms);
	/**
	 * 删除短信
	 * @param integralSms
	 */
	public void deleteIntegralSms(IntegralSms integralSms);
	/**
	 * 更新短信
	 * @param integralSms
	 */
	public void updateIntegralSms(IntegralSms integralSms);
	/**
	 * 根据条件筛选短信
	 * @param integralSms
	 * @return
	 */
	public List<IntegralSms> selectIntegralSms(IntegralSms integralSms);
	/**
	 * 根据短息编号选中短信
	 * @param id
	 * @return
	 */
	public IntegralSms selectIntegralSmsById(String id);
	/**
	 * 根据短信编号删除短信
	 * @param id
	 */
	public void deleteIntegralSmsById(String id);
}
