package com.lanxi.easyintegral.service;

import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.lanxi.easyintegral.util.AppException;

public class ConfigServiceImpl  {
	private static Logger logger=Logger.getLogger(ConfigServiceImpl.class);
	private static final Properties properties;
	static{
		properties=new Properties();
		try {
			InputStreamReader reader=new InputStreamReader(ConfigServiceImpl.class.getClassLoader().getResourceAsStream("properties/param.properties"), "utf-8");
			properties.load(reader);
		} catch (Exception e) {
			throw new AppException("加载配置文件异常",e);
		}
	}
//	@Override
	public  static String get(String name) {
		return properties.getProperty(name);
	}
	public  static void  addUserIndex(){
		Integer index=Integer.parseInt(properties.getProperty("userIndex"));
		Integer step =Integer.parseInt(properties.getProperty("userCount"));
		logger.info("更新取卡位置:"+(index+step));
		setReadPoint(index+step);
	}
	private  static void setReadPoint(Integer position){
		properties.setProperty("userIndex",position+"");
	}
//	@Override
	public static  String getCharset() {
		return get("sysCharset");
	}
//	@Override
	public static  String getSmsUrl() {
		return get("smsUrl");
	}
//	@Override
	public static  String getGiftUrl() {
		return get("giftUrl");
	}
//	@Override
	public  static String getSmsKey() {
		return get("smsKey");
	}
//	@Override
	public static  String getGiftKey() {
		return get("giftKey");
	}
//	@Override
	public  static Integer getSmsIndate() {
		return Integer.parseInt(get("smsIndate"));
	}
	
	public static Integer getMaxSend(){
		return Integer.parseInt(get("maxSend"));
	}
	
	public static Integer getMaxReply(){
		return Integer.parseInt(get("maxReply"));
	}
	
	public static void resetUserIndex(){
		properties.setProperty("userIndex","1");
		logger.info("重置取卡位置!");
	}
}
