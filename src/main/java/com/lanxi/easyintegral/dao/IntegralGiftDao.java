package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralGift;

public interface IntegralGiftDao {
	/**
	 * 添加礼品
	 * @param gift
	 */
	public void addIntegralGift(IntegralGift gift);
	/**
	 * 采用默认字段添加礼品
	 * @param gift
	 */
	public void addIntegralGiftDefault(IntegralGift gift);
	/**
	 * 删除礼品
	 * @param gift
	 */
	public void deleteIntegralGift(IntegralGift gift);
	/**
	 * 更新礼品
	 * @param gift
	 */
	public void updateIntegralGift(IntegralGift gift);
	/**
	 * 选中根据条件礼品列表
	 * @param gift
	 * @return
	 */
	public List<IntegralGift> selectIntegralGift(IntegralGift gift);
	/**
	 * 根据id选中礼品
	 * @param id
	 * @return
	 */
	public IntegralGift selectIntegralGiftById(String id);
	/**
	 * 根据id删除礼品
	 * @param id
	 */
	public void deleteIntegralGiftById(String id);
}
