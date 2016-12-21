package com.lanxi.easyintegral.test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.annotation.XmlTransient;

import org.junit.Test;

import com.lanxi.easyintegral.dao.IntegralSmsTemplateDao;
import com.lanxi.easyintegral.entity.IntegralGift;
import com.lanxi.easyintegral.entity.IntegralLevel;
import com.lanxi.easyintegral.entity.IntegralMerchant;
import com.lanxi.easyintegral.entity.IntegralOrder;
import com.lanxi.easyintegral.entity.IntegralSms;
import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.entity.IntegralUser;
import com.lanxi.easyintegral.util.FileUtil;
import com.lanxi.easyintegral.util.SqlUtil;

public class TestUtil {
	
	@Test
	public void makeSql() throws Exception{
		File	   file =FileUtil.getFileOppositeClassPath("../../target/classes/com/lanxi/easyintegral/");
		List<File> files=FileUtil.getFileListOppositeClassPath("../../target/classes/com/lanxi/easyintegral/entity",".class");
		URLClassLoader loader=new URLClassLoader(new URL[]{file.toURL()});
		URLClassLoader load=new URLClassLoader(new URL[]{new URL("fil:E:\\workspace\\EasyIntegral\\target\\classes\\com\\lanxi\\easyintegral\\entity")});
		System.out.println(load);
		System.out.println(load.loadClass("IngegralGift"));
//		
//		for(File each:files){
//			System.out.println(loader.loadClass(each.getName().substring(0, each.getName().length()-6)));
//		}
	}
	@Test
	public void make(){
		Class<?>[] classes=new Class<?>[]{IntegralGift.class,IntegralLevel.class,IntegralMerchant.class,IntegralOrder.class,IntegralSms.class,IntegralSmsTemplate.class,IntegralUser.class};
		Scanner input=new Scanner(System.in);
		String temp=null;
		for(Class<?> each:classes){
			SqlUtil.createMapperFile(each, toLine(each.getSimpleName()));
//			SqlUtil.createAll(each,toLine(each.getSimpleName()));
//			System.out.println(SqlUtil.createMethodName(each).toString().replaceAll(",",""));
//			temp=input.nextLine();
			if(temp!=null&&temp.trim().equals("exit"))
				return;
		}
	}
	
	private String toLine(String string){
		StringBuffer temp=new StringBuffer();
		boolean flag=true;
		for(char each:string.toCharArray()){
			if(each>='A'&&each<='Z'){
				if(flag){
					flag=false;
					temp.append((char)(each+32));
					continue;
				}
				temp.append("_").append((char)(each+32));
			}
			else{
				temp.append(each);
			}
		}
		return temp.toString();
	}
}
class MyLoader extends ClassLoader{
	public Class<?> defineClass(String name,byte[] bytes){
			return name==null?defineClass(null,bytes,0,bytes.length):defineClass(name, bytes, 0, bytes.length);
	}
}
