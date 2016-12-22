package com.lanxi.easyintegral.JFreport;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.lanxi.easyintegral.service.ConfigServiceImpl;
import com.lanxi.easyintegral.util.AppException;

public class JFBaoWen {
	private JFPoints points;

	public JFPoints getPoints() {
		return points;
	}

	public void setPoints(JFPoints points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "JFBaoWen [points=" + points + "]";
	}
	public Document toDocument(){
		Document document=DocumentHelper.createDocument();
		document.setRootElement(points.toElement());
		document.setXMLEncoding(ConfigServiceImpl.getCharset());
		return document;
	}
	public static JFBaoWen fromDocument(Document document){
		JFBaoWen baoWen=null;
		if(document.getRootElement().getName().trim().equals(JFPoints.NAME)){
			baoWen=new JFBaoWen();
			baoWen.setPoints(JFPoints.fromElement(document.getRootElement()));
		}
		return baoWen;
	}
	public static JFBaoWen fromDocStr(String docStr){
		try {
			return fromDocument(DocumentHelper.parseText(docStr));
		} catch (DocumentException e) {
			throw new AppException("字符串转报文异常",e);
		}
	}
}

