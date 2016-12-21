package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralOrder;

public interface IntegralOrderDao {
	/**
	 * 添加订单
	 * @param integralOrder
	 */
	public void addIntegralOrder(IntegralOrder integralOrder);
	/**
	 * 使用默认值添加订单
	 * @param integralOrder
	 */
	public void addIntegralOrderDefault(IntegralOrder integralOrder);
	/**
	 * 删除订单
	 * @param integralOrder
	 */
	public void deleteIntegralOrder(IntegralOrder integralOrder);
	/**
	 * 更新订单
	 * @param integralOrder
	 */
	public void updateIntegralOrder(IntegralOrder integralOrder);
	/**
	 * 根据条件选中订单
	 * @param integralOrder
	 * @return
	 */
	public List<IntegralOrder> selectIntegralOrder(IntegralOrder integralOrder);
	/**
	 * 根据订单号选中订单
	 * @param id
	 * @return
	 */
	 public IntegralOrder selectIntegralOrderById(String id);
	 /**
	  * 根据订单号删除订单
	  * @param id
	  */
	 public void deleteIntegralOrderById(String id);
}
