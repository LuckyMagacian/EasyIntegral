package com.lanxi.easyintegral.service;
/**
 * 此接口包含与配置文件相关的方法
 * @author 1
 *
 */
public interface ConfigService {
	/**
	 * 根据属性名称从配置文件中获取配置属性
	 * @param name
	 * @return
	 */
	public String get(String name);
	/**
	 * 获取设定字符集
	 * @return
	 */
	public String getCharset();
	/**
	 * 获取短信发送平台url
	 * @return
	 */
	public String getSmsUrl();
	/**
	 * 获取商品兑换平台url
	 * @return
	 */
	public String getGiftUrl();
	/**
	 * 获取短信签名密钥
	 * @return
	 */
	public String getSmsKey();
	/**
	 * 获取礼品签名密钥
	 * @return
	 */
	public String getGiftKey();
	/**
	 * 获取短信有效期
	 * @return
	 */
	public Integer getSmsIndate();
}
		