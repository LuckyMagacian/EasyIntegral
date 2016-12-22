package com.lanxi.easyintegral.JFreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.easyintegral.service.ConfigServiceImpl;
import com.lanxi.easyintegral.util.AppException;
import com.lanxi.easyintegral.util.FileUtil;
import com.lanxi.httpsclient.core.ESignature;

public class JFPoints {
	public static final String NAME="points";
	private Original original;	
	private String   sign;
	static{
		try{
			Properties properties=new Properties();
			File  file=FileUtil.getFileOppositeClassPath("client.properties");
			FileInputStream fin=new FileInputStream(file);
			properties.load(fin);
			fin.close();
			properties.setProperty("cafile",FileUtil.getFileOppositeClassPath("jks/client_1.jks").getAbsolutePath());
			FileOutputStream fou=new FileOutputStream(file);
			properties.store(fou,null);
			fou.close();
		}catch (Exception e) {
			throw new AppException("设置LanxiClient配置文件异常",e);
		}
	}
	public Original getOriginal() {
		return original;
	}
	public void setOriginal(Original original) {
		this.original = original;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String sign(){
		setSign(ESignature.sign(original.toElement().asXML(), ConfigServiceImpl.getCharset()));
		return sign;
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.add(original.toElement());
		element.addElement("sign").setText(sign);
		return element;
	}
	public static JFPoints fromElement(Element element){
		JFPoints points=null;
		if(element!=null&&element.getName().trim().equals(NAME)){
			points=new JFPoints();
			points.setOriginal(Original.fromElement(element.element(Original.NAME)));
			points.setSign(element.elementText("sign"));
		}
		return points;
	}
}
