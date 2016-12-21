package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralMerchant;

public interface IntegralMerchantDao {
	/**
	 * 添加商品供应商
	 * @param integralMerchant
	 */
	public void addIntegralMerchant(IntegralMerchant integralMerchant);
	/**
	 * 使用默认值添加商品供应商
	 * @param integralMerchant
	 */
	public void addIntegralMerchantDefault(IntegralMerchant integralMerchant);
	/**
	 * 删除商品供应商
	 * @param integralMerchant
	 */
	public void deleteIntegralMerchant(IntegralMerchant integralMerchant);
	/**
	 * 更新商品供应商信息
	 * @param integralMerchant
	 */
	public void updateIntegralMerchant(IntegralMerchant integralMerchant);
	/**
	 * 根据条件筛选供应商
	 * @param integralMerchant
	 * @return
	 */
	public List<IntegralMerchant> selectIntegralMerchant(IntegralMerchant integralMerchant);
	/**
	 * 根据供应商编号选中供应商
	 * @param id
	 * @return
	 */
	public IntegralMerchant selectIntegralMerchantById(String id);
	/**
	 * 根据供应商编号删除供应商
	 * @param id
	 */
	public void deleteIntegralMerchantById(String id);
}
