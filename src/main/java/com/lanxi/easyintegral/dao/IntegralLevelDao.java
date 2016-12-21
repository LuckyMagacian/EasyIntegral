package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralLevel;

public interface IntegralLevelDao {
	/**
	 * 增加积分档次
	 * @param integralLevel
	 */
	public void addIntegralLevel(IntegralLevel integralLevel); 
	/**
	 * 已默认值添加积分档次
	 * @param integralLevel
	 */
	public void addIntegralLevelDefault(IntegralLevel integralLevel); 
	/**
	 * 删除积分档次
	 * @param integralLevel
	 */
	public void deleteIntegralLevel(IntegralLevel integralLevel); 
	/**
	 * 更新积分档次
	 * @param integralLevel
	 */
	public void updateIntegralLevel(IntegralLevel integralLevel); 
	/**
	 * 根据条件筛选积分档次
	 * @param integralLevel
	 * @return
	 */
	public List<IntegralLevel> selectIntegralLevel(IntegralLevel integralLevel);
	/**
	 * 根据档次编号选中档次
	 * @param id
	 * @return
	 */
	public IntegralLevel selectIntegralLevelById(String id);
	/**
	 * 根据档次id删除档次
	 * @param id
	 */
	public void deleteIntegralLevelById(String id);
	/**
	 * 根据积分选出积分档次
	 * @param point
	 * @return
	 */
	public IntegralLevel selectIntegralLevelByPoint(Integer point);
}
