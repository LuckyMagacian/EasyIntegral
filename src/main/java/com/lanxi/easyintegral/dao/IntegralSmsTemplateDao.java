package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralSmsTemplate;

public interface IntegralSmsTemplateDao {
	/**
	 * 增加短信模版
	 * @param integralSmsTemplate
	 */
	public void addIntegralSmsTemplate(IntegralSmsTemplate integralSmsTemplate);
	/**
	 * 以默认值增加短信模版
	 * @param integralSmsTemplate
	 */
	public void addIntegralSmsTemplateDefault(IntegralSmsTemplate integralSmsTemplate);
	/**
	 * 删除短信模版
	 * @param integralSmsTemplate
	 */
	public void deleteIntegralSmsTemplate(IntegralSmsTemplate integralSmsTemplate);
	/**
	 * 更新短信模版
	 * @param integralSmsTemplate
	 */
	public void updateIntegralSmsTemplate(IntegralSmsTemplate integralSmsTemplate);
	/**
	 * 根据条件筛选短信模版
	 * @param integralSmsTemplate
	 * @return
	 */
	public List<IntegralSmsTemplate> selectIntegralSmsTemplate(IntegralSmsTemplate integralSmsTemplate);
	/**
	 * 根据短信模版编号筛选短信模版
	 * @param id
	 * @return
	 */
	public IntegralSmsTemplate selectIntegralSmsTemplateById(String id);
	/**
	 * 根据短信模版编号删除短信模版
	 * @param id
	 */
	public void deleteIntegralSmsTemplateById(String id);
}
