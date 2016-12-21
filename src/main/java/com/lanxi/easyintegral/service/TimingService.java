package com.lanxi.easyintegral.service;
/**
 * 本接口包含 定时执行的任务的方法
 * @author 1
 *
 */
public interface TimingService {
	/**
	 * 提醒用户任务
	 */
	public void noticeUser();
	/**
	 * 发送短信任务
	 */
	public void sendSmsList();
	/**
	 * 短信过期任务
	 */
	public void outSms();
	/**
	 * 
	 */
	public void outGift();
}
