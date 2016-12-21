package com.lanxi.easyintegral.dao;

import java.util.List;

import com.lanxi.easyintegral.entity.IntegralUser;

public interface IntegralUserDao {
	/**
	 * 添加用户
	 * @param integralUser
	 */
	public void addIntegralUser(IntegralUser integralUser);
	/**
	 * 以默认值添加用户
	 * @param integralUser
	 */
	public void addIntegralUserDefault(IntegralUser integralUser);
	/**
	 * 删除用户
	 * @param integralUser
	 */
	public void deleteIntegralUser(IntegralUser integralUser);
	/**
	 * 更新用户信息
	 * @param integralUser
	 */
	public void updateIntegralUser(IntegralUser integralUser);
	/**
	 * 根据条件选中用户
	 * @param integralUser
	 * @return
	 */
	public List<IntegralUser> selectIntegralUser(IntegralUser integralUser);
	/**
	 * 通过用户编号选中用户
	 * @param id
	 * @return
	 */
	public IntegralUser selectIntegralUserById(String id);
	/**
	 * 通过用户编号删除用户
	 * @param id
	 */
	public void deleteIntegralUserById(String id);
}
